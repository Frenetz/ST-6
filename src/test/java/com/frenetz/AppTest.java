package com.frenetz;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TicTacToe Tests")
public class GameTest {

    private static Game ticTacToeGame;

    @BeforeAll
    private static void initGame() {
        ticTacToeGame = new Game();
    }

    @Test
    @DisplayName("Initial Board Check")
    public void verifyInitialBoardState() {
        for (char cell : ticTacToeGame.board) {
            assertEquals(' ', cell);
        }
    }

    @Test
    @DisplayName("State Update After Move")
    public void verifyStateUpdateAfterMove() {
        ticTacToeGame.board[0] = 'X';
        ticTacToeGame.state = ticTacToeGame.checkState(ticTacToeGame.board);
        assertEquals(State.PLAYING, ticTacToeGame.state);

        ticTacToeGame.board[1] = 'X';
        ticTacToeGame.board[2] = 'X';
        ticTacToeGame.state = ticTacToeGame.checkState(ticTacToeGame.board);
        assertEquals(State.XWIN, ticTacToeGame.state);
    }

    @Test
    @DisplayName("Test O Win State")
    public void verifyOWinState() {
        ticTacToeGame.board[0] = 'O';
        ticTacToeGame.board[1] = 'O';
        ticTacToeGame.board[2] = 'O';
        ticTacToeGame.state = ticTacToeGame.checkState(ticTacToeGame.board);
        assertEquals(State.OWIN, ticTacToeGame.state);
    }

    @Test
    @DisplayName("Test Draw State")
    public void verifyDrawState() {
        ticTacToeGame.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        ticTacToeGame.state = ticTacToeGame.checkState(ticTacToeGame.board);
        assertEquals(State.DRAW, ticTacToeGame.state);
    }

    @Test
    @DisplayName("Test Playing State")
    public void verifyPlayingState() {
        ticTacToeGame.board[0] = 'X';
        ticTacToeGame.board[1] = 'O';
        ticTacToeGame.board[2] = 'X';
        ticTacToeGame.board[3] = 'O';
        ticTacToeGame.board[4] = ' ';
        ticTacToeGame.board[5] = 'X';
        ticTacToeGame.board[6] = 'O';
        ticTacToeGame.board[7] = 'X';
        ticTacToeGame.board[8] = 'O';
        ticTacToeGame.state = ticTacToeGame.checkState(ticTacToeGame.board);
        assertEquals(State.PLAYING, ticTacToeGame.state);
    }

    @Test
    @DisplayName("Test Move Generation")
    public void verifyMoveGeneration() {
        ArrayList<Integer> moveList = new ArrayList<>();
        ticTacToeGame.board[0] = 'X';
        ticTacToeGame.board[1] = 'O';
        ticTacToeGame.board[2] = 'X';
        ticTacToeGame.board[3] = ' ';
        ticTacToeGame.board[4] = 'O';
        ticTacToeGame.board[5] = ' ';
        ticTacToeGame.board[6] = 'O';
        ticTacToeGame.board[7] = 'X';
        ticTacToeGame.board[8] = ' ';
        ticTacToeGame.generateMoves(ticTacToeGame.board, moveList);
        assertEquals(3, moveList.size());
        assertTrue(moveList.contains(3));
        assertTrue(moveList.contains(5));
        assertTrue(moveList.contains(8));
    }

    @Test
    @DisplayName("Minimax Test on Empty Board")
    public void verifyMinimaxOnEmptyBoard() {
        char[] board = new char[9];
        int expectedMove = 0;

        int actualMove = ticTacToeGame.MiniMax(board, ticTacToeGame.player1);

        assertEquals(expectedMove, actualMove);
    }

