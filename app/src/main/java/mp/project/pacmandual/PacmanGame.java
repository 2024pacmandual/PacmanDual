
package mp.project.pacmandual;

import android.view.MotionEvent;

public class PacmanGame {
    private int[][] maze;
    private int pacmanX;
    private int pacmanY;

    public PacmanGame() {
        this.maze = new int[10][10]; // 예시로 10x10 크기의 미로
        this.pacmanX = 0; // Pacman의 초기 X 위치
        this.pacmanY = 0; // Pacman의 초기 Y 위치
        initializeMaze();
    }

    private void initializeMaze() {
        // 미로 초기화 로직 (1: 벽, 0: 빈 공간)
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[y].length; x++) {
                maze[y][x] = (x % 2 == 0) ? 1 : 0; // 예시로 간단한 미로 생성
            }
        }
    }

    public void movePacman(int dx, int dy) {
        // Pacman의 새로운 위치 계산
        int newX = pacmanX + dx;
        int newY = pacmanY + dy;

        // 이동 가능한지 검사
        if (isMoveValid(newX, newY)) {
            pacmanX = newX;
            pacmanY = newY;
        }
    }

    private boolean isMoveValid(int x, int y) {
        // 미로의 경계를 확인
        if (x < 0 || x >= maze[0].length || y < 0 || y >= maze.length) {
            return false;
        }
        return maze[y][x] == 0; // 빈 공간으로 이동 가능한지 확인
    }

    public ScreenState getScreen() {
        return new ScreenState(maze, pacmanX, pacmanY);
    }

    public void onTouchEvent(MotionEvent event) {
        // 터치 이벤트 처리 (여기서는 간단히 아래쪽으로 이동)
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // 실제 터치에 따라 dx, dy 값 계산
            float dx = event.getX() - pacmanX * 100;
            float dy = event.getY() - pacmanY * 100;

            // 간단히 dx, dy를 정수로 변환하여 이동
            movePacman((int) Math.signum(dx), (int) Math.signum(dy));
        }
    }

    // 스크린 상태를 나타내는 내부 클래스
    public static class ScreenState {
        public final int[][] maze;
        public final int pacmanX;
        public final int pacmanY;

        public ScreenState(int[][] maze, int pacmanX, int pacmanY) {
            this.maze = maze;
            this.pacmanX = pacmanX;
            this.pacmanY = pacmanY;
        }
    }
}
