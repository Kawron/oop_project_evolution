package agh.ics.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapCell;
import agh.ics.oop.IWorldMap;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.FileInputStream;

public class GuiBoxGenerator {

    Application app;
    VBox vbox;
    private Image up;
    private Image down;
    private Image left;
    private Image right;
    private Image plant;

    public GuiBoxGenerator(Application app) {
        this.app = app;
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

    public StackPane getView(IMapCell cell, int width, int height) {
        if (cell.getStrongest() == null && cell.plantExist()) {
            ImageView view = new ImageView(plant);
            view.setFitWidth(width);
            view.setFitHeight(height);
            return new StackPane(new Rectangle(width, height, Color.GREEN), view);
        }
        else if (cell.getStrongest() == null) {
            if (cell.isJungle()) return new StackPane(new Rectangle(width, height, Color.BROWN));
            return new StackPane(new Rectangle(width, height, Color.GREEN));
        }
        ImageView view = new ImageView(this.getImage(cell));
        view.setFitWidth(width);
        view.setFitHeight(height);

        Label energyLevel = new Label(String.valueOf(cell.getStrongest().getEnergy()));
        energyLevel.setFont(Font.font("Cambria", 10));

        StackPane stackPane = new StackPane(new Rectangle(width, height, Color.BLUE), new VBox(view, energyLevel));
        return stackPane;
    }
}