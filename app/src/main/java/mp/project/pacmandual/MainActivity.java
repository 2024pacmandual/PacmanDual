package mp.project.pacmandual;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

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

        setContentView(R.layout.activity_main);
        Button line_1_btn = (Button) findViewById(R.id.exitGameButton); //버튼 아이디
        line_1_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){                            //연결할 엑티비티
                Intent intent = new Intent(getApplicationContext(), GameDraw.class);
                startActivity(intent);
            }
        });


        // 게임 시작 버튼 클릭 시
        //startGameButton.setOnClickListener(v -> {
        //    game = new PacmanGame();
        //    pacmanView = new PacmanView(MainActivity.this);
        //    setContentView(pacmanView);  // 게임 화면으로 전환
        //});

        // 게임 종료 버튼 클릭 시
        //exitGameButton.setOnClickListener(v -> {
        //    finish();  // 앱 종료
        //});
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
