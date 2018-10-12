package br.com.checkers.board;

public class Board {
    private static final Integer EMPTY = 0;
    private static final Integer INITIAL_POSITION = 1;
    private static final Integer CANNOT_GO = -1;
    private Integer board[][];

    public Board() {
        board = new Integer[8][];

        for (int i = 0; i < 8; i++) {
            board[i] = new Integer[8];
        }

        //setting an empty board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = EMPTY;
            }
        }

        //setting checker's position
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = INITIAL_POSITION;
                }
                if ((7 - i + j) % 2 == 0) {
                    board[i][j] = CANNOT_GO;
                }
            }
        }
        for (int i = 7; i > 4; i--) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = INITIAL_POSITION;
                }
                if ((7 - i + j) % 2 == 0) {
                    board[i][j] = CANNOT_GO;
                }
            }
        }
    }

    //TODO remover
    public static void main(String[] args) {
        Board b = new Board();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(b.board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
