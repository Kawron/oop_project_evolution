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
                Thread.sleep(300);
                manager.buryAnimals();
                manager.moveAnimals();
                manager.feedAnimals();
                manager.breedAnimals();

                map.putPlants();
                map.sendData();

                gui.updateData();
                gui.updateStatistic(map);
                gui.renderNextDay(map);

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
