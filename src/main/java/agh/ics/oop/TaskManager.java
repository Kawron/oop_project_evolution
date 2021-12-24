package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManager implements ITaskManager {

    IWorldMap map;
    ISimulationEngine engine;
    HashMap<Vector2d, List<Animal>> animals;
    List<Vector2d> validPositions;
    HashMap<Vector2d, IMapCell> cells;
    Stack<Animal> animalStack = new Stack<>();
    Stack<Integer> geneStack = new Stack<>();

    public TaskManager(IWorldMap map, ISimulationEngine engine) {
        this.map = map;
        this.engine = engine;
        this.cells = map.getCells();
    }

    private void getData() {
        animals = map.getAnimals();
        validPositions = animals.entrySet()
                .stream()
                .filter(x -> x.getValue().size() > 0)
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public void breedAnimals() {
        getData();

        for (Vector2d position : validPositions) {
            cells.get(position).breed();
        }
    }

    public void buryAnimals() {
        getData();

        for (Vector2d position : validPositions) {
            IMapCell cell = cells.get(position);
            cells.get(position).buryAnimals();
        }
    }

    public void feedAnimals() {
        getData();

        for (Vector2d position : validPositions) {
            IMapCell cell = cells.get(position);
            cells.get(position).eatPlant();
        }
    }

    public void moveAnimals() {
        int gene;
        MapDirection direction;
        Vector2d position;

        getData();

        for (Vector2d cellPosition : validPositions) {
            for (Animal pet : animals.get(cellPosition)) {
                gene = pet.getRandomGene();
                direction = pet.getDirection();
                position = pet.getPosition();

                switch (gene) {
                    case 0:
                        if (map.canMove(position.add(direction.toUnitVector()))) {
                            animalStack.push(pet);
                            geneStack.push(gene);
                        }
                        break;
                    case 4:
                        if (map.canMove(position.subtract(direction.toUnitVector()))) {
                            animalStack.push(pet);
                            geneStack.push(gene);
                        }
                        break;
                    default: animalStack.push(pet); geneStack.push(gene);

                }
            }
        }
        while (!animalStack.isEmpty()) {
            gene = geneStack.pop();
            animalStack.pop().move(gene);
        }
    }
}
