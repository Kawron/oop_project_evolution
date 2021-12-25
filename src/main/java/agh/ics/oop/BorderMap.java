package agh.ics.oop;

import java.util.*;

public class BorderMap implements IWorldMap {

    final int width;
    final int height;
    final int jungleRatio;
    final int jungleWidth;
    final Vector2d jungleCorner;
    final Random rand = new Random();

    private final List<Animal> animals = new ArrayList<>();
    private final HashMap<Vector2d, List<Animal>> animalsOnCells = new HashMap<>();
    private final HashMap<Vector2d, IMapCell> cells = new HashMap<>();
    private final HashMap<Vector2d, IMapCell> cellsWithPlants = new HashMap<>();

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
        List<Animal> petList = animalsOnCells.computeIfAbsent(position, k -> new ArrayList<>());
        petList.add(pet);

        animals.add(pet);

        pet.addObserver(this);
    }

    public void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition) {
        List<Animal> petList = animalsOnCells.get(oldPosition);
        petList.remove(pet);

        petList = animalsOnCells.computeIfAbsent(newPosition, k -> new ArrayList<>());
        petList.add(pet);
    }

    public boolean canMove(Vector2d position) {
        return position.x < width && position.x >= 0 && position.y < height && position.y >= 0;
    }

    public void animalDied(IMapCell cell, Animal pet) {
        animalsOnCells.get(cell.getPosition()).remove(pet);
        animals.remove(pet);
    }

    public void animalBorn(IMapCell cell, Animal pet) {
        animalsOnCells.get(cell.getPosition()).add(pet);
        animals.add(pet);
    }

    public List<Animal> getAnimalsOnCell(IMapCell cell) {
        return animalsOnCells.get(cell.getPosition());
    }

    public HashMap<Vector2d, IMapCell> getCells() {
        return cells;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // to wg powinoo zostać poprawione bo działa słabo
    public void putPlants() {
        Vector2d junglePlantPos;
        Vector2d stepPlantPos;

        int cnt = 0;
        while (cnt < jungleWidth*jungleWidth) {
            junglePlantPos = jungleCorner.add(randomPosition(jungleWidth, jungleWidth));
            if (!cells.get(junglePlantPos).plantExist()) {
                cells.get(junglePlantPos).putPlant();
                break;
            }
            cnt++;
        }
        cnt = 0;
        while (cnt < width*height) {
            stepPlantPos = randomPosition(width, height);
            if (!cells.get(stepPlantPos).plantExist()) {
                cells.get(stepPlantPos).putPlant();
                break;
            }
            cnt++;
        }
    }

    private Vector2d randomPosition(int widthBound, int heightBound) {
        int x = rand.nextInt(widthBound);
        int y = rand.nextInt(heightBound);
        return new Vector2d(x, y);
    }
}
