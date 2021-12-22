package agh.ics.oop;

public interface IMoveObserver {

    void positionChanged(Animal pet, Vector2d oldPosition, Vector2d newPosition);
}