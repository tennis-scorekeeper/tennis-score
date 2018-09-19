package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tennis.score.R;

public class TennisScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tennis_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void signIn(View view) {
        Intent intent = new Intent(view.getContext(), MatchList.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(view.getContext(), Register.class);
        startActivity(intent);
    }
}
