package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    }

    public void onSubmit(View view) {
        boolean formValid = true;

        String matchName = ((EditText)findViewById(R.id.matchName)).getText().toString();
        String playerOneName = ((EditText)findViewById(R.id.playerOneName)).getText().toString();
        String playerTwoName = ((EditText)findViewById(R.id.playerTwoName)).getText().toString();

        if (matchName.length() <= 0) {
            ((TextView)findViewById(R.id.tournamentNameError)).setText("* Enter tournament format.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.tournamentNameError)).setText("");
        }

        if (playerOneName.length() <= 0) {
            ((TextView)findViewById(R.id.player1NameError)).setText("* Name.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.player1NameError)).setText("");
        }

        if (playerTwoName.length() <= 0) {
            ((TextView)findViewById(R.id.player2NameError)).setText("* Name.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.player2NameError)).setText("");
        }

        RadioButton selectedAdRule = (RadioButton)findViewById(((RadioGroup) findViewById(R.id.advantageSelect)).getCheckedRadioButtonId());
        if (selectedAdRule == null) {
            ((TextView)findViewById(R.id.adSelectError)).setText("* Select scoring format.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.adSelectError)).setText("");
        }

        RadioButton selectedFormat = (RadioButton)findViewById(((RadioGroup) findViewById(R.id.matchFormatSelect)).getCheckedRadioButtonId());
        if (selectedFormat == null) {
            ((TextView)findViewById(R.id.matchFormatSelectError)).setText("* Select match format.");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.matchFormatSelectError)).setText("");
        }

        String adRule = selectedAdRule.getText().toString();
        String matchFormat = selectedFormat.getText().toString();

        if (formValid) {
            File file = new File(getFilesDir(), "matches");
            try {
                FileWriter fw = new FileWriter(file, false);
                fw.write(matchName + "," + playerOneName + "," + playerTwoName + "," + adRule + "," + matchFormat);
                fw.flush();
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(view.getContext(), PreMatchSetup.class);
            intent.putExtra("matchName", matchName);
            intent.putExtra("playerOneName", playerOneName);
            intent.putExtra("playerTwoName", playerTwoName);
            intent.putExtra("adRule", adRule);
            intent.putExtra("matchFormat", matchFormat);
            startActivity(intent);
        }
    }
}

