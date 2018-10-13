package br.com.checkers.board;

import br.com.checkers.CheckersGame;
import br.com.checkers.utils.Pair;

import java.awt.*;
import java.util.Vector;

public class BoardInterface extends Canvas {
    private CheckersGame game;
    private Board board, visibleBoard;
    private Integer menor;
    Vector<Pair> move;

    public BoardInterface(CheckersGame game, Board board) {
        super();
        this.game = game;
        this.board = board;
        this.visibleBoard = new Board();
        this.board.copy(visibleBoard);
    }

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics.create();

        if (this.getSize().width < this.getSize().height) {
            menor = this.getSize().width;
        } else {
            menor = this.getSize().height;
        }
        int m = menor / 9;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    graphics2D.setStroke(new BasicStroke(1));
                    graphics2D.setColor(Color.gray);
                    graphics2D.fillRect(i * m, j * m, m + 1, m + 1);
                } else {
                    graphics2D.setColor(Color.gray);
                    graphics2D.drawRect(i * m, j * m, m, m);
                }
                if(visibleBoard.getBoard()[i][j] == 1){
                    drawChecker(i, j, "Black", graphics2D);
                }else if (visibleBoard.getBoard()[i][j] == 2){
                    drawChecker(i, j, "Red", graphics2D);
                }
            }

        }

    }

    public void drawChecker(Integer x, Integer y, String color, Graphics graphics) {
        Integer m = menor / 9;
        Integer pieceX;
        Integer pieceY;
        pieceX = (8 * m) / 9;
        pieceY = (60 * pieceX) / 100;

        if(color.equals("Red")){
            graphics.setColor(new Color(134, 12, 38));
        }else{
            graphics.setColor(new Color(20, 20, 27));
        }

        graphics.fillOval(x * m + m / 2 - pieceX / 2, y * m + m / 2 - pieceY / 2, pieceX, pieceY);

        if(color.equals("Red")){
            graphics.setColor(new Color(20, 20, 27));
        }else{
            graphics.setColor(new Color(134, 12, 38));
        }

        graphics.drawArc(x * m + m / 2 - pieceX / 2, y * m + m / 2 - pieceY / 2, pieceX,
                pieceY, 0, -180);
        graphics.drawArc(x * m + m / 2 - pieceX / 2, y * m + m / 2 - pieceY / 2 + 1,
                pieceX, pieceY, 0, -180);
    }
}
