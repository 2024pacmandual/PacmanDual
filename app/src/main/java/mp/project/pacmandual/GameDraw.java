package mp.project.pacmandual;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameDraw extends Activity {

    Button btn_sub;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View buttonLeft = findViewById(R.id.buttonLeft);

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

