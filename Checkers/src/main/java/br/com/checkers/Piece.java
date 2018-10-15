package br.com.checkers;
import java.awt.*;


public class Piece {
	
	private int row;
	
	private int col;
	
	public Color color;
	
	
	public Piece(Color c, int row, int col) {

		color = c;
		this.row = row;
		this.col = col;
		
	}
	
	public int getRow() {
		return row;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getCol() {
		return col;
	}
	
	public void setLoc(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public String toString() {
		
		StringBuilder s = new StringBuilder();
		
		if(this.color == Color.BLACK)
			s.append("Black ");
		
		else
			s.append("Red ");
		
		s.append("piece at row " + Integer.toString(this.getRow()) + 
				 ", col " + Integer.toString(this.getCol()));
		
		return s.toString();
	}

}
