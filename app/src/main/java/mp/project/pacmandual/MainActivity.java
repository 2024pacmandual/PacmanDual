package mp.project.pacmandual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private PacmanGame game;
    private PacmanGame.ScreenState enemy_game;
    private PacmanView pacmanView;
    private PacmanView pacmanView2;

    ImageView buttonUp;
    ImageView buttonDown;
    ImageView buttonLeft;
    ImageView buttonRight;

    private String gameMode;

    private Thread gameLoopThread;
    private boolean isRunning = true;

    private PacmanGame.PacmanState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameMode = getIntent().getStringExtra("GAME_MODE");


        if (gameMode == null ||gameMode.equals("SINGLE")) {
            Toast.makeText(this, "1인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
            game = new PacmanGame(3);
        } else if (gameMode == null ||gameMode.equals("TWO_PLAYER")) {
            Toast.makeText(this, "2인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
            game = new PacmanGame(3);
            //enemy_game = new PacmanGame(3);
        }

         // gameMode에 따라 게임을 초기화
        //pacmanView = new PacmanView(this);

        setContentView(R.layout.activity_main);

        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);

        pacmanView = new PacmanView(this);
        pacmanView = findViewById(R.id.pacmanView);
        pacmanView2 = new PacmanView(this);
        pacmanView2 = findViewById(R.id.pacmanView2);
        //setContentView(pacmanView);

        buttonUp.setOnTouchListener((v, event) -> {
            game.onTouchAccept("UP");
            v.performClick();
            return true;
        });

        buttonDown.setOnTouchListener((v, event) -> {
            game.onTouchAccept("DOWN");
            v.performClick();
            return true;
        });

        buttonLeft.setOnTouchListener((v, event) -> {
            game.onTouchAccept("LEFT");
            return true;
        });

        buttonRight.setOnTouchListener((v, event) -> {
            game.onTouchAccept("RIGHT");
            return true;
        });
        //startGameLoop();
    }
    @Override
    protected void onStart(){
        super.onStart();
        isRunning = true;
        game.timer.startTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (game.timer.getTimerFlag() > 0) {
            isRunning = true;
            startGameLoop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        game.timer.stopTimer(0);
    }

    protected void onStop(){
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void startGameLoop() {
        if (gameLoopThread == null || !gameLoopThread.isAlive()) {
            gameLoopThread = new Thread(() -> {
                while (isRunning) {
                    try {
                        state = game.updateGameState();
                        pacmanView.getScreenState(game.getScreen());

                        if (state == PacmanGame.PacmanState.finished){
                            isRunning = false;

                            Intent intent = new Intent(this, EndActivity.class);
                            intent.putExtra("GAME_RESULT", game.getResult());
                            startActivity(intent);
                            finish();
                        }

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
}
