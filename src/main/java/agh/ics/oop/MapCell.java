package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

public class MapCell implements IMapCell {
    final Vector2d position;
    final BorderMap map;
    public List<Animal> animals = new ArrayList<>();
    private Stack<Animal> animalsToRemove = new Stack<>();

    private Boolean plantExist = false;
    final int plantEnergy = 15;
    final int energyToBreed = 100;

    public MapCell(Vector2d position, BorderMap map){
        this.position = position;
        this.map = map;
    }

    public void animalEnteredCell(Animal pet) {
        animals.add(pet);
    }

    public void animalLeftCell(Animal pet) {
        animals.remove(pet);
    }

    public void breed() {
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
            return;
        }

        int weakRatio = Math.floorDiv(weakParent.getEnergy()*100,weakParent.getEnergy() + strongParent.getEnergy());
        int strongRatio = 100 - weakRatio;

        Random rand = new Random();
        boolean side = rand.nextBoolean();

        int childEnergy = Math.floorDiv(weakParent.getEnergy() + strongParent.getEnergy(),4);

        List<Integer> genes = strongParent.giveGenes(strongRatio, side, true);
        List<Integer> weakGenes = weakParent.giveGenes(weakRatio, !side, false);
        genes.addAll(weakGenes);
        Collections.sort(genes);

        Animal child = new Animal(position, genes, childEnergy);
        animals.add(child);
        map.animalBorn(child);
    }

    public void eatPlant() {
        if (!plantExist || animals.size() == 0) return;

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

    public void buryAnimals() {
        for (Animal pet : animals) {
            if (pet.getEnergy() < 0) {
                animalsToRemove.push(pet);
            }
        }
        Animal petToRemove;
        while (!animalsToRemove.isEmpty()) {
            petToRemove = animalsToRemove.pop();
            petToRemove.removeObserver(map);
            animals.remove(petToRemove);
            map.animalDied(petToRemove);
        }
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean plantExist() {
        return plantExist;
    }

    public void putPlant() {
        plantExist = !plantExist;
    }

    public Animal getStrongest() {
        Animal pet = null;
        int maximum = -1;
        for (Animal value : animals) {
            if (value.getEnergy() > maximum) {
                maximum = value.getEnergy();
                pet = value;
            }
        }
        return pet;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public boolean hasAnimals() {
        return animals.size() > 0;
    }
}
