package com.frenetz;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TicTacToe tests")
public class AppTest {

    private static Game game;

    @BeforeAll
    private static void initializeGame() {
        game = new Game();
    }

    @Test
    @DisplayName("Initial Board State")
    public void checkInitialBoardState() {
        for (char cell : game.board) {
            assertEquals(cell, ' ');
        }
    }

    @Test
    @DisplayName("Move and State Update Test")
    public void checkMoveAndStateUpdate() {
        game.board[0] = 'X';
        game.state = game.checkState(game.board);
        assertEquals(game.state, State.PLAYING);

        game.board[1] = 'X';
        game.board[2] = 'X';
        game.state = game.checkState(game.board);
        assertEquals(game.state, State.XWIN);
    }

    @Test
    @DisplayName("OWin State Test")
    public void checkOWinState() {
        game.board[0] = 'O';
        game.board[1] = 'O';
        game.board[2] = 'O';
        game.state = game.checkState(game.board);
        assertEquals(game.state, State.OWIN);
    }

    @Test
    @DisplayName("Draw State Test")
    public void checkDrawState() {
        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        game.state = game.checkState(game.board);
        assertEquals(game.state, State.DRAW);
    }

    @Test
    @DisplayName("Playing State Test")
    public void checkPlayingState() {
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = 'O';
        game.board[4] = ' ';
        game.board[5] = 'X';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = 'O';
        game.state = game.checkState(game.board);
        assertEquals(game.state, State.PLAYING);
    }

    @Test
    @DisplayName("Generate Moves Test")
    public void checkGenerateMoves() {
        ArrayList<Integer> moveList = new ArrayList<>();
        game.board[0] = 'X';
        game.board[1] = 'O';
        game.board[2] = 'X';
        game.board[3] = ' ';
        game.board[4] = 'O';
        game.board[5] = ' ';
        game.board[6] = 'O';
        game.board[7] = 'X';
        game.board[8] = ' ';
        game.generateMoves(game.board, moveList);
        assertEquals(moveList.size(), 3);
        assertTrue(moveList.contains(3));
        assertTrue(moveList.contains(5));
        assertTrue(moveList.contains(8));
    }

    @Test
    @DisplayName("Minimax empty board")
    public void checkMinimaxEmptyBoard() {
        char[] board = new char[9];
        int expectedMove = 0;

        int actualMove = game.MiniMax(board, game.player1);

        assertEquals(expectedMove, actualMove);
    }

    @Test
    @DisplayName("Evaluate Position Test")
    public void checkEvaluatePosition() {
        game.board = new char[]{'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        assertEquals(game.evaluatePosition(game.board, game.player1), Game.INF);
        assertEquals(game.evaluatePosition(game.board, game.player2), -Game.INF);

        game.board = new char[]{'O', 'O', 'O', 'X', 'X', ' ', ' ', ' ', ' '};
        assertEquals(game.evaluatePosition(game.board, game.player1), -Game.INF);
        assertEquals(game.evaluatePosition(game.board, game.player2), Game.INF);

        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(game.evaluatePosition(game.board, game.player1), 0);
        assertEquals(game.evaluatePosition(game.board, game.player2), 0);

        game.board = new char[]{'X', 'O', ' ', ' ', 'X', 'O', ' ', ' ', ' '};
        assertEquals(game.evaluatePosition(game.board, game.player1), -1);
        assertEquals(game.evaluatePosition(game.board, game.player2), -1);
    }

    @Test
    @DisplayName("Generate Moves on Empty and Full Board")
    public void checkGenerateMovesOnEmptyAndFullBoard() {
        game.board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moveList = new ArrayList<>();
        game.generateMoves(game.board, moveList);
        assertEquals(moveList.size(), 9);

        game.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        moveList.clear();
        game.generateMoves(game.board, moveList);
        assertEquals(moveList.size(), 0);
    }

    @Test
    @DisplayName("Panel init")
    public void checkPanelInit() {
        assertDoesNotThrow(() -> {
            new TicTacToePanel(new GridLayout(3, 3));
        });
    }

}