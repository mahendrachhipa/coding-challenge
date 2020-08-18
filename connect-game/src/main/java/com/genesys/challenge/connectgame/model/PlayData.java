package com.genesys.challenge.connectgame.model;

/*
 * this class hold the information needed to play the turn
 */
public class PlayData {
    private String gameId;
    private String playerName;
    private int column;

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setColumn(int column) {
        this.column = column;
    }

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
