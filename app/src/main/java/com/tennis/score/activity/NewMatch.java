package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tennis.score.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Ali on 9/6/2018.
 */

public class NewMatch extends AppCompatActivity {

    private final int blackColor = 0xff000000;
    private final int redColor = 0xffcc0000;

    String signedInEmail;
    int tournamentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_match_form);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        signedInEmail = intent.getStringExtra("signedInEmail");
        tournamentId = intent.getIntExtra("tournamentId", -1);
    }

    public void onSubmit(View view) {
        boolean formValid = true;

        String date = ((EditText)findViewById(R.id.date)).getText().toString();
        String sqlDate = "";

        String playerOneName = ((EditText)findViewById(R.id.playerOneName)).getText().toString();
        String playerOneFrom = ((EditText)findViewById(R.id.player1from)).getText().toString();
        String playerTwoName = ((EditText)findViewById(R.id.playerTwoName)).getText().toString();
        String playerTwoFrom = ((EditText)findViewById(R.id.player2from)).getText().toString();

        String round = ((EditText)findViewById(R.id.round)).getText().toString();
        String division = ((EditText)findViewById(R.id.division)).getText().toString();
        String referee = ((EditText)findViewById(R.id.referee)).getText().toString();

        String adRule = "";
        String matchFormat = "";

        if (date.length() <= 0) {
            date = " ";
            ((TextView)findViewById(R.id.dateText)).setTextColor(blackColor);
        }
        else {
            ((TextView)findViewById(R.id.dateText)).setTextColor(blackColor);
            try {
                sqlDate = new java.sql.Date(new Date(date).getTime()).toString();
            }
            catch (IllegalArgumentException iae) {
                formValid = false;
                ((TextView)findViewById(R.id.dateText)).setTextColor(redColor);
            }
        }

        if (playerOneName.length() <= 0) {
            formValid = false;
            ((TextView)findViewById(R.id.playerOneText)).setTextColor(redColor);
        }
        else {
            ((TextView)findViewById(R.id.playerOneText)).setTextColor(blackColor);
        }

        if (playerOneFrom.length() <= 0) {
            playerOneFrom = " ";
        }

        if (playerTwoName.length() <= 0) {
            formValid = false;
            ((TextView)findViewById(R.id.playerTwoText)).setTextColor(redColor);
        }
        else {
            ((TextView)findViewById(R.id.playerTwoText)).setTextColor(blackColor);
        }

        if (playerTwoFrom.length() <= 0) {
            playerTwoFrom = " ";
        }

        if (round.length() <= 0) {
            round = " ";
        }

        if (division.length() <= 0) {
            division = " ";
        }

        RadioButton selectedAdRule = (RadioButton)findViewById(((RadioGroup) findViewById(R.id.advantageSelect)).getCheckedRadioButtonId());
        if (selectedAdRule == null) {
            formValid = false;
            ((TextView)findViewById(R.id.adRuleText)).setTextColor(redColor);
        }
        else {
            adRule = selectedAdRule.getText().toString();
            ((TextView)findViewById(R.id.adRuleText)).setTextColor(blackColor);
        }

        RadioButton selectedFormat = (RadioButton)findViewById(((RadioGroup) findViewById(R.id.matchFormatSelect)).getCheckedRadioButtonId());
        if (selectedFormat == null) {
            formValid = false;
            ((TextView)findViewById(R.id.matchFormatText)).setTextColor(redColor);
        }
        else {
            matchFormat = selectedFormat.getText().toString();
            ((TextView)findViewById(R.id.matchFormatText)).setTextColor(blackColor);
        }

        if (referee.length() <= 0) {
            referee = " ";
        }

        if (formValid) {
            String[] params = new String[]{
                    "tournamentId", String.valueOf(tournamentId),
                    "date", sqlDate,
                    "playerOneName", playerOneName,
                    "playerOneFrom", playerOneFrom,
                    "playerTwoName", playerTwoName,
                    "playerTwoFrom", playerTwoFrom,
                    "round", round,
                    "division", division,
                    "matchFormat", matchFormat,
                    "adRule", adRule,
                    "referee", referee};
            try {
                String response = new RetrieveFeedTask(
                        "https://www.mikenguyenmd.com/match_live/createMatch", params).execute().get();

                System.out.println(response);

                setResult(0);
                finish();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

