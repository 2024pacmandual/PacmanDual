package mp.project.pacmandual;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private PacmanGame game;
    private PacmanView pacmanView;

    private String gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameMode = getIntent().getStringExtra("GAME_MODE");

        if (gameMode.equals("SINGLE")) {
            Toast.makeText(this, "1인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
        } else if (gameMode.equals("TWO_PLAYER")) {
            Toast.makeText(this, "2인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
        }

        pacmanView = new PacmanView(this, gameMode);
        setContentView(pacmanView);

        Log.i("MainActivity", "onCreate");

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 초기 상태로 뷰 업데이트 (필요시)
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.onTouchEvent(event); // 이벤트를 PacmanGame으로 전달
        updateView(); // 게임 뷰 갱신
        return true;
    }

    private void updateView() {
        PacmanGame.ScreenState state = game.getScreen(); // 현재 게임 상태를 가져옴
        pacmanView.update(state); // 게임 상태를 뷰에 전달하여 업데이트
    }
}
