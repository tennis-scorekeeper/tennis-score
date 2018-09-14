package com.tennis.score.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali on 9/10/2018.
 */

public class Game {
    private int playerOneScore;
    private int playerTwoScore;

    private boolean tiebreak;

    private final String[] displayScores = {"0", "15", "30", "40", "Ad"};

    public Game() {
        playerOneScore = 0;
        playerTwoScore = 0;

        tiebreak = false;
    }

    public Game(boolean isTiebreak) {
        playerOneScore = 0;
        playerTwoScore = 0;

        tiebreak = isTiebreak;
    }

    public Game(Game game) {
        playerOneScore = game.playerOneScore;
        playerTwoScore = game.playerTwoScore;

        tiebreak = game.tiebreak;
    }

    public boolean incrementPlayerOneScore() {
        if (tiebreak) {
            playerOneScore++;
            if ((playerOneScore) >= 7 && ((playerOneScore - playerTwoScore) > 1)){
                return true;
            }
            return false;
        }
        else {
            if (playerTwoScore == 4) {
                playerTwoScore--;
            } else {
                playerOneScore++;
            }
            if (playerOneScore == 4 && playerTwoScore < 3) {
                return true;
            }
            if (playerOneScore == 5) {
                return true;
            }
            return false;
        }
    }

    public boolean incrementPlayerTwoScore() {
        if (tiebreak) {
            playerTwoScore++;
            if ((playerTwoScore) >= 7 && ((playerTwoScore - playerOneScore) > 1)){
                return true;
            }
            return false;
        }
        else {
            if (playerOneScore == 4) {
                playerOneScore--;
            } else {
                playerTwoScore++;
            }
            if (playerTwoScore == 4 && playerOneScore < 3) {
                return true;
            }
            if (playerTwoScore == 5) {
                return true;
            }
            return false;
        }
    }

    public String getScoreDisplay(boolean playerOneFirst) {
        if (tiebreak) {
            if (playerOneScore >= playerTwoScore) {
                return playerOneScore + " - " + playerTwoScore;
            }
            else {
                return playerTwoScore + " - " + playerOneScore;
            }
        }
        else {
            if (playerOneScore == 4 || playerTwoScore == 4) {
                return "Ad";
            }
            if (playerOneFirst) {
                return displayScores[playerOneScore] + " - " + displayScores[playerTwoScore];
            } else {
                return displayScores[playerTwoScore] + " - " + displayScores[playerOneScore];
            }
        }
    }

    public String getHistoryScoreDisplay() {
        if (tiebreak) {
            return playerOneScore + "-" + playerTwoScore;
        }
        else {
            return displayScores[playerOneScore] + "-" + displayScores[playerTwoScore];
        }
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public boolean isTiebreak() {
        return tiebreak;
    }

    public int getTotalGameScore() {
        return playerOneScore + playerTwoScore;
    }
}
