package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.tennis.score.R;
import com.tennis.score.model.Match;

/**
 * Created by Ali on 9/10/2018.
 */

public class MatchInterface extends AppCompatActivity {

    private String tournamentName;
    private String date;
    private String playerOneName;
    private String playerOneFrom;
    private String playerTwoName;
    private String playerTwoFrom;
    private String round;
    private String division;
    private String matchFormat;
    private String adRule;
    private String referee;

    private String courtNumber;
    private String chairUmpire;
    private String coinTossWinner;
    private String winnerChoice;
    private String leftSide;
    private String rightSide;

    Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_interface);

        Intent intent = getIntent();

        tournamentName = intent.getStringExtra("tournamentName");
        date = intent.getStringExtra("date");
        playerOneName = intent.getStringExtra("playerOneName");
        playerOneFrom = intent.getStringExtra("playerOneFrom");
        playerTwoName = intent.getStringExtra("playerTwoName");
        playerTwoFrom = intent.getStringExtra("playerTwoFrom");
        round = intent.getStringExtra("round");
        division = intent.getStringExtra("division");
        matchFormat = intent.getStringExtra("matchFormat");
        adRule = intent.getStringExtra("adRule");
        referee = intent.getStringExtra("referee");

        courtNumber = intent.getStringExtra("courtNumber");
        chairUmpire = intent.getStringExtra("chairUmpire");
        coinTossWinner = intent.getStringExtra("coinTossWinner");
        winnerChoice = intent.getStringExtra("winnerChoice");
        leftSide = intent.getStringExtra("leftSide");
        rightSide = intent.getStringExtra("rightSide");

        String fetchedData = tournamentName + "," + date + "," + playerOneName + "," + playerOneFrom + ","
                + playerTwoName + "," + playerTwoFrom + "," + round + "," + division + ","
                + matchFormat + "," + adRule + "," + referee;
        System.out.println(fetchedData);
        fetchedData = courtNumber + "," + chairUmpire + "," + coinTossWinner + "," + winnerChoice + ","
                + leftSide + "," + rightSide;
        System.out.println(fetchedData);

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
