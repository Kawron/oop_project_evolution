package agh.ics.gui;

import agh.ics.oop.IMapCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GuiElementBox {

    VBox vbox;

    public GuiElementBox(IMapCell cell) {
        ImageView view = new ImageView(ImageGenerator.getImage(cell));
        view.setFitWidth(20);
        view.setFitHeight(20);
        vbox = new VBox();
        vbox.getChildren().add(view);
    }
}
