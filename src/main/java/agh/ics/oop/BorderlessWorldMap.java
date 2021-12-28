package agh.ics.oop;

import java.util.*;

public class BorderlessWorldMap extends AbstractWorldMap {

    public BorderlessWorldMap(int width, int height, int jungleRatio, StatisticEngine statEngine){
        super(width, height, jungleRatio, statEngine);
    }

    public void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition) {
        newPosition = newPosition.getModuloVector(width, height);

        IMapCell oldCell = cells.get(oldPosition);
        IMapCell newCell = cells.get(newPosition);

        oldCell.animalLeftCell(pet);
        newCell.animalEnteredCell(pet);
    }

    public boolean canMove(Vector2d position) {
        return true;
    }
}
