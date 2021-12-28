package agh.ics.oop;

import java.util.*;

public class WorldMap extends AbstractWorldMap {

    public WorldMap(int width, int height, int jungleRatio, StatisticEngine statEngine) {
        super(width, height, jungleRatio, statEngine);
    }

    public void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition) {
        IMapCell oldCell = cells.get(oldPosition);
        IMapCell newCell = cells.get(newPosition);

        oldCell.animalLeftCell(pet);
        newCell.animalEnteredCell(pet);
    }

    public boolean canMove(Vector2d position) {
        return position.x < width && position.x >= 0 && position.y < height && position.y >= 0;
    }
}
