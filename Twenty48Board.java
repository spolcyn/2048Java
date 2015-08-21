import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Board extension that gives specific functionality for the game of 2048
 * @author Stephen
 *
 */

public class Twenty48Board extends Board 
{	
	private boolean movementOccured;
	private int highScore;
	public final int MAX_TILE = 2048;
	
	public Twenty48Board(int row, int column)
	{
		//create the board with Twenty48Tiles
		super(new Twenty48Tile[row][column]);

		try{

			//initialize all the tile objects with their location
			for(int rows = 0; rows < row; rows++)
				for(int cols = 0; cols < column; cols++)
					getBoard()[rows][cols] = new Twenty48Tile(new Location(rows, cols), 0);

			//create the 2 initial tiles w/ values
			int r = (int)(Math.random() * 4);
			int c = (int)(Math.random() * 4);

			getBoard()[r][c] = new Twenty48Tile(new Location(r, c));

			//loop through until finding a tile that has a value of 0
			while(getBoard()[r][c].getValue() != 0)
			{
				r = (int)(Math.random() * 4);
				c = (int)(Math.random() * 4);
			}

			getBoard()[r][c] = new Twenty48Tile(new Location(r, c));

			//init the score object
			setScore(new Integer(0));
		}

		catch(IncompatibleNumberException e)
		{
			System.out.println("Send help");
		}
	}
	
	public int getHighScore()
	{
		return highScore;
	}
	
	public void setHighScore(int hS)
	{
		highScore = hS;
	}

	/**
	 * Saves the game board to an XML-like file
	 */
	public void saveToXML()
	{
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		String fileName = "save.xml";

		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
				keys.add("row" + row + "col" + col);
		}

		for(int row = 0; row < 4; row++)
		{
			for(int col = 0; col < 4; col++)
				values.add(Integer.toString(get(row, col).getValue()));
		}
		
		keys.add("score");
		values.add("" + getScore());

		try {
			SP_Lib.writeXML(keys, values, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the game board from the XML-like format used above
	 * @throws FileNotFoundException If the save file isn't found
	 */
	public void readFromXML() throws FileNotFoundException
	{
		Twenty48Tile[][] board = new Twenty48Tile[4][4];
		BufferedReader reader = new BufferedReader(new FileReader("save.xml"));
		String current = new String();

		try {
			int row, col, value;
			while((current = reader.readLine()) != null && current.indexOf("score") == -1)
			{
				row = Character.getNumericValue(current.charAt(current.indexOf("row") + 3));
				col = Character.getNumericValue(current.charAt(current.indexOf("col") + 3));
				value = Integer.parseInt(current.substring(current.indexOf(">") + 1, current.indexOf("</")));
								
				try 
				{
					board[row][col] = new Twenty48Tile(new Location(row, col), value);
				} 
				catch (IncompatibleNumberException e) {
					e.printStackTrace();
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		setScore(Integer.parseInt(current.substring(current.indexOf(">") + 1, current.indexOf("</"))));
		
		setBoard(board);
	}

	public Twenty48Tile get(Location loc)
	{
		return (Twenty48Tile)super.get(loc);
	}

	/**
	 * Increments the score of a given tile
	 * @param x Amount to increment by
	 */
	public void incrementScore(int x)
	{
		setScore((Integer)getScore() + x);
		if((Integer)getScore() > highScore)
		{
			highScore = (Integer)getScore();
		}
	}

	/**
	 * Moves all the tiles in the board in a given direction
	 * Also adds a new tile after movement is completed, and checks if the end game is reached
	 * @param dir Direction to move tiles
	 */
	public void moveBoard(Direction dir)
	{		
		movementOccured = false;
		
		switch(dir)
		{
		case UP:
		{
			for(int col = 0; col < 4; col++)
			{
				pushTile(new Location(3, col), dir);
			}
			break;
		}
		case DOWN:
		{
			for(int col = 0; col < 4; col++)
			{
				pushTile(new Location(0, col), dir);
			}
			break;
		}
		case RIGHT:
		{
			for(int row = 0; row < 4; row++)
			{
				pushTile(new Location(row, 0), dir);
			}
			break;
		}
		case LEFT:
		{
			for(int row = 0; row < 4; row++)
			{
				pushTile(new Location(row, 3), dir);
			}
			break;
		}
		}

		reset();
		
		if(movementOccured)
		{
			addTile(Types.RANDOM);
		}
		if(!checkForMoves())
			endGame();
		
	}

	/**
	 * Resets the merged status of all tiles to TRUE
	 */
	public void reset()
	{
		for(int r = 0; r < 4; r++)
			for(int c = 0; c < 4; c++)
				get(new Location(r,c)).setCanBeMerged(true);
	}

	/**
	 * Pushes tiles in a given direction, recursively
	 * @param loc Location to push
	 * @param dir Direction to push in
	 */
	public void pushTile(Location loc, Direction dir)
	{
		if(loc == null) //base case 1 + checks for invalid input
			return;

		Location moveTo = getLocationInDirection(loc, dir); //location being moved into

		if(moveTo == null) //main base case
			return;

		pushTile(moveTo, dir); //push all the tiles next to the current one away

		//merge tiles if necessary
		if(get(moveTo).getValue() == get(loc).getValue() && get(loc).getValue() != 0 && get(moveTo).canBeMerged() && get(loc).canBeMerged())
		{
			get(moveTo).increment();
			incrementScore(get(moveTo).getValue());
			set(new Twenty48Tile(loc, Types.EMPTY), loc);
			get(moveTo).setCanBeMerged(false);			
			pushTile(loc, dir);
			
			movementOccured = true;
			
		}
		else if(get(moveTo).getValue() == 0 && get(loc).getValue() != 0) //move tile if space available
		{			
			moveTile(loc, moveTo);	
			movementOccured = true;
		}
		
		//push current tile into newly freed space
		pushTile(moveTo, dir);



	}

	/**
	 * Adds a tile to the board
	 * @param t Type of addition
	 * @return Success of tile adding
	 */
	public boolean addTile(Types t)
	{
		switch(t)
		{

		case RANDOM: //add a tile in a random empty location on the board
		{
			ArrayList<Location> empty = getEmptyLocations();

			if(empty.size() == 0)
			{	
				if(!checkForMoves())
					endGame();
				return false;
			}

			int r = (int)(Math.random() * empty.size());

			set(new Twenty48Tile(empty.get(r)), empty.get(r));

			return true;
		}

		default:
			return true;
		}
	}

	/**
	 * Checks to see if there are any valid moves left on the board
	 * @return If there are valid moves left on the board
	 */
	public boolean checkForMoves()
	{	
		if(getEmptyLocations().size() > 0)
			return true;
		
		for(int x = 0; x < 4; x++)
			for(int y = 0; y < 4; y++)
				if(canMergeAround(new Location(x, y)))
					return true;

		return false;

	}

	/**
	 * Checks if a given tile can merged with the tiles around it
	 * @param l Location of tile to check neighbors
	 * @return Whether the tile can merge with any of its neighbors
	 */
	public boolean canMergeAround(Location l)
	{
		ArrayList<Location> adjacent = getAdjacentLocations(l);

		for(int x = 0; x < adjacent.size(); x++)
			if(get(adjacent.get(x)).getValue() == get(l).getValue())
				return true;

		return false;
	}
	
	/**
	 * End the game
	 */
	public void endGame()
	{
		Display.updateBoard();
		Display.endGame(false);
	}
}
