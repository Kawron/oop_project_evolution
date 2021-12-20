package agh.ics.oop;

import java.util.ArrayList;

public class MapCell{
    private Vector2d position;
    private ArrayList<Animal> animals;
    private Boolean plantExist;
    private int energyToBreed;

    public MapCell(Vector2d position){
        this.energyToBreed = 10;
        this.position = position;
    }

//    Jeżeli jest więcej niż dwoje zwierząt na jednej pozycji, to rozmnażają się te, które mają największą energię.
    private Animal Breed() {
        // find first pet
        Animal strongerParent = null;
        int maximum = energyToBreed;
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getEnergy() > maximum) {
                maximum = animals.get(i).getEnergy();
                strongerParent = animals.get(i);
            }
        }
        // find second pet
        Animal weakerParent = null;
        maximum = energyToBreed;
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getEnergy() > maximum && animals.get(i).equals(strongerParent)) {
                maximum = animals.get(i).getEnergy();
                weakerParent = animals.get(i);
            }
        }
        if (strongerParent == null || weakerParent == null) {
            return null;
        }
        return null;
    }

}
