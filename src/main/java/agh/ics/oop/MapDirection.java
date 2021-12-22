package agh.ics.oop;

// przetestować
public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    EAST_SOUTH,
    SOUTH,
    SOUTH_WEST,
    WEST,
    WEST_NORTH;

    public MapDirection turnRight(int degree) {
        int idx = (this.ordinal() + degree) % 8;
        return MapDirection.values()[idx];
    }

    public MapDirection next(){
        int idx = (this.ordinal()+1)%8;
        return MapDirection.values()[idx];
    }

    public MapDirection prev(){
        int idx = (this.ordinal()+7)%8;
        return MapDirection.values()[idx];
    }

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> new Vector2d(0,1);
            case EAST -> new Vector2d(1,0);
            case SOUTH -> new Vector2d(0,-1);
            case WEST -> new Vector2d(-1,0);
            default -> new Vector2d(0,0);
        };
    }
}
