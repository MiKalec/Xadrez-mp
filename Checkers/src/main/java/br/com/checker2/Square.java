package br.com.checker2;

import java.awt.*;


@SuppressWarnings("serial")
public class Square extends Canvas {
	
    public enum BackgroundColor { LIGHT, DARK };

	private BackgroundColor bgColor;
    
    private boolean occupied;
    
    private Piece occupant;

    private int row;
    
    private int col;

    
    public Square(BackgroundColor c, int myrow, int mycol) {
    	
    	this.setSize(64, 64);
    	
    	if(c == BackgroundColor.DARK)
    		this.setBackground(Color.DARK_GRAY);
    	else
    		this.setBackground(Color.LIGHT_GRAY);
    	
        bgColor = c;
        occupied = false;
        occupant = null;
        
        this.row = myrow;
        this.col = mycol;
        
    }

    public boolean isOccupied() {
        return this.occupied;
    }
    
    public int getRow() {
    	return this.row;
    }
    
    public int getCol() {
    	return this.col;
    }
    
    public BackgroundColor getBackgroundColor() {
    	return this.bgColor;
    }
    
    public Piece getOccupant() {
    	if(this.isOccupied())
    		return this.occupant;
    	
    	return null;
    }
    
    public void setHighlight(boolean doHighlight) {
    	
    	
    	Graphics g = this.getGraphics();
    	
		if(doHighlight) {

	    	if(!this.isOccupied()) {
	    		
	    		g.setColor(Color.BLACK);
	    	
	    		//Draw a dotted oval where this piece may land
	    		for(int i = 0; i < 360; i+= 30)
	    			g.drawArc(5, 5, 54, 54, i, 15);
	    	}
	    	
	    	else {
	    		//Draw a yellow rect around the border of this Square 
	    		g.setColor(Color.YELLOW);
	    		g.draw3DRect(0, 0, 63, 63, false);
	    		
	    	}
    	}
    	else
    		//If this square has a highlight in it, erase it by redrawing the Square
    		super.update(this.getGraphics());
    	
    }

    public void setOccupant(Piece visitor) {
    	if(visitor != null) {
    		
    		this.occupant = visitor;
    		this.occupied = true;
    		
    	}
    	
    	else {
    		
    		this.occupant = null;
    		this.occupied = false;
    		
    	}
    }

    @Override
	public void paint(Graphics g) {
		
		//Set the Canvas' background color equal to the Square's bgcolor
		if(this.getBackgroundColor() == BackgroundColor.DARK)
			this.setBackground(Color.DARK_GRAY);
		
		else
			this.setBackground(Color.LIGHT_GRAY);
		
		//Either draw a square or clear the rectangle
		if(this.isOccupied()) {
			
			g.setColor(occupant.getColor());
			g.fillOval(5, 5, 54, 54);
			

		}
		
		else
			g.clearRect(0, 0, 64, 64);
			
	}

}
