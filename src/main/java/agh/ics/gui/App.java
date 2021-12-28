package agh.ics.gui;

// Chciałbym podziękować Bognie Gawron za wykonanie ilustracji <3

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {


    Scene mainScene, optionsScene;

    Charts charts = new Charts();
    GuiBoxGenerator generator = new GuiBoxGenerator(this);
    VBox selectedAnimal = new VBox(new Label("No Animal Chosen"));
    GridPane leftMap;
    GridPane rightMap;
    Animal theChosenOne = null;
    OptionParser parser = new OptionParser();

    StatisticEngine borderStats;
    IWorldMap borderMap;
    ISimulationEngine borderEngine;
    Thread borderThread;

    StatisticEngine infStats;
    IWorldMap infMap;
    ISimulationEngine infEngine;
    Thread infThread;

    private void initThreads() {
         borderStats = new StatisticEngine();
         borderMap = new WorldMap(OptionParser.borderWidth,OptionParser.borderHeight,OptionParser.jungleRatio, borderStats);
         borderEngine = new SimulationEngine(OptionParser.borderAnimals, borderMap, this);
         borderThread = null;

         infStats = new StatisticEngine();
         infMap = new BorderlessWorldMap(OptionParser.infWidth,OptionParser.infHeight,OptionParser.jungleRatio, infStats);
         infEngine = new SimulationEngine(OptionParser.infAnimals, infMap, this);
         infThread = null;
    }


    private void updateGrid(GridPane pane, IWorldMap map) {
        pane.setGridLinesVisible(false);
        pane.getChildren().clear();
        pane.getRowConstraints().clear();
        pane.getColumnConstraints().clear();

        int constraint = Math.min((int)450/(map.getWidth()+1), (int)600/(map.getHeight()+1));
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


        for (IMapCell cell : map.getCells().values()) {
            Vector2d position = cell.getPosition();

            StackPane node = generator.getView(cell, constraint, constraint);
            node.setOnMouseClicked(event -> {
                theChosenOne = cell.getStrongest();
                showDataAboutAnimal();
            });
            pane.add(node, position.x+1, map.getHeight()-position.y,1,1);
        }
        pane.setGridLinesVisible(true);
    }

    public void renderNextDay(IWorldMap map) {
        Platform.runLater(() -> updateMap(map));
    }

    public void updateData() {
        Platform.runLater(this::showDataAboutAnimal);
    }

    public void updateStatistic(IWorldMap map) {Platform.runLater(()->{
        if (map == infMap) {
            charts.updateLeftChart(map, infStats);
        }
        else charts.updateRightChart(map, borderStats);
    });}

    private void updateMap(IWorldMap map) {
        if (map instanceof WorldMap) updateGrid(rightMap, map);
        else updateGrid(leftMap, map);
    }

    public HBox initMapView() {
        leftMap = new GridPane();
        updateGrid(leftMap, infMap);

        rightMap = new GridPane();
        updateGrid(rightMap, borderMap);

        HBox res = new HBox(leftMap,rightMap);
        res.setSpacing(100);
        res.setPrefSize(1000.0, 600.0);
        res.setAlignment(Pos.CENTER);

        return res;
    }

    public VBox initGraphConsole() {
        LineChart leftChart = charts.getLeftChart();
        LineChart rightChart = charts.getRightChart();

        HBox graphs = new HBox(leftChart, rightChart);
        return new VBox(graphs);
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
            System.out.println("Start All");
        });

        Button stopAll = new Button("Stop Simulation");
        stopAll.setMinWidth(100);
        stopAll.setMinHeight(40);
        stopAll.setOnAction((actionEvent) -> {
            infEngine.stopResume();
            borderEngine.stopResume();
            System.out.println("Stop All");
        });

        Button saveBorder = new Button("Save Border Simulation to CSV");
        saveBorder.setMinWidth(100);
        saveBorder.setMinHeight(40);
        saveBorder.setOnAction((actionEvent) -> {
            System.out.println("saveBorder");
            borderStats.saveToCSV("BorderCSV");
        });

        Button saveInf= new Button("Save Borderless Simulation to CSV");
        saveInf.setMinWidth(100);
        saveInf.setMinHeight(40);
        saveInf.setOnAction((actionEvent) -> {
            System.out.println("saveInf");
            infStats.saveToCSV("BorderlessCSV");
        });

        Button stopResumeBorder = new Button("Stop/Resume");
        stopResumeBorder.setMinWidth(100);
        stopResumeBorder.setMinHeight(40);
        stopResumeBorder.setOnAction((actionEvent) -> {
            borderEngine.stopResume();
            System.out.println("stopResumeBorder");
        });

        Button stopResumeInf = new Button("Stop/Resume");
        stopResumeInf.setMinWidth(100);
        stopResumeInf.setMinHeight(40);
        stopResumeInf.setOnAction((actionEvent) -> {
            infEngine.stopResume();
            System.out.println("stopResumeInf");
        });

        Button startBorder = new Button("Start");
        startBorder.setMinWidth(100);
        startBorder.setMinHeight(40);
        startBorder.setOnAction((actionEvent) -> {
            System.out.println("start BORDER");
            if (borderThread == null) {
                borderThread = new Thread(() -> borderEngine.run());
                borderThread.start();
            }
        });

        Button startInf = new Button("Start");
        startInf.setMinWidth(100);
        startInf.setMinHeight(40);
        startInf.setOnAction((actionEvent) -> {
            System.out.println("startInf");
            if (infThread == null) {
                infThread = new Thread(() -> infEngine.run());
                infThread.start();
            }
        });

        VBox borderVbox = new VBox(new Label("Border Simulation"), startBorder, stopResumeBorder);
        VBox infVbox = new VBox(new Label("Borderless Simulation"), startInf, stopResumeInf);
        HBox hbox = new HBox(infVbox, borderVbox);
        hbox.setSpacing(10.0);
        hbox.setAlignment(Pos.BASELINE_CENTER);

        VBox res = new VBox(startAll, stopAll, saveInf, saveBorder, hbox);
        res.setMinSize(450.0, 600.0);
        res.setSpacing(10.0);
        res.setAlignment(Pos.BASELINE_CENTER);
        return res;
    }

    public HBox initScene() {
        showDataAboutAnimal();
        VBox options = new VBox(initButtonsView(), selectedAnimal);
        VBox maps = new VBox(initMapView(), initGraphConsole());
        return new HBox(options, maps);
    }

    public VBox initOptionsScene(Stage primaryStage) {
        Label title = new Label("Select Options");

        Label infoAboutSize = new Label("Min size is 10 and max size is 27");
        Label infSizeLabel = new Label("Select Size for borderless map");
        Label borderSizeLabel = new Label("Select size for border map");
        TextField infSizeInput = new TextField();
        TextField borderSizeInput = new TextField();

        Label animalsInfLabel = new Label("Enter amount of animals on borderless map");
        TextField animalsInf = new TextField();

        Label animalsBorderLabel = new Label("Enter amount of animals on border map");
        TextField animalsBorder = new TextField();

        Label infoEnergy = new Label("Enter Starting Energy");
        TextField energy = new TextField();

        Label jungleRatioInfo = new Label("Select Jungle Ratio");
        TextField jungleRatio = new TextField();

        Button startButton = new Button("Apply");
        startButton.setOnAction(event -> {
            int infWidth = Integer.parseInt(infSizeInput.getText(0,2));
            int infHeight = Integer.parseInt(infSizeInput.getText(3,5));
            int infAnimals = Integer.parseInt(animalsInf.getText());

            int borderWidth = Integer.parseInt(borderSizeInput.getText(0,2));
            int borderHeight = Integer.parseInt(borderSizeInput.getText(3,5));
            int borderAnimals = Integer.parseInt(animalsBorder.getText());

            int energyLevel = Integer.parseInt(energy.getText());

            parser.infMapOptions(infWidth, infHeight, infAnimals);
            parser.borderMapOptions(borderWidth, borderHeight, borderAnimals);
            parser.setStartingEnergy(energyLevel);
            parser.setJungleRatio(Integer.parseInt(jungleRatio.getText()));
            initThreads();
            mainScene = new Scene(initScene(), 1500, 750);
            primaryStage.setScene(mainScene);
        });
        VBox res = new VBox(title, infoAboutSize, infSizeLabel, infSizeInput, borderSizeLabel, borderSizeInput);
        res.getChildren().addAll(animalsInfLabel, animalsInf, animalsBorderLabel, animalsBorder, infoEnergy, energy);
        res.getChildren().addAll(jungleRatioInfo, jungleRatio, startButton);
        return res;
    }

    public void start(Stage primaryStage) {

        optionsScene = new Scene(initOptionsScene(primaryStage), 500, 750);
        primaryStage.setScene(optionsScene);
        primaryStage.show();
    }

    public void showDataAboutAnimal() {
        selectedAnimal.getChildren().clear();
        Animal pet = theChosenOne;

        if (pet == null) {
            Label info = new Label("No Animal Chosen");
            selectedAnimal.getChildren().add(info);
            return;
        }

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

        Label childCount = new Label(String.format("The number of children: %d", pet.countChildren()));
        Label descendant = new Label(String.format("The number of descendants: %d", pet.countDescendants(pet)));
        selectedAnimal.getChildren().add(childCount);
        selectedAnimal.getChildren().add(descendant);
    }
}