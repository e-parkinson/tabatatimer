package esme.parkinson.tabatatimer;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;

import static android.R.color.holo_orange_light;

public class TimerActivity extends AppCompatActivity {
    //declare variables
    private TextView mTimer;
    private TextView mClue;
    private TextView mSetNumber;
    private RelativeLayout mRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //assign variables to views
        mTimer = (TextView) findViewById(R.id.textViewTimer);
        mClue = (TextView) findViewById(R.id.textViewClue);
        mSetNumber = (TextView) findViewById(R.id.textViewSetNumber);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.bigRelativeLayout);

        //turn off screen dimming
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //start timer
        int setNumber = 1;
        //change initial colour
        Context mContext = this.getBaseContext();
        mRelativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.yellowColor));
        mClue.setText(R.string.ready_countdown);
        mSetNumber.setText(" ");
        startATimer(4000, setNumber, mContext);
    }

    private void startATimer(final int timeToCount, final int setNumber, final Context mContext){
        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
        new CountDownTimer(timeToCount, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (timeToCount == 3000) {
                    mTimer.setText("0");
                }
                else{
                    mTimer.setText(String.valueOf(millisUntilFinished / 1000));
                }

            }

            @Override
            public void onFinish() {
                if (timeToCount == 20000){
                    //beep on end of set
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                    if (setNumber != 8){
                        //change bg yellow
                        mRelativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.yellowColor));
                        //display 'rest' if not final set
                        int newSetNumber = setNumber + 1;
                        mClue.setText(R.string.show_rest);
                        mSetNumber.setText(" ");
                        startATimer(10000, newSetNumber, mContext);
                    }
                    else {
                        //display finished screen if final set
                        //make bg red
                        mRelativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.redColor));
                        mTimer.setText("0");
                        mClue.setText("Done!");
                        mSetNumber.setText(" ");
                        startATimer(3000, setNumber, mContext);
                    }

                }
                else if (timeToCount == 3000) {
                    //go back to main menu when finished
                    Intent intent = new Intent(getBaseContext(), StartActivity.class);
                    startActivity(intent);
                }
                else {
                    //make bg green
                    mRelativeLayout.setBackgroundColor(mContext.getResources().getColor(R.color.greenColor));
                    //beep on start of set
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                    mClue.setText(R.string.show_go);
                    mSetNumber.setText("Set " + setNumber);
                    startATimer(20000, setNumber, mContext);
                }
            }
        }.start();
    }
}
