package agh.ics.oop;

public class World {

    public static void main(String[] args){
        BorderMap mapa = new BorderMap(100, 100, 5);
        System.out.println(mapa.jungleWidth);
        System.out.println(mapa.jungleCorner);

        Animal pet = new Animal();
        System.out.println(pet.genes);
    }
}
