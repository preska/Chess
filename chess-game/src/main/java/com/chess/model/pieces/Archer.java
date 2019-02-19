package com.chess.model.pieces;

import java.awt.geom.Point2D;

/**
 * Custom piece
 *
 * The Archer is a stationary position. It cannot move, but can launch an attack in a horizontal, vertical, or diagonal fashion.
 *
 */
public class Archer extends Piece {

    Point2D currCoord;
    int color;

    public Archer(Point2D currCoord, int color) {
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
        return "Archer";
    }
}
