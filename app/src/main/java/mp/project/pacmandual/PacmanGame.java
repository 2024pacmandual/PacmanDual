
package mp.project.pacmandual;

import java.util.ArrayList;
import java.util.List;


public class PacmanGame {
    private int pacmanX;
    private int pacmanY;

    private int score;
    private String inputButton = "NONE";
    private int timeAfterCaught = 0; //일시적 무적 상태


    private final List<Ghost> ghosts;
    private Map map;
    private final LifeManager lifeCounter;
    private StageContainer stage;

    public PacmanTimer timer;
    private PacmanState g_state;

    public enum PacmanState {
        Init(0), Running(1), NextStage(2), finished(-1);
        private final int value;
        private PacmanState(int value) { this.value = value; }
        public int value() { return value; }
    }


    public PacmanGame(int level) {
        g_state = PacmanState.Init;
        ghosts = new ArrayList<>();
        stage = new StageContainer();

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
        int n_ghost = level;

        array = stage.getMapArray(level);

        map = new Map(array, n_ghost); //setMap_lv3까지 계획

        List<int[]> coords = map.get_ghostSpawnsCoords();

        for (int[] coord : coords) {
            ghosts.add(new Ghost(map, coord[0], coord[1]));
        }
    }

    public synchronized PacmanState updateGameState() {
        if (g_state == PacmanState.Init) {
            timer.startTimer();
            g_state = PacmanState.Running;
        }
        if (timer.getTimerFlag() == 0) timer.startTimer(); //onPause -> onResume
        movePacman();
        if ((map.getDotCount() <= 0) || (timer.getTimerFlag() < 0)){
            if (lifeCounter.getLives() > 0) g_state = PacmanState.NextStage;

        } else if (lifeCounter.getLives() <= 0) {
            g_state = PacmanState.finished;
        }
        if(getCaught() && timeAfterCaught > 4) {
            lifeCounter.decreaseLife();
            timeAfterCaught = 0;
        } //ghost한테 잡힘
        for (Ghost ghost : ghosts) {
            ghost.move(map); // 고스트는 매번 타일 간 이동 후 동작
        }
        if(getCaught() && timeAfterCaught > 4) {
            lifeCounter.decreaseLife();
            timeAfterCaught = 0;
        } //ghost한테 잡힘
        timeAfterCaught++;
        return g_state;
    }



    private void movePacman() {
        int newY = pacmanY;
        int newX = pacmanX;
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
        if (y < 0 || y >= map.get_dy() || x < 0 || x >= map.get_dx()) {
            return false;
        }
        Tile tile = map.getTile(y, x);
        if (tile.isWall())
            return false;
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


    public int[] getResult(){
        int remaining_life = lifeCounter.getLives();
        int final_score = (remaining_life * 10) + score;
        int remaining_time = timer.getRemainingTime();

        int[] tot_result = {final_score, remaining_time};
        return tot_result;
    }

    public ScreenState getScreen() {
//        if (map.get_Grid() == null) {
//            return new ScreenState(new Tile[0][0], pacmanX, pacmanY, ghosts, score, lifeCounter.getLives(), timer.getRemainingTime());
//        }
        return new ScreenState(
                map.get_Grid(), // 현재 맵 데이터
                pacmanX,        // 팩맨의 X 좌표
                pacmanY,        // 팩맨의 Y 좌표
                ghosts,         // 고스트 리스트
                score,           // 현재 점수
                lifeCounter.getLives(),
                timer.getRemainingTime(),
                g_state
        );
    }

    public static class ScreenState {
        private final Tile[][] grid;  // 맵 데이터
        private final int pacmanX;       // 팩맨의 X 좌표
        private final int pacmanY;       // 팩맨의 Y 좌표
        private final List<Ghost> ghosts; // 고스트 리스트
        private final int score;         // 현재 점수
        private final int life;
        private final int time;
        private final PacmanState return_state;
        // ScreenState 생성자
        public ScreenState(Tile[][] grid, int pacmanX, int pacmanY, List<Ghost> ghosts, int score, int life, int time, PacmanState state) {
            this.grid = grid;
            this.pacmanX = pacmanX;
            this.pacmanY = pacmanY;
            this.ghosts = ghosts;
            this.score = score;
            this.life = life;
            this.time = time;
            this.return_state = state;
        }


        // 맵 데이터를 반환
        public Tile[][] getMapGrid() {
            return grid;
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
        public PacmanState getState() {
            return return_state;
        }
    }
}

