package mp.project.pacmandual;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private PacmanGame game, opponent_game;
    private PacmanView pacmanView, pacmanView2;
    ImageView buttonUp, buttonDown, buttonLeft, buttonRight;

    private String gameMode;
    private int pauseSync=0;

    private String servHostName;
    private int servPortNo;

    private PacmanGame.PacmanState state, opponent_state;
    private GameState LoopState, savedState;


    private enum GameState { //import from Tetris GameState
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
        servHostName = getIntent().getStringExtra("SERVER_IP");
        servPortNo = getIntent().getIntExtra("SERVER_PORT", 1111);

        setContentView(R.layout.activity_main);
        pacmanView = findViewById(R.id.pacmanView);
        pacmanView2 = findViewById(R.id.pacmanView2);
        if (gameMode == null ||gameMode.equals("SINGLE")) {
            Toast.makeText(this, "1인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
            game = new PacmanGame();
            pacmanView2.setVisibility(View.GONE);

        } else if (gameMode.equals("TWO_PLAYER")) {
            Toast.makeText(this, "2인 모드로 시작합니다.", Toast.LENGTH_SHORT).show();
            game = new PacmanGame();
            opponent_game = new PacmanGame();


            startThread(runnable4RecvThread); // launch RecvThread before SendThread
            startThread(runnable4SendThread); // launch SendThread
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
            pauseSync = 1;
            LoopState = GameState.Paused; //LoopState의 상태를 pause로 전환하는 것만으로도 pause가능
            pauseButton.setVisibility(View.GONE);
            resumeButton.setVisibility(View.VISIBLE);
        });

        // Resume 버튼 클릭 리스너
        resumeButton.setOnClickListener(view -> {
            LoopState = GameState.Running; //running으로 전환 후 loop재시작
            startGameLoop();
            resumeButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
        });

        // Exit 버튼 클릭 리스너
        exitButton.setOnClickListener(view -> {
            if (gameMode.equals("TWO_PLAYER")) {
                sendToPeer('Q'); // 서버에 종료 문자 전송
                disconnectServer(); // 서버와의 연결 종료
            }
            finish();
        });

        buttonUp.setOnTouchListener((v, event) -> {
            game.onTouchAccept("UP");
            if (gameMode.equals("TWO_PLAYER")) {
                sendToPeer('U');
            }
            v.performClick();
            return true;
        });

        buttonDown.setOnTouchListener((v, event) -> {
            game.onTouchAccept("DOWN");
            if (gameMode.equals("TWO_PLAYER")) {
                sendToPeer('D');
            }
            v.performClick();
            return true;
        });

        buttonLeft.setOnTouchListener((v, event) -> {
            game.onTouchAccept("LEFT");
            if (gameMode.equals("TWO_PLAYER")) {
                sendToPeer('L');
            }
            return true;
        });

        buttonRight.setOnTouchListener((v, event) -> {
            game.onTouchAccept("RIGHT");
            if (gameMode.equals("TWO_PLAYER")) {
                sendToPeer('R');
            }
            return true;
        });
        LoopState = GameState.Initial;
    }

    @Override
    protected void onStart(){
        super.onStart();
        game.timer.startTimer();
        if (gameMode.equals("TWO_PLAYER")) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> sendToPeer('S'), 200);
            //opponent_game.timer.startTimer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LoopState = GameState.Paused;
        handler.removeCallbacks(gameLoopRunnable);
        game.timer.stopTimer(0);
        if (gameMode.equals("TWO_PLAYER")) {
            opponent_game.timer.stopTimer(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pauseSync != 1)
            LoopState = GameState.Running;
        startGameLoop();
        game.timer.startTimer();
        if (gameMode.equals("TWO_PLAYER")) {
            opponent_game.timer.startTimer();
        }
    }



    protected void onStop(){
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }


    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable gameLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (LoopState == GameState.Running) {

                state = game.updateGameState();
                updateView(game, pacmanView);



                if (gameMode.equals("TWO_PLAYER")) {
                    opponent_state = opponent_game.updateGameState();
                    updateView(opponent_game, pacmanView2);
                }
                if (state == PacmanGame.PacmanState.finished ||
                        (gameMode.equals("TWO_PLAYER") && (opponent_state == PacmanGame.PacmanState.finished))) {
                    LoopState = GameState.Paused;
//                    Intent intent = new Intent(MainActivity.this, EndActivity.class);
//                    intent.putExtra("GAME_RESULT", game.getResult());
//                    intent.putExtra("GAME_MODE", gameMode);
                    if (gameMode.equals("TWO_PLAYER")) {
//                        intent.putExtra("GAME_RESULT_OP", opponent_game.getResult());
                        sendToPeer('Q');
                        disconnectServer();
                    }
                    navigateToEndScreen();

//                    startActivity(intent);
//                    finish();
                    return;
                } else if (state == PacmanGame.PacmanState.NextStage ||
                        (gameMode.equals("TWO_PLAYER") && opponent_state == PacmanGame.PacmanState.NextStage)) {
                    if(game.toNextLevel() == 0 ||
                            (gameMode.equals("TWO_PLAYER") && opponent_game.toNextLevel() == 0)) {
                        Intent intent = new Intent(MainActivity.this, EndActivity.class);
                        intent.putExtra("GAME_RESULT", game.getResult());
                        intent.putExtra("GAME_MODE", gameMode);
                        startActivity(intent);
                        finish();
                    }
                    runOnUiThread(() -> {
                        pacmanView.invalidate();
                        if (gameMode.equals("TWO_PLAYER")) {
                            pacmanView2.invalidate();
                        }

                    });
                }

                runOnUiThread(() -> {
                    pacmanView.invalidate();
                    if (gameMode.equals("TWO_PLAYER")) {
                        pacmanView2.invalidate();
                    }
                });
                if (LoopState != GameState.Running){LoopState = GameState.Error;}
                if(LoopState == GameState.Error){
                    navigateToStartScreen();
                    return;
                }
                handler.postDelayed(this, 100);
            }
        }
    };

    private void navigateToStartScreen() {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, "서버 연결이 끊어졌습니다. 시작 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 기존 액티비티 스택 제거
            startActivity(intent);
            finish(); // 현재 게임 화면 종료
        });
    }

    // 종료 화면으로 이동하는 메서드
    private void navigateToEndScreen() {
        Intent intent = new Intent(MainActivity.this, EndActivity.class);
        intent.putExtra("GAME_RESULT", game.getResult());
        intent.putExtra("GAME_MODE", gameMode);
        if (gameMode.equals("TWO_PLAYER")) {
            intent.putExtra("GAME_RESULT_OP", opponent_game.getResult());
        }
        startActivity(intent);
        finish();
    }

    private void startGameLoop() {
        if (LoopState == GameState.Running) {
            handler.post(gameLoopRunnable);
        }
    }

    private void updateView(PacmanGame game, PacmanView view){
        PacmanGame.ScreenState screenState = game.getScreen();
        view.getScreenState(screenState); // View로 전달
    }

