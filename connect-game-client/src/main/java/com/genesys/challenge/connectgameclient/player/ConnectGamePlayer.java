package com.genesys.challenge.connectgameclient.player;

import com.genesys.challenge.connectgameclient.model.GameBoard;
import com.genesys.challenge.connectgameclient.model.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConnectGamePlayer extends ResourceInfo {
    private GameBoard gameBoard;
    private boolean isPlayerA;
    @Autowired
    PlayerA playerA;
    @Autowired
    PlayerB playerB;

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
