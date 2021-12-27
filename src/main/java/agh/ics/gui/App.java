package agh.ics.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    IWorldMap borderMap = new WorldMap(11,10,2);
    ISimulationEngine borderEngine = new SimulationEngine(100, borderMap, this);
    Thread borderThread = null;

    IWorldMap infMap = new BorderlessWorldMap(11,10,2);
    ISimulationEngine infEngine = new SimulationEngine(100, infMap, this);
    Thread infThread = null;

    GuiBoxGenerator generator = new GuiBoxGenerator(this);
    HBox mainBox;
    VBox selectedAnimal;
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

        int constraint = Math.min((int)450/ map.getWidth(), (int)700/ map.getHeight());
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
        if (map instanceof WorldMap) updateGrid(rightMap, map);
        else updateGrid(leftMap, map);
    }

    public HBox initScene() {
        leftMap = new GridPane();
        leftMap.maxWidth(400);
        leftMap.minWidth(400);
        updateGrid(leftMap, infMap);

        Button leftStartButton = new Button("Start");
        leftStartButton.setMinWidth(100);
        leftStartButton.setMinHeight(40);
        leftStartButton.setOnAction((actionEvent) -> {
            if (infThread == null) {
                infThread = new Thread(() -> infEngine.run());
                infThread.start();
            }
        });

        Button leftStopButton = new Button("Stop/Resume");
        leftStopButton.setMinWidth(100);
        leftStopButton.setMinHeight(40);
        leftStopButton.setOnAction((actionEvent) -> {
            infEngine.stopResume();
        });

        VBox leftDiv = new VBox(leftMap, leftStartButton, leftStopButton);
        // right Div
        rightMap = new GridPane();
        rightMap.maxWidth(400);
        rightMap.minWidth(400);
        updateGrid(rightMap, borderMap);

        Button rightStartButton = new Button("Start");
        rightStartButton.setMinWidth(100);
        rightStartButton.setMinHeight(40);
        rightStartButton.setOnAction((actionEvent) -> {
            if (borderThread == null) {
                borderThread = new Thread(() -> borderEngine.run());
                borderThread.start();
            }
        });

        Button rightStopButton = new Button("Stop/Resume");
        rightStopButton.setMinWidth(100);
        rightStopButton.setMinHeight(40);
        rightStopButton.setOnAction((actionEvent) -> {
            showDataAboutAnimal();
            borderEngine.stopResume();
        });

        VBox rightDiv = new VBox(rightMap, rightStartButton, rightStopButton);

        // middle div
        selectedAnimal = new VBox();
        selectedAnimal.setMinWidth(220);
        selectedAnimal.setMaxWidth(220);

        HBox hbox = new HBox(leftDiv, selectedAnimal, rightDiv);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.setSpacing(5);
        return hbox;
    }

    public void start(Stage primaryStage) {
        mainBox = initScene();

        Scene scene = new Scene(mainBox, 1200, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showDataAboutAnimal() {
        Animal pet = new Animal(new Vector2d(1,2), null, 10 ,borderMap);
        Animal child = new Animal(new Vector2d(1,2), null, 10 ,borderMap);
        pet.addChild(child);
        Animal grandChild = new Animal(new Vector2d(1,2), null, 10 ,borderMap);
        child.addChild(grandChild);
        selectedAnimal.getChildren().clear();

        Label genesLabel = new Label("Genes of animal");
        Label genes = new Label(pet.printGenes());
        selectedAnimal.getChildren().add(genes);

        Label deathMsg;
        if (pet.deathDay < 0) {
            deathMsg = new Label("Animal is still alive");
        }
        else {
            deathMsg = new Label(String.format("The animal died on %d day", pet.deathDay));
        }
        selectedAnimal.getChildren().add(deathMsg);

        System.out.println(pet.countChildren(pet));
        System.out.println(pet.countDescendants(pet));
    }
}
