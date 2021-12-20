package agh.ics.oop;

public class BorderMap {

    final int width;
    final int height;
    final int jungleRatio;
    final int jungleWidth;
    final Vector2d jungleLeftCorner;

    public BorderMap(int width, int height, int jungleRatio){
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;

        double mapArea = width*height;
        this.jungleWidth = (int) Math.floor(Math.sqrt(mapArea));
        this.jungleLeftCorner = new Vector2d(Math.floorDiv(width, 2), Math.floorDiv(height, 2));
    }


}
