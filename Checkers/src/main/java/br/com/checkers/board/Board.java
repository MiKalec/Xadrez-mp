package br.com.checkers.board;

public class Board {
    private static final Integer EMPTY = 0;
    private static final Integer INITIAL_POSITION = 1;
    private static final Integer RED_INITIAL_POSITION = 2;
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
                    board[i][j] = RED_INITIAL_POSITION;
                }
                if ((7 - i + j) % 2 == 0) {
                    board[i][j] = CANNOT_GO;
                }
            }
        }
        printBoard();
    }

    public Integer[][] getBoard() {
        return board;
    }

    public void copy(Board visibleBoard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                visibleBoard.board[i][j] = this.board[i][j];
            }
        }
    }

    public void printBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
