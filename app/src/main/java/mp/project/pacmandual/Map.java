package mp.project.pacmandual;

//******************************************************************************************
//******************************************************************************************
// 각 타일을 초기화할 때, -1은 벽, 1은 도트, 0은 빈 타일을 의미함. 고스트와 팩맨 간의 충돌은 따로 구현해야함.
//******************************************************************************************
//******************************************************************************************
public class Map {
    private Tile[][] grid;  // 맵을 구성하는 타일들의 2D 배열
    private int dy = 0;
    private int dx = 0;

    public int get_dy() { return dy; }
    public int get_dx() { return dx; }
    public Tile[][] get_Grid() { return grid; }
    public Tile getTile(int y, int x) {
        return grid[y][x];
    }

    private void TileAlloc(int cy, int cx) { //modified from Matrix:alloc
        if((cy < 0) || (cx < 0)) throw new IllegalArgumentException("Map alloc : cy and cx must be bigger than 0");
        dy = cy;
        dx = cx;
        grid = new Tile[dy][dx];
    }

    public Map() {
        TileAlloc(0 ,0);
    }
    // 주어진 2D 배열을 이용해 맵 초기화
    public Map(int[][] mapArray) {
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
    }

}



//public class Map { //Matrix 클래스 변형
//    private int dy = 0;
//    private int dx = 0;
//    private int[][] array = null;
//    public int get_dy() { return dy; }
//    public int get_dx() { return dx; }
//    public int[][] get_array() { return array; }
//
//    private void alloc(int cy, int cx) {
//        if((cy < 0) || (cx < 0)) throw new IllegalArgumentException("Map alloc : cy and cx must be bigger than 0");
//        dy = cy;
//        dx = cx;
//        array = new int[dy][dx];
//    }
//
//    public Map() { alloc(0, 0);}
//    public Map(int cy, int cx){
//        alloc(cy, cx);
//        for (int y=0; y < cy; y++)
//            for (int x=0; x < cx; x++)
//                array[y][x] = 0;
//    }
//    public Map(Map obj) {
//        alloc(obj.dy, obj.dx);
//        for(int y = 0; y < dy; y++)
//            for(int x = 0; x < dx; x++)
//                array[y][x] = obj.array[y][x];
//    }
//    public Map(int[][] a) {
//        alloc(a.length, a[0].length);
//        for(int y = 0; y < dy; y++)
//            for(int x = 0; x < dx; x++)
//                array[y][x] = a[y][x];
//    }
//    public Map setMap_lv1(){
//        int[][] array = new int[7][20];
//        array[0] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
//        array[1] = new int[] {-1, 1, 1, 1, 1, 1, 1, -1, 0, 0, 0, 0, -1, 1, 1, 1, 1, 1, 1, -1};
//        array[2] = new int[] {-1, 1, 1, 1, -1, 1, 1, -1, 0, 0, 0, 0, -1, 1, 1, -1, 1, 1, 1, -1};
//        array[3] = new int[] {-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1};
//        array[4] = new int[] {-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1};
//        array[5] = new int[] {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1};
//        array[6] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
//        Map map_rt = new Map(array);
//        return map_rt;
//    }
//    public boolean isWall(int x, int y) {
//        return array[y][x] == -1;
//    }
//
//    public boolean isDot(int x, int y) {
//        return array[y][x] == 1;
//    }
//
//    public void collectDot(int x, int y) {
//        if (isDot(x, y)) {
//            array[y][x] = 0;  // 도트를 먹은 후 제거
//        }
//    }
//
//}