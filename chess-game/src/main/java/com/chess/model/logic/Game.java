package com.chess.model.logic;

import com.chess.model.pieces.Piece;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Instance of a game loop
 */
public class Game {

    private static Board board;
    private static Player blackPlayer;
    private static Player whitePlayer;
    private static Player currPlayer;
    private static List<Piece> blackPlayerCaptured;
    private static List<Piece> whitePlayerCaptured;
    private int turnNum;

    public Game() {
        board = new Board(8, 8);

        blackPlayerCaptured = new ArrayList<Piece>();
        whitePlayerCaptured = new ArrayList<Piece>();

        blackPlayer = new Player("Black Player", 0, blackPlayerCaptured);
        whitePlayer = new Player("White Player", 1, whitePlayerCaptured);

        currPlayer = whitePlayer;

        turnNum = 0;
    }

    /**
     * Calls move in the chessGame for a source coordinate and destination coordinate
     * @param srcCoord
     * @param destCoord
     * @param chessGame
     * @return
     */
    public boolean move(Point2D srcCoord, Point2D destCoord, Game chessGame) {
        boolean canMove = board.move(currPlayer, blackPlayer, whitePlayer, srcCoord, destCoord);
        if (canMove) {
            chessGame.turnNum++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets black player
     * @return
     */
    public static Player getBlackPlayer() {
        return blackPlayer;
    }

    /**
     * Gets white player
     * @return
     */
    public static Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Gets current player
     * @return
     */
    public static Player getCurrPlayer() {
        return currPlayer;
    }

    /**
     * Sets current player
     * @param currPlayer
     */
    public static void setCurrPlayer(Player currPlayer) {
        Game.currPlayer = currPlayer;
    }
}