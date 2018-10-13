package br.com.checkers.utils;

import java.util.Vector;

public class MoveList {
    private Vector moves;

    private Vector instavel;

    public MoveList() {
        moves = new Vector();
        instavel = new Vector();
    }

    public Vector getMoves() {
        return moves;
    }

    public Vector getInstavel() {
        return instavel;
    }
}
