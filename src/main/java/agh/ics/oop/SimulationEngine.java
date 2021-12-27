package agh.ics.oop;

import agh.ics.gui.App;

import java.util.Random;

public class SimulationEngine implements ISimulationEngine{

    IWorldMap map;
    App gui;
    final ITaskManager manager;
    private boolean flag = true;
    private Random rand = new Random();
    private int startingEnergy = 100;

    public SimulationEngine(int numOfAnimals, IWorldMap map, App gui) {
        this.gui = gui;
        this.map = map;
        this.manager = new TaskManager(map, this);
        for (int i = 0; i < numOfAnimals; i++) {
            Animal pet = new Animal(randomPosition(), null, startingEnergy, map);
            map.placeAnimal(pet);
        }
    }

    public void run() {
        int day = 0;

        long startTime;
        long endTime;

        while (map.getAnimals().size() > 0) {
            synchronized(this) {
                while(!flag) {
                    try {
                        wait();
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            try {
                Thread.sleep(100);
                startTime = System.nanoTime();
                manager.buryAnimals();
                manager.moveAnimals();
                manager.feedAnimals();
                manager.breedAnimals();
                map.putPlants();
                day++;
                endTime = System.nanoTime();
//            System.out.println(endTime-startTime);
//                System.out.println(day);
//                System.out.println(map.getAnimals().size());
                // jak ktoś chce to można day % x = 0 i jest co x dni
                gui.renderNextDay(map);
                gui.updateData();
                map.nextDay();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
//        System.out.println(day);
    }

    public void stopResume() {
        synchronized(this){
            flag = !flag;
            notifyAll();
        }
    }

    private Vector2d randomPosition() {
        int x = rand.nextInt(map.getWidth());
        int y = rand.nextInt(map.getHeight());
        return new Vector2d(x, y);
    }
}
