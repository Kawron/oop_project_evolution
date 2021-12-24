package agh.ics.oop;

import java.util.*;

public class BorderMap implements IWorldMap {

    final int width;
    final int height;
    final int jungleRatio;
    final int jungleWidth;
    final Vector2d jungleCorner;
    final Random rand = new Random();

    private final HashMap<Vector2d, List<Animal>> animals = new HashMap<>();
    private final HashMap<Vector2d, IMapCell> cells = new HashMap<>();

    public BorderMap(int width, int height, int jungleRatio){
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;

        double mapArea = width*height;
        this.jungleWidth = (int) Math.floor(Math.sqrt(mapArea/jungleRatio));
        int jungleCornerX = Math.floorDiv(this.width - this.jungleWidth, 2);
        int jungleCornerY = Math.floorDiv(this.height - this.jungleWidth, 2);
        this.jungleCorner = new Vector2d(jungleCornerX, jungleCornerY);

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Vector2d key = new Vector2d(x,y);
                MapCell value = new MapCell(key, this);
                cells.put(key, value);
            }
        }
    }

    public void placeAnimal(Animal pet) {
        Vector2d position = pet.getPosition();
        List<Animal> petList = animals.computeIfAbsent(position, k -> new ArrayList<>());

        petList.add(pet);
        pet.addObserver(this);
    }

    public void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition) {
        List<Animal> petList = animals.get(oldPosition);
        petList.remove(pet);

        petList = animals.computeIfAbsent(newPosition, k -> new ArrayList<>());
        petList.add(pet);
    }

    public boolean canMove(Vector2d position) {
        return position.x < width && position.x >= 0 && position.y < height && position.y >= 0;
    }

    public void animalDied(IMapCell cell, Animal animal) {
        animals.get(cell.getPosition()).remove(animal);
    }

    public void animalBorn(IMapCell cell, Animal animal) {
        animals.get(cell.getPosition()).add(animal);
    }

    public List<Animal> getAnimalsOnCell(IMapCell cell) {
        if (animals.get(cell.getPosition()) == null) System.out.println("kurwa");
        return animals.get(cell.getPosition());
    }

    public HashMap<Vector2d, IMapCell> getCells() {
        return cells;
    }

    public HashMap<Vector2d, List<Animal>> getAnimals() {
        return animals;
    }

    public void putPlants() {
        Vector2d junglePlantPos;
        Vector2d stepPlantPos;

        while (true) {
            junglePlantPos = jungleCorner.add(randomPosition(jungleWidth, jungleWidth));
            if (!cells.get(junglePlantPos).plantExist()) {
                cells.get(junglePlantPos).putPlant();
                break;
            }
        }
        while (true) {
            stepPlantPos = randomPosition(width, height);
            if (!cells.get(stepPlantPos).plantExist()) {
                cells.get(stepPlantPos).putPlant();
                break;
            }
        }
    }

    private Vector2d randomPosition(int widthBound, int heightBound) {
        int x = rand.nextInt(widthBound);
        int y = rand.nextInt(heightBound);
        return new Vector2d(x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
