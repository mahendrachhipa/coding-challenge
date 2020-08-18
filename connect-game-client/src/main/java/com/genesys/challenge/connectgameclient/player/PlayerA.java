package com.genesys.challenge.connectgameclient.player;

import com.genesys.challenge.connectgameclient.model.GameBoard;
import com.genesys.challenge.connectgameclient.model.ResourceInfo;
import org.springframework.stereotype.Component;

@Component
public class PlayerA extends ResourceInfo {
    public void playGame(GameBoard gameBoard) {
        playGame(gameBoard,gameBoard.getPlayerA(),gameBoard.getPlayerB());
    }
}
