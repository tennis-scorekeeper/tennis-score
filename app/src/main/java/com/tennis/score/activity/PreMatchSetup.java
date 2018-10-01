package com.tennis.score.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tennis.score.R;

import org.json.JSONObject;

public class PreMatchSetup extends AppCompatActivity {

    String signedInEmail;

    private TextView timerDisplay;

    private Button startTimerButton;
    private Button resetTimerButton;

    private int tournamentId;
    private int matchId;
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
    private int status;
    private String score;

    private final int blackColor = 0xff000000;
    private final int redColor = 0xffcc0000;

    private final int maxSeconds = 60*60;

    private int warmupSeconds = 0;
    private int stoppedAtSecond = 0;
    private boolean paused = true;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = getIntent();

        signedInEmail = intent.getStringExtra("signedInEmail");
        tournamentId = intent.getIntExtra("touranmentId", -1);
        matchId = intent.getIntExtra("matchId", -1);
        tournamentName = intent.getStringExtra("tournamentName");

        setMatchData();

        if (status == 1 || status == 2) {
            setContentView(R.layout.completed_match);

            String result = "";
            if (status == 1) {
                ((TextView)findViewById(R.id.matchResultDisplay)).setText(playerOneName + " def. " + playerTwoName);
            }
            else {
                ((TextView)findViewById(R.id.matchResultDisplay)).setText(playerTwoName + " def. " + playerOneName);
            }

            String scoreDisplay = "";
            String[] scores = score.split(",");
            for (int i = 0; i < scores.length; i += 2) {
                scoreDisplay += scores[i] + "-" + scores[i+1] + "; ";
            }
            ((TextView)findViewById(R.id.scoreDisplay)).setText(scoreDisplay);
        }
        else {
            setContentView(R.layout.pre_match_setup);

            timerDisplay = (TextView) findViewById(R.id.timerDisplay);

            startTimerButton = (Button) findViewById(R.id.startTimer);
            resetTimerButton = (Button) findViewById(R.id.resetTimer);


            String fetchedData = tournamentName + "," + date + "," + playerOneName + "," + playerOneFrom + ","
                    + playerTwoName + "," + playerTwoFrom + "," + round + "," + division + ","
                    + matchFormat + "," + adRule + "," + referee;
            System.out.println(fetchedData);

            // logic for printing players' names and locations
            if (playerOneFrom.equals(" ")) {
                ((RadioButton) findViewById(R.id.coinTossWinnerPlayerOne)).setText(playerOneName);
            } else {
                ((RadioButton) findViewById(R.id.coinTossWinnerPlayerOne)).setText(
                        playerOneName + " (" + playerOneFrom + ")");
            }

            if (playerTwoFrom.equals(" ")) {
                ((RadioButton) findViewById(R.id.coinTossWinnerPlayerTwo)).setText(playerTwoName);
            } else {
                ((RadioButton) findViewById(R.id.coinTossWinnerPlayerTwo)).setText(
                        playerTwoName + " (" + playerTwoFrom + ")");
            }


            ((RadioButton) findViewById(R.id.leftSidePlayerOne)).setText(playerOneName);
            ((RadioButton) findViewById(R.id.leftSidePlayerTwo)).setText(playerTwoName);

            ((RadioButton) findViewById(R.id.rightSidePlayerOne)).setText(playerOneName);
            ((RadioButton) findViewById(R.id.rightSidePlayerTwo)).setText(playerTwoName);

            ((TextView) findViewById(R.id.MatchTournament)).setText(tournamentName);

            // logic for printing of match division and round
            if (division.equals(" ")) {
                ((TextView) findViewById(R.id.divisionRound)).setText(round);
            } else if (round.equals(" ")) {
                ((TextView) findViewById(R.id.divisionRound)).setText(division);
            } else {
                ((TextView) findViewById(R.id.divisionRound)).setText(division + ", " + round);
            }

            ((TextView) findViewById(R.id.FormatScoring)).setText(matchFormat + ", " + adRule + " scoring");

            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    public void onStartMatch(View view) {
        boolean formValid = true;

        String courtNumber = ((EditText) findViewById(R.id.courtNumber)).getText().toString();
        String chairUmpire = ((EditText) findViewById(R.id.chairUmpire)).getText().toString();

        String coinTossWinner = "";
        String winnerChoice = "";
        String leftSide = "";
        String rightSide = "";

        //  creates error if coinTossWinnerSelect is not selected
        RadioButton selectedCoinTossWinnerSelect = (RadioButton)findViewById(((RadioGroup)
                findViewById(R.id.coinTossWinnerSelect)).getCheckedRadioButtonId());
        if (selectedCoinTossWinnerSelect == null) {
            formValid = false;
            ((TextView)findViewById(R.id.coinTossWinnerText)).setTextColor(redColor);
        }
        else {
            ((RadioButton)findViewById(R.id.coinTossWinnerPlayerOne)).setText(playerOneName);
            ((RadioButton)findViewById(R.id.coinTossWinnerPlayerTwo)).setText(playerTwoName);

            coinTossWinner = selectedCoinTossWinnerSelect.getText().toString();
            ((TextView)findViewById(R.id.coinTossWinnerText)).setTextColor(blackColor);
        }

        // creates error if winnerChoiceSelect is not selected
        RadioButton selectedWinnerChoiceSelect = (RadioButton)findViewById(((RadioGroup)
                findViewById(R.id.winnerChoiceSelect)).getCheckedRadioButtonId());
        if (selectedWinnerChoiceSelect == null) {
            formValid = false;
            ((TextView)findViewById(R.id.winnerElectedText)).setTextColor(redColor);
        }
        else {
            winnerChoice = selectedWinnerChoiceSelect.getText().toString();
            ((TextView)findViewById(R.id.winnerElectedText)).setTextColor(blackColor);
        }

        // creates error if leftSideSelect is not selected
        RadioButton selectedLeftSideSelect = (RadioButton)findViewById(((RadioGroup)
                findViewById(R.id.leftSideSelect)).getCheckedRadioButtonId());
        if (selectedLeftSideSelect == null) {
            formValid = false;
            ((TextView)findViewById(R.id.leftSideText)).setTextColor(redColor);
        }
        else {
            leftSide = selectedLeftSideSelect.getText().toString();
            ((TextView) findViewById(R.id.leftSideText)).setTextColor(blackColor);
        }

        // creates error if rightSideSelect is not selected
        RadioButton selectedRightSideSelect = (RadioButton)findViewById(((RadioGroup)
                findViewById(R.id.rightSideSelect)).getCheckedRadioButtonId());
        if (selectedRightSideSelect == null) {
            formValid = false;
            ((TextView)findViewById(R.id.rightSideText)).setTextColor(redColor);
        }
        else {
            rightSide = selectedRightSideSelect.getText().toString();
            ((TextView) findViewById(R.id.rightSideText)).setTextColor(blackColor);
        }

        if (rightSide.equals(leftSide)) {
            ((TextView)findViewById(R.id.rightSideText)).setTextColor(redColor);
            ((TextView)findViewById(R.id.leftSideText)).setTextColor(redColor);
            formValid = false;
        }
        else {
            ((TextView) findViewById(R.id.rightSideText)).setTextColor(blackColor);
            ((TextView) findViewById(R.id.leftSideText)).setTextColor(blackColor);
        }

        if (formValid) {
            Intent intent = new Intent(view.getContext(), MatchInterface.class);

            intent.putExtra("tournamentName", tournamentName);
            intent.putExtra("date", date);
            intent.putExtra("playerOneName", playerOneName);
            intent.putExtra("playerOneFrom", playerOneFrom);
            intent.putExtra("playerTwoName", playerTwoName);
            intent.putExtra("playerTwoFrom", playerTwoFrom);
            intent.putExtra("round", round);
            intent.putExtra("division", division);
            intent.putExtra("matchFormat", matchFormat);
            intent.putExtra("adRule", adRule);
            intent.putExtra("referee", referee);

            intent.putExtra("courtNumber", courtNumber);
            intent.putExtra("chairUmpire", chairUmpire);
            intent.putExtra("coinTossWinner", coinTossWinner);
            intent.putExtra("winnerChoice", winnerChoice);
            intent.putExtra("leftSide", leftSide);
            intent.putExtra("rightSide", rightSide);

            intent.putExtra("matchId", matchId);

            startActivity(intent);
            this.finish();
        }
    }

    public void startTimer(View view) {
        if (paused) {
            countDownTimer = new CountDownTimer(maxSeconds * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    warmupSeconds = maxSeconds - ((int) (millisUntilFinished / 1000)) - 1;
                    int secondsToDisplay = warmupSeconds + stoppedAtSecond;
                    if (secondsToDisplay < 0) {
                        secondsToDisplay = 0;
                    }
                    timerDisplay.setText(getTimeString(secondsToDisplay));
                }

                public void onFinish() {
                    timerDisplay.setText("00:00");
                    resetTimerButton.setEnabled(true);
                }
            };

            countDownTimer.start();
            paused = false;

            startTimerButton.setText("Stop");
            resetTimerButton.setEnabled(false);
        }
        else {
            stoppedAtSecond += warmupSeconds;

            countDownTimer.cancel();
            paused = true;

            startTimerButton.setText("Start");
            resetTimerButton.setEnabled(true);

        }
    }

    public void resetTimer(View view) {
        stoppedAtSecond = 0;
        timerDisplay.setText("00:00");
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

    private void setMatchData() {
        String[] params = new String[]{
                "matchId", String.valueOf(matchId)};

        try {
            String response = new RetrieveFeedTask(
                    "https://www.mikenguyenmd.com/match_live/getMatch", params).execute().get();

            if (!response.equals("null")) {
                JSONObject matchObject = new JSONObject(response.toString());

                date = matchObject.getString("date");
                playerOneName = matchObject.getString("playerOneName");
                playerOneFrom = matchObject.getString("playerOneFrom");
                playerTwoName = matchObject.getString("playerTwoName");
                playerTwoFrom = matchObject.getString("playerTwoFrom");
                round = matchObject.getString("round");
                division = matchObject.getString("division");
                matchFormat = matchObject.getString("matchFormat");
                adRule = matchObject.getString("adRule");
                referee = matchObject.getString("referee");
                status =  matchObject.getInt("status");
                if (status == 1 || status == 2) {
                    score = matchObject.getString("score");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
