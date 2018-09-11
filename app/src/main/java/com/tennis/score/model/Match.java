package com.tennis.score.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 9/10/2018.
 */

public class Match {

    private MatchState currentMatchState;
    private List<MatchState> pastMatchStates;

    public Match(boolean p1Serve, boolean p1LeftSide) {
        currentMatchState = new MatchState(p1Serve, p1LeftSide);

        pastMatchStates = new ArrayList<>();
    }

    public String getCurrentGameScore() {
        return currentMatchState.currentSet.currentGame.getScoreDisplay(true);
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
}
