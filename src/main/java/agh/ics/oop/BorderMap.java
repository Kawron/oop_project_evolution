package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class BorderMap implements IWorldMap {

    final int width;
    final int height;
    final int jungleRatio;
    final int jungleWidth;
    final Vector2d jungleCorner;

    private HashMap<Vector2d, List<Animal>> animals = new HashMap<>();
//    private List<Animal> animals = new ArrayList<>();
    private HashMap<Vector2d, IMapCell> mapCells;

    public BorderMap(int width, int height, int jungleRatio){
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
        this.mapCells = new HashMap<>();

        double mapArea = width*height;
        this.jungleWidth = (int) Math.floor(Math.sqrt(mapArea/jungleRatio));
        int jungleCornerX = Math.floorDiv(this.width - this.jungleWidth, 2);
        int jungleCornerY = Math.floorDiv(this.height - this.jungleWidth, 2);
        this.jungleCorner = new Vector2d(jungleCornerX, jungleCornerY);

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Vector2d key = new Vector2d(x,y);
                MapCell value = new MapCell(key, this);
                mapCells.put(key, value);
            }
        }
    }

    public void placeAnimal(Animal pet) {
        Vector2d position = pet.getPosition();
        List<Animal> petList = animals.computeIfAbsent(position, k -> new LinkedList<Animal>());

        petList.add(pet);
        pet.addObserver(this);
    }

    public void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition) {
        List<Animal> petList = animals.get(oldPosition);
        petList.remove(pet);

        petList = animals.computeIfAbsent(newPosition, k -> new LinkedList<Animal>());
        petList.add(pet);
    }

    public boolean canMove(Vector2d position) {
        return position.x <= width && position.y <= height;
    }

    public void animalDied(MapCell cell, Animal animal) {
        animals.get(cell.position).remove(animal);
    }

    public void animalBorn(MapCell cell, Animal animal) {
        animals.get(cell.position).add(animal);
    }

    public List<Animal> getAnimalOnCell(IMapCell cell) {
        return animals.get(cell);
    }

    public List<IMapCell> getAllCells() {
        return new ArrayList<>(mapCells.values());
    }
}
