package com.genesys.challenge.connectgameclient.player;

import com.genesys.challenge.connectgameclient.model.GameBoard;
import com.genesys.challenge.connectgameclient.model.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/*
 * This class is responsible to initiate the Game
 */
@Service
public class ConnectGamePlayer extends ResourceInfo {
    private GameBoard gameBoard;
    private boolean isPlayerA;
    @Autowired
    PlayerA playerA;
    @Autowired
    PlayerB playerB;

    /*
     * This method send the request to server to create the game board for the player.
     * If other player is not available wait for another player to join.
     * Depend upon the playerA or PlayerB invoke the playGame method of
     * playerA and PlayerB..
     */
    public void startGame(String playerName) {
        Map <String,String> params = new HashMap<>();
        params.put("playerName",playerName);
        Map <String,String> gameInfoParams = new HashMap<>();
        gameBoard = restTemplate.getForObject(destAddress+startUri,GameBoard.class,params);
        if(gameBoard.getPlayerA().equals(playerName)) {
            isPlayerA = true;
        }
        String gameId = gameBoard.getGameId();
        while (gameBoard == null ||
                gameBoard.getPlayerB() == null) {
            System.out.println("Waiting for another player to join");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameInfoParams.put("gameId",gameId);
            gameBoard = restTemplate.getForObject(destAddress+gameInfoUri,GameBoard.class,gameInfoParams);
        }
        if(!isPlayerA) {
            playerB.playGame(gameBoard);
        } else {
            playerA.playGame(gameBoard);
        }
    }
}
