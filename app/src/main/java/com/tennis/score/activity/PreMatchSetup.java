package com.tennis.score.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
}
