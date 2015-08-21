import java.util.ArrayList;

/**
 * Board for a grid based game
 * @author Stephen
 *
 */

public abstract class Board {

	private Tile gameBoard[][]; //2-D grid board
	private Object score; //score object; can be specialized based on game

	public Board(Tile[][] board)
	{
		gameBoard = board;
	}

	public Board(int rows, int columns)
	{
		gameBoard = new Tile[rows][columns];
	}

	public Tile[][] getBoard()
	{
		return gameBoard;
	}

	public void setBoard(Tile[][] board)
	{
		gameBoard = board;
	}

	public Object getScore()
	{
		return score;
	}

	public void setScore(Object s)
	{
		score = s;
	}

	//clears the log for the board
	public void clearLog() 
	{
		SP_Lib.clearFile("log.txt");
	}

	/**
	 * Returns the location directly adjacent to the specified location in a given direction
	 * @param loc Source location
	 * @param dir Direction to look in
	 * @return Adjacent location in the specified direction
	 */
	public Location getLocationInDirection(Location loc, Direction dir)
	{		
		int row = loc.getRow();
		int column = loc.getColumn();

		switch(dir)
		{
		case UP:
			row -= 1;
			break;
		case DOWN:
			row += 1;
			break;
		case RIGHT:
			column += 1;
			break;
		case LEFT:
			column -= 1;
			break;
		default:
			System.out.println("Default case!?");
		}
		
		Location returnL = new Location(row, column);
		
		if(isValid(returnL))
			return returnL;
		else
			return null;
	}

	/**
	 * Checks if a location is valid in the current board
	 * @param l Location to check validity of
	 * @return Whether location is valid or not
	 */
	public boolean isValid(Location l)
	{
		if(l.getRow() >= 0 && l.getRow() < gameBoard.length)
			if(l.getColumn() >= 0 && l.getColumn() < gameBoard[0].length)
				return true;

		return false;
	}

	/**
	 * Gets a tile from a given location
	 * @param loc Location to retrieve from
	 * @return Tile at the location
	 */
	public Tile get(Location loc)
	{
		if(isValid(loc))
			return gameBoard[loc.getRow()][loc.getColumn()];
		else
			return null;
	}
	
	public Tile get(int row, int col)
	{
		return get(new Location(row, col));
	}
	
	/**
	 * Moves a tile from one location to another
	 * @param t Tile to move
	 * @param l Location to move the tile to
	 */
	public void moveTile(Tile t, Location l)
	{
		gameBoard[t.getLocation().getRow()][t.getLocation().getColumn()] = new Twenty48Tile(t.getLocation(), Types.EMPTY);
		t.setLocation(l);
		gameBoard[l.getRow()][l.getColumn()] = t;
	}
	
	/**
	 * Moves the tile at a location to another location
	 * @param from Location to move from
	 * @param to Location to move to
	 */
	public void moveTile(Location from, Location to)
	{
		moveTile(get(from), to);
	}
	
	/**
	 * Sets a tile at a given location, and updates its location
	 * @param t Tile to set
	 * @param l Location to set at
	 */
	public void set(Tile t, Location l)
	{
		if(isValid(l))
		{
			gameBoard[l.getRow()][l.getColumn()] = t;
			t.setLocation(l);
		}
	}
	
	/**
	 * Gets all empty locations in a grid
	 * @return All the empty locations in the grid
	 */
	public ArrayList<Location> getEmptyLocations()
	{
		ArrayList<Location> l = new ArrayList<Location>();
		
		for(int r = 0; r < gameBoard.length; r++)
			for(int c = 0; c < gameBoard[0].length; c++)
				if(gameBoard[r][c].getValue() == 0)
					l.add(new Location(r,c));
		
		return l;
			
	}
	
	/**
	 * Gets the locations surrounding a given grid point
	 * @param loc Location to get adjacent locations around
	 * @return All valid adjacent locations
	 */
	public ArrayList<Location> getAdjacentLocations(Location loc)
	{
		ArrayList<Location> returnA = new ArrayList<Location>();
		Location temp;
		
		for(int x = 0; x < 4; x++)
		{
			temp = getLocationInDirection(loc, Direction.values()[x]);
			
			if(temp != null)
				if(isValid(temp))
					returnA.add(temp);
		}
		
		return returnA;
	}
	
	/**
	 * Prints out the board and the score to the console; mainly for debug purposes, but can be used in a text-based game
	 */
	public String toString()
	{
		String output = new String();
		String temp;
		
		output += "Score: " + getScore().toString() + "\n";

		for(int row = 0; row < gameBoard.length; row++)
		{
			for(int col = 0; col < gameBoard[0].length; col++)
			{
				temp = (gameBoard[row][col]) + "   ";
				output += "|" + temp.substring(0, 4);
			}

			output += "|\n";
		}

		return output;
	}

}
