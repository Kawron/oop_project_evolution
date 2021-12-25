package agh.ics.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    IWorldMap borderMap = new WorldMap(10,10,2);
    ISimulationEngine borderEngine = new SimulationEngine(100, borderMap, this);
    Thread borderThread;

    IWorldMap infMap = new BorderlessWorldMap(10,10,2);
    ISimulationEngine infEngine = new SimulationEngine(100, infMap, this);
    Thread infThread;

    GuiBoxGenerator generator = new GuiBoxGenerator();
    HBox mainBox;
    GridPane leftMap;
    GridPane rightMap;
    VBox options;

    public boolean leftFlag = false;
    public boolean rightFlag = false;

    int minWidth = 10;
    int minHeight = 10;
    int maxWidth = 25;
    int maxHeight = 50;

    public void initOptions() {
//        map = new BorderMap(20, 50, 2);
//        engine = new SimulationEngine(10);
    }

    private void updateGrid(GridPane pane, IWorldMap map) {
        pane.setGridLinesVisible(false);
        pane.getChildren().clear();
        pane.getRowConstraints().clear();
        pane.getColumnConstraints().clear();

        int constraint = Math.min((int)300/ map.getWidth(), (int)700/ map.getHeight());
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
            pane.add(node, position.x+1, map.getHeight()-position.y,1,1);
        }
        pane.setGridLinesVisible(true);
    }

    public void renderNextDay(IWorldMap map) {
        Platform.runLater(() -> updateMap(map));
    }

    private void updateMap(IWorldMap map) {
        System.out.println(leftFlag);
        System.out.println(rightFlag);
        if (map instanceof WorldMap) updateGrid(rightMap, map);
        else updateGrid(leftMap, map);
    }

    public HBox initScene() {
        leftMap = new GridPane();
        updateGrid(leftMap, infMap);

        Button leftStartButton = new Button("Start");
        leftStartButton.setMinWidth(100);
        leftStartButton.setMinHeight(40);
        leftStartButton.setOnAction((actionEvent) -> {
            infThread = new Thread(() -> infEngine.run());
            infThread.start();
        });

        Button leftStopButton = new Button("Stop");
        leftStopButton.setMinWidth(100);
        leftStopButton.setMinHeight(40);
        leftStopButton.setOnAction((actionEvent) -> {
            this.leftFlag = true;
        });

        VBox leftDiv = new VBox(leftMap, leftStartButton, leftStopButton);
        // right Div
        rightMap = new GridPane();
        updateGrid(rightMap, borderMap);

        Button rightStartButton = new Button("Start");
        rightStartButton.setMinWidth(100);
        rightStartButton.setMinHeight(40);
        rightStartButton.setOnAction((actionEvent) -> {
            borderThread = new Thread(() -> borderEngine.run());
            borderThread.start();
        });

        Button rightStopButton = new Button("Stop");
        rightStopButton.setMinWidth(100);
        rightStopButton.setMinHeight(40);
        rightStopButton.setOnAction((actionEvent) -> {
            this.rightFlag = true;
        });

        VBox rightDiv = new VBox(rightMap, rightStartButton, rightStopButton);

        return new HBox(leftDiv, rightDiv);
    }

    public void start(Stage primaryStage) {
        HBox mainBox = initScene();

        Scene scene = new Scene(mainBox, 1000, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void stopSimulations(boolean value) {
        value = !value;
    }

    public boolean shouldIWork(IWorldMap map) {
        if (map instanceof WorldMap) return rightFlag;
        return leftFlag;
    }
}
