package com.chess;

import com.chess.model.logic.Board;
import com.chess.model.logic.Move;
import com.chess.model.logic.Player;
import com.chess.model.pieces.King;
import com.chess.model.pieces.Pawn;
import com.chess.model.pieces.Piece;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;


public class KingTest {

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
        possibleMoves = Move.generatePossibleMovesKing(currPlayer, possibleMoves, srcCoord, board.getBoard());

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
        Player whitePlayer = new Player("White Player", 1, null);

        Player currPlayer = blackPlayer;
        clearBoard();

        Point2D srcCoord = new Point2D.Double(5, 6);
        Point2D destCoord = new Point2D.Double(5, 5);

        Pawn p = new Pawn(new Point2D.Double(5, 5), 1);
        King k = new King(new Point2D.Double(5, 6), 0);
        board.getBoard()[5][5] = p;
        board.getBoard()[5][6] = k;

        board.capture(currPlayer, blackPlayer, whitePlayer, destCoord, srcCoord);
        assertEquals(k.getName(), "King");
        assertEquals(p, blackPlayer.getCapturedPieces().get(0));
        assertEquals(board.getBoard()[5][5], k);
        assertEquals(board.getBoard()[5][6], null);
        assertEquals(k.getCurrCoord(), new Point2D.Double(5, 5));
        assertEquals(k.getColor(), 0);
        board.undo(destCoord, srcCoord);
        board.move(currPlayer, blackPlayer, whitePlayer, srcCoord, destCoord);
    }

    @Test
    public void testCheck() {
        List<Piece> capturedPieces = new ArrayList<Piece>();

        Player blackPlayer = new Player("Black Player", 0, capturedPieces);

        Player currPlayer = blackPlayer;
        clearBoard();

        Point2D srcCoord = new Point2D.Double(5, 6);

        Pawn p = new Pawn(new Point2D.Double(5, 4), 1);
        King k = new King(new Point2D.Double(5, 6), 0);

        List<Point2D> possibleMoves = new ArrayList<Point2D>();
        possibleMoves = Move.generatePossibleMovesKing(currPlayer, possibleMoves, srcCoord, board.getBoard());

        //assertFalse(possibleMoves.contains(new Point2D.Double(6, 5)));

    }

    private void clearBoard() {
        for(int i = 0; i < rowSize; i++) {
            for(int j = 0; j < colSize; j++) {
                board.board[i][j] = null;
            }
        }
    }
}
