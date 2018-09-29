package com.tennis.score.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 9/10/2018.
 */

public class Set {
    private int playerOneScore;
    private int playerTwoScore;

    public Game currentGame;
    private List<Game> completedGames;

    boolean adRule;
    boolean eightGameProSet;
    boolean matchTiebreakSet;

    public Set(boolean ads, boolean isEightGame, boolean isMatchTiebreakSet) {
        playerOneScore = 0;
        playerTwoScore = 0;

        currentGame = new Game(isMatchTiebreakSet, 10, ads);
        completedGames = new ArrayList<>();

        adRule = ads;
        eightGameProSet = isEightGame;
        matchTiebreakSet = isMatchTiebreakSet;
    }

    public Set(Set set) {
        playerOneScore = set.playerOneScore;
        playerTwoScore = set.playerTwoScore;

        currentGame = new Game(set.currentGame);
        completedGames = new ArrayList<>();
        for (Game completedGame : set.completedGames) {
            completedGames.add(new Game(completedGame));
        }

        adRule = set.adRule;
        eightGameProSet = set.eightGameProSet;
        matchTiebreakSet = set.matchTiebreakSet;
    }

    public boolean incrementPlayerOneGameScore() {
        return currentGame.incrementPlayerOneScore();
    }

    public boolean incrementPlayerTwoGameScore() {
        return currentGame.incrementPlayerTwoScore();
    }

    public boolean incrementPlayerOneScore() {
        if (matchTiebreakSet) {
            return true;
        }
        else {
            playerOneScore++;
            completedGames.add(currentGame);

            int gamesNeeded = 6;
            if (eightGameProSet) {
                gamesNeeded = 8;
            }

            if (playerOneScore == gamesNeeded && playerTwoScore == gamesNeeded) {
                currentGame = new Game(true, 7, adRule);
            } else {
                currentGame = new Game(adRule);
            }

            if (playerOneScore == gamesNeeded && playerTwoScore <= gamesNeeded - 2) {
                return true;
            }
            if (playerOneScore == gamesNeeded + 1) {
                return true;
            }
            return false;
        }
    }

    public boolean incrementPlayerTwoScore() {
        if (matchTiebreakSet) {
            return true;
        }
        else {
            playerTwoScore++;

            completedGames.add(currentGame);

            int gamesNeeded = 6;
            if (eightGameProSet) {
                gamesNeeded = 8;
            }

            if (playerOneScore == gamesNeeded && playerTwoScore == gamesNeeded) {
                currentGame = new Game(true, 7, adRule);
            } else {
                currentGame = new Game(adRule);
            }

            if (playerTwoScore == gamesNeeded && playerOneScore <= gamesNeeded - 2) {
                return true;
            }
            if (playerTwoScore == gamesNeeded + 1) {
                return true;
            }
            return false;
        }
    }

    public int getTotalGames() {
        return playerOneScore + playerTwoScore;
    }

    public boolean inTiebreak() {
        return currentGame.isTiebreak();
    }

    public int getCurrentGameTotalScore() {
        return currentGame.getTotalGameScore();
    }

    public int getCurrentGamePlayerOneScore() {
        return currentGame.getPlayerOneScore();
    }

    public int getCurrentGamePlayerTwoScore() {
        return currentGame.getPlayerTwoScore();
    }

    public String getGameHistoryScoreDisplay() { return currentGame.getHistoryScoreDisplay(); }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public String getSetScore() {
        return playerOneScore + "-" + playerTwoScore;
    }

    public String getScoreDisplay(boolean playerOneFirst) {
        return currentGame.getScoreDisplay(playerOneFirst);
    }

    public boolean isMatchTiebreakSet() {
        return matchTiebreakSet;
    }
}
