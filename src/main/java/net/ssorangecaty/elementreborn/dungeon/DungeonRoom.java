package net.ssorangecaty.elementreborn.dungeon;

import java.awt.*;

public class DungeonRoom {
    int id;
    Point position;

    public DungeonRoom(int id, int x, int y) {
        this.id = id;
        this.position = new Point(x, y);
    }

}
