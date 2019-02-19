package com.chess.model.logic;

import com.chess.model.pieces.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to generate all possible moves of each piece.
 * Each method return a List of Point2D objects with the possible destination coordinates.
 */
public class Move {

    /**
     * Generates possible moves for a Bishop piece.
     * @param currPlayer
     * @param possibleMoves
     * @param currCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMovesBishop(Player currPlayer, List<Point2D> possibleMoves, Point2D currCoord, Piece[][] board) {
        possibleMoves.clear();

        int row = (int) currCoord.getX();
        int column = (int) currCoord.getY();

        //first quadrant
        for (int i = row + 1, j = column - 1; i < board.length && j > -1; i++, j--) {
            Piece p = board[i][j];
            if(p == null || isOpponentPiece(p, currPlayer)) {
                possibleMoves.add(new Point2D.Double(i, j));
            } else {
                break;
            }
        }

        //second quadrant
        for (int i = row - 1, j = column - 1; i > -1 && j > -1; i--, j--) {
            Piece p = board[i][j];
            if(p == null || isOpponentPiece(p, currPlayer)) {
                possibleMoves.add(new Point2D.Double(i, j));
            } else {
                break;
            }
        }

        //third quadrant
        for (int i = row - 1, j = column + 1; i > -1 && j < board[0].length ; i--, j++) {
            Piece p = board[i][j];
            if(p == null || isOpponentPiece(p, currPlayer)) {
                possibleMoves.add(new Point2D.Double(i, j));
            } else {
                break;
            }
        }

        //fourth quadrant
        for (int i = row + 1, j = column + 1; i < board.length && j < board[0].length; i++, j++) {
            Piece p = board[i][j];
            if(p == null || isOpponentPiece(p, currPlayer)) {
                possibleMoves.add(new Point2D.Double(i, j));
            } else {
                break;
            }
        }
        return possibleMoves;
    }

    /**
     * Generates possible moves for a King piece.
     * @param currPlayer
     * @param possibleMoves
     * @param currCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMovesKing(Player currPlayer, List<Point2D> possibleMoves, Point2D currCoord, Piece[][] board) {
        possibleMoves.clear();
        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] d : directions) {
            int i = (int)currCoord.getX() + d[0], j = (int)currCoord.getY() + d[1];
            if(i >= 0 && i < 8 && j >= 0 && j < 8) { //check if out of bounds
                Piece p = board[i][j];
                if(p == null || isOpponentPiece(p, currPlayer)) {
                    possibleMoves.add(new Point2D.Double(i, j));
                }
            }
        }

//        //removes possible moves that put the King in check
//        for(int i = 0; i < board.length; i++) {
//            for(int j = 0; j < board[0].length; j++) {
//                Piece p = board[i][j];
//                if(p != null && isOpponentPiece(p, currPlayer)) {
//                    List<Point2D> opponentMoves = generatePossibleMoves(p, currPlayer, possibleMoves, currCoord, board);
//                    Iterator<Point2D> iter = possibleMoves.iterator();
//                    while(iter.hasNext()) {
//                        if(opponentMoves.contains(iter.next())) {
//                            possibleMoves.remove(iter.next());
//                        }
//                    }
//                }
//            }
//        }
        return possibleMoves;
    }

    /**
     * Generates possible moves for a Knight piece.
     * @param currPlayer
     * @param possibleMoves
     * @param currCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMovesKnight(Player currPlayer, List<Point2D> possibleMoves, Point2D currCoord, Piece[][] board) {
        possibleMoves.clear();
        int[][] directions = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};
        for (int[] d : directions) {
            int i = (int)currCoord.getX() + d[0], j = (int)currCoord.getY() + d[1];
            if(i >= 0 && i < 8 && j >= 0 && j < 8) { //check if out of bounds
                Piece p = board[i][j];
                if(p == null || isOpponentPiece(p, currPlayer)) {
                    possibleMoves.add(new Point2D.Double(i, j));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Generates possible moves for a Pawn piece.
     * @param currPlayer
     * @param possibleMoves
     * @param currCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMovesPawn(Player currPlayer, List<Point2D> possibleMoves, Point2D currCoord, Piece[][] board) {
        possibleMoves.clear();

        int x, y;
        if (currPlayer.color == 0) { //black pawn
            x = (int) currCoord.getX() + 1;
            y = (int) currCoord.getY();
            if(isInBounds(x, y)) {
                Piece ahead = board[x][y]; //tile one step ahead of pawn
                if (ahead == null) { //tile is unoccupied
                    possibleMoves.add(new Point2D.Double(x, y));
                }
            }

            x = (int) currCoord.getX() + 1;
            y = (int) currCoord.getY() + 1;
            if(isInBounds(x, y)) {
                if (board[x][y] != null) { //tile to the right diagonal
                    Piece rightDiag = board[(int) currCoord.getX() + 1][(int) currCoord.getY() + 1];
                    if (isOpponentPiece(rightDiag, currPlayer)) {
                        possibleMoves.add(new Point2D.Double(x, y));
                    }
                }
            }

            x = (int) currCoord.getX() + 1;
            y = (int) currCoord.getY() - 1;
            if(isInBounds(x, y)) {
                if (board[x][y] != null) { //tile to the left diagonal
                    Piece leftDiag = board[x][y];
                    if (isOpponentPiece(leftDiag, currPlayer)) {
                        possibleMoves.add(new Point2D.Double(x, y));
                    }
                }
            }
        } else { //white pawn
            x = (int) currCoord.getX() - 1;
            y = (int) currCoord.getY();
            if(isInBounds(x, y)) {
                Piece ahead = board[x][y];
                if (ahead == null) { //tile one step ahead of pawn
                    possibleMoves.add(new Point2D.Double(x, y));
                }
            }

            x = (int) currCoord.getX() - 1;
            y = (int) currCoord.getY() + 1;
            if(isInBounds(x, y)) {
                if (board[x][y] != null) { //tile to the right diagonal
                    Piece rightDiag = board[x][y];
                    if (isOpponentPiece(rightDiag, currPlayer)) {
                        possibleMoves.add(new Point2D.Double(x, y));
                    }
                }
            }

            x = (int) currCoord.getX() - 1;
            y = (int) currCoord.getY() - 1;
            if(isInBounds(x, y)) {
                if (board[x][y] != null) { //tile to the left diagonal
                    Piece leftDiag = board[x][y];
                    if (isOpponentPiece(leftDiag, currPlayer)) {
                        possibleMoves.add(new Point2D.Double(x, y));
                    }
                }
            }
        }

        return possibleMoves;
    }

    public static boolean isInBounds(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates possible moves for a Queen piece.
     * @param currPlayer
     * @param possibleMoves
     * @param currCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMovesQueen(Player currPlayer, List<Point2D> possibleMoves, Point2D currCoord, Piece[][] board) {
        possibleMoves.clear();

        //use bishop and rook to generate all possible moves
        List<Point2D> bishopMoves = new ArrayList<>();
        bishopMoves = Move.generatePossibleMovesBishop(currPlayer, bishopMoves, currCoord, board);

        List<Point2D> rookMoves = new ArrayList<>();
        rookMoves = Move.generatePossibleMovesRook(currPlayer, rookMoves, currCoord, board);

        possibleMoves.addAll(bishopMoves);
        possibleMoves.addAll(rookMoves);

        return possibleMoves;
    }

    /**
     * Generates possible moves for a Rook piece.
     * @param currPlayer
     * @param possibleMoves
     * @param currCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMovesRook(Player currPlayer, List<Point2D> possibleMoves, Point2D currCoord, Piece[][] board) {
        possibleMoves.clear();

        int row = (int) currCoord.getX();
        int col = (int) currCoord.getY();

        //move up
        for (int i = row + 1; i < board.length; i++) {
            Piece p = board[i][col];
            if (p == null) {
                possibleMoves.add(new Point2D.Double(i, col));
            } else if (isOpponentPiece(p, currPlayer)) {
                possibleMoves.add(new Point2D.Double(i, col));
                break;
            } else {
                break;
            }
        }
        //move down
        for (int i = row - 1; i >= 0; i--) {
            Piece p = board[i][col];
            if (p == null) {
                possibleMoves.add(new Point2D.Double(i, col));
            } else if (isOpponentPiece(p, currPlayer)) {
                possibleMoves.add(new Point2D.Double(i, col));
                break;
            } else {
                break;
            }
        }

        //move left
        for (int i = col - 1; i >= 0; i--) {
            Piece p = board[row][i];
            if (p == null) {
                possibleMoves.add(new Point2D.Double(row, i));
            } else if (isOpponentPiece(p, currPlayer)) {
                possibleMoves.add(new Point2D.Double(row, i));
                break;
            } else {
                break;
            }
        }

        //move right
        for (int i = col + 1; i < board.length; i++) {
            Piece p = board[row][i];
            if (p == null) {
                possibleMoves.add(new Point2D.Double(row, i));
            } else if (isOpponentPiece(p, currPlayer)) {
                possibleMoves.add(new Point2D.Double(row, i));
                break;
            } else {
                break;
            }
        }

        return possibleMoves;
    }

    /**
     * Generates possible moves for an Sorcerer piece.
     * @param currPlayer
     * @param possibleMoves
     * @param currCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMovesSorcerer(Player currPlayer, List<Point2D> possibleMoves, Point2D currCoord, Piece[][] board) {
        return generatePossibleMovesKing(currPlayer, possibleMoves, currCoord, board);
    }

    /**
     * Generates possible moves for an Archer piece.
     * @param currPlayer
     * @param possibleMoves
     * @param currCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMovesArcher(Player currPlayer, List<Point2D> possibleMoves, Point2D currCoord, Piece[][] board) {
        return generatePossibleMovesQueen(currPlayer, possibleMoves, currCoord, board);
    }


    /**
     * Returns true if the given piece belongs to the opponent of the current player.
     * @param p
     * @param currPlayer
     * @return
     */
    private static boolean isOpponentPiece(Piece p, Player currPlayer) {
        if(p.getColor() != currPlayer.getColor()){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates possible moves for the correct piece type.
     * @param p
     * @param currPlayer
     * @param possibleMoves
     * @param srcCoord
     * @param board
     * @return
     */
    public static List<Point2D> generatePossibleMoves(Piece p, Player currPlayer, List<Point2D> possibleMoves, Point2D srcCoord, Piece[][] board) {
        if(p instanceof Bishop) {
            possibleMoves = Move.generatePossibleMovesBishop(currPlayer, possibleMoves, srcCoord, board);
        } else if (p instanceof King) {
            possibleMoves = Move.generatePossibleMovesKing(currPlayer, possibleMoves, srcCoord, board);
        } else if (p instanceof Knight) {
            possibleMoves = Move.generatePossibleMovesKnight(currPlayer, possibleMoves, srcCoord, board);
        } else if (p instanceof Pawn) {
            possibleMoves = Move.generatePossibleMovesPawn(currPlayer, possibleMoves, srcCoord, board);
        } else if (p instanceof Queen) {
            possibleMoves = Move.generatePossibleMovesQueen(currPlayer, possibleMoves, srcCoord, board);
        } else if (p instanceof Rook) {
            possibleMoves = Move.generatePossibleMovesRook(currPlayer, possibleMoves, srcCoord, board);
        } else if (p instanceof Sorcerer) {
            possibleMoves = Move.generatePossibleMovesSorcerer(currPlayer, possibleMoves, srcCoord, board);
        } else if (p instanceof Archer) {
            possibleMoves = Move.generatePossibleMovesArcher(currPlayer, possibleMoves, srcCoord, board);
        }
        return possibleMoves;
    }
}
