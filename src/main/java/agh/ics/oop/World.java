package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class World {

    public static void main(String[] args){
//        BorderMap mapa = new BorderMap(100, 100, 5);
//        System.out.println(mapa.jungleWidth);
//        System.out.println(mapa.jungleCorner);

        Vector2d eg = new Vector2d(1,1);

//        Animal pet1 = new Animal(eg, null, 6);
//        Animal pet2 = new Animal(eg, null, 8);
//        System.out.println(pet1.printGenes());
//        System.out.println(pet2.printGenes());
//        MapCell cell = new MapCell(eg);
//        cell.animals.add(pet1);
//        cell.animals.add(pet2);
//        Animal child = cell.Breed();
//        System.out.println(child.printGenes());
//        System.out.println(child.getEnergy());
//        cell.eatPlant();
//        System.out.println(pet2.getEnergy());

//        Animal pet1 = new Animal(eg, null, 10);
//        System.out.println(pet1.getPosition());
//        pet1.move(pet1.getRandomGene());
//        System.out.println(pet1.getDirection());
//        System.out.println(pet1.getPosition());

//        List<Animal> test = new ArrayList<>();
//        test.add(pet1);
//        test.add(pet2);
//        test.add(child);
//        System.out.println(Collections.max(test.stream().map(Animal::getEnergy).collect(Collectors.toList())));
        SimulationEngine engine = new SimulationEngine(5);
        engine.run();
    }
}
