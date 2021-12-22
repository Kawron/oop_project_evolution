package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

public class MapCell{
    final Vector2d position;
    public List<Animal> animals = new ArrayList<>();
    private Boolean plantExist = true;
    int plantEnergy = 5;
    final int energyToBreed;

    public MapCell(Vector2d position){
        this.energyToBreed = 0;
        this.position = position;
    }

//    Jeżeli jest więcej niż dwoje zwierząt na jednej pozycji, to rozmnażają się te, które mają największą energię.
    public Animal Breed() {
        // tutaj może coś poprawić? żeby usunąc te fory
        // find first pet
        Animal strongParent = null;
        int maximum = energyToBreed;
        for (Animal value : animals) {
            if (value.getEnergy() > maximum) {
                maximum = value.getEnergy();
                strongParent = value;
            }
        }
        // find second pet
        Animal weakParent = null;
        maximum = energyToBreed;
        for (Animal animal : animals) {
            if (animal.getEnergy() > maximum && !animal.equals(strongParent)) {
                maximum = animal.getEnergy();
                weakParent = animal;
            }
        }

        if (strongParent == null || weakParent == null) {
            return null;
        }

        int weakRatio = Math.floorDiv(weakParent.getEnergy()*100,weakParent.getEnergy() + strongParent.getEnergy());
        int strongRatio = 100 - weakRatio;

        Random rand = new Random();
        boolean side = rand.nextBoolean();

        int childEnergy = Math.floorDiv(weakParent.getEnergy() + strongParent.getEnergy(),4);

        List<Integer> genes = strongParent.giveGenes(strongRatio, side, true);
        genes.addAll(weakParent.giveGenes(weakRatio, !side, false));
        Collections.sort(genes);

        return new Animal(position, genes, childEnergy);
    }

    public void eatPlant() {
        if (!plantExist) return;

        List<Integer> energyLevels = animals.stream().map(Animal::getEnergy).collect(Collectors.toList());
        int maximum = Collections.max(energyLevels);
        int matches = (int) energyLevels.stream().filter(v -> v == maximum).count();

        for (Animal pet : animals) {
            if (pet.getEnergy() == maximum) {
                pet.eatPlant(Math.floorDiv(plantEnergy, matches));
            }
        }
        plantExist = !plantExist;
    }

}
