package com.chess.model.logic;

import com.chess.model.pieces.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a 2-Dimensional array of pieces with a specified size, and initializes pieces at their starting coordinates.
 */
public class Board {

    public static Piece[][] board;
    public static boolean didCapture;
    public static boolean checkmate = false;

    private static int rowSize, colSize;

    public Board(int rowSize, int colSize) {
        didCapture = false;
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.board = new Piece[rowSize][colSize];
        fillBoard();
    }

    /**
     *  Fills each square with appropriate starting piece.
     */
    private void fillBoard() {
        //black player
        board[0][0] = new Rook(new Point2D.Double(0, 0), 0);
        board[0][1] = new Knight(new Point2D.Double(0, 1), 0);
        board[0][2] = new Bishop(new Point2D.Double(0, 2), 0);
        board[0][3] = new Queen(new Point2D.Double(0, 3), 0);
        board[0][4] = new King(new Point2D.Double(0, 4), 0);
        board[0][5] = new Bishop(new Point2D.Double(0, 5), 0);
        board[0][6] = new Knight(new Point2D.Double(0, 6), 0);
        board[0][7] = new Rook(new Point2D.Double(0, 7), 0);
        board[1][0] = new Pawn(new Point2D.Double(1, 0), 0);
        board[1][1] = new Pawn(new Point2D.Double(1, 1), 0);
        board[1][2] = new Pawn(new Point2D.Double(1, 2), 0);
        board[1][3] = new Pawn(new Point2D.Double(1, 3), 0);
        board[1][4] = new Pawn(new Point2D.Double(1, 4), 0);
        board[1][5] = new Pawn(new Point2D.Double(1, 5), 0);
        board[1][6] = new Pawn(new Point2D.Double(1, 6), 0);
        board[1][7] = new Pawn(new Point2D.Double(1, 7), 0);

        //white player
        board[7][0] = new Rook(new Point2D.Double(7, 0), 1);
        board[7][1] = new Knight(new Point2D.Double(7, 1), 1);
        board[7][2] = new Bishop(new Point2D.Double(7, 2), 1);
        board[7][3] = new Queen(new Point2D.Double(7, 3), 1);
        board[7][4] = new King(new Point2D.Double(7, 4), 1);
        board[7][5] = new Bishop(new Point2D.Double(7, 5), 1);
        board[7][6] = new Knight(new Point2D.Double(7, 6), 1);
        board[7][7] = new Rook(new Point2D.Double(7, 7), 1);
        board[6][0] = new Pawn(new Point2D.Double(6, 0), 1);
        board[6][1] = new Pawn(new Point2D.Double(6, 1), 1);
        board[6][2] = new Pawn(new Point2D.Double(6, 2), 1);
        board[6][3] = new Pawn(new Point2D.Double(6, 3), 1);
        board[6][4] = new Pawn(new Point2D.Double(6, 4), 1);
        board[6][5] = new Pawn(new Point2D.Double(6, 5), 1);
        board[6][6] = new Pawn(new Point2D.Double(6, 6), 1);
        board[6][7] = new Pawn(new Point2D.Double(6, 7), 1);
    }

    public void setCustomPieces() {
        board[7][0] = new Archer(new Point2D.Double(7, 0), 1);
        board[0][7] = new Archer(new Point2D.Double(0, 7), 0);
        board[7][7] = new Sorcerer(new Point2D.Double(7, 7), 1);
        board[0][0] = new Sorcerer(new Point2D.Double(0, 0), 0);
    }

    /**
     * Undos a move by moving a piece back to its original source coordinate.
     * @param srcCoord
     * @param destCoord
     */
    public static void undo(Point2D srcCoord, Point2D destCoord) {
        board[(int) destCoord.getX()][(int) destCoord.getY()] = board[(int) srcCoord.getX()][(int) srcCoord.getY()];
        board[(int) destCoord.getX()][(int) destCoord.getY()].setCurrCoord((int) destCoord.getX(), (int) destCoord.getY());
        board[(int) srcCoord.getX()][(int) srcCoord.getY()] = null;
    }

    /**
     * Given a source coordinate and destination coordinate, check if the move by the source piece to the destination is valid.
     * If valid, the piece is moved. If the destination contains an opposing piece, it is captured.
     * @param currPlayer
     * @param blackPlayer
     * @param whitePlayer
     * @param srcCoord
     * @param destCoord
     */
    public static boolean move(Player currPlayer, Player blackPlayer, Player whitePlayer, Point2D srcCoord, Point2D destCoord) {
        Piece p = board[(int) srcCoord.getX()][(int) srcCoord.getY()];

        if(p.getColor() != currPlayer.getColor()) {
            //cannot move an opponent's piece
            return false;
        }
        if(p == null) {
            //no piece to move here
            return false;
        }

        List<Point2D> possibleMoves = new ArrayList<>();
        Move.generatePossibleMoves(p, currPlayer, possibleMoves, srcCoord, board);

        //check if destination coordinates are within the possible moves
        if(possibleMoves.contains(destCoord)) {
            Piece srcPiece = board[(int) srcCoord.getX()][(int) srcCoord.getY()];
            Piece destPiece = board[(int) destCoord.getX()][(int) destCoord.getY()];

            //check if destCoord contains an opposing piece and capture it
            if(destPiece != null) {
                if(destPiece.getColor() != srcPiece.getColor()) {
                    //captures successfully
                    capture(currPlayer, blackPlayer, whitePlayer, destCoord, srcCoord);
                    didCapture = true;
                    return true;
                } else {
                    //cannot capture own piece
                    return false;
                }
            } else { //destination is empty, piece can be moved successfully unless piece is Archer
                if (srcPiece instanceof Archer) {
                    //cannot move the archer
                    return false;
                } else {
                    //moved successfully
                    board[(int) destCoord.getX()][(int) destCoord.getY()] = board[(int) srcCoord.getX()][(int) srcCoord.getY()];
                    board[(int) destCoord.getX()][(int) destCoord.getY()].setCurrCoord((int) destCoord.getX(), (int) destCoord.getY());
                    board[(int) srcCoord.getX()][(int) srcCoord.getY()] = null;
                    return true;
                }
            }
        } else {
            //invalid move
            return false;
        }
    }

