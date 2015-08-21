/**
 * Basic tile holding an integer value and a location
 * @author Stephen
 *
 */

public class Tile {
	
	private int value;
	private Location location;
	
	public Tile(Location loc)
	{
		location = loc;
	}
	
	public Tile(Location loc, int val)
	{
		value = val;
		location = loc;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int num) throws IncompatibleNumberException
	{
		value = num;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public void setLocation(Location loc)
	{
		location = loc;
	}
	
	public String toString()
	{
		return Integer.toString(value);
	}


}
