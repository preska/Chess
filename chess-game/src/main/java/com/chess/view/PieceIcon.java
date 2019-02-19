package com.chess.view;

import javax.swing.*;

public class PieceIcon extends JLabel {
    private int xCoord;
    private int yCoord;

    public PieceIcon(Icon image, int xCoord, int yCoord) {
        super(image);
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
