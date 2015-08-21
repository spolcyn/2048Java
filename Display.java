import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

/**
 * Class to provide a GUI for the underlying board
 * @author Stephen
 *
 */

public class Display 
{
	private static JFrame mainFrame;
	private static JPanel mainContainer;
	private static JLabel score;
	private static JPanel boardContainer;
	private static JLabel[] tiles;
	private static Twenty48Board gameBoard;
	private static JLabel highScore;
 
	//getters and setters
	public JFrame getMainFrame()
	{
		return mainFrame;
	}

	public JLabel getScore()
	{
		return score;
	}

	public static JPanel getBoardContainer()
	{
		return boardContainer;
	}

	public void setScore(JLabel jl)
	{
		score = jl;
	}

	public JPanel getMainContainer()
	{
		return mainContainer;
	}
	
	public static Twenty48Board getBoard()
	{
		return gameBoard;
	}
	
	//centers a jcomponent in another, both vertically and horizontally
	public static void center(JComponent toCenter, JComponent centerIn)
	{
		toCenter.setLocation((int)((centerIn.getSize().getWidth() - toCenter.getWidth()) / 2), (int)((centerIn.getSize().getHeight() - toCenter.getHeight()) / 2));
	}
	
	/**
	 * centers a jcomponent vertically in another
	 * @param toCenter Component to center
	 * @param centerIn Component in which to center
	 * @param heightInset Pixels to put the newly centered component from the top
	 */
	public static void verticalCenter(JComponent toCenter, JComponent centerIn, int heightInset)
	{
		toCenter.setLocation((int)((centerIn.getSize().getWidth() - toCenter.getWidth()) / 2), heightInset);

	}
	
	/**
	 * Updates the board, both the tile values and the tile colors; also checks if the game is over (max_tile reached)
	 */
	public static void updateBoard()
	{
		boolean endGame = false;
		
		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				if(gameBoard.get(row, col).getValue() != 0)
				{
					if(gameBoard.get(row, col).getValue() > 99)
					{
						if(gameBoard.get(row, col).getValue() > 999)
						{
							tiles[row * 4 + col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
							if(gameBoard.get(row, col).getValue() == gameBoard.MAX_TILE)
								endGame = true;
						}
						else
							tiles[row * 4 + col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
					}
					else
						tiles[row * 4 + col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 55));

					
					tiles[row * 4 + col].setText(Integer.toString(gameBoard.get(row, col).getValue()));
					updateTileColor(gameBoard.get(row, col).getValue(), tiles[row * 4 + col]);
				}
				else
				{
					tiles[row * 4 + col].setText("");
					tiles[row * 4 + col].setBackground(new Color(201, 192, 180));
				}
			}
		}
		
		if(endGame)
			endGame(endGame);
		
