@SuppressWarnings("all")

public class IncompatibleNumberException extends Exception
{
	private final String errorMessage;
	
	public IncompatibleNumberException(String message)
	{
		errorMessage = message;
	}
	
	public String getError()
	{
		return errorMessage;
	}

}
