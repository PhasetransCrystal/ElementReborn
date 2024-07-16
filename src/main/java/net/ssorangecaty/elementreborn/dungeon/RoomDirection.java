package net.ssorangecaty.elementreborn.dungeon;

import java.util.Arrays;
import java.util.List;

public class RoomDirection {

    public final List<Direction> directions;

    public RoomDirection(Direction[] directions) {
        this.directions = List.of(directions);
    }

    public RoomDirection(List<Direction> directions) {
        this.directions = directions;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public boolean checkDirection(Direction direction){
        return directions.contains(direction);
    }

    public enum Direction {
        EAST,WEST,SOUTH,NORTH
    }
}
