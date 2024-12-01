package mp.project.pacmandual;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private PacmanGame game;
    private PacmanView pacmanView;

    ImageView buttonUp;
    ImageView buttonDown;
    ImageView buttonLeft;
    ImageView buttonRight;

    private String gameMode;

    private Thread gameLoopThread;
    private boolean isRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameMode = getIntent().getStringExtra("GAME_MODE");

//        if (gameMode == null) {
//            Toast.makeText(this, "게임 모드가 전달되지 않았습니다.", Toast.LENGTH_SHORT).show();
//            finish(); // 액티비티 종료 또는 기본 동작 설정
//            return;
//        }
//
//        if (gameMode.equals("SINGLE")) {
//            Toast.makeText(this, "1인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
//        } else if (gameMode.equals("TWO_PLAYER")) {
//            Toast.makeText(this, "2인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
//        }

        // 일단 1인 모드만 가능하게 설정
        if (gameMode == null || !gameMode.equals("SINGLE")) {
            Toast.makeText(this, "1인 모드로 설정합니다.", Toast.LENGTH_SHORT).show();
            gameMode = "SINGLE"; // 기본값 설정
        }

        game = new PacmanGame(); // gameMode에 따라 게임을 초기화
        //pacmanView = new PacmanView(this);

        setContentView(R.layout.activity_main);

        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);

        pacmanView = new PacmanView(this);
        pacmanView = findViewById(R.id.pacmanView);
        //setContentView(pacmanView);

        buttonUp.setOnTouchListener((v, event) -> {
            game.onTouchAccept(event, "UP");
            v.performClick();
            return true; // 이벤트를 소비
        });

        buttonDown.setOnTouchListener((v, event) -> {
            game.onTouchAccept(event, "DOWN");
            v.performClick();
            return true;
        });

        buttonLeft.setOnTouchListener((v, event) -> {
            game.onTouchAccept(event, "LEFT");
            return true;
        });

        buttonRight.setOnTouchListener((v, event) -> {
            game.onTouchAccept(event, "RIGHT");
            return true;
        });
        //startGameLoop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        startGameLoop();
        // 초기 상태로 뷰 업데이트 (필요시)
    }

    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void startGameLoop() {
        if (gameLoopThread == null || !gameLoopThread.isAlive()) {
            gameLoopThread = new Thread(() -> {
                while (isRunning) {
                    try {
                        game.updateGameState();
                        pacmanView.getScreenState(game.getScreen());
                        runOnUiThread(() -> {
                            pacmanView.invalidate(); // PacmanView의 onDraw() 호출
                        });
                        Thread.sleep(100); // 게임 루프 간격 설정
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            gameLoopThread.start();
        }

    }

//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        game.onTouchEvent(event); // 이벤트를 PacmanGame으로 전달
//        updateView(); // 게임 뷰 갱신
//        return true;
//    }
//
//    private void updateView() {
//        PacmanGame.ScreenState state = game.getScreen(); // 현재 게임 상태를 가져옴
//        pacmanview.update(state) // 게임 상태를 뷰에 전달하여 업데이트
//    }
}
