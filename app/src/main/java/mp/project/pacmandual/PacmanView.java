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
    private Bitmap spectorBitmap;
    private Bitmap lifeBitmap;

    private PacmanGame.ScreenState screen;
    private String gameMode;

    private Paint dotPaint, wallPaint, bgPaint;

    private List<Ghost> ghosts;
    private int tileSize;

    // Pacman 위치와 목표 위치
    private float pacmanX, pacmanY;
    private float targetPacmanX, targetPacmanY;
    private float pacmanSpeed = 20.0f; // 픽셀 단위 이동 속도

    private boolean isPacmanMoving = false; // 팩맨이 현재 이동 중인지 확인
    private boolean isinit;

    // Ghost 위치와 목표 위치
    private Map<Ghost, float[]> ghostPositions; // 각 고스트의 현재 위치
    private float ghostSpeed = 6.0f; // 고스트의 픽셀 단위 이동 속도

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
        // 리소스에서 비트맵을 불러오고 타일 크기에 맞게 리사이즈
        tileSize = 36;
        Bitmap originalPacmanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
        pacmanBitmap = Bitmap.createScaledBitmap(originalPacmanBitmap, tileSize, tileSize, true);

        Bitmap originalGhostBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
        ghostBitmap = Bitmap.createScaledBitmap(originalGhostBitmap, tileSize, tileSize, true);

        Bitmap SpectorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mare);
        spectorBitmap = Bitmap.createScaledBitmap(SpectorBitmap, tileSize, tileSize, true);

        Bitmap originalLifeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        lifeBitmap = Bitmap.createScaledBitmap(originalLifeBitmap, tileSize, tileSize, true);

        wallPaint = new Paint();
        wallPaint.setColor(0xFF0000FF); // 파란색 (벽)
        dotPaint = new Paint();
        dotPaint.setColor(0xFFFFD700); // 금색 (도트)
        dotPaint.setStyle(Paint.Style.FILL);
        bgPaint = new Paint();
        bgPaint.setColor(0xFF000000);

        pacmanX = 0;
        pacmanY = 0;
        targetPacmanX = 0;
        targetPacmanY = 0;

        ghostPositions = new HashMap<>();
    }

    public void getScreenState(PacmanGame.ScreenState screenState) {
        screen = screenState;
        ghosts = screen.getGhosts();
        isinit = screen.isInit();
        //Log.d("from view", "is Init " + screen.isInit());
        if (screen.isInit()) {
            pacmanX = screen.getPacmanX() * tileSize;
            pacmanY = screen.getPacmanY() * tileSize;
            targetPacmanX = pacmanX;
            targetPacmanY = pacmanY;
            //isinit = false; // 초기 스폰 완료
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
        //Log.d("view", "onDraw");
        super.onDraw(canvas);
        if (screen == null) return;
        drawMap(canvas);
        drawPacman(canvas);
        drawGhosts(canvas);
        drawLives(canvas);
        drawScore(canvas);
        drawTimer(canvas);

        // 애니메이션 업데이트
        updateAnimation();
        invalidate(); // 다음 프레임을 요청
    }

    private void drawMap(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);

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
                if (ghost.type == 1) {
                    canvas.drawBitmap(spectorBitmap, pos[0], pos[1], null);
                    continue;
                }
                canvas.drawBitmap(ghostBitmap, pos[0], pos[1], null);
            }
        }
    }

    private void drawLives(Canvas canvas) {
        int lifeCount = screen.getLife(); // 남은 라이프 수
        int startX = 20; // 시작 X 좌표
        int startY = 20; // 시작 Y 좌표
        int spacing = 10; // 아이콘 간 간격

        for (int i = 0; i < lifeCount; i++) {
            int x = startX + i * (lifeBitmap.getWidth() + spacing);
            canvas.drawBitmap(lifeBitmap, x, startY, null);
        }
    }

    private void drawScore(Canvas canvas) {
        Paint scorePaint = new Paint();
        scorePaint.setColor(0xFF006600);
        scorePaint.setTextSize(50);
        scorePaint.setFakeBoldText(true);

        String scoreText = "Score: " + screen.getScore();
        canvas.drawText(scoreText, 20, 100, scorePaint); // 화면 상단에 점수 표시
    }

    private void drawTimer(Canvas canvas) {
        Paint timerPaint = new Paint();
        timerPaint.setColor(0xFFFF0000); // 빨간색
        timerPaint.setTextSize(50); // 텍스트 크기
        timerPaint.setFakeBoldText(true);

        String timerText = "Time: " + screen.getTime() + "s";
        canvas.drawText(timerText, 20, 160, timerPaint); // 화면 상단에 시간 표시
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