package mp.project.pacmandual;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost {
    private int ghostX;
    private int ghostY;
    public int type;
    public int speed;

    private static final Random rand = new Random();

    private void checkAndAddMove(List<int[]> possibleMoves, Map map, int y, int x, int dy, int dx) {
        // 맵 경계를 넘지 않고, 벽이 아닌 경우만 추가
        if (y >= 0 && y < dy && x >= 0 && x < dx && !map.getTile(y, x).isWall()) {
            possibleMoves.add(new int[]{y, x});
        }
    }

    public Ghost(Map map, int ghostY, int ghostX){
        type = rand.nextInt(2);
        this.speed = 1;
        this.ghostX = ghostX;
        this.ghostY = ghostY;
        if (type == 1){
            Log.d("tag", "Ghost: nighmare");
            this.speed=0;
        }
    }

    public int getGhostX() {
        return ghostX;
    }

    public int getGhostY() {
        return ghostY;
    }

    public void move(Map map, int py, int px) {
        int dy = map.get_dy();
        int dx = map.get_dx();
        int cy = this.ghostY, cx = this.ghostX;

        // 이동 가능한 방향을 저장할 리스트
        List<int[]> possibleMoves = new ArrayList<>();

        checkAndAddMove(possibleMoves, map, cy + 1, cx, dy, dx); // 아래
        checkAndAddMove(possibleMoves, map, cy - 1, cx, dy, dx); // 위
        checkAndAddMove(possibleMoves, map, cy, cx - 1, dy, dx); // 왼쪽
        checkAndAddMove(possibleMoves, map, cy, cx + 1, dy, dx); // 오른쪽

        if (type == 0){
            possibleMoves.add(new int[] {cy, cx});
            int[] move = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            this.ghostY = move[0];
            this.ghostX = move[1];
            return;
        }

        int[] bestMove = null;
        double minDistance = Double.MAX_VALUE;

        for (int[] move : possibleMoves) {
            int newY = move[0];
            int newX = move[1];

            // 유클리드 거리 계산
            double distance = Math.sqrt(Math.pow(newY - py, 2) + Math.pow(newX - px, 2));

            // 더 가까운 거리를 가진 좌표로 업데이트
            if (distance < minDistance) {
                minDistance = distance;
                bestMove = move;
            }
        }

        // 선택된 위치로 고스트 이동
        if (bestMove != null) {
            this.ghostY = bestMove[0];
            this.ghostX = bestMove[1];
        }
    }

}
