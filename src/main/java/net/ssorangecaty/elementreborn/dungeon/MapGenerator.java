package net.ssorangecaty.elementreborn.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {
    private List<DungeonRoom> DungeonRooms = new ArrayList<>();
    private int[][] map = new int[9][8];
    private Random random = new Random();
    private int stageId = 1; // 初始关卡ID
    private boolean isHardDifficulty = false; // 难度设置
    private boolean curseOfTheLabyrinth = false; // 迷宫诅咒
    private boolean curseOfTheLost = false; // 迷失诅咒


    enum RoomType {
        BOSS, SUPER_SECRET, SHOP, TREASURE, DICE, SACRIFICE, LIBRARY, CURSE, MINI_BOSS, CHALLENGE, CHEST, ARCADE, BEDROOM, SECRET, GRAVE
    }
    // 计算房间数
    private int calculateNumberOfRooms() {
        int baseRooms = Math.min(20, random.nextInt(2) + 5 + stageId * 10 / 3);
        if (curseOfTheLabyrinth) {
            baseRooms = Math.min(45, (int) Math.floor(baseRooms * 1.8));
        } else if (curseOfTheLost) {
            baseRooms += 4;
        }
        if (stageId == 12) { // The Void
            baseRooms = 50 + random.nextInt(10);
        }
        if (isHardDifficulty) {
            baseRooms += 2 + random.nextInt(2);
        }
        return baseRooms;
    }

    // 计算死胡同数
    private int calculateMinDeadEnds() {
        int minDeadEnds = 5;
        if (stageId != 1) {
            minDeadEnds += 1;
        }
        if (curseOfTheLabyrinth) {
            minDeadEnds += 1;
        }
        if (stageId == 12) {
            minDeadEnds += 2;
        }
        return minDeadEnds;
    }

    public void generateMap() {
        // 随机生成起始房间
        DungeonRoom startDungeonRoom = new DungeonRoom(1, 5, 4);
        DungeonRooms.add(startDungeonRoom);

        int deadEndCount = this.calculateMinDeadEnds();
        this.generateDeadEnds(deadEndCount);

    }


    private void generateDeadEnds(int deadEndCount) {
        int baseRoom = calculateNumberOfRooms();
        List<DungeonRoom> possibleStarts = new ArrayList<>(DungeonRooms); // 所有可能的起始点

        for (int i = 0; i < deadEndCount; i++) {
            DungeonRoom startRoom = possibleStarts.get(random.nextInt(possibleStarts.size()));
            int direction = random.nextInt(4); // 0: up, 1: right, 2: down, 3: left
            int x = startRoom.position.x;
            int y = startRoom.position.y;

            // 根据方向调整起始点坐标
            switch (direction) {
                case 0: // up
                    if (y > 0) y--;
                    break;
                case 1: // right
                    if (x < 7) x++;
                    break;
                case 2: // down
                    if (y < 6) y++;
                    break;
                case 3: // left
                    if (x > 0) x--;
                    break;
            }

            // 检查是否越界
            if (x >= 0 && x < 8 && y >= 0 && y < 7) {
                // 在死路尽头生成新的死路
                if (random.nextBoolean()) { // 50%的概率生成新的死路
                    int newDirection = (direction + 2) % 4; // 选择与原方向相反的方向
                    // 根据新方向调整坐标
                    switch (newDirection) {
                        case 0: // up
                            if (y > 1) y--;
                            break;
                        case 1: // right
                            if (x < 6) x++;
                            break;
                        case 2: // down
                            if (y < 5) y++;
                            break;
                        case 3: // left
                            if (x > 1) x--;
                            break;
                    }
                    // 在地图上标记死路
                    generateDungeonRoom(x,y);
                    map[x][y] = 1;
                }
            }
        }
    }


    private DungeonRoom generateDungeonRoom(int x, int y) {
        return new DungeonRoom(DungeonRooms.size() + 1, x, y);
    }

    private boolean isConnectable(DungeonRoom newDungeonRoom) {
        if (newDungeonRoom.position.x < 0 || newDungeonRoom.position.x >= 9 ||
                newDungeonRoom.position.y < 0 || newDungeonRoom.position.y >= 8) {
            return false;
        }

        for (DungeonRoom room : DungeonRooms) {
            if (room.position.x == newDungeonRoom.position.x &&
                    (Math.abs(room.position.y - newDungeonRoom.position.y) == 1)) {
                return true;
            }
            if (room.position.y == newDungeonRoom.position.y &&
                    (Math.abs(room.position.x - newDungeonRoom.position.x) == 1)) {
                return true;
            }
        }

        return false;
    }

    public void printMap() {
        for (int x = 0; x < 8 ; x++) {
            for (int y = 0 ; y < 7 ; y++){
                System.out.print(map[x][y]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        MapGenerator generator = new MapGenerator();
        generator.generateMap();
        generator.printMap();
    }
}