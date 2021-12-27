package agh.ics.oop;

import java.lang.invoke.VarHandle;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager implements ITaskManager {

    IWorldMap map;
    ISimulationEngine engine;
    List<IMapCell> validCells;
    List<Animal> animals;

    public TaskManager(IWorldMap map, ISimulationEngine engine) {
        this.map = map;
        this.engine = engine;
    }

    private void getValidCells() {
        validCells = map.getCells().values().stream().filter(IMapCell::hasAnimals).collect(Collectors.toList());
    }

    public void breedAnimals() {
        getValidCells();

        for (IMapCell cell : validCells) {
            cell.breed();
        }
    }

    public void buryAnimals() {
        getValidCells();

        for (IMapCell cell : validCells) {
            cell.buryAnimals();
        }
    }

    public void feedAnimals() {
        getValidCells();

        for (IMapCell cell : validCells) {
            cell.eatPlant();
        }
    }

    public void moveAnimals() {
        int gene;
        MapDirection direction;
        Vector2d position;

        animals = map.getAnimals();

        for (Animal pet : animals) {
            gene = pet.getRandomGene();
            direction = pet.getDirection();
            position = pet.getPosition();

            switch (gene) {
                case 0:
                    if (map.canMove(position.add(direction.toUnitVector()))) {
                        pet.move(gene);
                    }
                    break;
                case 4:
                    if (map.canMove(position.add(direction.toUnitVector().opposite()))) {
                        pet.move(gene);
                    }
                    break;
                default: pet.move(gene);
            }
        }
    }
}
