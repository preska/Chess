package com.chess.model.logic;

import com.chess.model.pieces.Piece;

import java.util.List;

public class Player {

    private String name;
    int color; //0 = black, 1 = white
    List<Piece> capturedPieces;

    public Player(String name, int color, List<Piece> capturedPieces) {
        this.name = name;
        this.color = color;
        this.capturedPieces = capturedPieces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

    /**
     * Adds the given captured piece to the captures bag of the player.
     * @param p
     */
    public void addCapturedPiece(Piece p) {
        capturedPieces.add(p);
    }
}
