package mp.project.pacmandual;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;


public class PacmanView extends View {
    private Bitmap pacmanBitmap;
    private Bitmap ghostBitmap;
    private Map map;
    private PacmanGame.ScreenState screen;
    private String gameMode;

    private Paint dotPaint, wallPaint;

    //private PacmanGame pacmanGame;
    private List<Ghost> ghosts;
    private int tileSize;

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
    }

    public void getScreenState(PacmanGame.ScreenState screenState){
        screen = screenState;
        ghosts = screen.getGhosts();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //if (pacmanGame == null) return;

        drawMap(canvas);

        int pacmanX = screen.getPacmanX() * tileSize;
        int pacmanY = screen.getPacmanY() * tileSize;
        canvas.drawBitmap(pacmanBitmap, pacmanX, pacmanY, null);

        drawGhosts(canvas);
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
                    float dotRadius = tileSize * 0.6f;
                    canvas.drawCircle(left + dotRadius, top + dotRadius, dotRadius, dotPaint);
                }
            }
        }
    }

    private void drawGhosts(Canvas canvas) {
        if (ghosts == null) return;

        for (Ghost ghost : ghosts) {
            int ghostX = ghost.getGhostX() * tileSize;
            int ghostY = ghost.getGhostY() * tileSize;
            canvas.drawBitmap(ghostBitmap, ghostX, ghostY, null);
        }
    }



}

//
//    // 터치 이벤트 처리 메서드
//    public boolean onTouchAccept(View v, MotionEvent event, String direction) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            // 방향 키에 따라 로그 출력
//            Log.d("PacmanView", "Button pressed: " + direction);
//
//            // PacmanView 동작 처리 (예: 맵 업데이트, 플레이어 이동 등)
//            if (gameMode.equals("SINGLE")) {
//                // Classic 모드 동작 처리
//                Log.d("PacmanView", "Game mode: SINGLE, moving " + direction);
//            } else if (gameMode.equals("DUAL")) {
//                // Arcade 모드 동작 처리
//                Log.d("PacmanView", "Game mode: DUAL, moving " + direction);
//            }
//        }
//
//        // 터치 이벤트를 소비하여 버튼 자체 이벤트가 처리되지 않도록 설정
//        return true;
//    }
//}
