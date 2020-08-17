package com.genesys.challenge.connectgame.model;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class ConnectGame {
    private Queue<GameBoard> waitingGames = new ArrayDeque<>();
    private Map<String,GameBoard> activeGames = new HashMap<>();

    public GameBoard startGame(String playerName) {
        GameBoard gameBoard;
        if(!waitingGames.isEmpty()){
            gameBoard = waitingGames.poll();
            gameBoard.setPlayerB(playerName);
            activeGames.put(gameBoard.getGameId(),gameBoard);
        } else {
            gameBoard = new GameBoard(playerName);
            waitingGames.add(gameBoard);
        }
        return gameBoard;
    }

    public GameBoard getGame(String gameId) {
        return activeGames.get(gameId);
    }

}
