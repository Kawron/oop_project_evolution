package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SimulationEngine implements ISimulationEngine{

    IWorldMap map;
    final ITaskManager manager;
    private Random rand = new Random();
    private int startingEnergy = 10000;

    public SimulationEngine(int numOfAnimals, IWorldMap map) {
        this.map = map;
        this.manager = new TaskManager(map, this);
        for (int i = 0; i < numOfAnimals; i++) {
            Animal pet = new Animal(randomPosition(), null, startingEnergy);
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
        while (day < 900) {
            startTime = System.nanoTime();
            manager.buryAnimals();
            manager.moveAnimals();
            manager.feedAnimals();
            manager.breedAnimals();
            map.putPlants();
            day++;
            endTime = System.nanoTime();
            System.out.println(endTime-startTime);
            System.out.println(day);
        }
        System.out.println("Done");
    }

    private Vector2d randomPosition() {
        int x = rand.nextInt(map.getWidth());
        int y = rand.nextInt(map.getHeight());
        if (x < 0 || y < 0) System.out.println("XD?");
        return new Vector2d(x, y);
    }
}
