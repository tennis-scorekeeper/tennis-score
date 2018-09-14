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
    private boolean playerOneStartedLeft;

    public Match(boolean p1Serve, boolean p1LeftSide) {
        currentMatchState = new MatchState();

        pastMatchStates = new ArrayList<>();

        playerOneServedFirst = p1Serve;

        playerOneStartedLeft = p1LeftSide;
    }

    public boolean checkInTiebreak() {
        return currentMatchState.inTieBreak();
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

    public boolean checkPlayerOneLeftSide() {
        int totalGames = currentMatchState.getTotalGames();

        boolean playerOneLeftSide = playerOneStartedLeft;
        if (totalGames == 0) {
            return playerOneStartedLeft;
        }
        if (((totalGames - 1) / 2) % 2 == 0) {
            playerOneLeftSide = !playerOneLeftSide;
        }

        if (currentMatchState.inTieBreak()) {
            int tiebreakPoints = currentMatchState.getCurrentGameTotalScore();
            if ((tiebreakPoints / 6) % 2 == 1) {
                playerOneLeftSide = !playerOneLeftSide;
            }
        }

        return playerOneLeftSide;
    }

    public String getGameHistory() {
        List<MatchState> history = new ArrayList<>();

        int lastPlayerOneGameScore = currentMatchState.getCurrentGamePlayerOneScore();
        int lastPlayerTwoGameScore = currentMatchState.getCurrentGamePlayerTwoScore();

        int i = pastMatchStates.size() - 1;

        while ((i >= 0) && (history.size() < 5)) {
            MatchState historyCurrentState = pastMatchStates.get(i);

            if (historyCurrentState.getCurrentSetPlayerOneScore()
                    != currentMatchState.getCurrentSetPlayerOneScore()) {
                break;
            }
            if (historyCurrentState.getCurrentSetPlayerTwoScore()
                    != currentMatchState.getCurrentSetPlayerTwoScore()) {
                break;
            }

            if ((historyCurrentState.getCurrentGamePlayerOneScore() != lastPlayerOneGameScore)
                    || (historyCurrentState.getCurrentGamePlayerTwoScore() != lastPlayerTwoGameScore)) {
                history.add(historyCurrentState);
                lastPlayerOneGameScore = historyCurrentState.getCurrentGamePlayerOneScore();
                lastPlayerTwoGameScore = historyCurrentState.getCurrentGamePlayerTwoScore();
            }
            i--;
        }

        String result = "";

        for (MatchState matchState : history) {
            result += matchState.getCurrentSetPlayerOneScore() + "-" + matchState.getCurrentSetPlayerTwoScore();
            result += " (" + matchState.getGameHistoryScoreDisplay() + ") ";
        }

        return result.trim();
    }

    public boolean getFaulted() {
        return currentMatchState.getFaulted();
    }

    public String getCurrentGameScore() {
        return currentMatchState.currentSet.currentGame.getScoreDisplay(checkPlayerOneServing());
    }

    public int getCurrentGamePlayerOneScore() {
        return currentMatchState.getCurrentGamePlayerOneScore();
    }

    public int getCurrentGamePlayerTwoScore() {
        return currentMatchState.getCurrentGamePlayerTwoScore();
    }

    public int getPlayerOneAces() {
        return currentMatchState.getPlayerOneAces();
    }

    public int getPlayerTwoAces() {
        return currentMatchState.getPlayerTwoAces();
    }

    public int getPlayerOneFaults() {
        return currentMatchState.getPlayerOneFaults();
    }

    public int getPlayerOneDoubleFaults() {
        return currentMatchState.getPlayerOneDoubleFaults();
    }

    public int getPlayerTwoFaults() {
        return currentMatchState.getPlayerTwoFaults();
    }

    public int getPlayerTwoDoubleFaults() {
        return currentMatchState.getPlayerTwoDoubleFaults();
    }

    public void incrementPlayerOneScore() {
        pastMatchStates.add(currentMatchState);

        currentMatchState = currentMatchState.incrementPlayerOneScore();
    }

    public void incrementPlayerTwoScore() {
        pastMatchStates.add(currentMatchState);

        currentMatchState = currentMatchState.incrementPlayerTwoScore();
    }

    public void let() {

        if (currentMatchState.getFaulted()) {
            MatchState nextMatchState = new MatchState(currentMatchState);
            if (checkPlayerOneServing()) {
                nextMatchState.playerOneSubtractFault();
            }
            else {
                nextMatchState.playerTwoSubtractFault();
            }
            pastMatchStates.add(currentMatchState);
            currentMatchState = nextMatchState;
        }
    }

    public void serverAced() {
        pastMatchStates.add(currentMatchState);
        if (checkPlayerOneServing()) {
            currentMatchState = currentMatchState.playerOneAce();
        }
        else {
            currentMatchState = currentMatchState.playerTwoAce();
        }
    }

    public void serverFaulted() {
        pastMatchStates.add(currentMatchState);
        if (checkPlayerOneServing()) {
            currentMatchState = currentMatchState.playerOneFault();
        }
        else {
            currentMatchState = currentMatchState.playerTwoFault();
        }
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
