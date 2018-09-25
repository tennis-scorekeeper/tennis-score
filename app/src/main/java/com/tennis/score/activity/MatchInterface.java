package com.tennis.score.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
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
                matchSeconds = maxMatchSeconds - ((int) (millisUntilFinished / 1000)) - 1;
                if (matchSeconds < 0) {
                    matchSeconds = 0;
                }
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
                warmupSeconds = maxSeconds - ((int) (millisUntilFinished / 1000)) - 1;
                if (warmupSeconds < 0) {
                    warmupSeconds = 0;
                }
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

    public void optionsMenu(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        ScrollView scrollView = new ScrollView(this);

        final LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setPadding(0,25,0,0);

        scrollView.addView(innerLayout);
        layout.addView(scrollView);
        builder.setView(layout);

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        final AlertDialog dialog = builder.create();
        buildOptionsMenu(innerLayout, dialog);

        dialog.show();
    }

    private void buildOptionsMenu(final LinearLayout innerLayout, final AlertDialog dialog) {
        Button codeButton = new Button(this);
        codeButton.setText("Code Violation");
        codeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buildCodeMenu(innerLayout);
            }
        });

        Button tvButton = new Button(this);
        tvButton.setText("Time Violation");
        tvButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                buildTVMenu(innerLayout, dialog);
            }
        });

        Button timeoutButton = new Button(this);
        timeoutButton.setText("Timeout");
        timeoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

            }
        });

        innerLayout.addView(codeButton);
        innerLayout.addView(tvButton);
        innerLayout.addView(timeoutButton);
    }

    private void buildCodeMenu(LinearLayout innerLayout) {
        final RadioGroup playerPenalty = new RadioGroup(this);
        playerPenalty.setOrientation(RadioGroup.HORIZONTAL);
        playerPenalty.setPadding(96,16,0,16);

        final RadioButton playerOneChoice = new RadioButton(this);
        playerOneChoice.setText(playerOneName);
        final RadioButton playerTwoChoice = new RadioButton(this);
        playerTwoChoice.setText(playerTwoName);
        playerOneChoice.setPadding(0,0,16,0);

        playerPenalty.addView(playerOneChoice);
        playerPenalty.addView(playerTwoChoice);

        Spinner penalty = new Spinner(this);
        List<String> penaltyOptions = new ArrayList<>();
        penaltyOptions.add("Warning");
        penaltyOptions.add("Point Penalty");
        penaltyOptions.add("Game Penalty");
        penaltyOptions.add("Default");
        ArrayAdapter<String> penaltyAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, penaltyOptions);
        penaltyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        penalty.setAdapter(penaltyAdapter);
        penalty.setPadding(96,16,0,32);

        Spinner codeType = new Spinner(this);
        List<String> codeOptions = new ArrayList<>();
        codeOptions.add("Del - Unreasonable Delays");
        codeOptions.add("AOb - Audible Obscenity");
        codeOptions.add("VOb - Visible Obscenity");
        codeOptions.add("BA - Ball Abuse");
        codeOptions.add("RA - Racket Abuse");
        codeOptions.add("VA - Verbal Abuse");
        codeOptions.add("PhA - Physical Abuse");
        codeOptions.add("CC - Coaching, Coaches");
        codeOptions.add("UnC - Unsportsmanlike Conduct");
        ArrayAdapter<String> codeTypeAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, codeOptions);
        codeTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        codeType.setAdapter(codeTypeAdapter);
        codeType.setPadding(96,32,0,16);

        innerLayout.removeAllViews();
        innerLayout.addView(playerPenalty);
        innerLayout.addView(penalty);
        innerLayout.addView(codeType);

    }

    private void buildTVMenu(LinearLayout innerLayout, final AlertDialog dialog) {
        final TextView playerLabel = new TextView(this);
        playerLabel.setText("Player");
        playerLabel.setTextColor(blackColor);
        playerLabel.setPadding(96, 16, 0, 8);

        final RadioGroup playerPenalty = new RadioGroup(this);
        playerPenalty.setOrientation(RadioGroup.HORIZONTAL);
        playerPenalty.setPadding(96,0,0,16);

        final RadioButton playerOneChoice = new RadioButton(this);
        playerOneChoice.setText(playerOneName);
        final RadioButton playerTwoChoice = new RadioButton(this);
        playerTwoChoice.setText(playerTwoName);
        playerOneChoice.setPadding(0,0,16,0);

        playerPenalty.addView(playerOneChoice);
        playerPenalty.addView(playerTwoChoice);

        TextView penaltyLabel = new TextView(this);
        penaltyLabel.setText("Penalty");
        penaltyLabel.setTextColor(blackColor);
        penaltyLabel.setPadding(96, 16, 0, 8);

        final Spinner penalty = new Spinner(this);
        List<String> penaltyOptions = new ArrayList<>();
        penaltyOptions.add("Warning");
        penaltyOptions.add("Point Penalty");
        ArrayAdapter<String> penaltyAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, penaltyOptions);
        penaltyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        penalty.setAdapter(penaltyAdapter);
        penalty.setPadding(96,16,0,32);

        Button submitButton = new Button(this);
        submitButton.setText("Submit");
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String penaltyType = penalty.getSelectedItem().toString();
                boolean pointPenalty = true;
                if (penaltyType.equals("Warning")) {
                    pointPenalty = false;
                }

                int selectedPlayer = playerPenalty.getCheckedRadioButtonId();
                if (selectedPlayer == playerOneChoice.getId()) {
                    dialog.dismiss();
                    match.playerOneTimeViolation(pointPenalty);
                    updateAllDisplays();
                }
                else if (selectedPlayer == playerTwoChoice.getId()) {
                    dialog.dismiss();
                    match.playerTwoTimeViolation(pointPenalty);
                    updateAllDisplays();
                }
                else {
                    playerLabel.setTextColor(redColor);
                }
            }
        });

        innerLayout.removeAllViews();
        innerLayout.addView(playerLabel);
        innerLayout.addView(playerPenalty);
        innerLayout.addView(penaltyLabel);
        innerLayout.addView(penalty);
        innerLayout.addView(submitButton);
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
        int minutes = totalSeconds / 60;

        String hourString = String.valueOf(hours);
        String minuteString = String.valueOf(minutes);


        if (minuteString.length() == 1) {
            minuteString = "0" + minuteString;
        }
        return hourString + ":" + minuteString;
    }

    private final int blackColor = 0xff000000;
    private final int redColor = 0xffcc0000;
}