    @Test
    @DisplayName("Test Position Evaluation")
    public void verifyPositionEvaluation() {
        ticTacToeGame.board = new char[]{'X', 'X', 'X', 'O', 'O', ' ', ' ', ' ', ' '};
        assertEquals(Game.INF, ticTacToeGame.evaluatePosition(ticTacToeGame.board, ticTacToeGame.player1));
        assertEquals(-Game.INF, ticTacToeGame.evaluatePosition(ticTacToeGame.board, ticTacToeGame.player2));

        ticTacToeGame.board = new char[]{'O', 'O', 'O', 'X', 'X', ' ', ' ', ' ', ' '};
        assertEquals(-Game.INF, ticTacToeGame.evaluatePosition(ticTacToeGame.board, ticTacToeGame.player1));
        assertEquals(Game.INF, ticTacToeGame.evaluatePosition(ticTacToeGame.board, ticTacToeGame.player2));

        ticTacToeGame.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        assertEquals(0, ticTacToeGame.evaluatePosition(ticTacToeGame.board, ticTacToeGame.player1));
        assertEquals(0, ticTacToeGame.evaluatePosition(ticTacToeGame.board, ticTacToeGame.player2));

        ticTacToeGame.board = new char[]{'X', 'O', ' ', ' ', 'X', 'O', ' ', ' ', ' '};
        assertEquals(-1, ticTacToeGame.evaluatePosition(ticTacToeGame.board, ticTacToeGame.player1));
        assertEquals(-1, ticTacToeGame.evaluatePosition(ticTacToeGame.board, ticTacToeGame.player2));
    }

    @Test
    @DisplayName("Move Generation on Different Boards")
    public void verifyMoveGenerationOnDifferentBoards() {
        ticTacToeGame.board = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        ArrayList<Integer> moveList = new ArrayList<>();
        ticTacToeGame.generateMoves(ticTacToeGame.board, moveList);
        assertEquals(9, moveList.size());

        ticTacToeGame.board = new char[]{'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        moveList.clear();
        ticTacToeGame.generateMoves(ticTacToeGame.board, moveList);
        assertEquals(0, moveList.size());
    }

    @Test
    @DisplayName("Panel Initialization")
    public void verifyPanelInitialization() {
        assertDoesNotThrow(() -> new TicTacToePanel(new GridLayout(3, 3)));
    }

    @Test
    @DisplayName("Mid Game Move Generation")
    public void checkMidGameMoveGeneration() {
        ArrayList<Integer> moveList = new ArrayList<>();
        ticTacToeGame.board = new char[]{'X', 'O', 'X', ' ', 'O', ' ', 'O', 'X', ' '};
        ticTacToeGame.generateMoves(ticTacToeGame.board, moveList);
        assertEquals(3, moveList.size());
        assertTrue(moveList.contains(3));
        assertTrue(moveList.contains(5));
        assertTrue(moveList.contains(8));
    }

    @Test
    @DisplayName("Invalid Move Test")
    public void checkInvalidMove() {
        ticTacToeGame.board[0] = 'X';
        assertThrows(IllegalArgumentException.class, () -> {
            ticTacToeGame.board[0] = 'O';
        });
    }

    @Test
    @DisplayName("Game Reset Test")
    public void checkGameReset() {
        ticTacToeGame.board[0] = 'X';
        ticTacToeGame.state = State.XWIN;
        ticTacToeGame.board = new char[9];
        ticTacToeGame.state = State.PLAYING;
        for (char cell : ticTacToeGame.board) {
            assertEquals(' ', cell);
        }
        assertEquals(State.PLAYING, ticTacToeGame.state);
    }

    @Test
    @DisplayName("Minimax Optimal Move Test")
    public void checkMinimaxOptimalMove() {
        ticTacToeGame.board = new char[]{'X', 'O', 'X', 'O', 'O', 'X', 'X', ' ', ' '};
        int bestMove = ticTacToeGame.MiniMax(ticTacToeGame.board, ticTacToeGame.player2);
        assertEquals(7, bestMove);
    }

    @Test
    @DisplayName("MinMove Function Test")
    public void checkMinMoveFunction() {
        ticTacToeGame.board = new char[]{'X', 'O', 'X', ' ', 'O', ' ', ' ', ' ', ' '};
        int bestMove = ticTacToeGame.MinMove(ticTacToeGame.board);
        assertTrue(bestMove >= 0 && bestMove < 9);
    }

    @Test
    @DisplayName("MaxMove Function Test")
    public void checkMaxMoveFunction() {
        ticTacToeGame.board = new char[]{'X', 'O', 'X', ' ', 'O', ' ', ' ', ' ', ' '};
        int bestMove = ticTacToeGame.MaxMove(ticTacToeGame.board);
        assertTrue(bestMove >= 0 && bestMove < 9);
    }
}
