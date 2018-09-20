package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tennis.score.R;

import java.util.Date;

/**
 * Created by Ali on 9/19/2018.
 */

public class NewTournament extends AppCompatActivity {

    String signedInEmail;

    private final int blackColor = 0xff000000;
    private final int redColor = 0xffcc0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_tournament_form);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Intent intent = getIntent();
        signedInEmail = intent.getStringExtra("signedInEmail");
    }

    public void onSubmit(View view) {
        boolean formValid = true;

        String tournamentName = ((EditText)findViewById(R.id.tournamentName)).getText().toString();
        String startDate = ((EditText)findViewById(R.id.startDate)).getText().toString();

        String sqlDate = "";

        if (tournamentName.length() <= 0) {
            ((TextView)findViewById(R.id.tournamentNameLabel)).setTextColor(redColor);
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.tournamentNameLabel)).setTextColor(blackColor);
        }

        if (startDate.length() <= 0) {
            ((TextView)findViewById(R.id.startDateLabel)).setTextColor(redColor);
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.startDateLabel)).setTextColor(blackColor);
            try {
                sqlDate = new java.sql.Date(new Date(startDate).getTime()).toString();
            }
            catch (IllegalArgumentException iae) {
                formValid = false;
                ((TextView)findViewById(R.id.startDateLabel)).setTextColor(redColor);
            }
        }

        if (formValid) {
            String[] params = new String[]{
                    "creatorEmail", signedInEmail, "tournamentName", tournamentName, "startDate", sqlDate};

            try {
                String response = new RetrieveFeedTask(
                        "https://www.mikenguyenmd.com/match_live/createTournament", params).execute().get();

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
