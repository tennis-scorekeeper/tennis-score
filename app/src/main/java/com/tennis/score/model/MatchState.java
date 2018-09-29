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

    private int playerOneTimeViolations;
    private int playerTwoTimeViolations;

    private List<CodeViolation> playerOneCodeViolations;
    private List<CodeViolation> playerTwoCodeViolations;

    private boolean faulted;
    private boolean adRule;

    private boolean eightGameProSet;
    private boolean hasMatchTiebreakSet;

    MatchState(boolean ads, boolean isEightGameProSet, boolean matchTiebreakSet) {
        currentSet = new Set(ads, isEightGameProSet, false);
        completedSets = new ArrayList<>();

        playerOneAces = 0;
        playerTwoAces = 0;

        playerOneFaults = 0;
        playerOneDoubleFaults = 0;
        playerTwoFaults = 0;
        playerTwoDoubleFaults = 0;

        playerOneTimeViolations = 0;
        playerTwoTimeViolations = 0;

        playerOneCodeViolations = new ArrayList<>();
        playerTwoCodeViolations = new ArrayList<>();

        faulted = false;
        adRule = ads;

        eightGameProSet = isEightGameProSet;
        hasMatchTiebreakSet = matchTiebreakSet;
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

        playerOneTimeViolations = oldState.playerOneTimeViolations;
        playerTwoTimeViolations = oldState.playerTwoTimeViolations;

        playerOneCodeViolations = new ArrayList<>();
        for (CodeViolation code : oldState.playerOneCodeViolations) {
            playerOneCodeViolations.add(new CodeViolation(code));
        }

        playerTwoCodeViolations = new ArrayList<>();
        for (CodeViolation code : oldState.playerTwoCodeViolations) {
            playerTwoCodeViolations.add(new CodeViolation(code));
        }

        faulted = false;
        adRule = oldState.adRule;
        eightGameProSet = oldState.eightGameProSet;
        hasMatchTiebreakSet = oldState.hasMatchTiebreakSet;
    }

    public MatchState incrementPlayerOneScore() {
        MatchState nextMatchState = new MatchState(this);
        boolean wonGame = nextMatchState.currentSet.incrementPlayerOneGameScore();
        if (wonGame) {
            boolean wonSet = nextMatchState.currentSet.incrementPlayerOneScore();
            if (wonSet) {
                nextMatchState.completedSets.add(nextMatchState.currentSet);
                boolean nextSetMatchTiebreak = false;
                if (hasMatchTiebreakSet && nextMatchState.completedSets.size() == 2){
                    nextSetMatchTiebreak = true;
                }
                nextMatchState.currentSet = new Set(adRule, eightGameProSet, nextSetMatchTiebreak);
            }
        }
        return nextMatchState;
    }

    public MatchState incrementPlayerTwoScore() {
        MatchState nextMatchState = new MatchState(this);
        boolean wonGame = nextMatchState.currentSet.incrementPlayerTwoGameScore();
        if (wonGame) {
            boolean wonSet = nextMatchState.currentSet.incrementPlayerTwoScore();
            if (wonSet) {
                nextMatchState.completedSets.add(nextMatchState.currentSet);
                boolean nextSetMatchTiebreak = false;
                if (hasMatchTiebreakSet && nextMatchState.completedSets.size() == 2){
                    nextSetMatchTiebreak = true;
                }
                nextMatchState.currentSet = new Set(adRule, eightGameProSet, nextSetMatchTiebreak);
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

    public MatchState playerOneTimeViolation(boolean pointPenalty) {
        if (pointPenalty) {
            MatchState nextMatchState = incrementPlayerTwoScore();
            nextMatchState.playerOneTimeViolations += 1;
            return nextMatchState;
        }
        else {
            MatchState nextMatchState = new MatchState(this);
            nextMatchState.playerOneTimeViolations += 1;
            return nextMatchState;
        }
    }

    public MatchState playerTwoTimeViolation(boolean pointPenalty) {
        if (pointPenalty) {
            MatchState nextMatchState = incrementPlayerOneScore();
            nextMatchState.playerTwoTimeViolations += 1;
            return nextMatchState;
        }
        else {
            MatchState nextMatchState = new MatchState(this);
            nextMatchState.playerTwoTimeViolations += 1;
            return nextMatchState;
        }
    }

    public MatchState playerOneCodeViolation(int penaltyType, String penaltyReason, String playerName) {
        // penaltyType: 0 = warning, 1 = point penalty, 2 = game penalty, 3 = default
        if (penaltyType == 0) {
            MatchState nextMatchState = new MatchState(this);
            nextMatchState.playerOneCodeViolations.add(new CodeViolation(playerName, penaltyType, penaltyReason));
            return nextMatchState;
        }
        else if (penaltyType == 1) {
            MatchState nextMatchState = incrementPlayerTwoScore();
            nextMatchState.playerOneCodeViolations.add(new CodeViolation(playerName, penaltyType, penaltyReason));
            return nextMatchState;
        }
        else if (penaltyType == 2) {
            MatchState nextMatchState = new MatchState(this);
            int playerOneOriginalScore = nextMatchState.getCurrentSetPlayerOneScore();
            int playerTwoOriginalScore = nextMatchState.getCurrentSetPlayerTwoScore();

            while ((nextMatchState.getCurrentSetPlayerOneScore() == playerOneOriginalScore)
                    && (nextMatchState.getCurrentSetPlayerTwoScore() == playerTwoOriginalScore)) {
                nextMatchState = nextMatchState.incrementPlayerTwoScore();
            }
            nextMatchState.playerOneCodeViolations.add(new CodeViolation(playerName, penaltyType, penaltyReason));
            return nextMatchState;
        }
        else {
            MatchState nextMatchState = new MatchState(this);
            nextMatchState.playerOneCodeViolations.add(new CodeViolation(playerName, penaltyType, penaltyReason));
            return nextMatchState;
        }
    }

    public MatchState playerTwoCodeViolation(int penaltyType, String penaltyReason, String playerName) {
        // penaltyType: 0 = warning, 1 = point penalty, 2 = game penalty, 3 = default
        if (penaltyType == 0) {
            MatchState nextMatchState = new MatchState(this);
            nextMatchState.playerTwoCodeViolations.add(new CodeViolation(playerName, penaltyType, penaltyReason));
            return nextMatchState;
        }
        else if (penaltyType == 1) {
            MatchState nextMatchState = incrementPlayerOneScore();
            nextMatchState.playerTwoCodeViolations.add(new CodeViolation(playerName, penaltyType, penaltyReason));
            return nextMatchState;
        }
        else if (penaltyType == 2) {
            MatchState nextMatchState = new MatchState(this);
            int playerOneOriginalScore = nextMatchState.getCurrentSetPlayerOneScore();
            int playerTwoOriginalScore = nextMatchState.getCurrentSetPlayerTwoScore();

            while ((nextMatchState.getCurrentSetPlayerOneScore() == playerOneOriginalScore)
                    && (nextMatchState.getCurrentSetPlayerTwoScore() == playerTwoOriginalScore)) {
                nextMatchState = nextMatchState.incrementPlayerOneScore();
            }
            nextMatchState.playerTwoCodeViolations.add(new CodeViolation(playerName, penaltyType, penaltyReason));
            return nextMatchState;
        }
        else {
            MatchState nextMatchState = new MatchState(this);
            nextMatchState.playerTwoCodeViolations.add(new CodeViolation(playerName, penaltyType, penaltyReason));
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

    public int getPlayerOneTimeViolations() { return playerOneTimeViolations; }

    public int getPlayerTwoTimeViolations() { return playerTwoTimeViolations; }

    public List<Set> getCompletedSets() { return completedSets; }

    public List<CodeViolation> getPlayerOneCodeViolations() { return playerOneCodeViolations; }

    public List<CodeViolation> getPlayerTwoCodeViolations() { return playerTwoCodeViolations; }

    public List<String> getSetScores() {
        List<String> result = new ArrayList<>();
        for (Set completedSet : completedSets) {
            if (completedSet.isMatchTiebreakSet()) {
                result.add(String.valueOf(completedSet.getCurrentGamePlayerOneScore()));
                result.add(String.valueOf(completedSet.getCurrentGamePlayerTwoScore()));
            }
            else {
                result.add(String.valueOf(completedSet.getPlayerOneScore()));
                result.add(String.valueOf(completedSet.getPlayerTwoScore()));
            }
        }
        if (currentSet.isMatchTiebreakSet()) {
            result.add(String.valueOf(currentSet.getCurrentGamePlayerOneScore()));
            result.add(String.valueOf(currentSet.getCurrentGamePlayerTwoScore()));
        }
        else {
            result.add(String.valueOf(currentSet.getPlayerOneScore()));
            result.add(String.valueOf(currentSet.getPlayerTwoScore()));
        }

        return result;
    }
}
