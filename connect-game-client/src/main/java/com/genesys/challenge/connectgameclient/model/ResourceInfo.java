package com.genesys.challenge.connectgameclient.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class ResourceInfo {
    @Value("${destination.address}")
    protected String destAddress;
    protected String startUri = "/game/{playerName}";
    protected String playUri = "/game";
    protected String gameInfoUri = "/gameinfo/{gameId}";
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
        return restTemplate.postForObject(destAddress+playUri,playData,GameBoard.class);
    }

    public void playGame(GameBoard gameBoard,String firstPlayer, String secondPlayer) {
        int count =0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Another player " + secondPlayer+" have joined you");
        while (gameBoard.getGameStatus().contains(" turn")) {
            if(gameBoard.getGameStatus().equals(firstPlayer+" turn")) {
                displayBoard(gameBoard);
                System.out.print(String.format("It's your turn %s, please enter column (1-9) : ",firstPlayer));
                int column = sc.nextInt();
                while(!validateColumn(column,gameBoard)) {
                    System.out.println("Please Enter valid column:");
                    column = sc.nextInt();
                }
                PlayData playData = new PlayData(gameBoard.getGameId(),firstPlayer,column);
                gameBoard = playTurn(playData);
                displayBoard(gameBoard);
            } else {
                if(count%4 == 0) {
                    System.out.println("Wait for "+secondPlayer+ " to play his/her turn");
                }
                try {
                    Thread.sleep(5000);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String,String> gameInfoParams = new HashMap<>();
                gameInfoParams.put("gameId",gameBoard.getGameId());
                gameBoard = restTemplate.getForObject(destAddress+gameInfoUri,GameBoard.class,gameInfoParams);
            }
        }
        Map<String,String> gameInfoParams = new HashMap<>();
        gameInfoParams.put("gameId",gameBoard.getGameId());
        gameBoard = restTemplate.getForObject(destAddress+gameInfoUri,GameBoard.class,gameInfoParams);
        displayBoard(gameBoard);
        if(gameBoard.getGameStatus().equals(firstPlayer+" Won!")) {
            System.out.println("Congratulations " + firstPlayer + " You Won!");
        } else {
            System.out.println("Congratulations " + secondPlayer + " You Won!");
        }
    }
}
