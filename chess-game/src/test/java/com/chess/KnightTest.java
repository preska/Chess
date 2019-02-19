package com.chess;

import com.chess.model.logic.Board;
import com.chess.model.logic.Move;
import com.chess.model.logic.Player;
import com.chess.model.pieces.Knight;
import com.chess.model.pieces.Pawn;
import com.chess.model.pieces.Piece;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class KnightTest {

    int rowSize = 8;
    int colSize = 8;
    Board board = new Board(rowSize, colSize);

    @Test
    public void testMoveValidity() {
        clearBoard();
        List<Piece> capturedPieces = new ArrayList<Piece>();

        Player blackPlayer = new Player("Black Player", 0, capturedPieces);
        Player currPlayer = blackPlayer;

        Point2D srcCoord = new Point2D.Double(7, 3);
        List<Point2D> possibleMoves = new ArrayList<Point2D>();
        possibleMoves = Move.generatePossibleMovesKnight(currPlayer, possibleMoves, srcCoord, board.getBoard());

        List<Point2D> correctMoves = new ArrayList<Point2D>();
        correctMoves.add(new Point2D.Double(5.0, 2.0));
        correctMoves.add(new Point2D.Double(5.0, 4.0));
        correctMoves.add(new Point2D.Double(6.0, 1.0));
        correctMoves.add(new Point2D.Double(6.0, 5.0));

        assertEquals(correctMoves, possibleMoves);
    }

    @Test
    public void testCapture() {
        List<Piece> capturedPieces = new ArrayList<Piece>();

        Player blackPlayer = new Player("Black Player", 0, capturedPieces);
        Player whitePlayer = new Player("White Player", 1, null);

        Player currPlayer = blackPlayer;
        clearBoard();

        Point2D srcCoord = new Point2D.Double(3, 3);
        Point2D destCoord = new Point2D.Double(2, 5);

        Pawn p = new Pawn(new Point2D.Double(2, 5), 1);
        Knight b = new Knight(new Point2D.Double(3, 3), 0);
        board.getBoard()[2][5] = p;
        board.getBoard()[3][3] = b;

        board.capture(currPlayer, blackPlayer, whitePlayer, destCoord, srcCoord);

        assertEquals(b.getName(), "Knight");
        assertEquals(p, blackPlayer.getCapturedPieces().get(0));
        assertEquals(board.getBoard()[2][5], b);
        assertEquals(board.getBoard()[3][3], null);
        assertEquals(b.getCurrCoord(), new Point2D.Double(2, 5));
        assertEquals(b.getColor(), 0);
        assertEquals(board.isDidCapture(), false);
        assertEquals(board.isCheckmate(), false);
    }

    private void clearBoard() {
        for(int i = 0; i < rowSize; i++) {
            for(int j = 0; j < colSize; j++) {
                board.board[i][j] = null;
            }
        }
    }
}
