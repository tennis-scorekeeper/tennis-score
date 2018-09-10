package com.tennis.score.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
    private String date;
    private String playerOneName;
    private String playerOneFrom;
    private String playerTwoName;
    private String playerTwoFrom;
    private String round;
    private String division;
    private String matchFormat;
    private String adRule;
    private String referee;


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
        date = intent.getStringExtra("date");
        playerOneName = intent.getStringExtra("playerOneName");
        playerOneFrom = intent.getStringExtra("playerOneFrom");
        playerTwoName = intent.getStringExtra("playerTwoName");
        playerTwoFrom = intent.getStringExtra("playerTwoFrom");
        round = intent.getStringExtra("round");
        division = intent.getStringExtra("division");
        matchFormat = intent.getStringExtra("matchFormat");
        adRule = intent.getStringExtra("adRule");
        referee = intent.getStringExtra("referee");

        timerDisplay = (TextView)findViewById(R.id.timerDisplay);

        startTimerButton = (Button)findViewById(R.id.startTimer);
        resetTimerButton = (Button)findViewById(R.id.resetTimer);


        String fetchedData = tournamentName + "," + date + "," + playerOneName + "," + playerOneFrom + ","
                + playerTwoName + "," + playerTwoFrom + "," + round + "," + division + ","
                + matchFormat + "," + adRule + "," + referee;
        System.out.println(fetchedData);

        ((RadioButton)findViewById(R.id.coinTossWinnerPlayerOne)).setText(playerOneName);
        ((RadioButton)findViewById(R.id.coinTossWinnerPlayerTwo)).setText(playerTwoName);

        ((RadioButton)findViewById(R.id.leftSidePlayerOne)).setText(playerOneName);
        ((RadioButton)findViewById(R.id.leftSidePlayerTwo)).setText(playerTwoName);

        ((RadioButton)findViewById(R.id.rightSidePlayerOne)).setText(playerOneName);
        ((RadioButton)findViewById(R.id.rightSidePlayerTwo)).setText(playerTwoName);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
            stoppedAtSecond += warmupSeconds;

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
