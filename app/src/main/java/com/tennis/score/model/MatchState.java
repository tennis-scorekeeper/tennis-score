package com.tennis.score.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 9/10/2018.
 */

public class MatchState {

    private boolean playerOneServing;
    private boolean playerOneOnLeftSide;

    public Set currentSet;
    private List<Set> completedSets;

    MatchState(boolean p1Serve, boolean p1LeftSide) {
        playerOneServing = p1Serve;
        playerOneOnLeftSide = p1LeftSide;

        currentSet = new Set();
        completedSets = new ArrayList<>();
    }

    MatchState(MatchState oldState) {
        playerOneServing = oldState.playerOneServing;
        playerOneOnLeftSide = oldState.playerOneOnLeftSide;

        currentSet = new Set(oldState.currentSet);
        completedSets = new ArrayList<>();
        for (Set completedSet : oldState.completedSets) {
            completedSets.add(new Set(completedSet));
        }
    }

    public MatchState incrementPlayerOneScore() {
        MatchState nextMatchState = new MatchState(this);
        boolean wonGame = nextMatchState.currentSet.currentGame.incrementPlayerOneScore();
        if (wonGame) {
            boolean wonSet = nextMatchState.currentSet.incrementPlayerOneScore();
            if (wonSet) {
                nextMatchState.completedSets.add(currentSet);
                nextMatchState.currentSet = new Set();
            }
        }
        return nextMatchState;
    }

    public MatchState incrementPlayerTwoScore() {
        MatchState nextMatchState = new MatchState(this);
        boolean wonGame = nextMatchState.currentSet.currentGame.incrementPlayerTwoScore();
        if (wonGame) {
            boolean wonSet = nextMatchState.currentSet.incrementPlayerTwoScore();
            if (wonSet) {
                nextMatchState.completedSets.add(currentSet);
                nextMatchState.currentSet = new Set();
            }
        }
        return nextMatchState;
    }
}
