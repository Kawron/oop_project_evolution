package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class World {

    public static void main(String[] args){
        SimulationEngine engine = new SimulationEngine(50000);
        engine.run();
    }
}
