package agh.ics.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapCell;
import agh.ics.oop.IWorldMap;
import agh.ics.oop.OptionParser;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javax.swing.text.html.Option;
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
            plant = new Image(new FileInputStream("src/main/resources/watermelon.png"));
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

    private Color getColor(IMapCell cell) {
        if (cell.isJungle()) return Color.LAWNGREEN;
        return Color.LIGHTBLUE;
    }

    public StackPane getView(IMapCell cell, int width, int height) {
        Animal pet = cell.getStrongest();
        if (pet == null && cell.plantExist()) {
            ImageView view = new ImageView(plant);
            view.setFitWidth(width);
            view.setFitHeight(height);
            return new StackPane(new Rectangle(width, height, getColor(cell)), view);
        }
        else if (pet == null) {
            if (cell.isJungle()) return new StackPane(new Rectangle(width, height, getColor(cell)));
            return new StackPane(new Rectangle(width, height, getColor(cell)));
        }
        ImageView view = new ImageView(this.getImage(cell));
        view.setFitWidth(width-4);
        view.setFitHeight(height-4);

        int green = Math.min(255, (255*pet.getEnergy() / OptionParser.startingEnergy));
        System.out.println(green);
        Rectangle energyLevel = new Rectangle(width,4,Color.rgb(255-green,green,0, 1.0));

//        Rectangle energyLevel = new Rectangle(width,4,Color.hsb(120,1.0,0.6, 1.0));

        StackPane stackPane = new StackPane(new Rectangle(width, height, getColor(cell)), new VBox(view, energyLevel));
        return stackPane;
    }
}