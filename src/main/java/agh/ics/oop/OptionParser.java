package agh.ics.oop;

public class OptionParser {

    public static int infWidth, infHeight;
    public static int borderWidth, borderHeight;
    public static int infAnimals;
    public static int borderAnimals;
    public static int startingEnergy;
    public static int jungleRatio;

    public OptionParser() {}

    public void infMapOptions(int width, int height, int animalsAmount) {
        infWidth = width;
        infHeight = height;
        infAnimals = animalsAmount;
    }

    public void borderMapOptions(int width, int height, int animalsAmount) {
        borderWidth = width;
        borderHeight = height;
        borderAnimals = animalsAmount;
    }

    public void setStartingEnergy(int energy) {
        startingEnergy = energy;
    }

    public void setJungleRatio(int ratio) {
        jungleRatio = ratio;
    }
}
