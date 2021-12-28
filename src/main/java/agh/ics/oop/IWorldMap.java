package agh.ics.oop;

import java.util.HashMap;
import java.util.List;

public interface IWorldMap extends IMoveObserver{

    void placeAnimal(Animal pet);

    void removeAnimal(Animal animal);

    void putPlants();

    boolean canMove(Vector2d position);

    List<Animal> getAnimals();

    HashMap<Vector2d, IMapCell> getCells();

    int getWidth();

    int getHeight();

    void nextDay();

    int getDay();

    void plantEaten();

    int getNumOfPlants();

    void sendData();
}