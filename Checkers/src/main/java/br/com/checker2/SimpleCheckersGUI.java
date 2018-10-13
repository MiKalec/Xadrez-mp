package br.com.checker2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;


public class SimpleCheckersGUI implements MouseListener,
									ActionListener {
	
	private JFrame frame;
	
	private JPanel boardpanel;
	
	private JLabel piecesLabel;
	
	private JMenuBar menubar;
	
	private JMenu fileMenu;
	
	private JMenuItem newGame;
	
	private JMenuItem exit;
	
	private Color currentTurn;
	
	private final int borderWidth = 1;
	
	private Board board;
	
	private int blackCheckersLeft;
	
	private int redCheckersLeft;

	private Square selectedSquare;
	
	
	
	public SimpleCheckersGUI() {
		
		//display the interface
		CreateAndShowGUI();
		
		//set the initial turn
		currentTurn = Color.GREEN;
		
		//initial values for checkers
		redCheckersLeft = 12;
		blackCheckersLeft = 12;
		
		//show how many checkers are left
		updateStatus();
		
		//event-driven onward
	}
	
	public void CreateAndShowGUI() {
		
		//Set up the window information
		frame = new JFrame("SimpleCheckersGUI - Matthew Proetsch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new FlowLayout());
		
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		//Give our Board a visual representation
		boardpanel = new JPanel(new GridLayout(8, 8));
		boardpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		board = new Board();
		board.placeStartingPieces();
		
		//Keep track of how many pieces are left
		piecesLabel = new JLabel(" ");
		piecesLabel.setHorizontalTextPosition(JLabel.LEFT);
		piecesLabel.setVerticalTextPosition(JLabel.BOTTOM);
		
		//Add the menubar to the window
		menubar = new JMenuBar();
		fileMenu = new JMenu("File");
		
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(this);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		
		fileMenu.add(newGame);
		fileMenu.add(exit);
		menubar.add(fileMenu);

		//Add our board to boardpanel and add everything to the window
		addBoardToPanel(board, boardpanel);
		frame.add(boardpanel);
		frame.add(piecesLabel);
		frame.setJMenuBar(menubar);
		frame.pack();
		
		//Resize the frame because for some reason it wants to cut off the last character of our JLabel
		Rectangle boundingRect = frame.getBounds();
		frame.setBounds(boundingRect.x, boundingRect.y, boundingRect.width + 5, boundingRect.height);
		
		frame.setVisible(true);


	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		

		Square sel = (Square)e.getComponent();
		
		//Ensure that the correct color Piece has been chosen
		//The Piece's color should be equal to currentTurn, unless this is the first move
		//in which case currentTurn is going to be Color.GREEN
		if(sel.isOccupied()) 
			if(sel.getOccupant().getColor() != currentTurn &&
				currentTurn != Color.GREEN) {
			piecesLabel.setText("Ash! This isn't the time to use that!");
			return;
		}
		
		
		
		/* Now, since we can be sure that the user has clicked on a piece of their own color, there are
		 * only four scenarios:
		 * 1) The user has nothing highlighted and wishes to highlight a new Square
		 * 2) The user wishes to change the Square that is selected
		 * 3) The user wishes to deselect the current Square
		 * 4) The user wishes to perform a jump
		 */
		
		
		if(sel.isOccupied() && selectedSquare == null) {
			//There is currently no square selected, so highlight all possible moves
			selectedSquare = sel;
			selectedSquare.setHighlight(true);
			board.setMovesHighlighted(selectedSquare.getOccupant(), true);
			return;
			
		}
		
		
		
		
		else if(sel.isOccupied() && !sel.equals(selectedSquare)) {
			//The user has clicked on a different Piece than the one currently selected
			selectedSquare.setHighlight(false);
			board.setMovesHighlighted(selectedSquare.getOccupant(), false);
			
			//Reset selectedSquare to the one currently under the cursor
			selectedSquare = sel;
			selectedSquare.setHighlight(true);
			board.setMovesHighlighted(selectedSquare.getOccupant(), true);
			return;
			
		}
		
		
		else if(sel.equals(selectedSquare)) {
			//The user has deselected the current square
			selectedSquare.setHighlight(false);
			board.setMovesHighlighted(selectedSquare.getOccupant(), false);
			selectedSquare = null;
		}
		
		

		//Move-checking code
		else if(!sel.isOccupied() && selectedSquare != null) {
			//The user is trying to make a move by moving from the selectedSquare to the one they just clicked
			//First check to see if their choice corresponds to a square in possibleMoves
			
			boolean found = false;
			boolean jumped = false;
			
			Vector<Square> oldPossibleMoves = board.getPossibleMoves(selectedSquare.getOccupant());
			
			for(Square choice : oldPossibleMoves) {
				if(choice.equals(sel)) {
					
					//Move found in the Vector of possible moves, so perform it
					
					//First, check to see if this was the first move being performed
					if(currentTurn == Color.GREEN)
						currentTurn = selectedSquare.getOccupant().getColor();
					
					//Next, store in a variable whether or not a jump was performed
					jumped = board.move(selectedSquare, sel);
					
					//Finally, indicate internally that a move has been found and performed
					found = true;
					

				}
			}
			

			
			if(found) {
				if(jumped) {
					if(currentTurn == Color.BLACK) {
						redCheckersLeft--;
					}
					else {
						blackCheckersLeft--;
					}
					
				}
				
				//Unhighlight the moves from the Piece's previous position
				selectedSquare.setHighlight(false);
				for (Square unhighlight : oldPossibleMoves)
					unhighlight.setHighlight(false);
				selectedSquare = null;
				
				endTurn();
				//Update the number of checkers left
				updateStatus();
				
				
				//See if that move ended the game
				String winningStr = winner();
				if(winningStr != null) {
					int restart = JOptionPane.showConfirmDialog(null, winningStr + " Do you want to start a new game?", "New Game?", JOptionPane.YES_NO_OPTION);
					
					if(restart == JOptionPane.YES_OPTION)
						restartGame();
					else {
						frame.setVisible(false);
						frame.dispose();
					}
						
				}
			}
			
			else if(!found) 
				//Tell the user the obvious: that they can't move there.
				piecesLabel.setText("Can't let you do that, Dave");
		}
		
		

		

		

		
	}

	//Must implement as per MouseListener
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame) {
			restartGame();
		}
		else if(e.getSource() == exit) {
			frame.setVisible(false);
			frame.dispose();
		}
		
	}
	
	public void addBoardToPanel(Board b, JPanel p) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				Square sq = b.getSquare(i, j);
				sq.addMouseListener(this);
				
				JPanel ContainerPanel = new JPanel(new FlowLayout());
				ContainerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
																					borderWidth));
				ContainerPanel.add(sq);
				if(sq.getBackgroundColor() == Square.BackgroundColor.DARK)
					ContainerPanel.setBackground(Color.DARK_GRAY);
				else
					ContainerPanel.setBackground(Color.LIGHT_GRAY);
				p.add(ContainerPanel);
			}
		}
	}
	
	
	
	public void updateStatus() {
		piecesLabel.setText("Red pieces left: " + redCheckersLeft + "             Black pieces left: " + blackCheckersLeft);
	}
	
	
	public String winner() {
		
		//Check first ending condition: one side loses all pieces
		if(blackCheckersLeft == 0)
			return "Red has won by taking Black's pieces!";
			
		if(redCheckersLeft == 0)
			return "Black has won by taking Red's pieces!";
		
		
		//Check second ending condition: one side cannot move its remaining pieces
		boolean redCanMove = false;
		boolean blackCanMove = false;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				//Get all possible moves for all pieces currently on the board
				if(board.getSquare(i, j).isOccupied()) {
					Vector<Square> potentialMoves = board.getPossibleMoves(board.getSquare(i, j).getOccupant());
					
					if(! potentialMoves.isEmpty()) {
						//The potentialMoves Vector contains at least one square, so that side is capable of making a move
						//Find out what the color of the piece that can make the move is, then set its <color>CanMove var to true
						
						if(board.getSquare(i, j).getOccupant().getColor() == Color.black)
							blackCanMove = true;
						else
							redCanMove = true;
						
					}
				}
			}
		}
		
		if(redCanMove && !blackCanMove) {
			return "Red wins since Black can make no more moves!";
		}
		else if(blackCanMove && !redCanMove) {
			return "Black wins since Red can make no more moves!";
		}
		else if(!redCanMove && !blackCanMove) {
			return "Neither side can make a move!";
		}
		
		//None of the above cases hold true, so the game is not over yet
		return null;
	}
	
	
	public void endTurn() {
		if(currentTurn == Color.BLACK) {
			currentTurn = Color.RED;
		}
		else {
			currentTurn = Color.BLACK;
		}
	}
	
	public void restartGame() {
		
		frame.setVisible(false);
		selectedSquare = null;
		
		frame.remove(boardpanel);
		boardpanel = new JPanel(new GridLayout(8, 8));
		boardpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		board = new Board();
		board.placeStartingPieces();
		
		addBoardToPanel(board, boardpanel);
		frame.add(boardpanel, 0);
		
		redCheckersLeft = 12;
		blackCheckersLeft = 12;
		
		currentTurn = Color.BLACK;
		
		updateStatus();
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		new SimpleCheckersGUI();
	}

	

}
