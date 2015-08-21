import java.io.*;
import java.util.ArrayList;

/**
 * Various useful utility methods to shorten code by factoring it to here
 * @author Stephen
 *
 */

public class SP_Lib {

	/**
	 * Writes a string to a file
	 * @param file File to write with
	 * @param data Data to write to the file
	 */
	public static void writeToFile(String file, String data)
	{
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			pw.println(data);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the contents of a file
	 * @param file File to read from
	 * @return String of all data read from the file
	 * @throws FileNotFoundException
	 */
	public static String readFromFile(String file) throws FileNotFoundException
	{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line, returnString = new String();
			
			try {
				while((line = reader.readLine()) != null)
					returnString = returnString + line;
				reader.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			return returnString;
			
	}
	
	/**
	 * Logs an exception to the log
	 * @param t Exception to log
	 */
	public static void log(Throwable t) 
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String trace = sw.toString();

		try{
			FileWriter f = new FileWriter("log.txt", true);
			f.append(trace);
			f.close();
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println("Could not create log.txt! Check file permissions.");
		}
		catch(java.io.IOException e)
		{
			//oh well...
		}
	}

	/**
	 * Logs an exception to a file other than the log
	 * @param t Exception to log
	 * @param file File to log to
	 */
	public static void log(Throwable t, String file) //logs a stack trace to a file
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String trace = sw.toString();

		try
		{
			FileWriter f = new FileWriter(file, true);
			f.append(trace);
			f.close();
		}
		catch(java.io.FileNotFoundException e)
		{
			System.out.println("Could not create " + file + ".txt! Check file permissions.");
		}
		catch(java.io.IOException e)
		{
			//oh well...
		}
	}

	/**
	 * Clears a file
	 * @param file File to clear
	 */
	public static void clearFile(String file)
	{
		try
		{
			FileWriter f = new FileWriter(file, false);
			f.write("");
			f.close();
		}
		catch(java.io.IOException e)
		{
			//oh well..
		}
	}

	/**
	 * Gets string input from the console
	 * @param message Message to give the user
	 * @return User input
	 */
	public static String inputString(String message)
	{
		boolean control = true;
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader input = new BufferedReader(reader);


		do{
			control = false;
			System.out.print(message);

			try{
				return input.readLine();
			}

			catch(IOException e)
			{
				System.out.println("Input error! Try again.");
				control = true;
			}
		}
		while(control == true);

		return null;
	}

	/**
	 * Gets integer input from the console
	 * @param message Message to give the user
	 * @return User input
	 */
	public static int inputNumber(String message) //basic number input method; contains exceptions
	{
		boolean control = true;

		do{
			control = false;
			try{
				String stringRep = inputString(message);
				return Integer.parseInt(stringRep);
			}

			catch(NumberFormatException e)
			{
				System.out.println("Invalid number.\n");
				control = true;
				SP_Lib.log(e);
			}
		}
		while(control == true);

		return 0;
	}
	
	/**
	 * Writes 1 level XML to a file
	 * @param keys Keys to write 
	 * @param values Values for the keys
	 * @param fileName File to write to
	 * @throws FileNotFoundException
	 */
	public static void writeXML(ArrayList<String> keys, ArrayList<String> values, String fileName) throws FileNotFoundException
	{
		PrintWriter writer = new PrintWriter(fileName);
				
		for(int x = 0; x < keys.size(); x++)
		{
			writer.println("<" + keys.get(x) + ">" + values.get(x) + "</" + keys.get(x) + ">");
		}
		
		writer.close();
	}
	
}
