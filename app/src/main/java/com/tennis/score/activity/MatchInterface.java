package com.tennis.score.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tennis.score.R;
import com.tennis.score.model.Match;

/**
 * Created by Ali on 9/10/2018.
 */

public class MatchInterface extends AppCompatActivity {
    Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_interface);

        match = new Match(true, true);
    }

    public void p1Score(View view) {
        match.incrementPlayerOneScore();
        System.out.println(match.getCurrentMatchState().currentSet.getPlayerOneScore() + "-" + match.getCurrentMatchState().currentSet.getPlayerTwoScore() + " " + match.getCurrentGameScore());

    }

    public void p2Score(View view) {
        match.incrementPlayerTwoScore();
        System.out.println(match.getCurrentMatchState().currentSet.getPlayerOneScore() + "-" + match.getCurrentMatchState().currentSet.getPlayerTwoScore() + " " + match.getCurrentGameScore());
    }

    public void undo(View view) {
        match.undo();
        System.out.println(match.getCurrentMatchState().currentSet.getPlayerOneScore() + "-" + match.getCurrentMatchState().currentSet.getPlayerTwoScore() + " " + match.getCurrentGameScore());
    }

}
