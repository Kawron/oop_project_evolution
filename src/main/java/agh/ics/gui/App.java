package agh.ics.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App extends Application {

    IWorldMap map = new BorderMap(10,10,2);
    ISimulationEngine engine = new SimulationEngine(100, map, this);
    GuiBoxGenerator generator = new GuiBoxGenerator(map);
    Thread thread;

    HBox mainBox;
    GridPane leftMap;
    VBox options;
    GridPane rightMap;

    int minWidth = 10;
    int minHeight = 10;
    int maxWidth = 25;
    int maxHeight = 50;

    public void initOptions() {
//        map = new BorderMap(20, 50, 2);
//        engine = new SimulationEngine(10);
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
            pane.add(new Label(String.valueOf(i)), 0, cnt,1,1);
            pane.getRowConstraints().add(rowConstraints);
            cnt ++;
        }

        // można zrobic hashmape z roslinam i zwierzakami
        System.out.println(map.getAnimals().size());

        for (IMapCell cell : map.getCells().values()) {
            Vector2d position = cell.getPosition();

            VBox node = generator.getVBox(cell);
            pane.add(node, position.x+1,map.getHeight()-position.y,1,1);
        }
        pane.setGridLinesVisible(true);
    }

    public void renderNextDay() {
        Platform.runLater(() -> updateGrid(leftMap));
    }

    public void initScene() {
        leftMap = new GridPane();
        updateGrid(leftMap);

        Button startButton = new Button("Start");
        startButton.setMinWidth(100);
        startButton.setMinHeight(40);
        startButton.setOnAction((actionEvent) -> {
            thread = new Thread(() -> engine.run());
            thread.start();
        });

        Button stopButton = new Button("Stop");
        stopButton.setMinWidth(100);
        stopButton.setMinHeight(40);
        stopButton.setOnAction((actionEvent) -> {
            try {
                thread.wait();
            }
            catch (Exception e) {
                System.out.println("xd");
            }
        });

        mainBox = new HBox(leftMap, startButton, stopButton);
    }

    public void start(Stage primaryStage) {
        initScene();

        Scene scene = new Scene(mainBox, 1000, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
