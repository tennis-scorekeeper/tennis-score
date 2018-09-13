package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tennis.score.R;
import com.tennis.score.model.Match;

import java.util.ArrayList;
import java.util.List;

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

    private Match match;

    private List<TextView> setScoreTextViews;

    // Increment score button on click listeners

    private View.OnClickListener incrementPlayerOne = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            match.incrementPlayerOneScore();

            updateAllDisplays();
        }
    };

    private View.OnClickListener incrementPlayerTwo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            match.incrementPlayerTwoScore();

            updateAllDisplays();
        }
    };

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

        createSetTable();

        boolean playerOneServe = true;
        boolean playerOneLeftSide = true;

        if (coinTossWinner.equals(playerOneName)) {
            if (winnerChoice.equals("Receive")) {
                playerOneServe = false;
            }
        }
        if (coinTossWinner.equals(playerTwoName)) {
            if (winnerChoice.equals("Serve")) {
                playerOneServe = false;
            }
        }

        if (leftSide.equals(playerTwoName)) {
            playerOneLeftSide = false;
        }

        match = new Match(playerOneServe, playerOneLeftSide);

        updateAllDisplays();
    }

    public void undo(View view) {
        match.undo();

        updateAllDisplays();
    }

    public void setPlayerButtons() {
        if (match.checkPlayerOneLeftSide()) {
            Button leftPlayerButton = ((Button) findViewById(R.id.leftPlayerScore));
            leftPlayerButton.setOnClickListener(incrementPlayerOne);
            leftPlayerButton.setText(playerOneName);

            Button rightPlayerButton = ((Button) findViewById(R.id.rightPlayerScore));
            rightPlayerButton.setOnClickListener(incrementPlayerTwo);
            rightPlayerButton.setText(playerTwoName);
        }
        else {
            Button leftPlayerButton = ((Button) findViewById(R.id.leftPlayerScore));
            leftPlayerButton.setOnClickListener(incrementPlayerTwo);
            leftPlayerButton.setText(playerTwoName);

            Button rightPlayerButton = ((Button) findViewById(R.id.rightPlayerScore));
            rightPlayerButton.setOnClickListener(incrementPlayerOne);
            rightPlayerButton.setText(playerOneName);
        }
    }

    public void createSetTable() {
        setScoreTextViews = new ArrayList<>();
        setScoreTextViews.add(((TextView)findViewById(R.id.playerOneSetOne)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerTwoSetOne)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerOneSetTwo)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerTwoSetTwo)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerOneSetThree)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerTwoSetThree)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerOneSetFour)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerTwoSetFour)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerOneSetFive)));
        setScoreTextViews.add(((TextView)findViewById(R.id.playerTwoSetFive)));
    }

    public void setSetScoreDisplay() {
        List<String> setScores = match.getSetScores();
        for (int i = 0; i < setScores.size(); i++) {
            setScoreTextViews.get(i).setText(setScores.get(i));
            setScoreTextViews.get(i).setTextColor(blackColor);
        }
        if (match.checkPlayerOneServing()) {
            ((TextView)findViewById(R.id.playerOneNameDisplay)).setText(playerOneName + "*");
            ((TextView)findViewById(R.id.playerTwoNameDisplay)).setText(playerTwoName);
        }
        else {
            ((TextView)findViewById(R.id.playerOneNameDisplay)).setText(playerOneName);
            ((TextView)findViewById(R.id.playerTwoNameDisplay)).setText(playerTwoName + "*");
        }
    }

    public void updateLeadingPlayerName() {
        int playerOneScore = match.getCurrentGamePlayerOneScore();
        int playerTwoScore = match.getCurrentGamePlayerTwoScore();

        if (match.checkInTiebreak()) {
            if (playerOneScore > playerTwoScore) {
                ((TextView)findViewById(R.id.leadingPlayerName)).setText(playerOneName);
            }
            else if (playerOneScore < playerTwoScore) {
                ((TextView)findViewById(R.id.leadingPlayerName)).setText(playerTwoName);
            }
            else {
                ((TextView)findViewById(R.id.leadingPlayerName)).setText("");
            }
        }
        else {
            if (match.getCurrentGameScore().equals("Ad")) {
                if (playerOneScore == 4) {
                    ((TextView)findViewById(R.id.leadingPlayerName)).setText(playerOneName);
                }
                else {
                    ((TextView)findViewById(R.id.leadingPlayerName)).setText(playerTwoName);
                }
            }
            else {
                ((TextView)findViewById(R.id.leadingPlayerName)).setText("");
            }
        }
    }

    public void updateAllDisplays() {
        ((TextView) findViewById(R.id.gameScoreDisplay)).setText(match.getCurrentGameScore());
        setPlayerButtons();
        setSetScoreDisplay();
        updateLeadingPlayerName();
    }

    private final int blackColor = 0xff000000;
}
