package agh.ics.oop;

import java.util.List;

public interface IMapCell {

    void breed();

    void eatPlant();

    void animalEnteredCell(Animal pet);

    void animalLeftCell(Animal pet);

    void buryAnimals();

    Vector2d getPosition();

    boolean plantExist();

    void putPlant();

    Animal getStrongest();

    List<Animal> getAnimals();

    boolean hasAnimals();

    boolean isJungle();

}
