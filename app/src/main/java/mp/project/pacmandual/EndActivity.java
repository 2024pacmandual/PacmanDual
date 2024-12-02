package mp.project.pacmandual;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EndActivity extends AppCompatActivity {

    TextView gameResultText;
    TextView totalScoreText;
    Button backToStartButton;
    Button restartGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);

        gameResultText = findViewById(R.id.gameResultText);
        totalScoreText = findViewById(R.id.totalScoreText);
        backToStartButton = findViewById(R.id.backToStartButton);
        restartGameButton = findViewById(R.id.restartGameButton);

        int[] endInfo;

        endInfo = getIntent().getIntArrayExtra("GAME_RESULT");
        if (endInfo != null){
            totalScoreText.setText(getString(R.string.final_score, endInfo[0]));
            //totalScoreText.setText(getString(R.string.remaining_time, endInfo[0]));
        } else {
            String r1 = "No score, got no Intent";
            //String r2 = "No remaining time, got no Intent";
            totalScoreText.setText(r1);
            //totalScoreText.setText(r2);
        }


        backToStartButton.setOnClickListener(v -> {
            Intent intent = new Intent(EndActivity.this, StartActivity.class);
            startActivity(intent);
        });

        restartGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(EndActivity.this, MainActivity.class);
            startActivity(intent);
        });


    }
}





