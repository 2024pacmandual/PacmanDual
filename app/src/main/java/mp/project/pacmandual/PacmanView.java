package mp.project.pacmandual;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class PacmanView extends SurfaceView {
    private Map map;
    private int pacmanX;
    private int pacmanY;
    private final Paint wallPaint = new Paint();
    private final Paint dotPaint = new Paint();
    private final Paint pacmanPaint = new Paint();
    private final Paint ghostPaint = new Paint();
    private int tileSize = 50;

    public PacmanView(Context context) {
        super(context);
        wallPaint.setColor(Color.BLACK);
        dotPaint.setColor(Color.RED);
        pacmanPaint.setColor(Color.YELLOW);
    }


    public void update(PacmanGame.ScreenState state) {
        this.map = state.map;
        this.pacmanX = state.pacmanX;
        this.pacmanY = state.pacmanY;
        invalidate(); // 뷰를 다시 그리도록 요청
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (map == null) return;

        Tile[][] grid = map.get_Grid();
        int dy = map.get_dy();
        int dx = map.get_dx();

        // 맵 그리기
        for (int y = 0; y < dy; y++) {
            for (int x = 0; x < dx; x++) {
                int left = x * tileSize;
                int top = y * tileSize;
                int right = left + tileSize;
                int bottom = top + tileSize;

                Tile tile = grid[y][x];
                if (tile.isWall()) {
                    canvas.drawRect(left, top, right, bottom, wallPaint);
                } else if (tile.hasDot()) {
                    float centerX = left + tileSize / 2.0f;
                    float centerY = top + tileSize / 2.0f;
                    canvas.drawCircle(centerX, centerY, tileSize / 5.0f, dotPaint);
                }
            }
        }

        // Pacman 그리기
        float pacmanCenterX = pacmanX * tileSize + tileSize / 2.0f;
        float pacmanCenterY = pacmanY * tileSize + tileSize / 2.0f;
        canvas.drawCircle(pacmanCenterX, pacmanCenterY, tileSize / 2.5f, pacmanPaint);

        // 고스트 그리기

    }
}