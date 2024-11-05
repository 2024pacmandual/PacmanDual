package mp.project.pacmandual;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private mp.project.pacmandual.PacmanGame game;
    private mp.project.pacmandual.PacmanView pacmanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new mp.project.pacmandual.PacmanGame();
        pacmanView = new mp.project.pacmandual.PacmanView(this);
        setContentView(pacmanView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView(); // 초기 상태로 뷰 업데이트
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.onTouchEvent(event); // 이벤트를 PacmanGame으로 전달
        updateView(); // 게임 뷰 갱신
        return true; // 이벤트 처리가 완료되었음을 알림
    }

    private void updateView() {
        mp.project.pacmandual.PacmanGame.ScreenState state = game.getScreen(); // 현재 게임 상태를 가져옴
        pacmanView.update(state); // 게임 상태를 뷰에 전달하여 업데이트
    }
}