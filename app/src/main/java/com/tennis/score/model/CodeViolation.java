package com.tennis.score.model;

/**
 * Created by Ali on 9/26/2018.
 */

public class CodeViolation {
    private String playerName;
    private int penalty;
    private String reason;

    public CodeViolation(String name, int pen, String res) {
        playerName = name;
        penalty = pen;
        reason = res;
    }

    public CodeViolation(CodeViolation oldCode) {
        playerName = oldCode.playerName;
        penalty = oldCode.penalty;
        reason = oldCode.reason;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPenalty() {
        return penaltyNames[penalty];
    }

    public String getReason() {
        return reason;
    }

    final private String[] penaltyNames = {"Warning", "Point Penalty", "Game Penalty", "Default"};
}
