package com.genesys.challenge.connectgame.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private String playerA;
    private String playerB;
    private String gameId;
    private static int count;
    private String gameStatus;
    private static final int MAXROW = 6;
    private static final int MAXCOLUMN = 9;
    private List <List<Integer>> board = new ArrayList<>(MAXCOLUMN);


    public List<List<Integer>> getBoard() {
        return board;
    }

    public GameBoard(String player) {
        count++;
        playerA = player;
        gameId = playerA + count;
        gameStatus = "Waiting for another player";
        initializeBoard();
    }

    void setPlayerB(String playerB) {
        this.playerB = playerB;
        gameStatus = String.format("%s turn",playerA);
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

    public void play(String playerName, int column) {
        if(!playerA.equals(playerName) && !playerB.equals(playerName) || column <0 || column > 8) {
            throw new InvalidParameterException();
        }
        List<Integer> elements = board.get(column);
        int index = elements.indexOf(0);
        elements.set(index,playerA.equals(playerName)?1:2);
        determineStatus(playerName,column);
    }

    private void determineStatus(String playerName, int column) {
        int elementTobeChecked = playerA.equals(playerName)?  1 : 2;
        if (checkDiagonal(elementTobeChecked,column) || checkHorizontal(elementTobeChecked,column)
        || checkVertical(elementTobeChecked,column)) {
            gameStatus = playerName + " Won!";
        } else {
            gameStatus = String.format("%s turn",playerA.equals(playerName)? playerB : playerA );
        }
    }
    private boolean checkVertical(int element, int column) {
        List<Integer> verElements = board.get(column);
        int index = verElements.lastIndexOf(element);
        int count =0;
        for (int i = index ; i >= 0; i--){
            if (verElements.get(i) != element) {
                return false;
            }
            count++;
            if(count == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean checkHorizontal(int element, int column) {
        List<Integer> elements = board.get(column);
        int index = elements.lastIndexOf(element);
        //Left travel for column
        int left = column - 1;
        int count=1;
        while(left>= 0) {
            if(board.get(left).get(index) == element) {
                count++;
                left--;
            } else {
                break;
            }
            if(count == 5){
                return true;
            }
        }
        // Right travel for column
        int right = column + 1;
        while(right < MAXCOLUMN) {
            if(board.get(right).get(index) == element) {
                count++;
                right++;
            } else {
                break;
            }
            if(count == 5){
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonal(int element, int column) {
        return checkLeftToRightDiagonal(element,column) ||
                checkRightToLeftDiagonal(element,column);
    }
    private boolean checkLeftToRightDiagonal(int element, int column){
        List<Integer> elements = board.get(column);
        int index = elements.lastIndexOf(element);

        //Check left to right diagonal
        int count = 1;
        int rowIndex = index + 1;
        int columnIndex = column -1;
        while(rowIndex < MAXROW && columnIndex >= 0) {
            if(board.get(columnIndex).get(rowIndex) != element) {
                break;
            }
            count++;
            rowIndex++;
            columnIndex--;
            if(count == 5) {
                return true;
            }
        }

        rowIndex = index - 1;
        columnIndex = column + 1;
        while(rowIndex >= 0 && columnIndex < MAXCOLUMN) {
            if(board.get(columnIndex).get(rowIndex) != element) {
                break;
            }
            count++;
            rowIndex--;
            columnIndex++;
            if(count == 5) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRightToLeftDiagonal(int element, int column){
        List<Integer> elements = board.get(column);
        int index = elements.lastIndexOf(element);

        //Check right to left diagonal
        int count = 1;
        int rowIndex = index + 1;
        int columnIndex = column + 1;
        while(rowIndex < MAXROW && columnIndex < MAXCOLUMN) {
            if(board.get(columnIndex).get(rowIndex) != element) {
                break;
            }
            count++;
            rowIndex++;
            columnIndex++;
            if(count == 5) {
                return true;
            }
        }

        rowIndex = index - 1;
        columnIndex = column - 1;
        while(rowIndex >= 0 && columnIndex >= 0) {
            if(board.get(columnIndex).get(rowIndex) != element) {
                break;
            }
            count++;
            rowIndex--;
            columnIndex--;
            if(count == 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "playerA='" + playerA + '\'' +
                ", playerB='" + playerB + '\'' +
                ", gameId='" + gameId + '\'' +
                ", gameStatus='" + gameStatus + '\'' +
                ", board=" + board +
                '}';
    }
    private void initializeBoard() {
        for(int column = 0; column < MAXCOLUMN; column++) {
            List <Integer> rows = new ArrayList<>(MAXROW);
            for(int row =0;row<MAXROW;row++) {
                rows.add(0);
            }
            board.add(rows);
        }
    }
}
