package agh.ics.oop;

import java.util.List;

public interface IMapObserver {

    void animalListUpdated(List<Animal> animals, Animal pet);
}
