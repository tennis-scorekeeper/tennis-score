package com.tennis.score.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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

    public void onClickTest(View view) {
        EditText edit = (EditText)findViewById(R.id.matchName);
        System.out.println(edit.getText().toString());
    }
}

