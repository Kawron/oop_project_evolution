package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

public class MapCell{
    final Vector2d position;
    public List<Animal> animals = new ArrayList<>();
    private Boolean plantExist;
    int plantEnergy = 5;
    final int energyToBreed;

    public MapCell(Vector2d position){
        this.energyToBreed = 0;
        this.position = position;
    }

//    Jeżeli jest więcej niż dwoje zwierząt na jednej pozycji, to rozmnażają się te, które mają największą energię.
    public Animal Breed() {
        // find first pet
        Animal strongParent = null;
        int maximum = energyToBreed;
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getEnergy() > maximum) {
                maximum = animals.get(i).getEnergy();
                strongParent = animals.get(i);
            }
        }
        // find second pet
        Animal weakParent = null;
        maximum = energyToBreed;
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).getEnergy() > maximum && animals.get(i).equals(strongParent)) {
                maximum = animals.get(i).getEnergy();
                weakParent = animals.get(i);
            }
        }

        if (strongParent == null || weakParent == null) {
            return null;
        }

        int weakRatio = Math.floorDiv(weakParent.getEnergy()*100,weakParent.getEnergy() + strongParent.getEnergy());
        int strongRatio = 100 - weakRatio;

        Random rand = new Random();
        boolean side = rand.nextBoolean();

        List<Integer> genes = strongParent.giveGenes(strongRatio, side, true);
        genes.addAll(weakParent.giveGenes(weakRatio, !side, false));
        Collections.sort(genes);

        Animal child = new Animal(position, genes);
        return child;
    }

    public void eatPlant() {
        if (!plantExist) return;

        // czy dwie petle sa szybsze od tworzenia arrayList
        int maximum = Collections.max(animals.stream().map(Animal::getEnergy).collect(Collectors.toList()));
        // to może zmienić bo można to chyba na wiele sposobów zrobić
        int matches = (int) animals.stream().map(Animal::getEnergy).filter(v -> v == maximum).count();
        for (Animal pet:animals) {
            if (pet.getEnergy() == maximum) {
                pet.eatPlant(Math.floorDiv(plantEnergy, matches));
            }
        }
    }

}
