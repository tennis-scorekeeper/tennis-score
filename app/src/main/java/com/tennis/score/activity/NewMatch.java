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
            ((TextView)findViewById(R.id.tournamentNameError)).setText("* Enter tournament.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.tournamentNameError)).setText("");
        }

        if (date.length() <= 0) {
            date = " ";
        }

        if (playerOneName.length() <= 0) {
            ((TextView)findViewById(R.id.player1NameError)).setText("* Name.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.player1NameError)).setText("");
        }

        if (playerOneFrom.length() <= 0) {
            playerOneFrom = " ";
        }

        if (playerTwoName.length() <= 0) {
            ((TextView)findViewById(R.id.player2NameError)).setText("* Name.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.player2NameError)).setText("");
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
            ((TextView)findViewById(R.id.adSelectError)).setText("* Select scoring format.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.adSelectError)).setText("");
            adRule = selectedAdRule.getText().toString();
        }

        RadioButton selectedFormat = (RadioButton)findViewById(((RadioGroup) findViewById(R.id.matchFormatSelect)).getCheckedRadioButtonId());
        if (selectedFormat == null) {
            ((TextView)findViewById(R.id.matchFormatSelectError)).setText("* Select match format.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.matchFormatSelectError)).setText("");
            matchFormat = selectedFormat.getText().toString();
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

