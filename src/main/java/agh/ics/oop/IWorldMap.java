package agh.ics.oop;

import java.util.HashMap;
import java.util.List;

public interface IWorldMap extends IMoveObserver{

    void placeAnimal(Animal pet);

    boolean canMove(Vector2d position);

    void animalDied(Animal animal);

    void animalBorn(Animal animal);

    List<Animal> getAnimals();

    HashMap<Vector2d, IMapCell> getCells();

    void putPlants();

    int getWidth();

    int getHeight();
}