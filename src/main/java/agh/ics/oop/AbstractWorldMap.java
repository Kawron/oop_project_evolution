package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

abstract public class AbstractWorldMap implements IWorldMap {

    protected int width;
    protected int height;
    protected int day;
    protected Random rand = new Random();
    protected StatisticEngine statEngine;

    protected int jungleRatio;
    protected int jungleLength;
    protected Vector2d jungleLeftCorner;
    protected Vector2d jungleRightCorner;

    protected int numOfPlants = 0;

    protected List<Animal> animals = new ArrayList<>();
    protected HashMap<Vector2d, IMapCell> cells = new HashMap<>();

    public AbstractWorldMap(int width, int height, int jungleRatio, StatisticEngine statEngine) {
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
        this.statEngine = statEngine;
        calculateJungleArea();
        insertCells();
    }

    private void insertCells() {
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Vector2d key = new Vector2d(x,y);
                IMapCell value;
                if (key.follows(jungleLeftCorner) && key.precedes(jungleRightCorner)) {
                    value = new MapCell(key, this, true);
                }
                else {
                    value = new MapCell(key, this, false);
                }
                cells.put(key, value);
            }
        }
    }

    private void calculateJungleArea() {
        double mapArea = width*height;
        this.jungleLength = (int) Math.floor(Math.sqrt(mapArea/jungleRatio));
        int jungleCornerX = Math.floorDiv(this.width - this.jungleLength, 2);
        int jungleCornerY = Math.floorDiv(this.height - this.jungleLength, 2);
        this.jungleLeftCorner = new Vector2d(jungleCornerX, jungleCornerY);
        this.jungleRightCorner = jungleLeftCorner.add(new Vector2d(jungleLength-1, jungleLength-1));
    }

    public void placeAnimal(Animal pet) {
        Vector2d position = pet.getPosition();
        cells.get(position).animalEnteredCell(pet);
        animals.add(pet);
        pet.addObserver(this);
    }

    public void removeAnimal(Animal pet) {
        animals.remove(pet);
        pet.removeObserver(this);
        statEngine.updateLifeSpan(pet);
    }

    public void nextDay() {
        day++;
    }

    abstract public void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition);

    abstract public boolean canMove(Vector2d position);

    private Vector2d randomPosition(int widthBound, int heightBound) {
        int x = rand.nextInt(widthBound);
        int y = rand.nextInt(heightBound);
        return new Vector2d(x, y);
    }

    public void putPlants() {
        Vector2d junglePlantPos;
        Vector2d stepPlantPos;

        boolean jungleFlag = false;
        boolean stepFlag = false;

        int cnt = 0;
        while (cnt < jungleLength * jungleLength) {
            junglePlantPos = jungleLeftCorner.add(randomPosition(jungleLength, jungleLength));
            if (!cells.get(junglePlantPos).plantExist()) {
                cells.get(junglePlantPos).putPlant();
                numOfPlants++;
                jungleFlag = true;
                break;
            }
            cnt++;
        }
        cnt = 0;
        while (cnt < width*height) {
            stepPlantPos = randomPosition(width, height);
            if (!cells.get(stepPlantPos).plantExist()) {
                cells.get(stepPlantPos).putPlant();
                numOfPlants++;
                stepFlag = true;
                break;
            }
            cnt++;
        }
        if (jungleFlag == false) {
            for (IMapCell cell : cells.values()) {
                if (cell.isJungle() && !cell.plantExist()) {
                    numOfPlants++;
                }
            }
        }
        if (stepFlag == false) {
            for (IMapCell cell : cells.values()) {
                if (!cell.isJungle() && !cell.plantExist()) {
                    numOfPlants++;
                }
            }
        }
    }


    public void plantEaten() {
        numOfPlants--;
    }

    public void sendData() {
        statEngine.updateData(this);
    }
    // GETTERS

    public List<Animal> getAnimals() {
        return animals;
    }

    public HashMap<Vector2d, IMapCell> getCells() {
        return cells;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDay() {
        return day;
    }

    public int getNumOfPlants() {
        return numOfPlants;
    }
}