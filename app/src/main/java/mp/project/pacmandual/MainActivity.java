package mp.project.pacmandual;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
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
    private boolean isRunning = false;

    private PacmanGame.PacmanState state;
    private GameState LoopState;

    private enum GameState {
        Error(-1), Initial(0), Running(1), Paused(2);
        private final int value;
        private GameState(int value) { this.value = value; }
        public int value() { return value; }
        public static GameState stateFromInteger(int value) {
            switch (value) {
                case -1: return Error;
                case 0: return Initial;
                case 1: return Running;
                case 2: return Paused;
                default: return null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameMode = getIntent().getStringExtra("GAME_MODE");

        setContentView(R.layout.activity_main);
        pacmanView = findViewById(R.id.pacmanView);
        pacmanView2 = findViewById(R.id.pacmanView2);
        if (gameMode == null ||gameMode.equals("SINGLE")) {
            Toast.makeText(this, "1인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
            game = new PacmanGame();
            //pacmanView = findViewById(R.id.pacmanView);

            pacmanView2.setVisibility(View.GONE);

        } else if (gameMode == null ||gameMode.equals("TWO_PLAYER")) {
            Toast.makeText(this, "2인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
            game = new PacmanGame();
            //pacmanView = findViewById(R.id.pacmanView);
            //pacmanView2 = findViewById(R.id.pacmanView2);
            //enemy_game = new PacmanGame(3);
        }



        buttonUp = findViewById(R.id.buttonUp);
        buttonDown = findViewById(R.id.buttonDown);
        buttonLeft = findViewById(R.id.buttonLeft);
        buttonRight = findViewById(R.id.buttonRight);
      
        ImageView pauseButton = findViewById(R.id.pauseButton);
        ImageView resumeButton = findViewById(R.id.resumeButton);
        ImageView exitButton = findViewById(R.id.exitButton);

        // Pause 버튼 클릭 리스너
        pauseButton.setOnClickListener(view -> {
            onPause(); // 게임 멈춤 기능 추가
            pauseButton.setVisibility(View.GONE);
            resumeButton.setVisibility(View.VISIBLE);
        });

        // Resume 버튼 클릭 리스너
        resumeButton.setOnClickListener(view -> {
            onResume(); // 게임 재생 기능 추가
            resumeButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
        });

        // Exit 버튼 클릭 리스너
        exitButton.setOnClickListener(view -> {
            finish(); // 액티비티 종료
        });

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
        LoopState = GameState.Initial;
    }
    @Override
    protected void onStart(){
        super.onStart();
        LoopState = GameState.Running;
        game.timer.startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
        LoopState = GameState.Paused;
        handler.removeCallbacks(gameLoopRunnable); // 루프 중단
        game.timer.stopTimer(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoopState = GameState.Running;
        startGameLoop(); // 게임 루프 재시작
        }



    protected void onStop(){
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    // Handler와 Runnable 추가
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable gameLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                // 게임 상태 업데이트
                state = game.updateGameState();
//                Log.d("pacmanGame on loop", "pacman state " + state);
//                Log.d("pacmanGame on loop", "game state(UI state) " + LoopState);

                pacmanView.getScreenState(game.getScreen());
                pacmanView2.getScreenState(game.getScreen()); // 서버로부터 상태 받는 부분은 후속 구현 필요.

                // 게임 상태 확인
                if (state == PacmanGame.PacmanState.finished) {
                    isRunning = false;
                    Intent intent = new Intent(MainActivity.this, EndActivity.class);
                    intent.putExtra("GAME_RESULT", game.getResult());
                    intent.putExtra("GAME_MODE", gameMode);
                    startActivity(intent);
                    finish();
                    return; // 다음 타이머 호출 방지
                } else if (state == PacmanGame.PacmanState.NextStage) {
                    // 다음 스테이지 처리 (현재는 구현되지 않음)
                    if(game.toNextLevel() == 0){ //레벨3까지 완료
                        Intent intent = new Intent(MainActivity.this, EndActivity.class);
                        intent.putExtra("GAME_RESULT", game.getResult());
                        intent.putExtra("GAME_MODE", gameMode);
                        startActivity(intent);
                        finish();
                    }
                    runOnUiThread(() -> {
                        pacmanView.invalidate();
                        pacmanView2.invalidate();
                    });
                }

                // 화면 갱신
                runOnUiThread(() -> {
                    pacmanView.invalidate();
                    pacmanView2.invalidate(); // PacmanView의 onDraw() 호출
                });

                // 100ms 후에 다음 게임 루프 실행
                handler.postDelayed(this, 100);
            }
        }
    };

    private void startGameLoop() {
        if (!isRunning) {
            isRunning = true;
            handler.post(gameLoopRunnable); // 게임 루프 시작
        }
    }



//    private void startGameLoop() {
//        if (gameLoopThread == null || !gameLoopThread.isAlive()) {
//            gameLoopThread = new Thread(() -> {
//                while (isRunning) {
//                    try {
//
//                        state = game.updateGameState();
//                        Log.d("pacmanGame on loop", "game state" + state);
//                        pacmanView.getScreenState(game.getScreen());
//                        pacmanView2.getScreenState(game.getScreen()); //서버로 부터 screenState를 받도록 고쳐야함.
//
//                        if (state == PacmanGame.PacmanState.finished){
//                            isRunning = false;
//
//                            Intent intent = new Intent(this, EndActivity.class);
//                            intent.putExtra("GAME_RESULT", game.getResult());
//                            startActivity(intent);
//                            finish();
//                        } else if (state == PacmanGame.PacmanState.NextStage) {
//
//                        }
//
//                        runOnUiThread(() -> {
//                            pacmanView.invalidate();
//                            pacmanView2.invalidate();// PacmanView의 onDraw() 호출
//                        });
//                        Thread.sleep(100); // 게임 루프 간격 설정
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            gameLoopThread.start();
//        }
//
//    }

}