package com.genesys.challenge.connectgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConnectGameTest {
    private ConnectGame connectGameUnderTest;

    @BeforeEach
    void setup(){
        connectGameUnderTest = new ConnectGame();
    }

    @Test
    void when_startGame_for_FirstPlayer_should_return_gameboard_withgameid_player(){
        GameBoard gameBoard =connectGameUnderTest.startGame("FirstPlayer");
        assert(gameBoard.getPlayerA().equals("FirstPlayer"));
        assert(gameBoard.getGameId().contains("FirstPlayer"));
        assert(gameBoard.getPlayerB() == null);
        assert (gameBoard.getGameStatus().equals("Waiting for another player"));
    }

    @Test
    void when_startGame_for_SecondPlayer_should_return_gameboard_withgameid_player(){
        connectGameUnderTest.startGame("FirstPlayer1");
        GameBoard gameBoard =connectGameUnderTest.startGame("SecondPlayer");
        assert(gameBoard.getPlayerA().equals("FirstPlayer1"));
        assert(gameBoard.getGameId().contains("FirstPlayer1"));
        assert(gameBoard.getPlayerB().equals("SecondPlayer"));
        assert (gameBoard.getGameStatus().equals("FirstPlayer1 turn"));
    }

    @Test
    void when_startGame_for_two_players_with_same_name_should_not_play_together(){
        GameBoard gameBoard1 = connectGameUnderTest.startGame("Player");
        GameBoard gameBoard2 =connectGameUnderTest.startGame("Player");
        assert(gameBoard1.getPlayerA().equals("Player"));
        assert(!gameBoard1.getGameId().equals(gameBoard2.getGameId()));
        assert(gameBoard2.getPlayerA().equals("Player"));
        assert (gameBoard1.getGameStatus().equals("Waiting for another player"));
        assert (gameBoard2.getGameStatus().equals("Waiting for another player"));
        assert (gameBoard1.getPlayerB()==null);
        assert(gameBoard2.getPlayerB()==null);
    }
}
