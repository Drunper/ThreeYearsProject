package it.unibs.ing.domohouse.view;

import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RawInputHandler {

	private static final String FORMAT_ERROR = "Attenzione: il dato inserito non e' nel formato corretto";
	private static final String MINIMUM_VALUE_ERROR = "Attenzione: e' richiesto un valore maggiore o uguale a ";
	private static final String VOID_STRING_ERROR = "Attenzione: non hai inserito alcun carattere";
	private static final String MAXIMUM_VALUE_ERROR = "Attenzione: e' richiesto un valore minore o uguale a ";
	private static final String USABLE_CHARACTERS = "Attenzione: i caratteri ammissibili sono: ";
	private static final String MAX_LENGTH_STRING_ERROR = "Attenzione: il numero massimo di caratteri ammessi è: ";

	private final static char ANSWER_YES = 'S';
	private final static char ANSWER_NO = 'N';

	private static final int POSITIVE_NUMBER_MINIMUM_VALUE = 1;
	private static final int NON_NEGATIVE_MINIMUM_VALUE = 0;

	private Scanner reader;
	private PrintWriter output;

	public RawInputHandler(Scanner input, PrintWriter output) {
		reader = input;
		this.output = output;
	}

	public String readString(String message) {
		assert message != null;

		output.println(message);
		return reader.next();
	}

	public String readNotVoidString(String message) {
		assert message != null;

		boolean endFlag = false;
		String rdString = null;
		do {
			rdString = readString(message);
			rdString = rdString.trim();
			if (rdString.length() > 0)
				endFlag = true;
			else
				output.println(VOID_STRING_ERROR);
		}
		while (!endFlag);
		return rdString;
	}

	public String readStringWithMaximumLength(String message, int maxLength) {
		assert message != null;

		boolean endFlag = false;
		String rdString = null;
		do {
			rdString = readNotVoidString(message);
			if (rdString.length() < maxLength)
				endFlag = true;
			else
				output.println(MAX_LENGTH_STRING_ERROR + maxLength);
		}
		while (!endFlag);
		return rdString;
	}

	public char readChar(String message) {
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
				output.println(VOID_STRING_ERROR);
		}
		while (!endFlag);
		return readValue;
	}

	public char readUpperChar(String message, String usableChars) {
		assert message != null && usableChars != null;

		boolean endFlag = false;
		char readValue = '\0';
		do {
			readValue = readChar(message);
			readValue = Character.toUpperCase(readValue);
			if (usableChars.indexOf(readValue) != -1)
				endFlag = true;
			else
				output.println(USABLE_CHARACTERS + usableChars);
		}
		while (!endFlag);
		return readValue;
	}

	@SuppressWarnings("unused")
	public int readInt(String message) {
		assert message != null;

		boolean endflag = false;
		int readValue = 0;
		do {
			output.print(message);
			output.flush(); // necessario per la corretta visualizzazione
			try {
				readValue = reader.nextInt();
				endflag = true;
			}
			catch (InputMismatchException e) {
				output.println(FORMAT_ERROR);
				String toEliminate = reader.next();
			}
		}
		while (!endflag);
		return readValue;
	}

	public int readIntWithMinimum(String message, int minimumValue) {
		assert message != null;

		boolean endFlag = false;
		int readValue = 0;
		do {
			readValue = readInt(message);
			if (readValue >= minimumValue)
				endFlag = true;
			else
				output.println(MINIMUM_VALUE_ERROR + minimumValue);
		}
		while (!endFlag);
		return readValue;
	}

	public int readIntWithBounds(String message, int minimumValue, int maximumValue) {
		assert message != null;

		boolean endFlag = false;
		int readValue = 0;
		do {
			readValue = readInt(message);
			if (readValue >= minimumValue && readValue <= maximumValue)
				endFlag = true;
			else if (readValue < minimumValue)
				output.println(MINIMUM_VALUE_ERROR + minimumValue);
			else
				output.println(MAXIMUM_VALUE_ERROR + maximumValue);
		}
		while (!endFlag);
		return readValue;
	}

	@SuppressWarnings("unused")
	public double readDouble(String message) {
		assert message != null;

		boolean endFlag = false;
		double readValue = 0;
		do {
			output.println(message);
			try {
				readValue = reader.nextDouble();
				endFlag = true;
			}
			catch (InputMismatchException e) {
				output.println(FORMAT_ERROR);
				String toEliminate = reader.next(); // To avoid problems with the readings after
			}
		}
		while (!endFlag);
		return readValue;
	}

	public int readPositiveInteger(String message) {
		assert message != null;
		return readIntWithMinimum(message, POSITIVE_NUMBER_MINIMUM_VALUE);
	}

	public int readNonNegativeInteger(String message) {
		assert message != null;
		return readIntWithMinimum(message, NON_NEGATIVE_MINIMUM_VALUE);
	}

	public double readDoubleWithMinumum(String message, double minimumValue) {
		assert message != null;

		boolean endFlag = false;
		double readValue = 0;
		do {
			readValue = readDouble(message);
			if (readValue >= minimumValue)
				endFlag = true;
			else
				output.println(MINIMUM_VALUE_ERROR + minimumValue);
		}
		while (!endFlag);
		return readValue;
	}

	public boolean yesOrNo(String message) {
		assert message != null;

		String myMessage = message + ViewStrings.OPEN_BRACKET + ANSWER_YES + "/" + ANSWER_NO
				+ ViewStrings.CLOSED_BRACKET;
		char valoreLetto = readUpperChar(myMessage, String.valueOf(ANSWER_YES) + String.valueOf(ANSWER_NO));

		if (valoreLetto == ANSWER_YES)
			return true;
		else
			return false;
	}
}
