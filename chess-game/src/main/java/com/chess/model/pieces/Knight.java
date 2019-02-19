package com.chess.model.pieces;

import java.awt.geom.Point2D;

public class Knight extends Piece {

    Point2D currCoord;
    int color;

    public Knight(Point2D currCoord, int color) {
        this.currCoord = currCoord;
        this.color = color; //0 = black, 1 = white
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setCurrCoord(int x, int y) {
        currCoord.setLocation(x, y);
    }

    @Override
    public Point2D getCurrCoord() {
        return currCoord;
    }

    @Override
    public String getName() {
        return "Knight";
    }
}
