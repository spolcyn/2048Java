/**
 * Twenty 48 Tile, only allowing values that are multiples of 2, and enabling random value generation
 * Also holds a merging property, telling the board whether or not it can be merged in a given movement
 * @author Stephen
 *
 */
public class Twenty48Tile extends Tile
{	
	private boolean canBeMerged;
	
	public Twenty48Tile(Location loc)
	{
		super(loc, (int)(Math.ceil(Math.random() * 2)) % 2 * 2 + 2);
		canBeMerged = true;
	}

	public Twenty48Tile(Location loc, int value) throws IncompatibleNumberException
	{
		super(loc);
		setValue(value);
		canBeMerged = true;
	}
	
	public Twenty48Tile(Location loc, Types t)
	{
		super(loc);

		if(t == Types.EMPTY)
		{
			try { setValue(0); } catch(IncompatibleNumberException e) { /*.. won't happen*/ };
		}
		canBeMerged = true;

	}

	public Twenty48Tile(Twenty48Tile movingTo, Twenty48Tile moving)
	{
		super(movingTo.getLocation());

		try
		{
			setValue(movingTo.getValue() + moving.getValue());
		}
		catch(IncompatibleNumberException e)
		{
			//can't happen
		}
		canBeMerged = true;

	}
	
	public void increment()
	{
				
		try{
		setValue(getValue()*2); 
		}
		catch(IncompatibleNumberException e)
		{
			System.out.println("Oh nein bad value");
		}
	}

	public void setValue(int v) throws IncompatibleNumberException
	{
		if(v % 2 != 0)
			throw new IncompatibleNumberException("Number not a multiple of 2!");
		else
			super.setValue(v);
	}
	
	public boolean canBeMerged()
	{
		return canBeMerged;
	}
	
	public void setCanBeMerged(boolean b)
	{
		canBeMerged = b;
	}

	public String toString()
	{
		if(getValue() == 0)
			return " ";
		else
			return Integer.toString(getValue());
	}


}

