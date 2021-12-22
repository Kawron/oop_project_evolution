package agh.ics.oop;

public interface IMapCell {

    void breed();

    void eatPlant();

    void buryAnimals();

    void animalEnteredCell(Animal animal);

    void animalLeftCell(Animal animal);

    boolean isEmpty();
}
