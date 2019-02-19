package com.chess.view;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {

    private int xCoord;
    private int yCoord;

    public Tile(LayoutManager layout, int xCoord, int yCoord) {
        super(layout);
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
