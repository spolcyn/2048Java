/**
 * Allows easy use of locations in a grid environment
 * @author Stephen
 *
 */

public class Location {
	
	private int row; //row
	private int column; //column
	
	public Location(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
	
	public Location()
	{
		
	}
	
	public int getRow()
	{
		return row;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}
	
	public int getColumn()
	{
		return column;
	}
	
	public void setColumn(int column)
	{
		this.column = column;
	}
	
	public String toString()
	{
		return "(" + row + ", " + column + ")";
	}

}
