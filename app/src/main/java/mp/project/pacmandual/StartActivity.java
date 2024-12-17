package mp.project.pacmandual;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private RadioButton singlePlayerButton;
    private RadioButton twoPlayerButton;
    private Button startGameButton;
    private Button exitGameButton;
    private String selectedMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        singlePlayerButton = findViewById(R.id.singlePlayerButton);
        twoPlayerButton = findViewById(R.id.twoPlayerButton);
        startGameButton = findViewById(R.id.startGameButton);
        exitGameButton = findViewById(R.id.exitGameButton);

        singlePlayerButton.setOnClickListener(v -> {
            selectedMode = "SINGLE";
        });

        twoPlayerButton.setOnClickListener(v -> {
            selectedMode = "TWO_PLAYER";
        });

        startGameButton.setOnClickListener(v -> {
            if (selectedMode == null) {
                Toast.makeText(StartActivity.this, "모드를 선택해주세요.", Toast.LENGTH_SHORT).show();
                selectedMode = "SINGLE";
            } else if (selectedMode.equals("TWO_PLAYER")) {
                // 2인 모드 선택 시 서버 입력 팝업 호출
                showServerInputDialog();
            } else {
                // 싱글 모드 바로 시작
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("GAME_MODE", selectedMode);
                startActivity(intent);
            }
        });

        exitGameButton.setOnClickListener(v -> finish());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        singlePlayerButton.setChecked(false);
        twoPlayerButton.setChecked(false);
        selectedMode = null;
    }

    // 서버 입력 팝업
    public void showServerInputDialog() {
        // 팝업 레이아웃 가져오기
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.server_info, null);

        // EditText 연결
        EditText inputServerIP = dialogView.findViewById(R.id.inputServerIP);
        EditText inputServerPort = dialogView.findViewById(R.id.inputServerPort);

        // AlertDialog 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("서버 설정")
                .setView(dialogView)
                .setPositiveButton("연결", (dialog, which) -> {
                    // 입력된 IP와 포트 가져오기
                    String serverIP = inputServerIP.getText().toString();
                    String serverPort = inputServerPort.getText().toString();
                    int serverPortNo = Integer.parseInt(serverPort);

                    if (!serverIP.isEmpty() && !serverPort.isEmpty()) {
                        Toast.makeText(this, "IP: " + serverIP + ", Port: " + serverPort, Toast.LENGTH_SHORT).show();

                        // MainActivity로 이동하며 서버 정보 전달
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.putExtra("GAME_MODE", selectedMode); // 2인 모드 정보
                        intent.putExtra("SERVER_IP", serverIP); // 서버 IP
                        intent.putExtra("SERVER_PORT", serverPortNo); // 서버 Port
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "IP와 포트를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", (dialog, which) -> {
                    dialog.dismiss(); // 팝업 닫기
                });

        // 팝업 띄우기
        builder.create().show();
    }

}
