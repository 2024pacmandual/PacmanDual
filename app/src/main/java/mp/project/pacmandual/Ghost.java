package mp.project.pacmandual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost {
    private int ghostX;
    private int ghostY;
    private int GHOST_SPEED = 1; //default = 1

    private static final Random rand = new Random();

    private void checkAndAddMove(List<int[]> possibleMoves, Map map, int y, int x, int dy, int dx) {
        // 맵 경계를 넘지 않고, 벽이 아닌 경우만 추가
        if (y >= 0 && y < dy && x >= 0 && x < dx && !map.getTile(y, x).isWall()) {
            possibleMoves.add(new int[]{y, x});
        }
    }

    public Ghost(Map map, int speed, int ghostX, int ghostY){
        this.GHOST_SPEED = speed;
        this.ghostX = ghostX;
        this.ghostY = ghostY;
    }
    public void move(Map map) {
        int dy = map.get_dy();
        int dx = map.get_dx();
        int cy = this.ghostY, cx = this.ghostX;

        // 이동 가능한 방향을 저장할 리스트
        List<int[]> possibleMoves = new ArrayList<>();

        // 이동 가능한 방향 추가
        checkAndAddMove(possibleMoves, map, cy + 1, cx, dy, dx); // 아래
        checkAndAddMove(possibleMoves, map, cy - 1, cx, dy, dx); // 위
        checkAndAddMove(possibleMoves, map, cy, cx - 1, dy, dx); // 왼쪽
        checkAndAddMove(possibleMoves, map, cy, cx + 1, dy, dx); // 오른쪽

        // 이동 가능한 방향이 있으면 랜덤하게 선택
        if (!possibleMoves.isEmpty()) {
            int[] move = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            this.ghostY = move[0];
            this.ghostX = move[1];
        }
    }
}

//!!!!!!!!!!!!!!Ghost Class for multi-Thread env!!!!!!!!!!!!!!!!
//import java.util.concurrent.ThreadLocalRandom;
//public class Ghost {
//    private int ghostX;
//    private int ghostY;
//    private int GHOST_SPEED = 1; // default = 1
//
//    private void checkAndAddMove(List<int[]> possibleMoves, Map map, int y, int x, int dy, int dx) {
//
//        if (y >= 0 && y < dy && x >= 0 && x < dx && !map.getTile(y, x).isWall()) {
//            possibleMoves.add(new int[]{y, x});
//        }
//    }
//
//    public Ghost(Map map, int speed, int ghostX, int ghostY) {
//        this.GHOST_SPEED = speed;
//        this.ghostX = ghostX;
//        this.ghostY = ghostY;
//    }
//
//    public void move(Map map) {
//        int dy = map.get_dy();
//        int dx = map.get_dx();
//        int cy = this.ghostY, cx = this.ghostX;
//
//        List<int[]> possibleMoves = new ArrayList<>();
//
//
//        checkAndAddMove(possibleMoves, map, cy + 1, cx, dy, dx); // 아래
//        checkAndAddMove(possibleMoves, map, cy - 1, cx, dy, dx); // 위
//        checkAndAddMove(possibleMoves, map, cy, cx - 1, dy, dx); // 왼쪽
//        checkAndAddMove(possibleMoves, map, cy, cx + 1, dy, dx); // 오른쪽
//
//
//        if (!possibleMoves.isEmpty()) {
//
//            int[] move = possibleMoves.get(ThreadLocalRandom.current().nextInt(possibleMoves.size()));
//            this.ghostY = move[0];
//            this.ghostX = move[1];
//        }
//    }
//}