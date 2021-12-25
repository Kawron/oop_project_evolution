package agh.ics.oop;

import java.util.*;

public class BorderlessWorldMap implements IWorldMap {

    final int width;
    final int height;
    final int jungleRatio;
    final int jungleLength;
    final Vector2d jungleLeftCorner;
    final Vector2d jungleRightCorner;
    final Random rand = new Random();

    final List<Animal> animals = new ArrayList<>();
    final HashMap<Vector2d, IMapCell> cells = new HashMap<>();

    public BorderlessWorldMap(int width, int height, int jungleRatio){
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;

        double mapArea = width*height;
        this.jungleLength = (int) Math.floor(Math.sqrt(mapArea/jungleRatio));
        int jungleCornerX = Math.floorDiv(this.width - this.jungleLength, 2);
        int jungleCornerY = Math.floorDiv(this.height - this.jungleLength, 2);
        this.jungleLeftCorner = new Vector2d(jungleCornerX, jungleCornerY);
        this.jungleRightCorner = jungleLeftCorner.add(new Vector2d(jungleLength, jungleLength));

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
        cells.get(position).animalEnteredCell(pet);

        animals.add(pet);
        pet.addObserver(this);
    }

    public void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition) {
        newPosition = newPosition.getModuloVector(width, height);

        System.out.println(oldPosition);
        IMapCell oldCell = cells.get(oldPosition);
        IMapCell newCell = cells.get(newPosition);

        oldCell.animalLeftCell(pet);
        newCell.animalEnteredCell(pet);
    }

    public boolean canMove(Vector2d position) {
        return true;
    }

    public void animalDied(Animal pet) {
        animals.remove(pet);
    }

    public void animalBorn(Animal pet) {
        animals.add(pet);
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
        while (cnt < jungleLength * jungleLength) {
            junglePlantPos = jungleLeftCorner.add(randomPosition(jungleLength, jungleLength));
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

    public Vector2d getJungleLeftCorner() {
        return jungleLeftCorner;
    }

    public Vector2d getJungleRightCorner() {
        return jungleRightCorner;
    }
}
