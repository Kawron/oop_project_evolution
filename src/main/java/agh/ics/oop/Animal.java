package agh.ics.oop;

public class Animal {
    private Vector2d position;
    private int energy;
    private MapDirection direction;
    //map
    //observer
    //energy cap
    private int[] genes;

    public Animal(){
        this.energy = 2;
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }
}
