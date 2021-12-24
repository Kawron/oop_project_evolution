package agh.ics.oop;

public interface IMapCell {

    void breed();

    void eatPlant();

    void buryAnimals();

    Vector2d getPosition();

    boolean plantExist();

    void putPlant();

    Animal getStrongest();
}
