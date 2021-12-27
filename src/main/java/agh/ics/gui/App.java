package agh.ics.gui;

import agh.ics.oop.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;


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
    Animal theChosenOne = null;

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
//        System.out.println(map.getAnimals().size());

        for (IMapCell cell : map.getCells().values()) {
            Vector2d position = cell.getPosition();

            StackPane node = generator.getView(cell);
            node.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    theChosenOne = cell.getStrongest();
                }
            });
            pane.add(node, position.x+1, map.getHeight()-position.y,1,1);
        }
        pane.setGridLinesVisible(true);
    }

    // imo bezsensu no bo bezsensu no dwie mapy będą co dzień robiły to samo
    public void renderNextDay(IWorldMap map) {
        Platform.runLater(() -> updateMap(map));
    }

    private void updateMap(IWorldMap map) {
        if (map instanceof WorldMap) updateGrid(rightMap, map);
        else updateGrid(leftMap, map);
    }

    public HBox initMapView() {
        leftMap = new GridPane();
        leftMap.maxWidth(400);
        leftMap.minWidth(400);
        updateGrid(leftMap, infMap);

        rightMap = new GridPane();
        rightMap.maxWidth(400);
        rightMap.minWidth(400);
        updateGrid(rightMap, borderMap);

        return new HBox(leftMap, rightMap);
    }

    public VBox initGraphConsole() {
        return new VBox(new Label("test"));
    }

    public VBox initButtonsView() {

        // tu ewidtenie naprawić DRY
        Button startAll = new Button("Start All Maps");
        startAll.setMinWidth(100);
        startAll.setMinHeight(40);
        startAll.setOnAction((actionEvent) -> {
            if (borderThread == null) {
                borderThread = new Thread(() -> borderEngine.run());
                borderThread.start();
            }
            if (infThread == null) {
                infThread = new Thread(() -> infEngine.run());
                infThread.start();
            }
//            Thread thread = new Thread(() -> {
//                while (true) {
//                    try {
//                        Thread.sleep(71);
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    finally {
//                        renderNextDay(borderMap);
//                        renderNextDay(infMap);
//                        System.out.println("dadaldadsklj");
//                    }
//                }
//            });
//            thread.start();
        });

        Button stopAll = new Button("Stop Simulation");
        stopAll.setMinWidth(100);
        stopAll.setMinHeight(40);
        stopAll.setOnAction((actionEvent) -> {
            infEngine.stopResume();
            borderEngine.stopResume();
        });

        Button saveBorder = new Button("Save Border Simulation to CSV");
        saveBorder.setMinWidth(100);
        saveBorder.setMinHeight(40);
        saveBorder.setOnAction((actionEvent) -> {
            System.out.println("saveBorder");
        });

        Button saveInf= new Button("Save Borderless Simulation to CSV");
        saveBorder.setMinWidth(100);
        saveBorder.setMinHeight(40);
        saveBorder.setOnAction((actionEvent) -> {
            System.out.println("saveInf");
        });

        Button stopResumeBorder = new Button("stop/Resume");
        saveBorder.setMinWidth(100);
        saveBorder.setMinHeight(40);
        saveBorder.setOnAction((actionEvent) -> {
            borderEngine.stopResume();
        });

        Button stopResumeInf = new Button("stop/Resume");
        saveBorder.setMinWidth(100);
        saveBorder.setMinHeight(40);
        saveBorder.setOnAction((actionEvent) -> {
            infEngine.stopResume();
        });

        Button startBorder = new Button("Start");
        saveBorder.setMinWidth(100);
        saveBorder.setMinHeight(40);
        saveBorder.setOnAction((actionEvent) -> {
            if (borderThread == null) {
                borderThread = new Thread(() -> borderEngine.run());
                borderThread.start();
            }
        });

        Button startInf = new Button("Start");
        saveBorder.setMinWidth(100);
        saveBorder.setMinHeight(40);
        saveBorder.setOnAction((actionEvent) -> {
            if (infThread == null) {
                infThread = new Thread(() -> infEngine.run());
                infThread.start();
            }
        });

        VBox borderVbox = new VBox(new Label("Border Simulation"), stopResumeBorder, startBorder);
        VBox infVbox = new VBox(new Label("Borderless Simulation"), stopResumeInf, startInf);
        HBox stopResumeHbox = new HBox(infVbox, borderVbox);

        return new VBox(startAll, stopAll, saveBorder, saveInf, stopResumeHbox);
    }

    public VBox initChosenAnimal() {
        return new VBox(new Label("testLabel"));
    }

    public HBox initScene() {
        VBox options = new VBox(initButtonsView(), initChosenAnimal());
        VBox maps = new VBox(initMapView(), initGraphConsole());
        return new HBox(options, maps);
    }

    public void start(Stage primaryStage) {
        mainBox = initScene();

        Scene scene = new Scene(mainBox, 1500, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showDataAboutAnimal() {
        Animal pet = null;
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
