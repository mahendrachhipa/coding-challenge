package com.genesys.challenge.connectgameclient.model;

import com.genesys.challenge.connectgameclient.player.ShutdownManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * This class is responsible for sending requests to server, displaying board
 * at terminal,and validating user inputs.
 */
public class ResourceInfo {
    @Value("${destination.address}")
    protected String destAddress;
    protected String startUri = "/game/{playerName}";
    protected String playUri = "/game";
    protected String gameInfoUri = "/gameinfo/{gameId}";
    protected RestTemplate restTemplate = new RestTemplate();
    @Autowired
    protected ShutdownManager shutdownManager;

    /*
     * This method display the board in command line
     */
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

    /*
     * This method validate that entered column is not out of range and
     * space is available in column.
     */
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

    /*
     * This method sends the post request to server with information about
     * the player choice of column.
     */
    public GameBoard playTurn(PlayData playData) {
        return restTemplate.postForObject(destAddress+playUri,playData,GameBoard.class);
    }

    /*
     * This method is executed until the game is over. Following steps are done:
     * - Informed the first player that second player have joined.
     * - start a loop until game status changed to won
     * - display the board and ask the first player to enter the column if it is first
     * - player turn otherwise ask first player to wait for his/her turn
     * - take the input from first player and validate the input.
     * - refresh and display the board and send the request to server
     * - if server response have game status changed to won, then display
     * - winning message to both the players. otherwise continue with the loop.
     * - Once game is over, call shutdown manager to exit the application.
     */
    public void playGame(GameBoard gameBoard,String firstPlayer, String secondPlayer) {
        int count =0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Another player " + secondPlayer+" have joined you");
        while (gameBoard.getGameStatus().contains(" turn")) {
            if(gameBoard.getGameStatus().equals(firstPlayer+" turn")) {
                displayBoard(gameBoard);
                System.out.print(String.format("It's your turn %s, please enter column (1-9) : ",firstPlayer));
                while(!sc.hasNextInt()) {
                    System.out.println("Please enter numeric value.");
                    sc.nextLine();
                }
                int column = sc.nextInt();
                while(!validateColumn(column,gameBoard)) {
                    System.out.println("Please Enter valid column:");
                    while(!sc.hasNextInt()) {
                        System.out.println("Please enter numeric value.");
                        sc.nextLine();
                    }
                    column = sc.nextInt();
                }
                PlayData playData = new PlayData(gameBoard.getGameId(),firstPlayer,column);
                gameBoard = playTurn(playData);
                displayBoard(gameBoard);
                count=0;
            } else {
                if(count%4 == 0) {
                    System.out.println("Wait for "+secondPlayer+ " to play his/her turn");
                }
                try {
                    Thread.sleep(3000);
                    count++;
                    if(count >= 20 ) {
                        System.out.println("Looks like another player is disconnected," +
                                " please try another game. Bye");
                        shutdownManager.initiateShutdown(0);
                        return;
                    }
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
        shutdownManager.initiateShutdown(0);
    }
}
