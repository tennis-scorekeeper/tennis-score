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
    private boolean ads;
    private int tiebreakWinScore;

    private final String[] displayScores = {"0", "15", "30", "40", "Ad"};

    public Game(boolean ad) {
        playerOneScore = 0;
        playerTwoScore = 0;

        tiebreak = false;
        tiebreakWinScore = 0;
        ads = ad;
    }

    public Game(boolean isTiebreak, int tiebreakWin, boolean ad) {
        playerOneScore = 0;
        playerTwoScore = 0;

        tiebreak = isTiebreak;
        tiebreakWinScore = tiebreakWin;
        ads = ad;
    }

    public Game(Game game) {
        playerOneScore = game.playerOneScore;
        playerTwoScore = game.playerTwoScore;

        tiebreak = game.tiebreak;
        tiebreakWinScore = game.tiebreakWinScore;
        ads = game.ads;
    }

    public boolean incrementPlayerOneScore() {
        if (tiebreak) {
            playerOneScore++;
            if ((playerOneScore) >= tiebreakWinScore && ((playerOneScore - playerTwoScore) > 1)){
                return true;
            }
            return false;
        }
        else {
            if (ads) {
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
            else {
                playerOneScore++;
                if (playerOneScore >= 4) {
                    return true;
                }
                return false;
            }
        }
    }

    public boolean incrementPlayerTwoScore() {
        if (tiebreak) {
            playerTwoScore++;
            if ((playerTwoScore) >= tiebreakWinScore && ((playerTwoScore - playerOneScore) > 1)){
                return true;
            }
            return false;
        }
        else {
            if (ads) {
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
            else {
                playerTwoScore++;
                if (playerTwoScore >= 4) {
                    return true;
                }
                return false;
            }
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
