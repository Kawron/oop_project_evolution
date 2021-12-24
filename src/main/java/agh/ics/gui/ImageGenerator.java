package agh.ics.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapCell;
import javafx.scene.image.Image;

public final class ImageGenerator {

    private static final Image up = new Image("src/main/resources/up.png");
    private static final Image down = new Image("src/main/resources/down.png");
    private static final Image left = new Image("src/main/resources/left.png");
    private static final Image right = new Image("src/main/resources/right.png");
    private static final Image plant = new Image("src/main/resources/plant.png");

    public static Image getImage(IMapCell cell) {
        if (cell.getStrongest() == null && cell.plantExist()) {
            return plant;
        }
        Animal pet = cell.getStrongest();
        return switch (pet.getDirection()) {
            case NORTH, NORTH_EAST -> up;
            case SOUTH, SOUTH_WEST -> down;
            case EAST, EAST_SOUTH -> right;
            case WEST, WEST_NORTH -> left;
        };
    }
}
