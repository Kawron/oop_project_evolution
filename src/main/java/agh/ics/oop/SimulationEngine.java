package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SimulationEngine implements ISimulationEngine{

    BorderMap map = new BorderMap(2000, 2000, 10);
    private Random rand = new Random();
    private int startingEnergy = 10;
    final ITaskManager manager = new TaskManager(map, this);

    public SimulationEngine(int numOfAnimals) {
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
        while (day < 1_000) {
            startTime = System.nanoTime();
//            System.out.println(day);
            manager.buryAnimals();
//            System.out.println("Done bury");
            manager.moveAnimals();
//            System.out.println("Done move");
            manager.feedAnimals();
//            System.out.println("Done feed");
            manager.breedAnimals();
//            System.out.println("Done breed");
            map.putPlants();
//            System.out.println("Done planting");
//            System.out.println("-----------------------");
            day++;
//            endTime = System.nanoTime();
//            System.out.println(endTime-startTime);
        }
        System.out.println("Done");
    }

    private Vector2d randomPosition() {
        int x = rand.nextInt(map.width);
        int y = rand.nextInt(map.height);
        if (x < 0 || y < 0) System.out.println("XD?");
        return new Vector2d(x, y);
    }
}
