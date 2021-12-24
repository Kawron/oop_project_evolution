package agh.ics.gui;

import agh.ics.oop.BorderMap;
import agh.ics.oop.ISimulationEngine;
import agh.ics.oop.IWorldMap;
import agh.ics.oop.SimulationEngine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    IWorldMap map = new BorderMap(20,30,2);
    ISimulationEngine engine;

    HBox mainBox;
    GridPane leftMap;
    VBox options;
    GridPane rightMap;

    int maxWidth = 30;
    int maxHeight = 60;

    public void initOptions() {
        map = new BorderMap(20, 50, 2);
        engine = new SimulationEngine(10);
    }

    private void updateGrid(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.getChildren().clear();
        pane.getRowConstraints().clear();
        pane.getColumnConstraints().clear();

        int constraint = Math.min((int)300/map.getWidth(), (int)700/map.getHeight());
        ColumnConstraints columnConstraints = new ColumnConstraints(constraint);
        RowConstraints rowConstraints = new RowConstraints(constraint);
        pane.getColumnConstraints().add(columnConstraints);
        pane.getRowConstraints().add(rowConstraints);

        pane.add(new Label("y/x"),0,0,1,1);
        int cnt = 1;
        for (int i = 0; i < map.getWidth(); i++) {
            pane.add(new Label(String.valueOf(i)), cnt, 0,1,1);
            pane.getColumnConstraints().add(columnConstraints);
            cnt ++;
        }
        cnt = 1;
        for (int i = 0; i < map.getHeight(); i++) {
            System.out.println(map.getHeight());
            pane.add(new Label(String.valueOf(i)), 0, cnt,1,1);
            pane.getRowConstraints().add(rowConstraints);
            cnt ++;
        }
        pane.setGridLinesVisible(true);
    }

    public void initScene() {
        leftMap = new GridPane();
        updateGrid(leftMap);

        mainBox = new HBox(leftMap);
    }

    public void start(Stage primaryStage) {
        initScene();

        Scene scene = new Scene(mainBox, 1000, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
