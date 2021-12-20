package agh.ics.oop;

import java.util.ArrayList;

public class MapCell{
    private Vector2d position;
    private ArrayList<Animal> animals;
    private Boolean plantExist;

    public MapCell(Vector2d position){
        this.position = position;
    }
}
