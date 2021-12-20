package agh.ics.oop;

import java.util.Arrays;
import java.util.Random;

public class Animal {
    private Vector2d position;
    private int energy;
    private MapDirection direction;
    //map
    //observer
    //energy cap
    public int[] genes = new int[32];

    public Animal(){
        this.energy = 2;

        // randomize genes
        for (int i = 0; i < 32; i++){
            int rand = (int) (Math.random() * 8);
            genes[i] = rand;
        }
        Arrays.sort(genes);

        for (int i = 0; i < 32; i++) {
            System.out.println(genes[i]);
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
}
