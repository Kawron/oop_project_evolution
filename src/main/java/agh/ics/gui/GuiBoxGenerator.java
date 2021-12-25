package agh.ics.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapCell;
import agh.ics.oop.IWorldMap;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.FileInputStream;

public class GuiBoxGenerator {

    VBox vbox;
    private Image up;
    private Image down;
    private Image left;
    private Image right;
    private Image plant;

    public GuiBoxGenerator() {
        try {
            up = new Image(new FileInputStream("src/main/resources/up.png"));
            down = new Image(new FileInputStream("src/main/resources/down.png"));
            left = new Image(new FileInputStream("src/main/resources/left.png"));
            right = new Image(new FileInputStream("src/main/resources/right.png"));
            plant = new Image(new FileInputStream("src/main/resources/grass.png"));
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private Image getImage(IMapCell cell) {
        Animal pet = cell.getStrongest();
        return switch (pet.getDirection()) {
            case NORTH, NORTH_EAST -> up;
            case SOUTH, SOUTH_WEST -> down;
            case EAST, EAST_SOUTH -> right;
            case WEST, WEST_NORTH -> left;
        };
    }

    public VBox getVBox(IMapCell cell) {
        if (cell.getStrongest() == null && cell.plantExist()) {
            ImageView view = new ImageView(plant);
            view.setFitWidth(14);
            view.setFitHeight(14);
            return vbox = new VBox(view);
        }
        else if (cell.getStrongest() == null) {
            return vbox = new VBox();
        }
        ImageView view = new ImageView(this.getImage(cell));
        view.setFitWidth(14);
        view.setFitHeight(14);

        Label energyLevel = new Label(String.valueOf(cell.getStrongest().getEnergy()));
        energyLevel.setFont(Font.font("Cambria", 10));
        return vbox = new VBox(view, energyLevel);
    }
}
