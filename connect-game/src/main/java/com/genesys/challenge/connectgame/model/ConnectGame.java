package com.genesys.challenge.connectgame.model;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/*
 * This class is responsible for creating the Game Board and
 * storing and maintaining them
 */
public class ConnectGame {
    private Queue<GameBoard> waitingGames = new ArrayDeque<>();
    private Map<String,GameBoard> activeGames = new HashMap<>();

    /*
     * This method check if any Game board is in waiting queue, if
     * their then fetch the game from queue compare the player names,
     * if both are different, then store in the active games hashmap
     * other wise create a new game board and add this to waiting queue.
     * If waiting queue is already empty, then, create the game board assign
     * the player and add to the waiting queue.
     */
    public GameBoard startGame(String playerName) {
        GameBoard gameBoard;
        if(!waitingGames.isEmpty()){
            gameBoard = waitingGames.poll();
            if(!gameBoard.getPlayerA().equals(playerName)) {
                gameBoard.setPlayerB(playerName);
                activeGames.put(gameBoard.getGameId(),gameBoard);
                return gameBoard;
            } else {
                waitingGames.add(gameBoard);
            }
        }
        gameBoard = new GameBoard(playerName);
        waitingGames.add(gameBoard);
        return gameBoard;
    }

    /*
     * Returns the game board from the active games hashmap based on
     * game id.
     */
    public GameBoard getGame(String gameId) {
        return activeGames.get(gameId);
    }

}
