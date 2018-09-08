package com.tennis.score.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tennis.score.R;

import java.util.Timer;
import java.util.TimerTask;

public class PreMatchSetup extends AppCompatActivity {

    private TextView timerDisplay;

    private Button setTimerButton;
    private Button startTimerButton;
    private Button stopTimerButton;

    private String tournamentName;
    private String playerOneName;
    private String playerTwoName;
    private String adRule;
    private String matchFormat;

    private int warmupSeconds;

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

        setTimerButton = (Button)findViewById(R.id.setTimer);
        startTimerButton = (Button)findViewById(R.id.startTimer);
        stopTimerButton = (Button)findViewById(R.id.stopTimer);

        startTimerButton.setEnabled(false);
        stopTimerButton.setEnabled(false);

        System.out.println(tournamentName + "-" + playerOneName + "-" + playerTwoName + "-" + matchFormat + "-" + adRule);
    }

    public void setTimer(View view) {
        String inputWarmupTime = ((EditText)findViewById(R.id.warmupTime)).getText().toString();

        if (inputWarmupTime.length() <= 0) {
            return;
        }

        double warmupTime;

        try {
            warmupTime = Double.parseDouble(inputWarmupTime);
        }
        catch (NumberFormatException nfe) {
            System.out.println("Invalid input");
            return;
        }

        warmupSeconds = (int)(warmupTime * 60);

        countDownTimer = new CountDownTimer(warmupSeconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                warmupSeconds = (int)(millisUntilFinished / 1000);

                timerDisplay.setText(getTimeString(warmupSeconds));

            }
            public void onFinish() {
                timerDisplay.setText("00:00");
                setTimerButton.setEnabled(true);
                startTimerButton.setEnabled(true);
                stopTimerButton.setEnabled(false);
            }
        };

        timerDisplay.setText(getTimeString(warmupSeconds));

        startTimerButton.setEnabled(true);
    }

    public void startTimer(View view) {
        timerDisplay.setText(getTimeString(warmupSeconds-1));
        countDownTimer.start();

        setTimerButton.setEnabled(false);
        startTimerButton.setEnabled(false);
        stopTimerButton.setEnabled(true);
    }

    public void stopTimer(View view) {
        countDownTimer.cancel();

        setTimerButton.setEnabled(true);
        startTimerButton.setEnabled(true);
        stopTimerButton.setEnabled(false);
    }

    private String getTimeString(int totalSeconds) {
        int minutes = warmupSeconds / 60;
        int seconds = warmupSeconds % 60;

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
