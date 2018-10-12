package br.com.checkers.board;

import br.com.checkers.CheckersGame;

import java.awt.*;

public class BoardInterface extends Canvas {
    private CheckersGame game;
    private Board board;

    public BoardInterface(CheckersGame game, Board board){
        super();
        this.game = game;
        this.board = board;
    }
}
