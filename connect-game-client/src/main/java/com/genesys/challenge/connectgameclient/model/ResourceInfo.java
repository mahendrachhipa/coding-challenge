package com.genesys.challenge.connectgameclient.model;

import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ResourceInfo {
    protected String startUri = "http://localhost:8080/game/{playerName}";
    protected String playUri = "http://localhost:8080/game";
    protected String gameInfoUri = "http://localhost:8080/gameinfo/{gameId}";
    protected RestTemplate restTemplate = new RestTemplate();

    public void displayBoard(GameBoard gameBoard) {
        List<List<Integer>> board = gameBoard.getBoard ();
        int [][] boardArray = new int[9][6];
        for(int column = 0; column < 9;column++) {
            for(int row = 0; row < 6;row++) {
                boardArray[column][row] = board.get(column).get(row);
            }
        }
        StringBuilder sb = new StringBuilder(60);
        for(int column = 5; column >= 0; column-- ) {
            for(int row = 0; row < 9; row++) {
                if(boardArray[row][column] == 0) {
                    sb.append("[ ]");
                } else if(boardArray[row][column] == 1) {
                    sb.append("[o]");
                } else if(boardArray[row][column] == 2) {
                    sb.append("[x]");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public boolean validateColumn(int column,GameBoard gameBoard) {
        if(column < 1 || column > 9) {
            return false;
        }
        List<List<Integer>> board = gameBoard.getBoard();
        List<Integer> rows = board.get(column -1);
        if(rows.lastIndexOf(0) >= 0) {
            return true;
        }
        return false;
    }

    public GameBoard playTurn(PlayData playData) {
        return restTemplate.postForObject(playUri,playData,GameBoard.class);
    }
}
