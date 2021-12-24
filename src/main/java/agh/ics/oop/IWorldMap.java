package agh.ics.oop;

import java.util.HashMap;
import java.util.List;

public interface IWorldMap extends IMoveObserver{

    void placeAnimal(Animal pet);

    boolean canMove(Vector2d position);

    void animalDied(IMapCell cell, Animal animal);

    void animalBorn(IMapCell cell, Animal animal);

    List<Animal> getAnimalsOnCell(IMapCell cell);

    HashMap<Vector2d, List<Animal>> getAnimals();

    HashMap<Vector2d, IMapCell> getCells();
}