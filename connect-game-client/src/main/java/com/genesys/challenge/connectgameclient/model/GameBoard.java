package com.genesys.challenge.connectgameclient.model;

import java.util.ArrayList;
import java.util.List;
/*
 * This class is responsible for holding the information about the game
 * between two players.Information include players name, game id, status
 * of the game, and board information.
 */
public class GameBoard {
    private String playerA;
    private String playerB;
    private String gameId;
    private String gameStatus;
    private static final int MAXROW = 6;
    private static final int MAXCOLUMN = 9;
    private List <List<Integer>> board = new ArrayList<>(MAXCOLUMN);


    public List<List<Integer>> getBoard() {
        return board;
    }

    public String getGameId() {
        return gameId;
    }

    public String getPlayerA() {
        return playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }
}
