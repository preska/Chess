package com.chess.model.pieces;

import java.awt.geom.Point2D;

/**
 * Custom piece
 *
 * The Sorcerer moves like a King, but cannot be put in check. In addition, rather than capturing a piece, it can choose to replace any surrounding
 * opponent piece with any piece in the opponent's captured list.
 *
 */
public class Sorcerer extends Piece {

    Point2D currCoord;
    int color;

    public Sorcerer(Point2D currCoord, int color) {
        this.currCoord = currCoord;
        this.color = color; //0 = black, 1 = white
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public void setCurrCoord(int x, int y) {
        currCoord.setLocation(x, y);
    }

    @Override
    public Point2D getCurrCoord() {
        return null;
    }

    @Override
    public String getName() {
        return "Sorcerer";
    }

}