package agh.ics.oop;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticEngine {

    private final List<Integer> numOfAnimals = new ArrayList<>();
    private final List<Integer> numOfPlants = new ArrayList<>();
    private final List<Double> avgEnergy = new ArrayList<>();
    private final List<Double> avgLifeSpan = new ArrayList<>();
    private final List<Double> avgChildren = new ArrayList<>();

    private double lifeSpanNumerator = 0;
    private double lifeSpanDenominator = 0;

    public StatisticEngine() {};

    public void updateData(IWorldMap map) {
        numOfAnimals.add(map.getAnimals().size());
        numOfPlants.add(map.getNumOfPlants());
        avgEnergy.add(updateEnergy(map));
        if (lifeSpanDenominator == 0) {
            avgLifeSpan.add(0.0);
        }
        else avgLifeSpan.add(lifeSpanNumerator/lifeSpanDenominator);
        avgChildren.add(avgChildren(map));
    }

    private double updateEnergy(IWorldMap map) {
        double numerator = 0;
        double denominator = 0;
        for (Animal pet : map.getAnimals()) {
            numerator += pet.getEnergy();
            denominator ++;
        }
        return numerator/denominator;
    }

    public void updateLifeSpan(Animal pet) {
        lifeSpanNumerator += pet.deathDay - pet.bornDay;
        lifeSpanDenominator ++;
    }

    private double avgChildren(IWorldMap map) {
        double numerator = 0;
        double denominator = 0;
        for (Animal pet : map.getAnimals()) {
            numerator += pet.countChildren();
            denominator ++;
        }
        return numerator/denominator;
    }

//    https://stackoverflow.com/questions/31623214/convert-a-list-of-strings-into-a-single-string-using-arrayutils-in-java
    private String joiner(List<String> list, String separator){
        StringBuilder result = new StringBuilder();
        for(String term : list) result.append(term + separator);
        return result.deleteCharAt(result.length()-separator.length()).toString();
    }

    private List<String> transformData() {
        List<String> lines = new ArrayList<>();
        lines.add("NumOfAnimals, NumOfPlants, AverageEnergy, AverageLifespan, AverageChildren");
        for (int i = 0; i < numOfAnimals.size(); i++) {
            List<String> line = new ArrayList<>();
            line.add(String.valueOf(numOfAnimals.get(i)));
            line.add(String.valueOf(numOfPlants.get(i)));
            line.add(String.valueOf(avgEnergy.get(i)));
            line.add(String.valueOf(avgLifeSpan.get(i)));
            line.add(String.valueOf(avgChildren.get(i)));
            lines.add(joiner(line, ","));
        }
        lines.add(calculateAverages().replace("\n", ""));
        return lines;
    }

    private String calculateAverages() {
        List<String> res = new ArrayList<>();
        res.add(String.valueOf(numOfAnimals.stream().mapToInt(Integer::intValue).sum() /numOfAnimals.size()));
        res.add(String.valueOf(numOfPlants.stream().mapToInt(Integer::intValue).sum() /numOfPlants.size()));
        res.add(String.valueOf(avgEnergy.stream().mapToDouble(Double::doubleValue).sum() /avgEnergy.size()));
        res.add(String.valueOf(avgLifeSpan.stream().mapToDouble(Double::doubleValue).sum() /avgLifeSpan.size()));
        res.add(String.valueOf(avgChildren.stream().mapToDouble(Double::doubleValue).sum() /avgChildren.size()));
        return joiner(res, ",");
    }

//    https://www.baeldung.com/java-csv
    public void saveToCSV(String name) {
        List<String> data = transformData();
        name = name + ".csv";
        File csvOutputFile = new File(name);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            data.stream().forEach(pw::println);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Integer> getNumOfAnimals() {
        return numOfAnimals;
    }

    public List<Integer> getNumOfPlants() {
        return numOfPlants;
    }

    public List<Double> getAvgEnergy() {
        return avgEnergy;
    }

    public List<Double> getAvgLifeSpan() {
        return avgLifeSpan;
    }

    public List<Double> getAvgChildren() {
        return avgChildren;
    }
}
