package mp.project.pacmandual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

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
            } else {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("GAME_MODE", selectedMode);
                startActivity(intent);
            }
        });

        exitGameButton.setOnClickListener(v -> finish());
        }
    }





