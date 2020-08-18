package com.genesys.challenge.connectgameclient.player;

import com.genesys.challenge.connectgameclient.model.GameBoard;
import com.genesys.challenge.connectgameclient.model.ResourceInfo;
import org.springframework.stereotype.Component;

/*
 * This class represent the PlayerB
 */
@Component
public class PlayerB extends ResourceInfo {
    public void playGame(GameBoard gameBoard) {
        playGame(gameBoard,gameBoard.getPlayerB(),gameBoard.getPlayerA());
    }
}
