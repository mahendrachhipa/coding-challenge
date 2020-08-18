package com.genesys.challenge.connectgame.service;

import com.genesys.challenge.connectgame.model.ConnectGame;
import com.genesys.challenge.connectgame.model.GameBoard;
import com.genesys.challenge.connectgame.model.PlayData;
import org.springframework.stereotype.Service;

/*
 * This class is responsible for creating the game board, playing the turn
 * and fetching the game information.
 */
@Service
public class ConnectGameService {
    private ConnectGame connectGame = new ConnectGame();
    public GameBoard createGame(String playerName) {
        return connectGame.startGame(playerName);
    }

    public GameBoard getGame(String gameId) {
        return connectGame.getGame(gameId);
    }

    public GameBoard play(PlayData playData) {
        GameBoard gameBoard = connectGame.getGame(playData.getGameId());
        gameBoard.play(playData.getPlayerName(),playData.getColumn() - 1);
        return gameBoard;
    }


}
