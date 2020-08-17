package com.genesys.challenge.connectgameclient.model;

public class PlayData {
    private String gameId;
    private String playerName;
    private int column;

    public PlayData(String gameId,String playerName,int column) {
        this.gameId = gameId;
        this.playerName = playerName;
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
