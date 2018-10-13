package br.com.checkers.board;

import br.com.checkers.CheckersGame;
import br.com.checkers.utils.Pair;

import java.awt.*;
import java.util.Vector;

public class BoardInterface extends Canvas {
    private CheckersGame game;
    private Board board, visibleBoard;
    Vector<Pair> move;
    int menor;

    public BoardInterface(CheckersGame game, Board board){
        super();
        this.game = game;
        this.board = board;
        this.visibleBoard = new Board();
        this.board.copy(visibleBoard);
    }

    public void paint(Graphics graphics){
        Graphics2D graphics2D = (Graphics2D) graphics.create();

        if (this.getSize().width < this.getSize().height){
            menor = this.getSize().width;
        }else{
            menor = this.getSize().height;
        }
        int m = menor / 9;

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                if((i+j)%2==0){
                    graphics2D.setStroke(new BasicStroke(1));
                    graphics2D.setColor(Color.gray);
                    graphics2D.fillRect(i * m, j * m, m + 1, m + 1);
                }else {
                    graphics2D.setColor(Color.gray);
                    graphics2D.drawRect(i * m, j * m, m, m);
                }
            }

        }

    }
}
