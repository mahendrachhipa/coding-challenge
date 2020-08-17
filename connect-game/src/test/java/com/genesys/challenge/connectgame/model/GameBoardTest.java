package com.genesys.challenge.connectgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameBoardTest {
    private static final int MAXCOLUMN = 9;
    private static final int MAXROW = 6;
    GameBoard boardUnderTest;

    @BeforeEach
    void setup(){
        boardUnderTest = new GameBoard("A");
        boardUnderTest.setPlayerB("B");
    }

    @Test
    void when_play_is_called_first_time_gameStatus_setTo_turnB(){
        boardUnderTest.play("A",5);
        assert(boardUnderTest.getGameStatus().equals("B turn"));
    }
    @Test
    void when_play_is_called_second_time_gameStatus_setTo_turnA(){
        boardUnderTest.play("A",5);
        boardUnderTest.play("B",5);
        assert(boardUnderTest.getGameStatus().equals("A turn"));
    }

    @Test
    void when_horizontal_five_occupied_by_A_gameStatus_setTo_AWon(){
        boardUnderTest.play("A",5);
        boardUnderTest.play("B",1);
        boardUnderTest.play("A",4);
        boardUnderTest.play("B",2);
        boardUnderTest.play("A",3);
        boardUnderTest.play("B",8);
        boardUnderTest.play("A",6);
        boardUnderTest.play("B",0);
        boardUnderTest.play("A",7);
        assert(boardUnderTest.getGameStatus().equals("A Won!"));
    }

    @Test
    void when_vertical_five_occupied_by_B_gameStatus_setTo_BWon(){
        boardUnderTest.play("A",5);
        boardUnderTest.play("B",1);
        boardUnderTest.play("A",4);
        boardUnderTest.play("B",1);
        boardUnderTest.play("A",3);
        boardUnderTest.play("B",1);
        boardUnderTest.play("A",6);
        boardUnderTest.play("B",1);
        boardUnderTest.play("A",8);
        boardUnderTest.play("B",1);
        assert(boardUnderTest.getGameStatus().equals("B Won!"));
    }

    @Test
    void when_diagonal_LR_five_occupied_by_A_gameStatus_setTo_AWon(){
        List<List<Integer>> board = new ArrayList<>();
        initializeBoard(board);
        //First column
        board.get(0).set(0,2);
        board.get(0).set(1,2);
        board.get(0).set(2,2);
        board.get(0).set(3,2);
        board.get(0).set(4,1);
        //second column
        board.get(1).set(0,2);
        board.get(1).set(1,2);
        board.get(1).set(2,2);
        board.get(1).set(3,1);
        //Third column
        board.get(2).set(0,2);
        board.get(2).set(1,2);
        board.get(2).set(2,1);
        //Fourth column
        board.get(3).set(0,2);
        board.get(3).set(1,1);

        boardUnderTest.setBoard(board);

        boardUnderTest.play("A",4);
        assert(boardUnderTest.getGameStatus().equals("A Won!"));
    }

    @Test
    void when_diagonal_RL_five_occupied_by_B_gameStatus_setTo_BWon(){
        List<List<Integer>> board = new ArrayList<>();
        initializeBoard(board);
        //Ninth column
        board.get(8).set(0,1);
        board.get(8).set(1,1);
        board.get(8).set(2,1);
        board.get(8).set(3,1);
        board.get(8).set(4,2);
        //Eighth column
        board.get(7).set(0,1);
        board.get(7).set(1,1);
        board.get(7).set(2,1);
        board.get(7).set(3,2);
        //Seventh column
        board.get(6).set(0,1);
        board.get(6).set(1,1);
        board.get(6).set(2,2);
        //Sixth column
        board.get(5).set(0,1);
        board.get(5).set(1,2);

        boardUnderTest.setBoard(board);

        boardUnderTest.play("B",4);
        assert(boardUnderTest.getGameStatus().equals("B Won!"));
    }

    private void initializeBoard(List<List<Integer>> board) {
        for(int column = 0; column < MAXCOLUMN; column++) {
            List <Integer> rows = new ArrayList<>(MAXROW);
            for(int row =0;row<MAXROW;row++) {
                rows.add(0);
            }
            board.add(rows);
        }
    }
}
