package mp.project.pacmandual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class EndActivity extends AppCompatActivity {

    TextView gameResultText;
    TextView totalScoreText;
    TextView player1ScoreText;
    TextView player2ScoreText;
    Button backToStartButton;
    Button restartGameButton;
    LinearLayout twoPlayerScoreLayout;
    LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);

        gameResultText = findViewById(R.id.gameResultText);
        totalScoreText = findViewById(R.id.totalScoreText);
        player1ScoreText = findViewById(R.id.player1ScoreText);
        player2ScoreText = findViewById(R.id.player2ScoreText);
        backToStartButton = findViewById(R.id.backToStartButton);
        restartGameButton = findViewById(R.id.restartGameButton);
        twoPlayerScoreLayout = findViewById(R.id.twoPlayerScoreLayout);
        buttonLayout = findViewById(R.id.buttonLayout);

        int[] endInfo, Opponent_endInfo;
        String prevMode;

        endInfo = getIntent().getIntArrayExtra("GAME_RESULT");
        Opponent_endInfo = getIntent().getIntArrayExtra("GAME_RESULT_OP");
        prevMode = getIntent().getStringExtra("GAME_MODE");
        if (endInfo != null){
            if (prevMode.equals("TWO_PLAYER") && Opponent_endInfo != null) {

                if (endInfo[0] > Opponent_endInfo[0]){
                    player1ScoreText.setTextColor(ContextCompat.getColor(this, R.color.green));
                    player2ScoreText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else if (endInfo[0] < Opponent_endInfo[0]){
                    player1ScoreText.setTextColor(ContextCompat.getColor(this, R.color.red));
                    player2ScoreText.setTextColor(ContextCompat.getColor(this, R.color.green));
                } else{
                    player1ScoreText.setTextColor(ContextCompat.getColor(this, R.color.green));
                    player2ScoreText.setTextColor(ContextCompat.getColor(this, R.color.green));
                }


                if (endInfo[1] > Opponent_endInfo[1]){ //내가 이긴 경우
                    player1ScoreText.setTextColor(ContextCompat.getColor(this, R.color.green));
                    player2ScoreText.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else if (endInfo[1] < Opponent_endInfo[1]){ //내가 이긴 경우
                    player1ScoreText.setTextColor(ContextCompat.getColor(this, R.color.red));
                    player2ScoreText.setTextColor(ContextCompat.getColor(this, R.color.green));
                } else{
                    player1ScoreText.setTextColor(ContextCompat.getColor(this, R.color.green));
                    player2ScoreText.setTextColor(ContextCompat.getColor(this, R.color.green));
                }


                totalScoreText.setVisibility(View.GONE);
                twoPlayerScoreLayout.setVisibility(View.VISIBLE);
                player1ScoreText.setText(getString(R.string.player1_score, endInfo[0]));
                player2ScoreText.setText(getString(R.string.player2_score, Opponent_endInfo[0]));
                restartGameButton.setVisibility(View.GONE);
            } else {
                totalScoreText.setVisibility(View.VISIBLE);
                twoPlayerScoreLayout.setVisibility(View.GONE);
                totalScoreText.setText(getString(R.string.total_score, endInfo[0]));
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) buttonLayout.getLayoutParams();
                params.addRule(RelativeLayout.BELOW, R.id.totalScoreText);
                buttonLayout.setLayoutParams(params);
            }

        } else {
            String r1 = "No score, got no Intent";
            totalScoreText.setText(r1);
        }


        backToStartButton.setOnClickListener(v -> {
            finish();
        });

        restartGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(EndActivity.this, MainActivity.class);
            intent.putExtra("GAME_MODE", prevMode);

            startActivity(intent);
            finish();
        });


    }
}





