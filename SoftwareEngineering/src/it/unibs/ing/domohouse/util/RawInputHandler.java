package it.unibs.ing.domohouse.util;

import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Utilizzata nel corso Fondamenti di Programmazione. Autore sconosciuto, utilizzata solo a fini didattici.
 * Classe per la gestione basilare di input da tastiera per leggere interi, double e stringhe con alcuni vincoli.
 */
public class RawInputHandler {

	private static Scanner reader = createScanner();
	
	private static final String FORMAT_ERROR = "Attenzione: il dato inserito non e' nel formato corretto";
	private static final String MINIMUM_VALUE_ERROR = "Attenzione: e' richiesto un valore maggiore o uguale a ";
	private static final String VOID_STRING_ERROR = "Attenzione: non hai inserito alcun carattere";
	private static final String MAXIMUM_VALUE_ERROR = "Attenzione: e' richiesto un valore minore o uguale a ";
	private static final String USABLE_CHARACTERS = "Attenzione: i caratteri ammissibili sono: ";
	private static final String MAX_LENGTH_STRING_ERROR = "Attenzione: il numero massimo di caratteri ammessi è: ";
	
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
		assert message != null;
		
		System.out.println(message);
		return reader.next();
	}
	
	public static String readNotVoidString(String message) {
		assert message != null;
		
		boolean endFlag = false;
		String rdString = null;
		do {
			rdString = readString(message);
			rdString = rdString.trim();
			if (rdString.length() > 0)
				endFlag = true;
			else
				System.out.println(VOID_STRING_ERROR);
		} 
		while(!endFlag);
		return rdString;
	}
	
	public static String readStringWithMaximumLength(String message, int maxLength) {
		assert message != null;
		
		boolean endFlag = false;
		String rdString = null;
		do {
			rdString = readNotVoidString(message);
			if (rdString.length() < maxLength)
				endFlag = true;
			else
				System.out.println(MAX_LENGTH_STRING_ERROR + maxLength);
		}
		while(!endFlag);
		return rdString;
	}
	  
	public static char readChar (String message) {
		assert message != null;
		
		boolean endFlag = false;
		char readValue = '\0';
		do {
			String rdString = readString(message);
			if (rdString.length() > 0) {
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
		assert message != null && usableChars != null;
		
		boolean endFlag = false;
		char readValue = '\0';
		do {
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
	  
	@SuppressWarnings("unused")
	public static int readInt (String message) {
		assert message != null;
		
		boolean endflag = false;
		int readValue = 0;
		do {
			System.out.print(message);
			try {
				readValue = reader.nextInt();
				endflag = true;
			}
			catch (InputMismatchException e) {
				System.out.println(FORMAT_ERROR);
				String toEliminate = reader.next(); //To avoid problems with the readings after
			}
		} 
		while (!endflag);
		return readValue;
	}
  
	public static int readIntWithMinimum(String message, int minimumValue) {
	   assert message != null;
	   
	   boolean endFlag = false;
	   int readValue = 0;
	   do {
		   readValue = readInt(message);
		   if (readValue >= minimumValue)
			   endFlag = true;
		   else
			   System.out.println(MINIMUM_VALUE_ERROR + minimumValue);
	   } 
	   while (!endFlag);  
	   return readValue;
	}

	public static int readIntWithBounds(String message, int minimumValue, int maximumValue) {
		assert message != null;
		
		boolean endFlag = false;
		int readValue = 0;
		do {
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
 
	@SuppressWarnings("unused")
	public static double readDouble (String message) {
		assert message != null;
		
		boolean endFlag = false;
		double readValue = 0;
		do {
			System.out.print(message);
			try {
				readValue = reader.nextDouble();
				endFlag = true;
			}
			catch (InputMismatchException e) {
				System.out.println(FORMAT_ERROR);
				String toEliminate = reader.next(); //To avoid problems with the readings after
			}
		} 
		while (!endFlag);
		return readValue;
	}
	  
	public int readPositiveInteger(String message) {
		assert message != null;
		return readIntWithMinimum(message,POSITIVE_NUMBER_MINIMUM_VALUE);
	}
	  
	public static int readNonNegativeInteger(String message) {
		assert message != null;	
		return readIntWithMinimum(message,NON_NEGATIVE_MINIMUM_VALUE);
	}
	 
	public static double readDoubleWithMinumum (String message, double minimumValue) {
		assert message != null;
		
		boolean endFlag = false;
		double readValue = 0;
		do {
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
		assert message != null;
		
		String myMessage = message + Strings.OPEN_BRACKET+ANSWER_YES+"/"+ANSWER_NO+Strings.CLOSED_BRACKET;
		char valoreLetto = readUpperChar(myMessage,String.valueOf(ANSWER_YES)+String.valueOf(ANSWER_NO));
		  
		if (valoreLetto == ANSWER_YES)
			return true;
		else
			return false;
	}
}
