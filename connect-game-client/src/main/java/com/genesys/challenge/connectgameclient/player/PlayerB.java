package com.genesys.challenge.connectgameclient.player;

import com.genesys.challenge.connectgameclient.model.GameBoard;
import com.genesys.challenge.connectgameclient.model.PlayData;
import com.genesys.challenge.connectgameclient.model.ResourceInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class PlayerB extends ResourceInfo {

    public void playGame(GameBoard gameBoard) {
        int count =0;
        Scanner sc = new Scanner(System.in);
        while (gameBoard.getGameStatus().contains(" turn")) {
            if(gameBoard.getGameStatus().equals(gameBoard.getPlayerB()+" turn")) {
                displayBoard(gameBoard);
                System.out.print(String.format("It's your turn %s, please enter column (1-9) : ",gameBoard.getPlayerB()));
                int column = sc.nextInt();
                while(!validateColumn(column,gameBoard)) {
                    System.out.println("Please Enter valid column:");
                    column = sc.nextInt();
                }
                PlayData playData = new PlayData(gameBoard.getGameId(),gameBoard.getPlayerB(),column);
                gameBoard = playTurn(playData);
                displayBoard(gameBoard);
            } else {
                if(count % 4 == 0) {
                    System.out.println("Wait for "+gameBoard.getPlayerA()+ " to play his/her turn");
                }
                try {
                    Thread.sleep(5000);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String,String> gameInfoParams = new HashMap<>();
                gameInfoParams.put("gameId",gameBoard.getGameId());
                gameBoard = restTemplate.getForObject(gameInfoUri,GameBoard.class,gameInfoParams);
            }
        }
        Map<String,String> gameInfoParams = new HashMap<>();
        gameInfoParams.put("gameId",gameBoard.getGameId());
        gameBoard = restTemplate.getForObject(gameInfoUri,GameBoard.class,gameInfoParams);
        displayBoard(gameBoard);
        if(gameBoard.getGameStatus().equals(gameBoard.getPlayerB()+" Won!")) {
            System.out.println("Congratulations " + gameBoard.getPlayerB() + " You Won!");
        } else {
            System.out.println("Congratulations " + gameBoard.getPlayerA() + " You Won!");
        }
    }
}
