package com.tennis.score.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

    private CountDownTimer countDownTimer;
    private final int maxSeconds = 60*60;
    private TextView serveTimerDisplay;
    private int warmupSeconds = 0;

    private CountDownTimer matchTimeTimer;
    private final int maxMatchSeconds = 600*60;
    private int matchSeconds = 3595;
    private TextView matchTimeDisplay;

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

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        serveTimerDisplay = (TextView)findViewById(R.id.serveTimerDisplay);
        matchTimeDisplay = (TextView)findViewById(R.id.matchTimeDisplay);
        matchTimeTimer = new CountDownTimer(maxMatchSeconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                matchSeconds = maxMatchSeconds - ((int) (millisUntilFinished / 1000));
                matchTimeDisplay.setText(getHourTimeString(matchSeconds));
            }

            public void onFinish() {
                matchTimeDisplay.setText("00:00");
            }
        };
        matchTimeTimer.start();

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

    public void ace(View view) {
        match.serverAced();

        updateAllDisplays();
    }

    public void let(View view) {
        match.let();

        updateAllDisplays();
    }

    public void fault(View view) {
        match.serverFaulted();

        updateAllDisplays();
    }

    public void resetTimer(View view) {
        if (!(countDownTimer == null)) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(maxSeconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                warmupSeconds = maxSeconds - ((int) (millisUntilFinished / 1000));
                serveTimerDisplay.setText(getTimeString(warmupSeconds));
            }

            public void onFinish() {
                serveTimerDisplay.setText("00:00");
            }
        };

        countDownTimer.start();

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

    public void setFaultButton() {
        if (match.getFaulted()) {
            ((Button)findViewById(R.id.faultButton)).setText("Double Fault");
        }
        else {
            ((Button)findViewById(R.id.faultButton)).setText("Fault");
        }
    }

    public void updateGameHistoryDisplay() {
        ((TextView)findViewById(R.id.gameHistoryDisplay)).setText(match.getGameHistory());
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
        resetTimer(findViewById(R.id.resetTimer));
        ((TextView) findViewById(R.id.gameScoreDisplay)).setText(match.getCurrentGameScore());
        setPlayerButtons();
        setSetScoreDisplay();
        updateLeadingPlayerName();
        setFaultButton();
        updateGameHistoryDisplay();
    }

    private String getTimeString(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String minuteString = String.valueOf(minutes);
        String secondString = String.valueOf(seconds);

        if (minuteString.length() == 1) {
            minuteString = "0" + minuteString;
        }
        if (secondString.length() == 1) {
            secondString = "0" + secondString;
        }
        return minuteString + ":" + secondString;
    }

    private String getHourTimeString(int totalSeconds) {
        int hours = totalSeconds / 3600;
        String hourString = String.valueOf(hours);
        return hourString + ":" + getTimeString(totalSeconds-(hours*3600));
    }

    private final int blackColor = 0xff000000;
}
