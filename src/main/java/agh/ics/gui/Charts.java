package agh.ics.gui;

import agh.ics.oop.IWorldMap;
import agh.ics.oop.StatisticEngine;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Charts {

    private NumberAxis rightAxisX;
    private NumberAxis rightAxisY;
    private LineChart rightChart;
    private XYChart.Series rightNumOfAnimals;
    private XYChart.Series rightNumOfPlants;
    private XYChart.Series rightAvgEnergy;
    private XYChart.Series rightAvgLifeSpan;
    private XYChart.Series rightAvgChildren;

    private NumberAxis leftAxisX;
    private NumberAxis leftAxisY;
    private LineChart leftChart;
    private XYChart.Series leftNumOfAnimals;
    private XYChart.Series leftNumOfPlants;
    private XYChart.Series leftAvgEnergy;
    private XYChart.Series leftAvgLifeSpan;
    private XYChart.Series leftAvgChildren;

    public Charts() {
        // LeftChart
        rightAxisX = new NumberAxis();
        rightAxisX.setLabel("Days");
        rightAxisY = new NumberAxis();
        rightAxisY.setLabel("Values");

        rightChart = new LineChart<>(rightAxisX, rightAxisY);
        rightChart.setCreateSymbols(false);

        rightNumOfAnimals = new XYChart.Series();
        rightNumOfAnimals.setName("Num of animals");

        rightNumOfPlants = new XYChart.Series();
        rightNumOfPlants.setName("Num of plants");

        rightAvgEnergy = new XYChart.Series();
        rightAvgEnergy.setName("Average of energy");

        rightAvgLifeSpan = new XYChart.Series();
        rightAvgLifeSpan.setName("Average of lifespan");

        rightAvgChildren = new XYChart.Series();
        rightAvgChildren.setName("Average of Children");

        rightChart.getData().addAll(rightNumOfAnimals, rightNumOfPlants, rightAvgEnergy, rightAvgLifeSpan, rightAvgChildren);
        // left Chart
        leftAxisX = new NumberAxis();
        leftAxisX.setLabel("Days");
        leftAxisY = new NumberAxis();
        leftAxisY.setLabel("Values");

        leftChart = new LineChart<>(leftAxisX, leftAxisY);
        leftChart.setCreateSymbols(false);

        leftNumOfAnimals = new XYChart.Series();
        leftNumOfAnimals.setName("Num of animals");

        leftNumOfPlants = new XYChart.Series();
        leftNumOfPlants.setName("Num of plants");

        leftAvgEnergy = new XYChart.Series();
        leftAvgEnergy.setName("Average of energy");

        leftAvgLifeSpan = new XYChart.Series();
        leftAvgLifeSpan.setName("Average of lifespan");

        leftAvgChildren = new XYChart.Series();
        leftAvgChildren.setName("Average of children");


        leftChart.getData().addAll(leftNumOfAnimals, leftNumOfPlants, leftAvgEnergy, leftAvgLifeSpan, leftAvgChildren);
    }

    public void updateRightChart(IWorldMap map, StatisticEngine engine) {
        int day = map.getDay()-1;
        rightNumOfAnimals.getData().add(new XYChart.Data(day, engine.getNumOfAnimals().get(day)));
        rightNumOfPlants.getData().add(new XYChart.Data(day, engine.getNumOfPlants().get(day)));
        rightAvgEnergy.getData().add(new XYChart.Data(day, engine.getAvgEnergy().get(day)));
        rightAvgLifeSpan.getData().add(new XYChart.Data(day, engine.getAvgLifeSpan().get(day)));
        rightAvgChildren.getData().add(new XYChart.Data(day, engine.getAvgChildren().get(day)));
    }

    public void updateLeftChart(IWorldMap map, StatisticEngine engine) {
        int day = map.getDay()-1;
        leftNumOfAnimals.getData().add(new XYChart.Data(day, engine.getNumOfAnimals().get(day)));
        leftNumOfPlants.getData().add(new XYChart.Data(day, engine.getNumOfPlants().get(day)));
        leftAvgEnergy.getData().add(new XYChart.Data(day, engine.getAvgEnergy().get(day)));
        leftAvgLifeSpan.getData().add(new XYChart.Data(day, engine.getAvgLifeSpan().get(day)));
        leftAvgChildren.getData().add(new XYChart.Data(day, engine.getAvgChildren().get(day)));
    }

    public LineChart getLeftChart() {
        return leftChart;
    }

    public LineChart getRightChart() {
        return rightChart;
    }
}
