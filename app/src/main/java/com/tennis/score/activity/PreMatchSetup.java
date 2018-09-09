package com.tennis.score.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tennis.score.R;

import java.util.Timer;
import java.util.TimerTask;

public class PreMatchSetup extends AppCompatActivity {

    private TextView timerDisplay;

    private Button startTimerButton;
    private Button resetTimerButton;

    private String tournamentName;
    private String playerOneName;
    private String playerTwoName;
    private String adRule;
    private String matchFormat;

    private final int maxSeconds = 60*60;

    private int warmupSeconds = 0;
    private int stoppedAtSecond = 0;
    private boolean paused = true;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_match_setup);

        Intent intent = getIntent();

        tournamentName = intent.getStringExtra("tournamentName");
        playerOneName = intent.getStringExtra("playerOneName");
        playerTwoName = intent.getStringExtra("playerTwoName");
        matchFormat = intent.getStringExtra("matchFormat");
        adRule = intent.getStringExtra("adRule");

        timerDisplay = (TextView)findViewById(R.id.timerDisplay);

        startTimerButton = (Button)findViewById(R.id.startTimer);
        resetTimerButton = (Button)findViewById(R.id.resetTimer);

        System.out.println(tournamentName + "-" + playerOneName + "-" + playerTwoName + "-" + matchFormat + "-" + adRule);

        ((RadioButton)findViewById(R.id.playerOneServe)).setText(playerOneName);
        ((RadioButton)findViewById(R.id.playerTwoServe)).setText(playerTwoName);

        ((RadioButton)findViewById(R.id.leftSidePlayerOne)).setText(playerOneName);
        ((RadioButton)findViewById(R.id.leftSidePlayerTwo)).setText(playerTwoName);

        ((RadioButton)findViewById(R.id.rightSidePlayerOne)).setText(playerOneName);
        ((RadioButton)findViewById(R.id.rightSidePlayerTwo)).setText(playerTwoName);
    }

    public void startTimer(View view) {
        if (paused) {
            countDownTimer = new CountDownTimer(maxSeconds * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    warmupSeconds = maxSeconds - ((int) (millisUntilFinished / 1000));
                    timerDisplay.setText(getTimeString(warmupSeconds + stoppedAtSecond));
                }

                public void onFinish() {
                    timerDisplay.setText("00:00");
                    resetTimerButton.setEnabled(true);
                }
            };

            countDownTimer.start();
            paused = false;

            startTimerButton.setText("Stop");
            resetTimerButton.setEnabled(false);
        }
        else {
            stoppedAtSecond = warmupSeconds;

            countDownTimer.cancel();
            paused = true;

            startTimerButton.setText("Start");
            resetTimerButton.setEnabled(true);

        }
    }

    public void resetTimer(View view) {
        stoppedAtSecond = 0;
        timerDisplay.setText("00:00");
    }

    private String getTimeString(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String minuteString = String.valueOf(minutes);
        String secondString = String.valueOf(seconds);

        if (minuteString.length() == 1) {
            minuteString = "0" + minuteString;
        }
        if (secondString.length() == 1) {
            secondString = "0" + secondString;
        }
        return minuteString + ":" + secondString;
    }
}
