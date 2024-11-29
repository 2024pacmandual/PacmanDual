package mp.project.pacmandual;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class PacmanView extends View {
    private Map map; // 맵 객체
    private String gameMode;

    private ImageButton buttonUp;
    private ImageButton buttonDown;
    private ImageButton buttonLeft;
    private ImageButton buttonRight;

    private final Paint wallPaint = new Paint();
    private final Paint dotPaint = new Paint();
    private final Paint pacmanPaint = new Paint();
    private final Paint ghostPaint = new Paint();

    public PacmanView(Context context, String gameMode) {
        super(context);
        this.gameMode = gameMode;

        // 페인트 설정
        wallPaint.setColor(Color.BLACK);
        dotPaint.setColor(Color.RED);
        pacmanPaint.setColor(Color.YELLOW);
        ghostPaint.setColor(Color.BLUE);

        // 버튼 초기화 및 이벤트 리스너 설정
        if (context instanceof Activity) {
            Activity activity = (Activity) context;

            // 버튼 참조
            buttonUp = activity.findViewById(R.id.buttonUp);
            buttonDown = activity.findViewById(R.id.buttonDown);
            buttonLeft = activity.findViewById(R.id.buttonLeft);
            buttonRight = activity.findViewById(R.id.buttonRight);

            // 버튼 이벤트 설정
            if (buttonUp != null) {
                buttonUp.setOnTouchListener((v, event) -> onTouchAccept(v, event, "UP"));
            }
            if (buttonDown != null) {
                buttonDown.setOnTouchListener((v, event) -> onTouchAccept(v, event, "DOWN"));
            }
            if (buttonLeft != null) {
                buttonLeft.setOnTouchListener((v, event) -> onTouchAccept(v, event, "LEFT"));
            }
            if (buttonRight != null) {
                buttonRight.setOnTouchListener((v, event) -> onTouchAccept(v, event, "RIGHT"));
            }
        }
    }

    // 터치 이벤트 처리 메서드
    public boolean onTouchAccept(View v, MotionEvent event, String direction) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 방향 키에 따라 로그 출력
            Log.d("PacmanView", "Button pressed: " + direction);

            // PacmanView 동작 처리 (예: 맵 업데이트, 플레이어 이동 등)
            if (gameMode.equals("SINGLE")) {
                // Classic 모드 동작 처리
                Log.d("PacmanView", "Game mode: SINGLE, moving " + direction);
            } else if (gameMode.equals("DUAL")) {
                // Arcade 모드 동작 처리
                Log.d("PacmanView", "Game mode: DUAL, moving " + direction);
            }
        }

        // 터치 이벤트를 소비하여 버튼 자체 이벤트가 처리되지 않도록 설정
        return true;
    }
}
