package esme.parkinson.tabatatimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    //declare variables
    private Button mStartButton;
    //private Button mAboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //assign variables
        mStartButton = (Button) findViewById(R.id.btnStart);
        //mAboutButton = (Button) findViewById(R.id.btnAbout);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
    }

    private void startTimer() {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }
}
