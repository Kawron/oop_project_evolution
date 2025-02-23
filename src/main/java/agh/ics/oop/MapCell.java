package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

public class MapCell implements IMapCell {
    final Vector2d position;
    final IWorldMap map;
    private Boolean plantExist = false;
    private boolean isJungle;

    public List<Animal> animals = new ArrayList<>();
    private final Stack<Animal> animalsToRemove = new Stack<>();

    final int plantEnergy = 15;
    final int energyToBreed = OptionParser.startingEnergy/2;

    public MapCell(Vector2d position, IWorldMap map, Boolean isJungle){
        this.position = position;
        this.map = map;
        this.isJungle = isJungle;
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

        Animal child = new Animal(position, genes, childEnergy, map);
        strongParent.addChild(child);
        weakParent.addChild(child);
        map.placeAnimal(child);
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
        map.plantEaten();
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
            petToRemove.deathDay = map.getDay();
            animals.remove(petToRemove);
            map.removeAnimal(petToRemove);
        }
    }

    public Vector2d getPosition() {
        return position;
    }

    public boolean plantExist() {
        return plantExist;
    }

    public void putPlant() {
        plantExist = true;
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

    public boolean isJungle() {
        return isJungle;
    }
}
