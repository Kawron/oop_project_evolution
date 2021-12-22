package agh.ics.oop;

import java.util.List;

public interface IWorldMap extends IMoveObserver{

    void placeAnimal(Animal pet);

    boolean canMove(Vector2d position);

    void animalDied(MapCell cell, Animal animal);

    void animalBorn(MapCell cell, Animal animal);

    List<IMapCell> getAllCells();

//    void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition);
}