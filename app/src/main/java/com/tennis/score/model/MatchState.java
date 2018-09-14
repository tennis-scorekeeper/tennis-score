package com.tennis.score.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ali on 9/10/2018.
 */

public class MatchState {

    public Set currentSet;
    private List<Set> completedSets;

    private int playerOneAces;
    private int playerTwoAces;

    private int playerOneFaults;
    private int playerOneDoubleFaults;
    private int playerTwoFaults;
    private int playerTwoDoubleFaults;

    private boolean faulted;

    MatchState() {
        currentSet = new Set();
        completedSets = new ArrayList<>();

        playerOneAces = 0;
        playerTwoAces = 0;

        playerOneFaults = 0;
        playerOneDoubleFaults = 0;
        playerTwoFaults = 0;
        playerTwoDoubleFaults = 0;


        faulted = false;
    }

    MatchState(MatchState oldState) {
        currentSet = new Set(oldState.currentSet);
        completedSets = new ArrayList<>();
        for (Set completedSet : oldState.completedSets) {
            completedSets.add(new Set(completedSet));
        }

        playerOneAces = oldState.playerOneAces;
        playerTwoAces = oldState.playerTwoAces;

        playerOneFaults = oldState.playerOneFaults;
        playerOneDoubleFaults = oldState.playerOneDoubleFaults;
        playerTwoFaults = oldState.playerTwoFaults;
        playerTwoDoubleFaults = oldState.playerTwoDoubleFaults;

        faulted = false;
    }

    public MatchState incrementPlayerOneScore() {
        MatchState nextMatchState = new MatchState(this);
        boolean wonGame = nextMatchState.currentSet.currentGame.incrementPlayerOneScore();
        if (wonGame) {
            boolean wonSet = nextMatchState.currentSet.incrementPlayerOneScore();
            if (wonSet) {
                nextMatchState.completedSets.add(nextMatchState.currentSet);
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
                nextMatchState.completedSets.add(nextMatchState.currentSet);
                nextMatchState.currentSet = new Set();
            }
        }
        return nextMatchState;
    }

    public MatchState playerOneAce() {
        MatchState nextMatchState = incrementPlayerOneScore();
        nextMatchState.playerOneAces += 1;
        return nextMatchState;
    }

    public MatchState playerTwoAce() {
        MatchState nextMatchState = incrementPlayerTwoScore();
        nextMatchState.playerTwoAces += 1;
        return nextMatchState;
    }

    public MatchState playerOneFault() {
        if (faulted) {
            MatchState nextMatchState =  incrementPlayerTwoScore();
            nextMatchState.playerOneDoubleFaults += 1;
            return nextMatchState;
        }
        else {
            MatchState nextMatchState = new MatchState(this);
            nextMatchState.faulted = true;
            nextMatchState.playerOneFaults += 1;
            return nextMatchState;
        }
    }

    public MatchState playerTwoFault() {
        if (faulted) {
            MatchState nextMatchState = incrementPlayerOneScore();
            nextMatchState.playerTwoDoubleFaults += 1;
            return nextMatchState;
        }
        else {
            MatchState nextMatchState = new MatchState(this);
            nextMatchState.faulted = true;
            nextMatchState.playerTwoFaults += 1;
            return nextMatchState;
        }
    }

    public void playerOneSubtractFault() {
        playerOneFaults -= 1;
    }

    public void playerTwoSubtractFault() {
        playerTwoFaults -= 1;
    }

    public int getTotalGames() {
        int total = 0;
        for (Set set : completedSets) {
            total += set.getTotalGames();
        }
        total += currentSet.getTotalGames();
        return total;
    }

    public boolean inTieBreak() {
        return currentSet.inTiebreak();
    }

    public int getCurrentGameTotalScore() {
        return currentSet.getCurrentGameTotalScore();
    }

    public int getCurrentGamePlayerOneScore() {
        return currentSet.getCurrentGamePlayerOneScore();
    }

    public int getCurrentGamePlayerTwoScore() {
        return currentSet.getCurrentGamePlayerTwoScore();
    }

    public int getCurrentSetPlayerOneScore() {
        return currentSet.getPlayerOneScore();
    }

    public int getCurrentSetPlayerTwoScore() {
        return currentSet.getPlayerTwoScore();
    }

    public String getGameHistoryScoreDisplay() {
        return currentSet.getGameHistoryScoreDisplay();
    }

    public boolean getFaulted() { return faulted; }

    public int getPlayerOneAces() { return playerOneAces; }

    public int getPlayerTwoAces() { return playerTwoAces; }

    public int getPlayerOneFaults() { return playerOneFaults; }

    public int getPlayerOneDoubleFaults() { return playerOneDoubleFaults; }

    public int getPlayerTwoFaults() { return playerTwoFaults; }

    public int getPlayerTwoDoubleFaults() { return playerTwoDoubleFaults; }

    public List<String> getSetScores() {
        List<String> result = new ArrayList<>();
        for (Set completedSet : completedSets) {
            result.add(String.valueOf(completedSet.getPlayerOneScore()));
            result.add(String.valueOf(completedSet.getPlayerTwoScore()));
        }
        result.add(String.valueOf(currentSet.getPlayerOneScore()));
        result.add(String.valueOf(currentSet.getPlayerTwoScore()));

        return result;
    }
}
