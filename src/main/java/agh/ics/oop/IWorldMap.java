package agh.ics.oop;

import java.util.HashMap;
import java.util.List;

public interface IWorldMap extends IMoveObserver{

    void placeAnimal(Animal pet);

    void animalDied(Animal animal);

    void animalBorn(Animal animal);

    void putPlants();

    boolean canMove(Vector2d position);

    List<Animal> getAnimals();

    HashMap<Vector2d, IMapCell> getCells();

    int getWidth();

    int getHeight();

    void nextDay();

    int getDay();

    int countChildren(Animal pet);

    int countDescendants(Animal pet);
}