package agh.ics.oop;

import java.util.*;

public class Animal {
    public int deathDay = -1;
    private Vector2d position;
    private int energy;
    private MapDirection direction;
    public IWorldMap map;

    public Animal parent1;
    public Animal parent2;

    public List<Animal> children = new ArrayList<>();

    private List<Integer> genes = new ArrayList<>();
    private List<IMoveObserver> observerList = new ArrayList<>();

    // tutaj się będzie działo
    public Animal(Vector2d position, List<Integer> genes, int energy, IWorldMap map) {
        this.map = map;
        this.energy = energy;
        this.position = position;
        this.direction = MapDirection.NORTH;

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
        int range;
        if (stronger) range = (int) Math.ceil((float) (genes.size() * ratio)/100);
        else range = (int) Math.floor((float) (genes.size() * ratio)/100);

        List<Integer> res;
        if (leftSide) res = genes.subList(0, range);
        else res = genes.subList(genes.size()-range, genes.size());

        energy = Math.floorDiv(3*energy, 4);

        List<Integer> copyOfGenes = new ArrayList<>(res);
        return copyOfGenes;
    }

    public void eatPlant(int plantEnergy) {
        energy += plantEnergy;
    }

    public int getRandomGene() {
        int idx = (int) (Math.random() * 32);
        return genes.get(idx);
    }

    public void move(int gene) {
        Vector2d oldPosition = position;
        switch (gene) {
            case 0 -> {
                position = position.add(direction.toUnitVector());
                position = position.getModuloVector(map.getWidth(), map.getHeight());
                notifyObservers(oldPosition, position);
            }
            case 4 -> {
                position = position.subtract(direction.toUnitVector());
                position = position.getModuloVector(map.getWidth(), map.getHeight());
                notifyObservers(oldPosition, position);
            }
            default -> direction = direction.turnRight(gene);
        }
        energy--;
    }

    public void addObserver(IMoveObserver observer) {
        observerList.add(observer);
    }

    public void removeObserver(IMoveObserver observer) {
        observerList.remove(observer);
    }

    public void notifyObservers(Vector2d oldPosition, Vector2d newPosition) {
        for (IMoveObserver observer : observerList) {
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    public String printGenes() {
        return genes.toString();
    }

    public void addChild(Animal child) {
        children.add(child);
    }

    public void traverseThroughChildren(Set<Animal> descendants) {
        for (Animal child : children) {
            descendants.add(child);
            child.traverseThroughChildren(descendants);
        }
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

    public int countDescendants(Animal pet) {
        Set<Animal> descendants = new TreeSet<>(Comparator.comparing(Animal::hashCode));
        pet.traverseThroughChildren(descendants);
        return descendants.size();
    }

    public int countChildren(Animal pet) {
        return pet.children.size();
    }
}
