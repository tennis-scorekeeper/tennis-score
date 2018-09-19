package com.tennis.score.activity;

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

public class Register extends AppCompatActivity {

    private final int blackColor = 0xff000000;
    private final int redColor = 0xffcc0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_form);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void onSubmit(View view) {
        boolean formValid = true;

        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        String firstName = ((EditText)findViewById(R.id.firstName)).getText().toString();
        String lastName = ((EditText)findViewById(R.id.lastName)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirmPassword)).getText().toString();

        if (email.length() <= 0 || !email.contains("@")) {
            ((TextView)findViewById(R.id.emailLabel)).setTextColor(redColor);
            ((TextView)findViewById(R.id.emailError)).setText("Not a valid email!");
            formValid = false;
        }
        else {
            ((TextView)findViewById(R.id.emailLabel)).setTextColor(blackColor);
            ((TextView)findViewById(R.id.emailError)).setText("");

            try {
                String response = new RetrieveFeedTask(
                        "https://www.mikenguyenmd.com/match_live/checkEmailInUse",
                        new String[]{"email", email}).execute().get();

                if (response.equals("1")) {
                    ((TextView)findViewById(R.id.emailLabel)).setTextColor(redColor);
                    ((TextView)findViewById(R.id.emailError)).setText("Email already in use!");
                    formValid = false;
                }
                else {
                    ((TextView)findViewById(R.id.emailLabel)).setTextColor(blackColor);
                    ((TextView)findViewById(R.id.emailError)).setText("");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                formValid = false;
            }
        }

        if (firstName.length() <= 0) {
            formValid = false;
            ((TextView)findViewById(R.id.firstNameLabel)).setTextColor(redColor);
        }
        else {
            ((TextView)findViewById(R.id.firstNameLabel)).setTextColor(blackColor);
        }

        if (lastName.length() <= 0) {
            formValid = false;
            ((TextView)findViewById(R.id.lastNameLabel)).setTextColor(redColor);
        }
        else {
            ((TextView)findViewById(R.id.lastNameLabel)).setTextColor(blackColor);
        }

        if (password.length() <= 0) {
            formValid = false;
            ((TextView)findViewById(R.id.passwordLabel)).setTextColor(redColor);
        }
        else {
            ((TextView)findViewById(R.id.passwordLabel)).setTextColor(blackColor);
        }

        if (confirmPassword.length() <= 0) {
            formValid = false;
            ((TextView)findViewById(R.id.confirmPasswordLabel)).setTextColor(redColor);
        }
        else {
            ((TextView)findViewById(R.id.confirmPasswordLabel)).setTextColor(blackColor);
        }

        if (password.length() > 0 && confirmPassword.length() > 0) {
            if (!password.equals(confirmPassword)) {
                formValid = false;
                ((TextView) findViewById(R.id.passwordLabel)).setTextColor(redColor);
                ((TextView) findViewById(R.id.confirmPasswordLabel)).setTextColor(redColor);
                ((TextView)findViewById(R.id.passwordError)).setText("Passwords do not match!");
            } else {
                ((TextView) findViewById(R.id.passwordLabel)).setTextColor(blackColor);
                ((TextView) findViewById(R.id.confirmPasswordLabel)).setTextColor(blackColor);
                ((TextView)findViewById(R.id.passwordError)).setText("");
            }
        }

        if (formValid) {
            String[] params = new String[]{
                    "email", email, "firstName", firstName, "lastName", lastName, "password", password};

            try {
                String response = new RetrieveFeedTask(
                        "https://www.mikenguyenmd.com/match_live/register", params).execute().get();

                System.out.println(response);

                finish();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
