package agh.ics.oop;

import agh.ics.gui.App;

import java.util.Random;

public class SimulationEngine implements ISimulationEngine{

    IWorldMap map;
    App gui;
    final ITaskManager manager;
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
        /*
        1.Delete dead animals
        2.Move animals
        3.Eating
        4.Breeding
        5.Plant plants
         */
        long startTime;
        long endTime;

        while (map.getAnimals().size() > 0) {
            while (gui.shouldIWork(map)) {
                try {
                    Thread.sleep(100);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            try {
                Thread.sleep(16);
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
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
//        System.out.println(day);
    }

    private Vector2d randomPosition() {
        int x = rand.nextInt(map.getWidth());
        int y = rand.nextInt(map.getHeight());
        if (x < 0 || y < 0) System.out.println("XD?");
        return new Vector2d(x, y);
    }
}
