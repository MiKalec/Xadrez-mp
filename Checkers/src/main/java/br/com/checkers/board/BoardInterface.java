package br.com.checkers.board;

import br.com.checkers.CheckersGame;
import br.com.checkers.utils.MoveList;
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
        this.move = new Vector();
    }

    public boolean listMoves(Board board, Integer player, MoveList moveList) {
        Vector<Pair> moves = new Vector<>();
        moveList.getMoves().removeAllElements();
        moveList.getInstavel().removeAllElements();
        Integer checker;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++){
                if(board.getBoard()[i][j] * player <=0)
                    continue;
                checker = Math.abs(board.getBoard()[i][j]);

                if(checker == 1){
                    moves.removeAllElements();
                    seekPlay(player, i, j, board, moves,
                            moveList);
                }
            }
        }
        return true;
    }

    private void seekPlay(Integer player, int i, int j, Board board, Vector<Pair> moves, MoveList moveList) {
        explora_jogada_peca_comendo(player, i, j, board, moves, moveList);
        explora_jogada_peca_movendo(player, i, j, board, moves, moveList);

    }

    private void explora_jogada_peca_movendo(Integer player, int i, int j, Board board, Vector<Pair> moves, MoveList moveList) {
        Vector<Pair> mov;
        int fl;
        fl = 0;

        if (p_move(i, j, 1, 1, board) == true) {

            moves.removeAllElements();
            moves.addElement(new Pair(i, j));
            moves.addElement(new Pair(i + player, j + 1));
            mov = new Vector<Pair>();
            mov = (Vector) moves.clone();
            moveList.getMoves().addElement(mov);

            // coloca elemento que designa a estabilidade desta jogada
            // verifica se pode entrar em dama
            /* pretas */
            fl = getFl(player, i, j, board, fl);

            if (fl == 1)

                moveList.getInstavel().addElement(new Boolean(true));

            else

                moveList.getInstavel().addElement(new Boolean(false));

            moves.removeAllElements();

        }

        fl = 0;

        if (p_move(i, j, 1, -1, board) == true) {

            moves.removeAllElements();
            moves.addElement(new Pair(i, j));
            moves.addElement(new Pair(i + player, j - 1));
            mov = new Vector<Pair>();
            mov = (Vector) moves.clone();
            moveList.getInstavel().addElement(mov);

            // coloca elemento que designa a estabilidade desta jogada
            // verifica se pode entrar em dama
            /* pretas */
            fl = getFl(player, i, j, board, fl);

            if (fl == 1)

                moveList.getInstavel().addElement(new Boolean(true));

            else

                moveList.getInstavel().addElement(new Boolean(false));

            moves.removeAllElements();

        }

    }

    private int getFl(Integer player, int i, int j, Board board, int fl) {
        if (player == 1 && i + player == 6) {

            if (j + 1 < 8 && board.getBoard()[7][j + 1] == 0)
                fl = 1;

            if (j + 1 >= 0 && board.getBoard()[7][j - 1] == 0)
                fl = 1;

        } else if (player == 1 && i + player == 0) {

            if (j + 1 < 8 && board.getBoard()[0][j + 1] == 0)
                fl = 1;

            if (j + 1 >= 0 && board.getBoard()[0][j - 1] == 0)
                fl = 1;

        }
        return fl;
    }

    private void explora_jogada_peca_comendo(Integer player, int i, int j, Board board, Vector<Pair> moves, MoveList moveList) {
        int nelem, dama;
        Board t2;
        Vector<Pair> move;
        boolean moveu;
        t2 = new Board();
        board.copy(t2);
//        score_ant = score;
        moveu = false;

        if (p_come(i, j, 1, 1, board, t2) == true) {

            /* come dama ? */
            dama = (Math.abs(board.getBoard()[i + player][j + 1]));

            moves.addElement(new Pair(i, j));
            explora_jogada_peca_comendo(player, i + 2 * player, j + 2, t2,
                    moves, moveList);
            nelem = moves.size() - 1;
            moves.removeElementAt(nelem);
            moveu = true;
        }

        board.copy(t2);

        if (p_come(i, j, 1, -1, board, t2) == true) {

            dama = (Math.abs(board.getBoard()[i + player][j - 1]));

            moves.addElement(new Pair(i, j));
            explora_jogada_peca_comendo(player, i + 2 * player, j - 2, t2,
                    moves, moveList);
            nelem = moves.size() - 1;
            moves.removeElementAt(nelem);
            moveu = true;
        }

        if (moveu == true)
            return;

        move = new Vector<Pair>();
        move = (Vector) moves.clone();
        moveList.getMoves().addElement(move);

        // coloca elemento que designa a estabilidade desta jogada
        moveList.getInstavel().addElement(new Boolean(true));
        nelem = moves.size() - 1;
        moves.removeElementAt(nelem);
        return;
    }

    boolean p_come(Integer x, Integer y, Integer dirx, Integer diry, Board tabuleiro1,
                   Board tabuleiro2) {

        int wx, wy, positivo;

        positivo = tabuleiro1.getBoard()[x][y];

        if (positivo < 0) {
            dirx = -dirx;
        }

        wx = x + dirx + dirx;
        wy = y + diry + diry;

        if (wx > 7 || wx < 0 || wy > 7 || wy < 0)
            return false;

        if (tabuleiro1.getBoard()[wx][wy] != 0)
            return false;/* casa destino tem que estar vazia */

        if ((positivo * tabuleiro1.getBoard()[x + dirx][y + diry]) >= 0)
            return false; /* está vazia ou tem uma peça da mesma cor */

        tabuleiro2.getBoard()[wx][wy] = tabuleiro2.getBoard()[x][y];
        tabuleiro2.getBoard()[x + dirx][y + diry] = 0;
        tabuleiro2.getBoard()[x][y] = 0;

        if (positivo > 0 && wy == 7)
            tabuleiro2.getBoard()[wx][wy] *= 2;

        if (positivo < 0 && wy == 0)
            tabuleiro2.getBoard()[wx][wy] *= 2;

        return true;

    }

    boolean p_move(int x, int y, int dirx, int diry, Board tabuleiro1) {

        int positivo, wx, wy;

        positivo = tabuleiro1.getBoard()[x][y];

        if (positivo < 0)
            dirx = -dirx;

        wx = x + dirx;
        wy = y + diry;

        if (wx > 7 || wx < 0 || wy > 7 || wy < 0)
            return false;

        if (tabuleiro1.getBoard()[wx][wy] != 0)
            return false;

        return true;

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
                if (visibleBoard.getBoard()[j][i] == 1) {
                    drawChecker(i, j, "Black", graphics2D);
                } else if (visibleBoard.getBoard()[j][i] == 2) {
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

        if (color.equals("Red")) {
            graphics.setColor(new Color(134, 12, 38));
        } else {
            graphics.setColor(new Color(20, 20, 27));
        }

        graphics.fillOval(x * m + m / 2 - pieceX / 2, y * m + m / 2 - pieceY / 2, pieceX, pieceY);

        if (color.equals("Red")) {
            graphics.setColor(new Color(20, 20, 27));
        } else {
            graphics.setColor(new Color(134, 12, 38));
        }

        graphics.drawArc(x * m + m / 2 - pieceX / 2, y * m + m / 2 - pieceY / 2, pieceX,
                pieceY, 0, -180);
        graphics.drawArc(x * m + m / 2 - pieceX / 2, y * m + m / 2 - pieceY / 2 + 1,
                pieceX, pieceY, 0, -180);
    }
}
