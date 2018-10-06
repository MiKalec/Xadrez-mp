package br.com.redes.chess.pieces;

public abstract class Pieces {
    private int x;
    private int y;
    private Type type;

    public abstract void move();

    public Type getType() {
        return type;
    }
}
