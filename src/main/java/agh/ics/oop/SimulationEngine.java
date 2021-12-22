package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SimulationEngine {

    BorderMap map = new BorderMap(2, 2, 2);
    private Random rand = new Random();
    private List<Animal> animals = new ArrayList<>();
    private int startingEnergy = 10;
    final List<IMapCell> cells = map.getAllCells();

    public SimulationEngine(int numOfAnimals) {
        for (int i = 0; i < numOfAnimals; i++) {
            Animal pet = new Animal(randomPosition(), null, startingEnergy);
            animals.add(pet);
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
        while (day < 1000) {
            List<IMapCell> populatedCells = cells.stream().filter(iMapCell -> !iMapCell.isEmpty()).collect(Collectors.toList());
            for (IMapCell cell : populatedCells) {
                cell.buryAnimals();
                System.out.println("o kurwa");
            }
            moveEveryAnimal();
            for (IMapCell cell : populatedCells) {
                cell.eatPlant();
                cell.breed();
            }
            day++;
        }
        System.out.println(animals.size());
    }

    private void moveEveryAnimal() {
        int gene;
        MapDirection direction;
        Vector2d position;

        for (Animal pet : animals) {
            gene = pet.getRandomGene();
            direction = pet.getDirection();
            position = pet.getPosition();
            // poprawić switcha?
            switch (gene) {
                case 0 -> {
                    if (map.canMove(position.add(direction.toUnitVector()))) {
                        pet.move(gene);
                    }
                }
                case 4 -> {
                    if (map.canMove(position.subtract(direction.toUnitVector()))) {
                        pet.move(gene);
                    }
                }
                default -> pet.move(gene);
            }
        }
    }

    private Vector2d randomPosition() {
        int x = rand.nextInt(map.width+1);
        int y = rand.nextInt(map.height+1);
        return new Vector2d(1, 1);
    }
}
