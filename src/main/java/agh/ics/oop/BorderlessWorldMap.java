package agh.ics.oop;

import java.util.*;

public class BorderlessWorldMap extends AbstractWorldMap {

    public BorderlessWorldMap(int width, int height, int jungleRatio){
        super(width, height, jungleRatio);
    }

    public void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition) {
        newPosition = newPosition.getModuloVector(width, height);

        System.out.println(oldPosition);
        IMapCell oldCell = cells.get(oldPosition);
        IMapCell newCell = cells.get(newPosition);

        oldCell.animalLeftCell(pet);
        newCell.animalEnteredCell(pet);
    }

    public boolean canMove(Vector2d position) {
        return true;
    }
}