//server
    private void startThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

    private void sendMessage(Handler h, int type, char key) {
        Message msg = Message.obtain(h,type);
        msg.arg1 = key;
        h.sendMessage(msg);
    }
    public void sendToPeer(char key) {
        sendMessage(handler4SendThread, 1, key);
    }
    private void sendToMain(char key) {
        sendMessage(handler4MainThread, 1, key);
    }
    private Socket socket = null;
    private final int maxWaitingTime = 3000; // 3 seconds
    private DataOutputStream outStream = null;
    private DataInputStream inStream = null;
    private Object socketReady = new Object();
    private Object inStreamReady = new Object();

    private void _disconnectServer() {
        try {
            if (outStream != null) outStream.close();
            if (inStream != null) inStream.close();
            if (socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        outStream = null;
        inStream = null;
        socket = null;
    }
    private void disconnectServer() {
        synchronized(socketReady) {
            _disconnectServer();
        }
    }
    private boolean connectServer() {
        synchronized(socketReady) {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(servHostName, servPortNo), maxWaitingTime);
                outStream = new DataOutputStream(socket.getOutputStream());
                inStream = new DataInputStream(socket.getInputStream());
                synchronized (inStreamReady) {
                    inStreamReady.notify();
                }
                return true;
            } catch (Exception e) {
                _disconnectServer();
                e.printStackTrace();
                return false;
            }
        }
    }
    private Handler handler4SendThread = null; // handler for Loopback Thread

    private Runnable runnable4SendThread = new Runnable() { // runnable for SendThread
        @Override
        public void run() {
            Looper.prepare(); // The message loop is ready.
            handler4SendThread = new Handler(Looper.myLooper()) {
                public void handleMessage(Message msg) {
                    try {
                        char key = (char) msg.arg1;
                        Log.d("SendThread", "key='"+key+"'["+(int) key+"]");
                        if (key == 'S') {
                            if (connectServer() == false) {
                                sendToMain('A'); // issue 'Abort' command
                                return;
                            }
                        }

                        synchronized(socketReady) {
                            if (outStream != null) {
                                Log.d("SendThread", "wrote key='" + key + "'[" + (int) key + "]");
                                outStream.writeByte(key);
                                outStream.writeByte('\n');
                            }
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Looper.loop(); // The message loop runs.
        }
    };
    private Runnable runnable4RecvThread = new Runnable() { // runnable for RecvThread
        @Override
        public void run() {
            while (true) {
                try {
                    char key;
                    synchronized (inStreamReady) {
                        while (inStream == null) inStreamReady.wait();
                    }
                    while ((key = (char) inStream.readByte()) != 'Q') {
                        if (key != '\n') // skip the newline character
                            sendToMain(key);
                    }
                    sendToMain(key); // key == 'Q'
                }
                catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "서버 연결이 끊어졌습니다. 시작 화면으로 돌아갑니다.", Toast.LENGTH_SHORT).show();
                        finish(); // 현재 게임 화면 종료
                    });
                }
                disconnectServer();
            }
        }
    };
    private Handler handler4MainThread = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            try {
                char key = (char) msg.arg1;
                Log.d("MainThread", "key: " + key);
                //mirrorPeer(key);
                switch (key) {
                    case 'U':
                        opponent_game.onTouchAccept("UP");
                        break;
                    case 'D': opponent_game.onTouchAccept("DOWN"); break;
                    case 'L': opponent_game.onTouchAccept("LEFT"); break;
                    case 'R': opponent_game.onTouchAccept("RIGHT"); break;
                    case 'S': opponent_game.timer.startTimer(); break;
                    case 'A':
                        Log.d("server", "aborted");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}