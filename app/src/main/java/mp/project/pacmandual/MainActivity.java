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
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                // 터치된 위치 (x, y 좌표)
//                float touchX = event.getX();
//                float touchY = event.getY();
//
//                // Pacman이 현재 위치한 좌표를 기준으로 터치된 방향 계산
//                // 가령, 화면 크기나 맵의 크기에 비례하여 상하좌우 방향을 결정
//                int directionX = 0;
//                int directionY = 0;
//
//                // 예시로, 화면의 상단 반쪽을 터치하면 위로 이동, 하단 반쪽을 터치하면 아래로 이동
//                if (touchY < pacmanView.getHeight() / 2) {
//                    directionY = -1; // 위로 이동
//                } else {
//                    directionY = 1;  // 아래로 이동
//                }
//
//                // 화면의 좌측 반쪽을 터치하면 왼쪽으로, 우측 반쪽을 터치하면 오른쪽으로 이동
//                if (touchX < pacmanView.getWidth() / 2) {
//                    directionX = -1; // 왼쪽으로 이동
//                } else {
//                    directionX = 1;  // 오른쪽으로 이동
//                }
//
//                // 계산된 방향으로 Pacman을 이동시킴
//                game.movePacman(directionY, directionX);
//                updateView(); // 화면 업데이트
//                return true;
//
//            // 추가적인 이벤트 처리 (ACTION_MOVE, ACTION_UP 등)도 필요에 따라 구현 가능
//            default:
//                return super.onTouchEvent(event);
//        }
    }

    private void updateView() {
        mp.project.pacmandual.PacmanGame.ScreenState state = game.getScreen(); // 현재 게임 상태를 가져옴
        pacmanView.update(state); // 게임 상태를 뷰에 전달하여 업데이트
    }
}