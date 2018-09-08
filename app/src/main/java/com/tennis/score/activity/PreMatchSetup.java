package com.tennis.score.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tennis.score.R;

public class PreMatchSetup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_match_setup);

        Intent intent = getIntent();

        String matchName = intent.getStringExtra("matchName");
        String playerOneName = intent.getStringExtra("playerOneName");
        String playerTwoName = intent.getStringExtra("playerTwoName");
        String adRule = intent.getStringExtra("adRule");
        String matchFormat = intent.getStringExtra("matchFormat");

        System.out.println(matchName + "-" + playerOneName + "-" + playerTwoName + "-" + adRule + "-" + matchFormat);
    }

    public void startTimer(View view) {
        double warmupTime = Double.parseDouble(((EditText)findViewById(R.id.warmupTime)).getText().toString());

        int remainingSeconds = (int)(warmupTime * 60);

        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;

        String minuteString = String.valueOf(minutes);
        String secondString = String.valueOf(seconds);

        if (minuteString.length() == 1) {
            minuteString = "0" + minuteString;
        }
        if (secondString.length() == 1) {
            secondString = "0" + secondString;
        }

        ((TextView)findViewById(R.id.timerDisplay)).setText(minuteString + ":" + secondString);
    }

    public void tester(View view) {
        System.out.println("test");
    }
}
