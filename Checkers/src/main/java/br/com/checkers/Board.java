package br.com.checkers;

import java.awt.*;
import java.util.Vector;


public class Board {

    public static final int rows = 8;
    public static final int cols = 8;
    private Square[][] gameBoard;


    public Board() {


        gameBoard = new Square[rows][cols];

        //Set up the game board with alternating colors
        boolean lastcolor = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                if (lastcolor)
                    gameBoard[i][j] = new Square(Square.BackgroundColor.DARK, i, j);
                else
                    gameBoard[i][j] = new Square(Square.BackgroundColor.LIGHT, i, j);

                //Toggle lastcolor
                lastcolor = !lastcolor;
            }

            //Switch starting color for next row
            lastcolor = !lastcolor;
        }


    }


    public static boolean inBounds(int row, int col) {
        if (row >= 0 && row < rows &&
                col >= 0 && col < cols)

            return true;


        return false;

    }

    public Square getSquare(int row, int col) {
        if (inBounds(row, col))
            return gameBoard[row][col];


        return null;
    }

    public void placeStartingPieces() {

        //Have the Red side on top, Black side on bottom
        //Establish the Red side first
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 8; col++)
                if (getSquare(row, col).getBackgroundColor() == Square.BackgroundColor.DARK)
                    getSquare(row, col).setOccupant(new Piece(Color.RED, row, col));

        //Now establish the Black side
        for (int row = 5; row < 8; row++)
            for (int col = 0; col < 8; col++)
                if (getSquare(row, col).getBackgroundColor() == Square.BackgroundColor.DARK)
                    getSquare(row, col).setOccupant(new Piece(Color.BLACK, row, col));
    }

    public Vector<Square> getPossibleMoves(Piece p) {
        /*Possible moves include up-left, up-right, down-left, down-right
         * This corresponds to (row-- col--), (row-- col++),
         * 						(row++ col--), (row++ col++) respectively
         */

        Vector<Square> possibleMoves = new Vector<Square>();
        Color pColor = p.getColor();

        int row = p.getRow();
        int col = p.getCol();

        //Begin checking which moves are possible, keeping in mind that only black checkers may move up
        //and only red checkers may move downwards

        //Check moves to the top-left of this piece
        if (Board.inBounds(row - 1, col - 1) && pColor == Color.BLACK) {

            if (!this.getSquare(row - 1, col - 1).isOccupied())
                possibleMoves.add(this.getSquare(row - 1, col - 1));

                //if square is occupied, and the color of the Piece in square is
                //not equal to the piece whose moves we are checking, then
                //check to see if we can make the jump by checking
                //the next square in the same direction
            else if (Board.inBounds(row - 2, col - 2))

                if (!this.getSquare(row - 2, col - 2).isOccupied() &&
                        (this.getSquare(row - 1, col - 1).getOccupant().getColor() != pColor))

                    possibleMoves.add(this.getSquare(row - 2, col - 2));

        }

        //Check moves to the top-right of this piece
        if (Board.inBounds(row - 1, col + 1) && pColor == Color.BLACK) {

            if (!this.getSquare(row - 1, col + 1).isOccupied())
                possibleMoves.add(this.getSquare(row - 1, col + 1));

            else if (Board.inBounds(row - 2, col + 2))

                if (!this.getSquare(row - 2, col + 2).isOccupied() &&
                        (this.getSquare(row - 1, col + 1).getOccupant().getColor() != pColor))

                    possibleMoves.add(this.getSquare(row - 2, col + 2));
        }

        //check moves to the bottom-left of this piece
        if (Board.inBounds(row + 1, col - 1) && pColor == Color.RED) {

            if (!this.getSquare(row + 1, col - 1).isOccupied())
                possibleMoves.add(this.getSquare(row + 1, col - 1));


            else if (Board.inBounds(row + 2, col - 2))

                if (!this.getSquare(row + 2, col - 2).isOccupied() &&
                        (this.getSquare(row + 1, col - 1).getOccupant().getColor() != pColor))

                    possibleMoves.add(this.getSquare(row + 2, col - 2));
        }

        //check moves to the bottom-right of this piece
        if (Board.inBounds(row + 1, col + 1) && pColor == Color.RED) {

            if (!this.getSquare(row + 1, col + 1).isOccupied())
                possibleMoves.add(this.getSquare(row + 1, col + 1));

            else if (Board.inBounds(row + 2, col + 2))

                if (!this.getSquare(row + 2, col + 2).isOccupied() &&
                        (this.getSquare(row + 1, col + 1).getOccupant().getColor() != pColor))

                    possibleMoves.add(this.getSquare(row + 2, col + 2));
        }


        return possibleMoves;

    }

    public void setMovesHighlighted(Piece p, boolean doHighlight) {

        Vector<Square> possibleMoves = getPossibleMoves(p);

        if (doHighlight) {
            for (Square highlight : possibleMoves)
                highlight.setHighlight(true);
        } else {
            for (Square highlight : possibleMoves)
                highlight.setHighlight(false);
        }
    }

    public boolean move(Square from, Square to) {
        boolean jumpPerformed = false;

        Piece beingMoved = from.getOccupant();

        int oldRow = from.getRow(), newRow = to.getRow();
        int oldCol = from.getCol(), newCol = to.getCol();

        from.setOccupant(null);
        beingMoved.setLoc(to.getRow(), to.getCol());
        to.setOccupant(beingMoved);

        if (Math.abs(oldRow - newRow) > 1 || Math.abs(oldCol - newCol) > 1) {
            //A jump has been performed, so get the Square that lies between from and to
            int takeRow = (oldRow + newRow) / 2;
            int takeCol = (oldCol + newCol) / 2;

            Square takeSquare = getSquare(takeRow, takeCol);
            takeSquare.setOccupant(null);
            takeSquare.update(takeSquare.getGraphics());

            jumpPerformed = true;

        }

        from.update(from.getGraphics());
        to.update(to.getGraphics());

        return jumpPerformed;


    }

}
