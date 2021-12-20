package agh.ics.oop;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class BorderMap {

    final int width;
    final int height;
    final int jungleRatio;
    final int jungleWidth;
    final Vector2d jungleCorner;

    private HashMap<Vector2d, MapCell> mapCells;

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
                MapCell value = new MapCell(key);
                mapCells.put(key, value);
            }
        }
    }

}
