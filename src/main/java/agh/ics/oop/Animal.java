package agh.ics.oop;

import java.util.*;
import java.util.stream.Collectors;

public class Animal {
    private Vector2d position;
    private int energy;
    private MapDirection direction;
    //map
    //observer
    //energy cap
    private List<Integer> genes = new ArrayList<>();

    public Animal(Vector2d position, List<Integer> genes, int energy){
        this.energy = energy;
        this.position = position;


        List<String> upper = List.stream().map(String::toUpperCase).collect(Collectors.toList());
        // randomize genes
        if (genes == null) {
            for (int i = 0; i < 32; i++) {
                int rand = (int) (Math.random() * 8);
                this.genes.add(rand);
            }
            Collections.sort(this.genes);
        }
        else this.genes = genes;
    }

    public List<Integer> giveGenes(int ratio, boolean leftSide, boolean stronger) {
        int range = (genes.size() * ratio)/100;
        if (stronger) range = (int) Math.ceil(range);
        else range = (int) Math.floor(range);

        List<Integer> res;
        if (leftSide) res = genes.subList(0, range);
        else res = genes.subList(genes.size()-range-1, genes.size()-1);

        energy = Math.floorDiv(3*energy, 4);

        return res;
    }

    public void eatPlant(int plantEnergy) {
        energy += plantEnergy;
    }

    public int getRandomGene() {
        int idx = (int) (Math.random() * 32);
        return genes.get(idx);
    }

    public void move(int gene) {
        switch (gene) {
            case 0 -> position.add(direction.toUnitVector());
            case 4 -> position.subtract(direction.toUnitVector());
            default -> direction.turnRight(gene);
        }
    }

    public String printGenes() {
        return genes.toString();
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public int getEnergy() {
        return energy;
    }

    public String toString() {
        return String.format("(%d,%d)",position.x, position.y);
    }
}
