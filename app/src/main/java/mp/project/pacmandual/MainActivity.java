package mp.project.pacmandual;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private PacmanGame game;
    private PacmanView pacmanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start); // start.xml을 먼저 로드

        Button startGameButton = findViewById(R.id.startGameButton);
        Button exitGameButton = findViewById(R.id.exitGameButton);
        RadioButton singlePlayerButton = findViewById(R.id.singlePlayerButton);
        RadioButton twoPlayerButton = findViewById(R.id.twoPlayerButton);

        // 게임 시작 버튼 클릭 시
        startGameButton.setOnClickListener(v -> {
            game = new PacmanGame();
            pacmanView = new PacmanView(MainActivity.this);
            setContentView(pacmanView);  // 게임 화면으로 전환
        });

        // 게임 종료 버튼 클릭 시
        exitGameButton.setOnClickListener(v -> {
            finish();  // 앱 종료
        });
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
