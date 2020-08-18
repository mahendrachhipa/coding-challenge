package com.genesys.challenge.connectgame.service;

import com.genesys.challenge.connectgame.model.GameBoard;
import com.genesys.challenge.connectgame.model.PlayData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectGameServiceTest {
    private ConnectGameService objectUnderTest;

    @BeforeEach
    void setup() {
        objectUnderTest = new ConnectGameService();
    }

    @Test
    void when_create_get_play_game_called_returns_gameboard() {
        GameBoard gameBoard = objectUnderTest.createGame("firstPlayer");
        assert(gameBoard.getPlayerA().equals("firstPlayer"));
        assert(gameBoard.getPlayerB() == null);
        assert(gameBoard.getGameId().contains("firstPlayer"));

        gameBoard = objectUnderTest.createGame("secondPlayer");

        assert(gameBoard.getPlayerA().equals("firstPlayer"));
        assert(gameBoard.getPlayerB().equals("secondPlayer"));
        assert(gameBoard.getGameId().contains("firstPlayer"));
        assert(gameBoard.getGameStatus().equals("firstPlayer turn"));
        String id = gameBoard.getGameId();

        GameBoard gameBoard1 = objectUnderTest.getGame(id);
        assert(gameBoard1.getPlayerA().equals("firstPlayer"));
        assert (gameBoard1.getPlayerB().equals("secondPlayer"));

        PlayData playData = new PlayData();
        playData.setColumn(6);
        playData.setPlayerName("firstPlayer");
        playData.setGameId(id);

        gameBoard = objectUnderTest.play(playData);
        assert(gameBoard.getPlayerA().equals("firstPlayer"));
        assert(gameBoard.getPlayerB().equals("secondPlayer"));
        assert(gameBoard.getGameId().equals(id));
        assert(gameBoard.getGameStatus().equals("secondPlayer turn"));
    }
}
