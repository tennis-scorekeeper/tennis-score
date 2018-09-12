package com.tennis.score.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 9/10/2018.
 */

public class Match {

    private MatchState currentMatchState;
    private List<MatchState> pastMatchStates;

    private boolean playerOneServedFirst;

    public Match(boolean p1Serve, boolean p1LeftSide) {
        currentMatchState = new MatchState(p1LeftSide);

        pastMatchStates = new ArrayList<>();

        playerOneServedFirst = p1Serve;
    }

    public boolean checkPlayerOneServing() {
        int totalGames = currentMatchState.getTotalGames();

        boolean playerOneServing;
        if (totalGames % 2 == 0) {
            if (playerOneServedFirst) {
                playerOneServing =  true;
            }
            else {
                playerOneServing = false;
            }
        }
        else {
            if (playerOneServedFirst) {
                playerOneServing = false;
            }
            else {
                playerOneServing = true;
            }
        }

        if (currentMatchState.inTieBreak()) {
            int tiebreakPoints = currentMatchState.getCurrentGameTotalScore();
            if (tiebreakPoints == 0) {
                return playerOneServing;
            }
            else {
                int temp = (tiebreakPoints - 1) / 2;
                if (temp % 2 == 1) {
                    return playerOneServing;
                }
                else {
                    return !playerOneServing;
                }
            }
        }
        else {
            return playerOneServing;
        }
    }

    public String getCurrentGameScore() {
        return currentMatchState.currentSet.currentGame.getScoreDisplay(checkPlayerOneServing());
    }

    public void incrementPlayerOneScore() {
        pastMatchStates.add(currentMatchState);

        currentMatchState = currentMatchState.incrementPlayerOneScore();
    }

    public void incrementPlayerTwoScore() {
        pastMatchStates.add(currentMatchState);

        currentMatchState = currentMatchState.incrementPlayerTwoScore();
    }

    public MatchState getCurrentMatchState() {
        return currentMatchState;
    }

    public void undo() {
        if (pastMatchStates.size() > 0) {
            currentMatchState = pastMatchStates.remove(pastMatchStates.size() - 1);
        }
    }

    public List<String> getSetScores() {
        return currentMatchState.getSetScores();
    }
}
