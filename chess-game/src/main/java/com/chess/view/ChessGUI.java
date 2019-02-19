package com.chess.view;

import com.chess.model.logic.Board;
import com.chess.model.logic.Game;
import com.chess.model.logic.Player;
import com.chess.model.pieces.Piece;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * Citation: https://forgetcode.com/Java/848-Chess-game-Swing
 */
public class ChessGUI extends JFrame implements MouseListener, MouseMotionListener {
    // GAME //
    private static Game chessGame;
    private static int turnNum;
    private static Board board;
    private static Player blackPlayer;
    private static Player whitePlayer;
    private static List<Piece> blackPlayerCaptured;
    private static List<Piece> whitePlayerCaptured;
    private static List<List<Point2D>> moves = new ArrayList<List<Point2D>>(); //list containing srcCoord, destCoord

    // GUI //
    private static JLayeredPane layeredPane;
    private static JPanel boardGUI;
    private static JLabel piece;
    private static int xOffset;
    private static int yOffset;
    private static Point2D srcCoord;
    private static Point2D destCoord;
    private static JLabel turn;
    private static JLabel info;
    private static JLabel turnNumber;


    public ChessGUI(){
        turnNum = 0;
        board = new Board(8, 8);
        blackPlayerCaptured = new ArrayList<Piece>();
        whitePlayerCaptured = new ArrayList<Piece>();

        blackPlayer = new Player("BlackPlayer", 0, blackPlayerCaptured);
        whitePlayer = new Player("WhitePlayer", 1, whitePlayerCaptured);

        chessGame = new Game();
        chessGame.setCurrPlayer(whitePlayer);
        initializeBoard();
    }

