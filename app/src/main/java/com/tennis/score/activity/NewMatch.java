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
        if (playerOneName.length() <= 0) {
            ((TextView)findViewById(R.id.player1NameError)).setText("* Name.");
            formValid = false;
        }

        if (playerTwoName.length() <= 0) {
            ((TextView)findViewById(R.id.player2NameError)).setText("* Name.");
            formValid = false;
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

        if (formValid) {
            Intent intent = new Intent(view.getContext(), PreMatchSetup.class);
            intent.putExtra("matchName", matchName);
            intent.putExtra("playerOneName", playerOneName);
            intent.putExtra("playerTwoName", playerTwoName);
            intent.putExtra("adRule", selectedAdRule.getText().toString());
            intent.putExtra("matchFormat", selectedFormat.getText().toString());
            startActivity(intent);
        }
    }
}

