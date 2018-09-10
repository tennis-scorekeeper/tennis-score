package com.tennis.score.activity;

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

/**
 * Created by Ali on 9/6/2018.
 */

public class NewMatch extends AppCompatActivity {

    private final int blackColor = 0xff000000;
    private final int redColor = 0xffcc0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_match_form);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void onSubmit(View view) {
        boolean formValid = true;

        String tournamentName = ((EditText)findViewById(R.id.tournamentName)).getText().toString();
        String date = ((EditText)findViewById(R.id.date)).getText().toString();

        String playerOneName = ((EditText)findViewById(R.id.playerOneName)).getText().toString();
        String playerOneFrom = ((EditText)findViewById(R.id.player1from)).getText().toString();
        String playerTwoName = ((EditText)findViewById(R.id.playerTwoName)).getText().toString();
        String playerTwoFrom = ((EditText)findViewById(R.id.player2from)).getText().toString();

        String round = ((EditText)findViewById(R.id.MatchRound)).getText().toString();
        String division = ((EditText)findViewById(R.id.division)).getText().toString();
        String referee = ((EditText)findViewById(R.id.referee)).getText().toString();

        String adRule = "";
        String matchFormat = "";

        if (tournamentName.length() <= 0) {
            ((TextView)findViewById(R.id.tournamentNameText)).setTextColor(redColor);
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.tournamentNameText)).setTextColor(blackColor);
        }

        if (date.length() <= 0) {
            date = " ";
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
            File file = new File(getFilesDir(), "matchData");
            try {
                String lineToWrite = tournamentName + "," + date + "," + playerOneName + "," + playerOneFrom + ","
                        + playerTwoName + "," + playerTwoFrom + "," + round + "," + division + ","
                        + matchFormat + "," + adRule + "," + referee;
                FileWriter fw = new FileWriter(file, true);
                fw.write(lineToWrite);
                fw.write(System.getProperty("line.separator"));
                fw.flush();
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            setResult(0);
            finish();
        }
    }
}