    public static void main(String[] args) {
        JFrame frame = new ChessGUI();

        //add menu bar elements
        JMenuBar mb = new JMenuBar();
        turn = new JLabel(" " + chessGame.getCurrPlayer().getName() + " goes first.");
        JButton undo = new JButton("Undo");
        JButton restart = new JButton("Restart");
        JButton forfeit = new JButton("Forfeit");
        info = new JLabel("");
        turnNumber = new JLabel("           Turn Number: " + turnNum);

        mb.add(turn);
        mb.add(undo);
        mb.add(restart);
        mb.add(forfeit);
        mb.add(info);
        mb.add(turnNumber);
        frame.setJMenuBar(mb);

        undo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(moves.size() != 0) {
                    List<Point2D> lastMove = moves.get(moves.size() - 1);
                    Point2D newSrc = lastMove.get(1);
                    Point2D newDest = lastMove.get(0);
                    board.undo(newSrc, newDest);

                    //undo move in GUI
                    Component srcComp = boardGUI.findComponentAt((int) ((newSrc.getY() + 0.5) * 93.75), (int) ((newSrc.getX() + 0.5) * 93.75));
                    piece = (JLabel) srcComp;
                    piece.setVisible(false);
                    Component c =  boardGUI.findComponentAt((int) ((newDest.getY() + 0.5) * 93.75), (int) ((newDest.getX() + 0.5) * 93.75));

                    if (c instanceof JLabel){
                        Container parent = c.getParent();
                        parent.remove(0);
                        parent.add( piece );
                    }
                    else {
                        Container parent = (Container)c;
                        parent.add( piece );
                    }

                    ((PieceIcon) piece).setxCoord((int) newDest.getX());
                    ((PieceIcon) piece).setyCoord((int) newDest.getY());

                    piece.setVisible(true);
                    moves.remove(moves.size() - 1);
                    turnNum--;
                    turnNumber.setText("           Turn Number: " + turnNum);
                } else {
                    info.setText(" No more moves to undo.");
                }
            }
        });

        restart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int exit = JOptionPane.showConfirmDialog(null, "Do both players want to restart the game?" , null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (exit == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        forfeit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(turnNum % 2 == 0) {
                    JOptionPane.showMessageDialog(null, chessGame.getCurrPlayer().getName() + " has forfeited the game. " + chessGame.getBlackPlayer().getName() + " wins!");
                } else {
                    JOptionPane.showMessageDialog(null, chessGame.getCurrPlayer().getName() + " has forfeited the game. " + chessGame.getWhitePlayer().getName() + " wins!");
                }
                System.exit(0);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Initialize the chess boardGUI to start the game.
     */
    public void initializeBoard() {
        Dimension boardSize = new Dimension(750, 750);
        int ROW_SIZE = 8;
        int COL_SIZE = 8;

        //Create LayeredPane to track Mouse events
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add boardGUI onto the LayeredPane
        boardGUI = new JPanel();
        layeredPane.add(boardGUI, JLayeredPane.DEFAULT_LAYER);
        boardGUI.setLayout(new GridLayout(ROW_SIZE, COL_SIZE));
        boardGUI.setPreferredSize(boardSize);
        boardGUI.setBounds(0, 0, boardSize.width, boardSize.height);

        //Create a grid of Tiles (JPanels)
        for (int i = 0; i < ROW_SIZE * COL_SIZE; i++) {
            Tile tile = new Tile(new BorderLayout(), i / ROW_SIZE, i % COL_SIZE);
            boardGUI.add(tile);
            if ((i / ROW_SIZE + i % COL_SIZE) % 2 == 0) {
                tile.setBackground(Color.decode("#ba7130")); //dark color on odd tiles
            } else {
                tile.setBackground(Color.decode("#ffd6b2")); //light color on even tiles
            }
        }

        setInitialPieces();
    }

    /**
     * Set the appropriate starting piece on each tile.
     */
    public void setInitialPieces() {
        //add black pieces
        PieceIcon piece = new PieceIcon(new ImageIcon("src/images/blackRook.png"), 0, 0);
        piece.setName("blackRook");
        Tile tile = (Tile) boardGUI.getComponent(0);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/blackKnight.png"), 0, 1);
        piece.setName("blackKnight");
        tile = (Tile) boardGUI.getComponent(1);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/blackBishop.png"), 0, 2);
        piece.setName("blackBishop");
        tile = (Tile) boardGUI.getComponent(2);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/blackQueen.png"), 0, 3);
        piece.setName("blackQueen");
        tile = (Tile) boardGUI.getComponent(3);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/blackKing.png"), 0, 4);
        piece.setName("blackKing");
        tile = (Tile) boardGUI.getComponent(4);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/blackBishop.png"), 0, 5);
        piece.setName("blackBishop");
        tile = (Tile) boardGUI.getComponent(5);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/blackKnight.png"), 0, 6);
        piece.setName("blackKnight");
        tile = (Tile) boardGUI.getComponent(6);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/blackRook.png"), 0, 7);
        piece.setName("blackRook");
        tile = (Tile) boardGUI.getComponent(7);
        tile.add(piece);
        for(int i = 8; i <= 15; i++) {
            piece = new PieceIcon(new ImageIcon("src/images/blackPawn.png"), 1, (i - 8));
            piece.setName("blackPawn");
            tile = (Tile) boardGUI.getComponent(i);
            tile.add(piece);
        }

        //add white pieces
        piece = new PieceIcon(new ImageIcon("src/images/whiteRook.png"), 7, 0);
        piece.setName("whiteRook");
        tile = (Tile) boardGUI.getComponent(56);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/whiteKnight.png"), 7, 1);
        piece.setName("whiteKnight");
        tile = (Tile) boardGUI.getComponent(57);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/whiteBishop.png"), 7, 2);
        piece.setName("whiteBishop");
        tile = (Tile) boardGUI.getComponent(58);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/whiteQueen.png"), 7, 3);
        piece.setName("whiteQueen");
        tile = (Tile) boardGUI.getComponent(59);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/whiteKing.png"), 7, 4);
        piece.setName("whiteKing");
        tile = (Tile) boardGUI.getComponent(60);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/whiteBishop.png"), 7, 5);
        piece.setName("whiteBishop");
        tile = (Tile) boardGUI.getComponent(61);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/whiteKnight.png"), 7, 6);
        piece.setName("whiteKnight");
        tile = (Tile) boardGUI.getComponent(62);
        tile.add(piece);
        piece = new PieceIcon(new ImageIcon("src/images/whiteRook.png"), 7, 7);
        piece.setName("whiteRook");
        tile = (Tile) boardGUI.getComponent(63);
        tile.add(piece);
        for(int i = 48; i <= 55; i++) {
            piece = new PieceIcon(new ImageIcon("src/images/whitePawn.png"), 6, (i - 48));
            piece.setName("whitePawn");
            tile = (Tile) boardGUI.getComponent(i);
            tile.add(piece);
        }

        int custom = JOptionPane.showConfirmDialog(null, "Use custom pieces?" , null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (custom == JOptionPane.YES_OPTION) {
            piece = new PieceIcon(new ImageIcon("src/images/whiteArcher.png"), 7, 0);
            piece.setName("whiteArcher");
            tile = (Tile) boardGUI.getComponent(56);
            tile.add(piece);

            piece = new PieceIcon(new ImageIcon("src/images/blackArcher.png"), 0, 7);
            piece.setName("blackArcher");
            tile = (Tile) boardGUI.getComponent(7);
            tile.add(piece);

            piece = new PieceIcon(new ImageIcon("src/images/whiteSorcerer.png"), 7, 7);
            piece.setName("whiteSorcerer");
            tile = (Tile) boardGUI.getComponent(63);
            tile.add(piece);

            piece = new PieceIcon(new ImageIcon("src/images/blackSorcerer.png"), 0, 0);
            piece.setName("blackSorcerer");
            tile = (Tile) boardGUI.getComponent(0);
            tile.add(piece);

            board.setCustomPieces();
        }
    }

    /**
     * Set piece onto the nearest tile
     * @param e
     */
    public void mousePressed(MouseEvent e){
        if(turnNum % 2 == 0) {
            chessGame.setCurrPlayer(whitePlayer);
        } else {
            chessGame.setCurrPlayer(blackPlayer);
        }
        piece = null;
        Component comp = boardGUI.findComponentAt(e.getX(), e.getY());

        if(comp instanceof Tile) {
            srcCoord = new Point2D.Double(((Tile) comp).getxCoord(), ((Tile) comp).getyCoord());
        } else if(comp instanceof PieceIcon) {
            srcCoord = new Point2D.Double(((PieceIcon) comp).getxCoord(), ((PieceIcon) comp).getyCoord());
        }
        if (!(comp instanceof JPanel)) {
            Point parentLocation = comp.getParent().getLocation();
            xOffset = parentLocation.x - e.getX();
            yOffset = parentLocation.y - e.getY();
            piece = (JLabel) comp;
            piece.setLocation(e.getX() + xOffset, e.getY() + yOffset);
            piece.setSize(piece.getWidth(), piece.getHeight());
            layeredPane.add(piece, JLayeredPane.DRAG_LAYER);
        }
    }

    /**
     * Piece follows mouse movement when clicked on
     * @param me
     */
    public void mouseDragged(MouseEvent me) {
        if (piece == null)
            return;

        piece.setLocation(me.getX() + xOffset, me.getY() + yOffset);
    }

    /**
     * Replace piece if tile is non-empty
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        if(piece == null)
            return;

        piece.setVisible(false);
        Component comp = boardGUI.findComponentAt(e.getX(), e.getY());

        if(comp instanceof Tile) {
            destCoord = new Point2D.Double(((Tile) comp).getxCoord(), ((Tile) comp).getyCoord());
        } else if(comp instanceof PieceIcon) {
            destCoord = new Point2D.Double(((PieceIcon) comp).getxCoord(), ((PieceIcon) comp).getyCoord());
        }

        Boolean moved = chessGame.move(srcCoord, destCoord, chessGame);
        if(moved) { //move piece in GUI
            info.setText(" Successful move");
            List<Point2D> currMove = new ArrayList<Point2D>();
            currMove.add(srcCoord);
            currMove.add(destCoord);
            moves.add(currMove);

            if (comp instanceof JLabel){
                info.setText(" Piece captured successfully.");
                replacePieceIcon(comp);
            } else {
                Container parent = (Container) comp;
                parent.add(piece);
            }
            piece.setVisible(true);

            ((PieceIcon) piece).setxCoord((int) destCoord.getX());
            ((PieceIcon) piece).setyCoord((int) destCoord.getY());
            turnNum++;
            if(turnNum % 2 == 0) {
                turn.setText(" It's " + chessGame.getWhitePlayer().getName() + "'s turn.");
            } else {
                turn.setText(" It's " + chessGame.getBlackPlayer().getName() + "'s turn.");
            }
            turnNumber.setText("           Turn Number: " + turnNum);
        } else { //bring piece back to src coord
            info.setText(" Invalid move. Try again.");
            if (comp instanceof JLabel && board.isDidCapture()){
                replacePieceIcon(comp);
            } else {
                Tile tile = (Tile) boardGUI.getComponent((((int) srcCoord.getX() * 8) + (int) srcCoord.getY()));
                tile.add(piece);
            }
            piece.setVisible(true);
        }

        if(board.isCheckmate()) {
            JOptionPane.showMessageDialog(null, "Checkmate! " + chessGame.getCurrPlayer().getName() + " wins!");
            System.exit(0);
        }
    }

    /**
     * Replaces a piece icon by the given component
     * @param comp
     */
    private static void replacePieceIcon(Component comp) {
        Container parent = comp.getParent();
        parent.remove(0);
        parent.add(piece);
        Tile tile = (Tile) boardGUI.getComponent((((int) destCoord.getX() * 8) + (int) destCoord.getY()));
        tile.add(piece);
    }

    /**
     * Unimplemented method
     * @param e
     */
    public void mouseClicked(MouseEvent e) {}

    /**
     * Unimplemented method
     * @param e
     */
    public void mouseMoved(MouseEvent e) {}

    /**
     * Unimplemented method
     * @param e
     */
    public void mouseEntered(MouseEvent e){}

    /**
     * Unimplemented method
     * @param e
     */
    public void mouseExited(MouseEvent e) {}
}