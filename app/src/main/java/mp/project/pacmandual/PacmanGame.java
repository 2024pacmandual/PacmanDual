
package mp.project.pacmandual;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PacmanGame {
    private int pacmanX;
    private int pacmanY;

    private int score;
    private boolean RunningFlag = true;
    private String inputButton = "NONE";
    private int timeAfterCaught = 0;


    private final List<Ghost> ghosts;
    private Map map;
    private final LifeManager lifeCounter;
    private final StageContainer stage = new StageContainer();;

    public PacmanTimer timer;




    public PacmanGame(int level) {

        ghosts = new ArrayList<>();

        initializeMap(level);
        this.score = 0;

        int[] pacmanSpawn = map.get_pacmanSpawnCoord();
        pacmanY = pacmanSpawn[0];
        pacmanX = pacmanSpawn[1];
        lifeCounter = new LifeManager();
        timer = new PacmanTimer(300);


    }

    private void initializeMap(int level) {
        int[][] array;
        int n_ghost;
        if (level > 2) n_ghost = 4;
        else n_ghost = 3;

        array = stage.getMapArray(level);

        map = new Map(array, n_ghost); //setMap_lv3까지 계획
        List<int[]> coords = map.get_ghostSpawnsCoords();

        for (int[] coord : coords) {
            Log.d("coords", "coord :  " + Arrays.toString(coord));
        }
        for (int[] coord : coords) {
            ghosts.add(new Ghost(map, coord[0], coord[1]));
        }

    }

    public void updateGameState() {

        movePacman();
        if ((map.getDotCount() <= 0) || (lifeCounter.getLives() <= 0) || (timer.getTimerFlag() < 0)){
            RunningFlag = false;
        }
        for (Ghost ghost : ghosts) {
            ghost.move(map); // 고스트는 매번 타일 간 이동 후 동작
        }

        if(getCaught() && timeAfterCaught > 4) {
            lifeCounter.decreaseLife();
            timeAfterCaught = 0;
        } //ghost한테 잡힘
        timeAfterCaught++;
    }



    private void movePacman() {
        //Log.d("Mv", "mv to :" + inputButton);
        int newY = pacmanY;
        int newX = pacmanX;
        Log.d("inputButton", "inputButton = " + inputButton);
        switch (inputButton){
            case "UP": newY -= 1; break;
            case "DOWN": newY += 1; break;
            case "LEFT" : newX -= 1; break;
            case "RIGHT" : newX += 1; break;
            default: return;
        }


        if (isMoveValid(newY, newX)) {
            Tile tile = map.getTile(newY, newX);
            if (tile.hasDot()) {
                tile.consumeDot();
                score += 1;
            }
            pacmanX = newX;
            pacmanY = newY;
        }
        //inputButton = "NONE"; //테스트용 코드

    }

    private boolean isMoveValid(int y, int x) {
        //Log.d("Mv", "val");
        // 맵 경계를 벗어나지 않는지 확인
        if (y < 0 || y >= map.get_dy() || x < 0 || x >= map.get_dx()) {

            Log.d("Mv", "new X:" + x + ", dx : " + map.get_dx());
            return false;
        }
        Tile tile = map.getTile(y, x);
        if (tile.isWall()) {
            Log.d("Mv", "wall");
            return false;
        }
        return true;
    }

    private boolean getCaught(){
        for (Ghost g : ghosts){
            if (g.getGhostY() == pacmanY && g.getGhostX() == pacmanX) return true;
        }
        return false;
    }


    public void onTouchAccept(String action){
        inputButton = action;
    };

    public boolean gameOnRun(){
        return this.RunningFlag;
    }

    // ScreenState 생성 메서드
    public ScreenState getScreen() {
        return new ScreenState(
                map.get_Grid(), // 현재 맵 데이터
                pacmanX,        // 팩맨의 X 좌표
                pacmanY,        // 팩맨의 Y 좌표
                ghosts,         // 고스트 리스트
                score,           // 현재 점수
                lifeCounter.getLives(),
                timer.getRemainingTime()
        );
    }

    public int[] getResult(){
        int remaining_life = lifeCounter.getLives();
        int final_score = (remaining_life * 10) + score;
        int remaining_time = timer.getRemainingTime();

        int[] tot_result = {final_score, remaining_time};
        return tot_result;
    }

    public static class ScreenState {
        private final Tile[][] mapArray;  // 맵 데이터
        private final int pacmanX;       // 팩맨의 X 좌표
        private final int pacmanY;       // 팩맨의 Y 좌표
        private final List<Ghost> ghosts; // 고스트 리스트
        private final int score;         // 현재 점수
        private final int life;
        private final int time;
        // ScreenState 생성자
        public ScreenState(Tile[][] mapArray, int pacmanX, int pacmanY, List<Ghost> ghosts, int score, int life, int time) {
            this.mapArray = mapArray;
            this.pacmanX = pacmanX;
            this.pacmanY = pacmanY;
            this.ghosts = ghosts;
            this.score = score;
            this.life = life;
            this.time = time;
        }


        // 맵 데이터를 반환
        public Tile[][] getMapArray() {
            return mapArray;
        }

        // 팩맨의 X 좌표 반환
        public int getPacmanX() {
            return pacmanX;
        }

        // 팩맨의 Y 좌표 반환
        public int getPacmanY() {
            return pacmanY;
        }

        // 고스트 리스트 반환
        public List<Ghost> getGhosts() {
            return ghosts;
        }

        // 현재 점수 반환
        public int getScore() {
            return score;
        }

        public int getLife() {
            return life;
        }

        public int getTime() {
            return time;
        }
    }
}

