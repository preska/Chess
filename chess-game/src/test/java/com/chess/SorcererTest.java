package com.chess;

import com.chess.model.logic.Board;
import com.chess.model.logic.Move;
import com.chess.model.logic.Player;
import com.chess.model.pieces.*;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

public class SorcererTest {
    int rowSize = 8;
    int colSize = 8;
    Board board = new Board(rowSize, colSize);

    @Test
    public void testMoveValidity() {
        clearBoard();
        List<Piece> capturedPieces = new ArrayList<Piece>();

        Player blackPlayer = new Player("Black Player", 0, capturedPieces);
        blackPlayer.setName("Black Player 1");
        blackPlayer.setColor(0);
        assertEquals(blackPlayer.getName(), "Black Player 1");
        Player currPlayer = blackPlayer;

        Point2D srcCoord = new Point2D.Double(7, 3);
        List<Point2D> possibleMoves = new ArrayList<Point2D>();
        possibleMoves = Move.generatePossibleMovesSorcerer(currPlayer, possibleMoves, srcCoord, board.getBoard());

        Sorcerer a = new Sorcerer(srcCoord, 0);
        List<Point2D> testMoves = new ArrayList<Point2D>();
        testMoves =  Move.generatePossibleMoves(a, currPlayer, possibleMoves, srcCoord, board.getBoard());

        assertEquals(possibleMoves, testMoves);

        List<Point2D> correctMoves = new ArrayList<Point2D>();
        correctMoves.add(new Point2D.Double(6.0, 2.0));
        correctMoves.add(new Point2D.Double(6.0, 3.0));
        correctMoves.add(new Point2D.Double(6.0, 4.0));
        correctMoves.add(new Point2D.Double(7.0, 2.0));
        correctMoves.add(new Point2D.Double(7.0, 4.0));

        assertEquals(correctMoves, possibleMoves);
    }

    @Test
    public void testCapture() {
        List<Piece> capturedPieces = new ArrayList<Piece>();

        Player blackPlayer = new Player("Black Player", 0, capturedPieces);
        Player whitePlayer = new Player("White Player", 1, capturedPieces);

        Player currPlayer = blackPlayer;
        clearBoard();

        Point2D srcCoord = new Point2D.Double(5, 6);
        Point2D destCoord = new Point2D.Double(5, 5);

        Pawn p = new Pawn(new Point2D.Double(5, 5), 1);
        Sorcerer k = new Sorcerer(new Point2D.Double(5, 6), 0);
        k.setCurrCoord(5, 6);
        board.getBoard()[5][5] = p;
        board.getBoard()[5][6] = k;

        board.captureSorcerer(currPlayer, blackPlayer, whitePlayer, destCoord, srcCoord);

        assertEquals(k.getName(), "Sorcerer");
        assertEquals(blackPlayer.getCapturedPieces().size(), 0);
        assertEquals(k.getColor(), 0);
        assertEquals(k.getCurrCoord(), null);
    }

    private void clearBoard() {
        for(int i = 0; i < rowSize; i++) {
            for(int j = 0; j < colSize; j++) {
                board.board[i][j] = null;
            }
        }
    }
}
