package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other){
        int new_x = Math.max(this.x, other.x);
        int new_y = Math.max(this.y, other.y);
        return new Vector2d(new_x, new_y);
    }

    public Vector2d lowerLeft(Vector2d other){
        int new_x = Math.min(this.x, other.x);
        int new_y = Math.min(this.y, other.y);
        return new Vector2d(new_x, new_y);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x, this.y+other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x-other.x, this.y-other.y);
    }

    public Vector2d getModuloVector(int mod_x, int mod_y) {
        int new_x = Math.floorMod(this.x, mod_x);
        int new_y = Math.floorMod(this.y, mod_y);
        Vector2d moduloNewVector = new Vector2d(new_x, new_y);
        return moduloNewVector;
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (!(other instanceof Vector2d)) return false;
        Vector2d that = (Vector2d) other;
        return that.x == this.x && that.y == this.y;
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

    public String toString(){
        return String.format("(%d,%d)",x,y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
