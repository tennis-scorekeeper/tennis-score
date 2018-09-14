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

    public Set() {
        playerOneScore = 0;
        playerTwoScore = 0;

        currentGame = new Game();
        completedGames = new ArrayList<>();
    }

    public Set(Set set) {
        playerOneScore = set.playerOneScore;
        playerTwoScore = set.playerTwoScore;

        currentGame = new Game(set.currentGame);
        completedGames = new ArrayList<>();
        for (Game completedGame : set.completedGames) {
            completedGames.add(new Game(completedGame));
        }
    }

    public boolean incrementPlayerOneScore() {
        playerOneScore++;
        completedGames.add(currentGame);

        if (playerOneScore == 6 && playerTwoScore == 6) {
            currentGame = new Game(true);
        }
        else {
            currentGame = new Game();
        }

        if (playerOneScore == 6 && playerTwoScore <= 4) {
            return true;
        }
        if (playerOneScore == 7) {
            return true;
        }
        return false;
    }

    public boolean incrementPlayerTwoScore() {
        playerTwoScore++;
        completedGames.add(currentGame);

        if (playerOneScore == 6 && playerTwoScore == 6) {
            currentGame = new Game(true);
        }
        else {
            currentGame = new Game();
        }

        if (playerTwoScore == 6 && playerOneScore <= 4) {
            return true;
        }
        if (playerTwoScore == 7) {
            return true;
        }
        return false;
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
}
