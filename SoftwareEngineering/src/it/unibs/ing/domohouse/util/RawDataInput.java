package it.unibs.ing.domohouse.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Utilizzata nel corso Fondamenti di Programmazione. Autore sconosciuto, utilizzata solo a fini didattici.
 * Classe per la gestione basilare di input da tastiera per leggere interi, double e stringhe con alcuni vincoli.
 */
public class RawDataInput {

	private static Scanner reader = createScanner();
	
	private final static String FORMAT_ERROR = "Attenzione: il dato inserito non e' nel formato corretto";
	private final static String MINIMUM_VALUE_ERROR = "Attenzione: e' richiesto un valore maggiore o uguale a ";
	private final static String VOID_STRING_ERROR = "Attenzione: non hai inserito alcun carattere";
	private final static String MAXIMUM_VALUE_ERROR = "Attenzione: e' richiesto un valore minore o uguale a ";
	private final static String USABLE_CHARACTERS = "Attenzione: i caratteri ammissibili sono: ";
	
	private final static char ANSWER_YES ='S';
	private final static char ANSWER_NO ='N';
	
	private static final int POSITIVE_NUMBER_MINIMUM_VALUE = 1;
	private static final int NON_NEGATIVE_MINIMUM_VALUE = 0;
	
	public static Scanner createScanner() {
		Scanner createdScan = new Scanner(System.in);
		createdScan.useDelimiter(System.getProperty("line.separator"));
		return createdScan;
	}
	
	public static String readString(String message) {
		System.out.println(message);
		return reader.next();
	}
	
	public static String readNotVoidString(String message) {
		boolean endFlag=false;
		String rdString = null;
		do
		{
			rdString = readString(message);
			rdString = rdString.trim();
			if (rdString.length() > 0)
				endFlag=true;
			else
				System.out.println(VOID_STRING_ERROR);
		} 
		while (!endFlag);
		return rdString;
	}
	  
	public static char readChar (String message) {
		boolean endFlag = false;
		char readValue = '\0';
		do
	    {
			String rdString = readString(message);
			if (rdString.length() > 0)
			{
				readValue = rdString.charAt(0);
				endFlag = true;
			}
			else
				System.out.println(VOID_STRING_ERROR);
	    } 
		while (!endFlag);
		return readValue;
	}
	  
	public static char readUpperChar (String message, String usableChars) {
		boolean endFlag = false;
		char readValue = '\0';
		do
		{
			readValue = readChar(message);
			readValue = Character.toUpperCase(readValue);
			if (usableChars.indexOf(readValue) != -1)
				endFlag  = true;
			else
				System.out.println(USABLE_CHARACTERS + usableChars);
		} 
		while (!endFlag);
		return readValue;
	}
	  
	public static int readInt (String message) {
		boolean endflag = false;
		int readValue = 0;
		do
		{
			System.out.print(message);
			try
			{
				readValue = reader.nextInt();
				endflag = true;
			}
			catch (InputMismatchException e)
			{
				System.out.println(FORMAT_ERROR);
				String toEliminate = reader.next(); //To avoid problems with the readings after
			}
		} 
		while (!endflag);
		return readValue;
	}
  
	public static int readIntWithMinimum(String message, int minimumValue)
	{
	   boolean endFlag = false;
	   int readValue = 0;
	   do
	   {
		   readValue = readInt(message);
		   if (readValue >= minimumValue)
			   endFlag = true;
		   else
			   System.out.println(MINIMUM_VALUE_ERROR + minimumValue);
	   } 
	   while (!endFlag);  
	   return readValue;
	}

	public static int readIntWithBounds(String message, int minimumValue, int maximumValue)
	{
		boolean endFlag = false;
		int readValue = 0;
		do
		{
			readValue = readInt(message);
			if (readValue >= minimumValue && readValue<= maximumValue)
				endFlag = true;
			else
				if (readValue < minimumValue)
					System.out.println(MINIMUM_VALUE_ERROR + minimumValue);
				else
					System.out.println(MAXIMUM_VALUE_ERROR + maximumValue); 
		} 
		while (!endFlag); 
		return readValue;
	}
 
	public static double readDouble (String message) {
		boolean endFlag = false;
		double readValue = 0;
		do
		{
			System.out.print(message);
			try
			{
				readValue = reader.nextDouble();
				endFlag = true;
			}
			catch (InputMismatchException e)
			{
				System.out.println(FORMAT_ERROR);
				String toEliminate = reader.next(); //To avoid problems with the readings after
			}
		} 
		while (!endFlag);
		return readValue;
	}
	  
	public int readPositiveInteger(String message) {
		return readIntWithMinimum(message,POSITIVE_NUMBER_MINIMUM_VALUE);
	}
	  
	public static int readNonNegativeInteger(String message) {
		return readIntWithMinimum(message,NON_NEGATIVE_MINIMUM_VALUE);
	}
	 
	public static double readDoubleWithMinumum (String message, double minimumValue) {
		boolean endFlag = false;
		double readValue = 0;
		do
		{
			readValue = readDouble(message);
			if (readValue >= minimumValue)
				endFlag = true;
			else
				System.out.println(MINIMUM_VALUE_ERROR + minimumValue);
		} 
		while (!endFlag);
	    return readValue;
	}
	  
	public static boolean yesOrNo(String message) {
		String myMessage = message + "("+ANSWER_YES+"/"+ANSWER_NO+")";
		char valoreLetto = readUpperChar(myMessage,String.valueOf(ANSWER_YES)+String.valueOf(ANSWER_NO));
		  
		if (valoreLetto == ANSWER_YES)
			return true;
		else
			return false;
	}
}