    /**
     * If the piece is of the opposing team, it is captures and out into the appropriate capture list.
     * If the piece captured is the king, the current player wins.
     * @param currPlayer
     * @param blackPlayer
     * @param whitePlayer
     * @param destCoord
     * @param srcCoord
     */
    public static void capture(Player currPlayer, Player blackPlayer, Player whitePlayer, Point2D destCoord, Point2D srcCoord) {
        Piece srcPiece = board[(int) srcCoord.getX()][(int) srcCoord.getY()];
        if(srcPiece instanceof Sorcerer) {
            captureSorcerer(currPlayer, blackPlayer, whitePlayer, destCoord, srcCoord);
        } else if (srcPiece instanceof Archer) {
            captureArcher(currPlayer, blackPlayer, whitePlayer, destCoord, srcCoord);
        } else {
            if(currPlayer == blackPlayer) {
                blackPlayer.addCapturedPiece(board[(int) destCoord.getX()][(int) destCoord.getY()]);
            } else if (currPlayer == whitePlayer) {
                whitePlayer.addCapturedPiece(board[(int) destCoord.getX()][(int) destCoord.getY()]);
            }
            Piece temp = board[(int) destCoord.getX()][(int) destCoord.getY()];
            board[(int) destCoord.getX()][(int) destCoord.getY()] = srcPiece;
            srcPiece.setCurrCoord((int) destCoord.getX(), (int) destCoord.getY());
            board[(int) srcCoord.getX()][(int) srcCoord.getY()] = null;
            if(temp instanceof King) {
                checkmate = true;
            }
        }
    }

    /**
     * If the piece is of the opposing team, the current player gets to pick a piece to replace the captured piece with out of the
     * opponent's captured bag.
     * If the piece captured is the king, the current player wins.
     * @param currPlayer
     * @param blackPlayer
     * @param whitePlayer
     * @param destCoord
     * @param srcCoord
     */
    public static void captureSorcerer(Player currPlayer, Player blackPlayer, Player whitePlayer, Point2D destCoord, Point2D srcCoord) {
        if(currPlayer == blackPlayer) {
            if(whitePlayer.getCapturedPieces().size() != 0) {
                System.out.println("Choose a piece to transform the captured piece to: ");
                for (Piece p : whitePlayer.getCapturedPieces()) {
                    System.out.println(p.toString());
                    //Use buttons on Java Swing to select
                }
                //Simulate that user chooses piece
                Piece transformTo = whitePlayer.getCapturedPieces().get(0);
                blackPlayer.addCapturedPiece(board[(int) destCoord.getX()][(int) destCoord.getY()]);
                board[(int) destCoord.getX()][(int) destCoord.getY()] = transformTo;
                if(board[(int) destCoord.getX()][(int) destCoord.getY()] instanceof King) {
                    checkmate = true;
                    System.out.println("Checkmate! " + currPlayer.getName() + " wins!");
                }
            } else {
                System.out.println("No pieces to transform to yet. Capture a piece first.");
            }
        } else {
            if(blackPlayer.getCapturedPieces().size() != 0) {
                System.out.println("Choose a piece to transform the captured piece to: ");
                for (Piece p : blackPlayer.getCapturedPieces()) {
                    System.out.println(p.toString());
                    //Use buttons on Java Swing to select
                }
                //Simulate that user chooses piece
                Piece transformTo = blackPlayer.getCapturedPieces().get(0);
                whitePlayer.addCapturedPiece(board[(int) destCoord.getX()][(int) destCoord.getY()]);
                board[(int) destCoord.getX()][(int) destCoord.getY()] = transformTo;
                if(board[(int) destCoord.getX()][(int) destCoord.getY()] instanceof King) {
                    checkmate = true;
                }
            } else {
                System.out.println("No pieces to transform to yet. Capture a piece first.");
            }
        }
    }

    /**
     * The source piece (Archer) is stays in place, but the destination piece is captured.
     * @param currPlayer
     * @param blackPlayer
     * @param whitePlayer
     * @param destCoord
     * @param srcCoord
     */
    public static void captureArcher(Player currPlayer, Player blackPlayer, Player whitePlayer, Point2D destCoord, Point2D srcCoord) {
        if(currPlayer == blackPlayer) {
            blackPlayer.addCapturedPiece(board[(int) destCoord.getX()][(int) destCoord.getY()]);
        } else if (currPlayer == whitePlayer) {
            whitePlayer.addCapturedPiece(board[(int) destCoord.getX()][(int) destCoord.getY()]);
        }
        Piece temp = board[(int) destCoord.getX()][(int) destCoord.getY()];
        board[(int) destCoord.getX()][(int) destCoord.getY()] = null;
        if(temp instanceof King) {
            checkmate = true;
        }
    }

    /**
     * Get's board
     * @return
     */
    public static Piece[][] getBoard() {
        return board;
    }

    /**
     * Return true if captured
     * @return
     */
    public static boolean isDidCapture() {
        return didCapture;
    }

    /**
     * Return true if checkmate
     * @return
     */
    public static boolean isCheckmate() {
        return checkmate;
    }
}
