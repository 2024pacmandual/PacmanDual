package mp.project.pacmandual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//******************************************************************************************
//******************************************************************************************
// 각 타일을 초기화할 때, -1은 벽, 1은 도트, 0은 빈 타일을 의미함. 고스트와 팩맨 간의 충돌은 따로 구현.
//******************************************************************************************
//******************************************************************************************
public class Map {
    private Tile[][] grid;  // 맵을 구성하는 타일들의 2D 배열
    private int dy;
    private int dx;

    private final int[] pacmanSpawnCoord;  // 팩맨의 초기 스폰 좌표
    private final List<int[]> ghostSpawnsCoords; // 고스트들의 초기 스폰 좌표

    public int get_dy() { return dy; }
    public int get_dx() { return dx; }
    public int[] get_pacmanSpawnCoord() { return pacmanSpawnCoord; }
    public List<int[]> get_ghostSpawnsCoords() { return ghostSpawnsCoords; }

    private int dotCount = 0;


    public Tile[][] get_Grid() { return grid; }
    public Tile getTile(int y, int x) {
        return grid[y][x];
    }

    private int[] _get_pacman_coord(){
        Random random = new Random();
        while (true) {
            int cy = random.nextInt(dy);
            int cx = random.nextInt(dx);

            if (!grid[cy][cx].isWall() && !grid[cy][cx].hasDot()) {
                return new int[]{cy, cx};
            }
        }
    }
    private List<int[]> _get_ghost_coords(int count) {
        List<int[]> spawns = new ArrayList<>();
        Random random = new Random();

        while (spawns.size() < count) {
            int y = random.nextInt(dy);
            int x = random.nextInt(dx);
            int[] possible_spawn_coord = new int[]{y, x};

            if (!grid[y][x].isWall() && grid[y][x].hasDot() &&
                    spawns.stream().noneMatch(coord -> coord[0] == y && coord[1] == x)) {
                spawns.add(possible_spawn_coord);
            }
        }
        return spawns;
    }

    private void TileAlloc(int cy, int cx) { //modified from Tetris Matrix:alloc
        if((cy < 0) || (cx < 0)) throw new IllegalArgumentException("Map alloc : cy and cx must be bigger than 0");
        dy = cy;
        dx = cx;
        grid = new Tile[dy][dx];
    }

    public int getDotCount(){
        return this.dotCount;
    }
    
    public Map(int[][] mapArray,int n_ghost) {
        TileAlloc(mapArray.length, mapArray[0].length);
        for (int i = 0; i < dy; i++) {
            for (int j = 0; j < dx; j++) {
                int tileType = mapArray[i][j];
                switch (tileType) {
                    case -1:
                        grid[i][j] = new Tile(-1);  // 벽
                        break;
                    case 1:
                        grid[i][j] = new Tile(1);   // 도트
                        this.dotCount += 1;
                        break;
                    case 0:
                        grid[i][j] = new Tile(0);   // 빈 공간
                        break;
                    default:
                        grid[i][j] = new Tile(0);   // 기본적으로 빈 공간
                        break;
                }
            }
        }
        pacmanSpawnCoord = _get_pacman_coord();
        ghostSpawnsCoords = _get_ghost_coords(n_ghost);
    }

}
