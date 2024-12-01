package mp.project.pacmandual;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacmanView extends View {
    private Bitmap pacmanBitmap;
    private Bitmap ghostBitmap;

    private PacmanGame.ScreenState screen;
    private String gameMode;

    private Paint dotPaint, wallPaint;

    private List<Ghost> ghosts;
    private int tileSize;

    // Pacman 위치와 목표 위치
    private float pacmanX, pacmanY;
    private float targetPacmanX, targetPacmanY;
    private float pacmanSpeed = 20.0f; // 픽셀 단위 이동 속도

    private boolean isPacmanMoving = false; // 팩맨이 현재 이동 중인지 확인
    private boolean isinit = true;

    // Ghost 위치와 목표 위치
    private Map<Ghost, float[]> ghostPositions; // 각 고스트의 현재 위치
    private float ghostSpeed = 8.0f; // 고스트의 픽셀 단위 이동 속도

    public PacmanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PacmanView(MainActivity context) {
        super(context);
        this.gameMode = gameMode;
        init();
    }

    public void init() {
        pacmanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
        ghostBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);

        wallPaint = new Paint();
        wallPaint.setColor(0xFF0000FF); // 파란색 (벽)
        dotPaint = new Paint();
        dotPaint.setColor(0xFFFFD700); // 금색 (도트)
        dotPaint.setStyle(Paint.Style.FILL);

        tileSize = 64;

        pacmanX = 0;
        pacmanY = 0;
        targetPacmanX = 0;
        targetPacmanY = 0;

        ghostPositions = new HashMap<>();
    }

    public void getScreenState(PacmanGame.ScreenState screenState) {
        screen = screenState;
        ghosts = screen.getGhosts();

        if (isinit) {
            pacmanX = screen.getPacmanX() * tileSize;
            pacmanY = screen.getPacmanY() * tileSize;
            targetPacmanX = pacmanX;
            targetPacmanY = pacmanY;
            isinit = false; // 초기 스폰 완료
        } else if (!isPacmanMoving) {
            targetPacmanX = screen.getPacmanX() * tileSize;
            targetPacmanY = screen.getPacmanY() * tileSize;
        }

        for (Ghost ghost : ghosts) {
            float[] currentPos = ghostPositions.getOrDefault(ghost, new float[]{ghost.getGhostX() * tileSize, ghost.getGhostY() * tileSize});
            ghostPositions.put(ghost, currentPos);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMap(canvas);
        drawPacman(canvas);
        drawGhosts(canvas);

        // 애니메이션 업데이트
        updateAnimation();
        invalidate(); // 다음 프레임을 요청
    }

    private void drawMap(Canvas canvas) {
        Tile[][] mapArray = screen.getMapArray();
        for (int y = 0; y < mapArray.length; y++) {
            for (int x = 0; x < mapArray[y].length; x++) {
                Tile tile = mapArray[y][x];
                int left = x * tileSize;
                int top = y * tileSize;
                int right = left + tileSize;
                int bottom = top + tileSize;
                if (tile.isWall()) {
                    canvas.drawRect(left, top, right, bottom, wallPaint);
                } else if (tile.hasDot()) {
                    float dotRadius = tileSize * 0.3f;
                    canvas.drawCircle(left + tileSize / 2f, top + tileSize / 2f, dotRadius, dotPaint);
                }
            }
        }
    }

    private void drawPacman(Canvas canvas) {
        canvas.drawBitmap(pacmanBitmap, pacmanX, pacmanY, null);
    }

    private void drawGhosts(Canvas canvas) {
        if (ghosts == null) return;

        for (Ghost ghost : ghosts) {
            float[] pos = ghostPositions.get(ghost);
            if (pos != null) {
                canvas.drawBitmap(ghostBitmap, pos[0], pos[1], null);
            }
        }
    }

    private void updateAnimation() {
        // 팩맨 이동
        if (pacmanX != targetPacmanX || pacmanY != targetPacmanY) {
            isPacmanMoving = true;

            // X축 이동
            if (pacmanX < targetPacmanX) {
                pacmanX = Math.min(pacmanX + pacmanSpeed, targetPacmanX);
            } else if (pacmanX > targetPacmanX) {
                pacmanX = Math.max(pacmanX - pacmanSpeed, targetPacmanX);
            }

            // Y축 이동
            if (pacmanY < targetPacmanY) {
                pacmanY = Math.min(pacmanY + pacmanSpeed, targetPacmanY);
            } else if (pacmanY > targetPacmanY) {
                pacmanY = Math.max(pacmanY - pacmanSpeed, targetPacmanY);
            }
        } else {
            isPacmanMoving = false; // 목표 위치에 도달하면 이동 종료
        }

        // 고스트의 위치 업데이트
        for (Ghost ghost : ghosts) {
            float[] pos = ghostPositions.get(ghost);
            if (pos != null) {
                float targetX = ghost.getGhostX() * tileSize;
                float targetY = ghost.getGhostY() * tileSize;

                // 고스트 X축 이동
                if (pos[0] < targetX) {
                    pos[0] = Math.min(pos[0] + ghostSpeed, targetX);
                } else if (pos[0] > targetX) {
                    pos[0] = Math.max(pos[0] - ghostSpeed, targetX);
                }

                // 고스트 Y축 이동
                if (pos[1] < targetY) {
                    pos[1] = Math.min(pos[1] + ghostSpeed, targetY);
                } else if (pos[1] > targetY) {
                    pos[1] = Math.max(pos[1] - ghostSpeed, targetY);
                }

                ghostPositions.put(ghost, pos); // 업데이트된 위치 저장
            }
        }
    }
}