		updateScore();
	}
	
	/**
	 * Ends the game, checking for restart, resetting the board, and storing the high score
	 * @param won Whether the player won or lost
	 */
	public static void endGame(boolean won)
	{
		String wonorlost;
		
		if(won)
			wonorlost = "won";
		else
			wonorlost = "lost";
		
		int result = JOptionPane.showConfirmDialog(null, "You " + wonorlost + ". Would you like to restart? ", "Game Over", JOptionPane.YES_NO_OPTION);
		
		resetBoard(result);
	}
	
	/**
	 * Resets the board to the beginning and logs the highscore
	 * @param result Whether to make a new board or quit
	 */
	public static void resetBoard(int result)
	{	
		try {
			
			String line = SP_Lib.readFromFile("highscore.txt");
			
			if(Integer.parseInt(line) < ((Integer)gameBoard.getScore()).intValue())
					SP_Lib.writeToFile("highscore.txt", gameBoard.getScore().toString());

			}

		catch (FileNotFoundException e) {
			e.printStackTrace();
			SP_Lib.writeToFile("highscore.txt", gameBoard.getScore().toString());
		}
		
		if(result == JOptionPane.YES_OPTION)
		{
			gameBoard = new Twenty48Board(4, 4);
			updateBoard();
		}
		else
			System.exit(JFrame.EXIT_ON_CLOSE);
		
		try {
			gameBoard.setHighScore(Integer.parseInt(SP_Lib.readFromFile("highscore.txt")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		updateHighScore();
	}
	
	/**
	 * Updates the color of a tile/tile's text based on its value
	 * @param value Value of the tile
	 * @param label Reference to the label whose color is being changed
	 */
	public static void updateTileColor(int value, JLabel label)
	{
		switch(value)
		{
		case 2:
			label.setBackground(new Color(235, 227, 217));
			label.setForeground(Color.decode("#776e65"));
			break;
		case 4:
			label.setBackground(new Color(233, 223, 200));
			label.setForeground(Color.decode("#776e65"));
			break;
		case 8:
			label.setBackground(new Color(225, 175, 123));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 16:
			label.setBackground(new Color(222, 148, 101));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 32:
			label.setBackground(new Color(219, 124, 96));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 64:
			label.setBackground(new Color(215, 94, 63));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 128:
			label.setBackground(new Color(228, 206, 119));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 256:
			label.setBackground(new Color(227, 203, 105));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 512:
			label.setBackground(new Color(226, 199, 90));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 1024:
			label.setBackground(new Color(226, 196, 77));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 2048:
			label.setBackground(new Color(225, 193, 66));
			label.setForeground(new Color(255, 255, 255));
			break;
		case 4096:
			label.setBackground(new Color(0, 0, 255));
			label.setForeground(new Color(255, 255, 255));
			break;
		}
	}
	
	/**
	 * Updates the shown score to that held by the gameboard. Also updates the highscore.
	 */
	public static void updateScore()
	{
		score.setText("Score: " + gameBoard.getScore().toString());
		if((Integer)gameBoard.getScore() > gameBoard.getHighScore())
		{
			gameBoard.setHighScore((Integer)gameBoard.getScore());
		}
		updateHighScore();

	}
	
	/**
	 * Updates the shown high score to that held by the gameboard
	 */
	public static void updateHighScore()
	{
		highScore.setText("High score: " + gameBoard.getHighScore());
	}

	public static void main(String args[])
	{
		//create the gameboard and necessary GUI Objects
		gameBoard = new Twenty48Board(4, 4);

		mainFrame = new JFrame("2048");
		score = new JLabel("Score: " + gameBoard.getScore(), JLabel.CENTER);
		mainContainer = new JPanel();
		boardContainer = new JPanel(new GridLayout(4, 4, 15, 15));
		boardContainer.setBorder(BorderFactory.createLineBorder(new Color(182, 171, 159), 15));
		tiles = new JLabel[16];
		highScore = new JLabel("", JLabel.CENTER);
		
		//read in the highscore
		try {
			gameBoard.setHighScore(Integer.parseInt(SP_Lib.readFromFile("highscore.txt")));
		} catch (NumberFormatException e2) {
			e2.printStackTrace();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		@SuppressWarnings("serial")
		
		//actions for the key presses - up, down, left, right
		Action upAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				gameBoard.moveBoard(Direction.UP);
				updateBoard();
			}
		};
		
		@SuppressWarnings("serial")

		Action downAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				gameBoard.moveBoard(Direction.DOWN);
				updateBoard();
			}
		};
		
		@SuppressWarnings("serial")

		Action leftAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				gameBoard.moveBoard(Direction.LEFT);
				updateBoard();
			}
		};
		
		@SuppressWarnings("serial")

		Action rightAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				gameBoard.moveBoard(Direction.RIGHT);
				updateBoard();
			}
		};
		
		@SuppressWarnings("serial")

		//action for saving the game
		Action saveAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				gameBoard.saveToXML();
				JOptionPane.showMessageDialog(null, "Save Successful", "Save", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		
		@SuppressWarnings("serial")

		//action for loading a game
		Action loadAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				try {
					gameBoard.readFromXML();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				updateBoard();
				JOptionPane.showMessageDialog(null, "Load Successful", "Load", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		
		@SuppressWarnings("serial")
		
		//action for resetting the board
		Action resetAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e)
			{
				resetBoard(JOptionPane.YES_OPTION);
			}
		};
		
		//mapping keys to the frame for commands
		mainContainer.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
		mainContainer.getActionMap().put("up", upAction);
		mainContainer.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
		mainContainer.getActionMap().put("down", downAction);
		mainContainer.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
		mainContainer.getActionMap().put("right", rightAction);
		mainContainer.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
		mainContainer.getActionMap().put("left", leftAction);
		mainContainer.getInputMap().put(KeyStroke.getKeyStroke("S"), "save");
		mainContainer.getActionMap().put("save", saveAction);
		mainContainer.getInputMap().put(KeyStroke.getKeyStroke("L"), "load");
		mainContainer.getActionMap().put("load", loadAction);
		mainContainer.getInputMap().put(KeyStroke.getKeyStroke("R"), "reset");
		mainContainer.getActionMap().put("reset", resetAction);

		
		JLabel temp;
		
		//create the tiles with their initial colors, fonts, and font colors
		for(int x = 0; x < 16; x++)
		{
				temp = new JLabel();
				
				temp.setBackground(new Color(201, 192, 180));
				temp.setForeground(Color.decode("#776e65"));
				temp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 55));
				temp.setOpaque(true);
				temp.setBackground(new Color(235, 227, 218));
				temp.setHorizontalAlignment(JLabel.CENTER);
				
				boardContainer.add(temp);
				tiles[x] = temp;
		}

		//setting look and feel of the GUI
		highScore.setOpaque(true);
		highScore.setForeground(Color.WHITE);
		highScore.setSize(200, 50);
		highScore.setBackground(new Color(182, 172, 160));
		
		score.setOpaque(true);
		score.setForeground(Color.WHITE);

		//set size, center the frame on the screen, and make the x button quit the program
		mainFrame.setSize(600, 720);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainContainer.setLayout(null);

		mainContainer.setSize(600, 720);
		boardContainer.setSize(500, 500);
		score.setSize(200, 50);
		
		verticalCenter(score, mainContainer, 20);
		verticalCenter(boardContainer, mainContainer, 170);
		verticalCenter(highScore, mainContainer, 70);

		mainContainer.setBackground(new Color(249, 248, 239));
		boardContainer.setBackground(new Color(182, 171, 159));
		score.setBackground(new Color(182, 172, 160));

		mainFrame.add(mainContainer);
		mainContainer.add(boardContainer);
		mainContainer.add(score);
		mainContainer.add(highScore);
		
		updateBoard(); //initial gui update
		
		mainFrame.setVisible(true); //begin

	}
}
