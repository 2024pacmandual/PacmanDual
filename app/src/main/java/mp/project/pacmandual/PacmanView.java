package mp.project.pacmandual;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class PacmanView extends SurfaceView {
    private Paint paint;

    public PacmanView(Context context) {
        super(context);
        this.paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 현재 스크린 상태를 그리기 위해 update 메소드를 호출합니다.
    }

    public void update(PacmanGame.ScreenState state) {
        // 매개변수로 받은 게임 상태를 사용하여 화면을 그립니다.
        invalidate(); // 뷰를 무효화하고 다시 그리도록 요청
        drawCurrentScreen(state); // 게임 상태를 그립니다.
    }

    private void drawCurrentScreen(PacmanGame.ScreenState state) {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas == null) return;

        // 미로 그리기
        drawMaze(canvas, state.map);
        drawPacman(canvas, state.pacmanX, state.pacmanY);

        getHolder().unlockCanvasAndPost(canvas);
    }


    private void drawPacman(Canvas canvas, int pacmanX, int pacmanY) {
        paint.setColor(Color.YELLOW);
        int drawX = pacmanX * 100 + 50;
        int drawY = pacmanY * 100 + 50;
        canvas.drawCircle(drawX, drawY, 40, paint);
    }
}
