package com.chess.model.pieces;

import java.awt.geom.Point2D;

public abstract class Piece {

    public abstract int getColor();

    public abstract void setCurrCoord(int x, int y);

    public abstract Point2D getCurrCoord();

    public abstract String getName();

    @Override
    public String toString() {
        return "(" + getCurrCoord().getX() + ", " + getCurrCoord().getY() + ": " + getName() + ")";
    }
}
