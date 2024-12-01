
package mp.project.pacmandual;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class PacmanGame {
    private List<Ghost> ghosts;
    private Map map;
    private int pacmanX;
    private int pacmanY;
    private int PACMAN_SPEED = 1;
    private int score;

    public PacmanGame() {
        this.map = new Map();
        // 나중에 case나 if문으로 각 레벨별 팩맨의 초기위치 설정
        this.pacmanX = 1; // Pacman의 초기 X 위치
        this.pacmanY = 1; // Pacman의 초기 Y 위치
        this.score = 0;
        ghosts = new ArrayList<>();
        initializeMap(1);
        initializeGhosts();
    }

    private void initializeMap(int level) {
        int[][] array;
        int n_ghost;
        if (level>2) n_ghost = 4;
        else n_ghost = 3;
        switch (level){
            //case 1:
            //case 2:
            //case 3:
            default:
                array = new int[7][20];
                array[0] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                array[1] = new int[] {-1, 1, 1, 1, 1, 1, 1, -1, 0, 0, 0, 0, -1, 1, 1, 1, 1, 1, 1, -1};
                array[2] = new int[] {-1, 1, 1, 1, -1, 1, 1, -1, 0, 0, 0, 0, -1, 1, 1, -1, 1, 1, 1, -1};
                array[3] = new int[] {-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1};
                array[4] = new int[] {-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1};
                array[5] = new int[] {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1};
                array[6] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        }

        map = new Map(array, n_ghost); //setMap_lv3까지 계획
    }

    public int getPacmanX() {
        return pacmanX;
    }

    public int getPacmanY() {
        return pacmanY;
    }

    public Tile[][] getMapArray(){
        return map.get_Grid();
    }
    private void initializeGhosts() {
        ghosts.add(new Ghost(map, 1, 1, 1));
        ghosts.add(new Ghost(map, 1, 18, 1));
        ghosts.add(new Ghost(map, 1, 1, 5));
    }
    public List<Ghost> getGhosts(){
        return ghosts;
    }

    public void updateGameState() {
       // movePacman();
        for (Ghost ghost : ghosts) {
            ghost.move(map);
        }
        //충돌 여부 체크 추가하기
    }
    public void movePacman(int dy, int dx) {
        // Pacman의 새로운 위치 계산
        int newY = pacmanY + dy;
        int newX = pacmanX + dx;

        // 이동 가능한지 검사
        if (isMoveValid(newX, newY)) {
            Tile tile = map.getTile(newY, newX);
            if (tile.hasDot()){
                tile.consumeDot();
                score += 1;
            }
            pacmanX = newX;
            pacmanY = newY;

        }
    }

    private boolean isMoveValid(int y, int x) {
        // 맵 경계를 벗어나지 않는지 확인
        if (y < 0 || y >= map.get_dy() || x < 0 || x >= map.get_dx()) {
            return false;
        }
        Tile tile = map.getTile(y, x);
        if (tile.isWall()) {
            return false;
        }
        return true;
    }

    public ScreenState getScreen() {
        return new ScreenState(map, pacmanX, pacmanY);
    }

    public void onTouchEvent(MotionEvent event) {

    }

    // 스크린 상태를 나타내는 내부 클래스
    public static class ScreenState {
        public final Map map;
        public final int pacmanX;
        public final int pacmanY;

        public ScreenState(Map map, int pacmanX, int pacmanY) {
            this.map = map;
            this.pacmanX = pacmanX;
            this.pacmanY = pacmanY;
        }
    }
}

//public ScreenState getScreen() {
//    return new ScreenState(map, pacmanX, pacmanY);
//}
//
//public void onTouchEvent(MotionEvent event) {
//    // 터치 이벤트 처리 (여기서는 간단히 아래쪽으로 이동)
//    if (event.getAction() == MotionEvent.ACTION_MOVE) {
//        // 실제 터치에 따라 dx, dy 값 계산
//        float dx = event.getX() - pacmanX * 100;
//        float dy = event.getY() - pacmanY * 100;
//
//        // 간단히 dx, dy를 정수로 변환하여 이동
//        movePacman((int) Math.signum(dx), (int) Math.signum(dy));
//    }
//}
//
//// 스크린 상태를 나타내는 내부 클래스
//public static class ScreenState {
//    public final Map map;
//    public final int pacmanX;
//    public final int pacmanY;
//
//    public ScreenState(Map map, int pacmanX, int pacmanY) {
//        this.map = map;
//        this.pacmanX = pacmanX;
//        this.pacmanY = pacmanY;
//    }
//}