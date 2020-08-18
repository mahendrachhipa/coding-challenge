package com.genesys.challenge.connectgame.model;

/*
 * this class hold the information needed to play the turn
 */
public class PlayData {
    private String gameId;
    private String playerName;
    private int column;

    public String getGameId() {
        return gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getColumn() {
        return column;
    }
}
