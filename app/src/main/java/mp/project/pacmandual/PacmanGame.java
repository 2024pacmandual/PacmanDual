
package mp.project.pacmandual;

import java.util.ArrayList;
import java.util.List;


public class PacmanGame {
    private int pacmanX;
    private int pacmanY;
    private String pacmanDirection = "NONE";
    private int score;
    private String inputButton = "NONE";
    private int timeAfterCaught = 0; //일시적 무적 상태
    private boolean is_first_get_screen = true;


    private List<Ghost> ghosts;
    private Map map;
    private LifeManager lifeCounter;
    private final StageContainer stage = new StageContainer();
    private int level = 1;

    public PacmanTimer timer;
    private PacmanState g_state;

    public enum PacmanState {
        Init(0), Running(1), NextStage(2), finished(-1);
        private final int value;
        private PacmanState(int value) { this.value = value; }
        public int value() { return value; }
    }


    public PacmanGame() {
        g_state = PacmanState.Init;
        ghosts = new ArrayList<>();

        initializeMap(level);
        this.score = 0;

        int[] pacmanSpawn = map.get_pacmanSpawnCoord();
        pacmanY = pacmanSpawn[0];
        pacmanX = pacmanSpawn[1];
        lifeCounter = new LifeManager();


        timer = new PacmanTimer(level*30);
    }

    private void initializeMap(int level) {
        int[][] array;
        int n_ghost = level;
        if((array = stage.getMapArray(level)) == null) {
            return;
        }
        map = new Map(array, n_ghost); //setMap_lv3까지 계획
        List<int[]> coords = map.get_ghostSpawnsCoords();

        for (int[] coord : coords) {
            ghosts.add(new Ghost(map, coord[0], coord[1]));
        }

    }

    public PacmanState updateGameState() {
        if (g_state == PacmanState.Init) g_state = PacmanState.Running;

        movePacman();
        if (lifeCounter.getLives() <= 0){
            g_state = PacmanState.finished;
        } else if (level < 3 && ((map.getDotCount() <= 0) || (timer.getTimerFlag() < 0))) {
            if (lifeCounter.getLives() > 0) g_state = PacmanState.NextStage;
        } else if ((timer.getTimerFlag() < 0) && level == 3) {
            g_state = PacmanState.finished;
        }
        if(getCaught() && timeAfterCaught > 4) {
            lifeCounter.decreaseLife();
            timeAfterCaught = 0;
        } //ghost한테 잡힘
        for (Ghost ghost : ghosts) {
            if (ghost.speed == 1){
                if (ghost.type == 1) ghost.speed--;
                ghost.move(map, pacmanY, pacmanX);
            }
            else {
                ghost.speed++;
            }
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
            case "UP": newY -= 1; pacmanDirection = "UP"; break;
            case "DOWN": newY += 1; pacmanDirection = "DOWN"; break;
            case "LEFT" : newX -= 1; pacmanDirection = "LEFT"; break;
            case "RIGHT" : newX += 1; pacmanDirection = "RIGHT"; break;
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

    public ScreenState getScreen() {
        if(is_first_get_screen) {
            is_first_get_screen = false;
            return new ScreenState(
                    map.get_Grid(), // 현재 맵 데이터
                    pacmanX,        // 팩맨의 X 좌표
                    pacmanY,        // 팩맨의 Y 좌표
                    ghosts,         // 고스트 리스트
                    score,           // 현재 점수
                    lifeCounter.getLives(),
                    timer.getRemainingTime(),
                    true
            );
        }
        return new ScreenState(
                map.get_Grid(), // 현재 맵 데이터
                pacmanX,        // 팩맨의 X 좌표
                pacmanY,        // 팩맨의 Y 좌표
                pacmanDirection,
                ghosts,         // 고스트 리스트
                score,           // 현재 점수
                lifeCounter.getLives(),
                timer.getRemainingTime(),
                false
        );
    }

    public int[] getResult(){
        int remaining_life = lifeCounter.getLives();
        int final_score = (remaining_life * 30) + score;
        int remaining_time = timer.getRemainingTime();

        int[] tot_result = {final_score, remaining_time};
        return tot_result;
    }

    public int toNextLevel(){
        this.level++; // 레벨 증가
        g_state = PacmanState.Init;

        ghosts.clear();
        ghosts = new ArrayList<>();

        timer.setTime(level*30);
        initializeMap(level);
        int[] pacmanSpawn = map.get_pacmanSpawnCoord();
        pacmanY = pacmanSpawn[0];
        pacmanX = pacmanSpawn[1];

        timer.startTimer();

        is_first_get_screen = true;

        inputButton = "NONE";

        if (level > 3) return 0;
        return 1;
    }

    public static class ScreenState {
        private final Tile[][] mapArray;  // 맵 데이터
        private final int pacmanX;       // 팩맨의 X 좌표
        private final int pacmanY;       // 팩맨의 Y 좌표
        private final List<Ghost> ghosts; // 고스트 리스트
        private final int score;         // 현재 점수
        private final int life;
        private final int time;
        private final boolean isInit;
        private final String pacmanDirection;
        // ScreenState 생성자
        public ScreenState(Tile[][] mapArray, int pacmanX, int pacmanY, String pacmanDirection, List<Ghost> ghosts, int score, int life, int time, boolean isInit) {
            this.mapArray = mapArray;
            this.pacmanX = pacmanX;
            this.pacmanY = pacmanY;
            this.pacmanDirection = pacmanDirection;
            this.ghosts = ghosts;
            this.score = score;
            this.life = life;
            this.time = time;
            this.isInit = isInit;
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

        // 팩맨의 이동 방향 반환
        public String getPacmanDirection() { return pacmanDirection; }

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
        public boolean isInit(){
            return isInit;
        }
    }
}