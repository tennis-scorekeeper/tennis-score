package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.tennis.score.R;

/**
 * Created by Ali on 9/19/2018.
 */

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_form);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void onSignIn(View view) {
        boolean formValid = true;

        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        if (email.length() <= 0 || !email.contains("@")) {
            formValid = false;
        }
        if (password.length() <= 0) {
            formValid = false;
        }
        if (formValid) {
            try {
                String response = new RetrieveFeedTask(
                        "https://www.mikenguyenmd.com/match_live/checkSignIn",
                        new String[]{"email", email, "password", password}).execute().get();

                if (response.equals("1")) {
                    ((TextView) findViewById(R.id.signInError)).setText("");
                    Intent intent = new Intent(view.getContext(), MatchList.class);
                    startActivity(intent);
                } else {
                    ((TextView) findViewById(R.id.signInError)).setText("Incorrect email or password");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ((TextView) findViewById(R.id.signInError)).setText("An error occured while trying to sign in");
            }
        }
        else {
            ((TextView) findViewById(R.id.signInError)).setText("Incorrect email or password");
        }

    }
}
