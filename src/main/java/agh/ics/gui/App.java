package agh.ics.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App extends Application {

    IWorldMap map = new BorderMap(20,30,2);
    ISimulationEngine engine = new SimulationEngine(100, map);
    GuiBoxGenerator generator = new GuiBoxGenerator();

    HBox mainBox;
    GridPane leftMap;
    VBox options;
    GridPane rightMap;

    int maxWidth = 30;
    int maxHeight = 60;

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

        HashMap<Vector2d, List<Animal>> animals = map.getAnimals();
        List<Vector2d> validPositions = animals.entrySet()
                .stream()
                .filter(x -> x.getValue().size() > 0)
                .map(Map.Entry::getKey).collect(Collectors.toList());

        System.out.println(validPositions);
        for (Vector2d position : validPositions) {
            IMapCell cell = map.getCells().get(position);
            VBox node = generator.getVBox(cell);
            pane.add(node, position.x+1,map.getHeight()-position.y+1,1,1);
        }
        pane.setGridLinesVisible(true);
    }

    public void initScene() {
        engine.run();
        leftMap = new GridPane();
        updateGrid(leftMap);

        Button startButton = new Button("Start");
        startButton.setMinWidth(100);
        startButton.setMinHeight(40);
        startButton.setOnAction((event -> {
            System.out.println("przycisk dziala");
        }));

        mainBox = new HBox(leftMap, startButton);
    }

    public void start(Stage primaryStage) {
        initScene();

        Scene scene = new Scene(mainBox, 1000, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
