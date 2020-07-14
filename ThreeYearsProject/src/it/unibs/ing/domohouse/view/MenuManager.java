package it.unibs.ing.domohouse.view;

import java.io.PrintWriter;
import java.util.Collection;

public class MenuManager {
	private String title;
	private String[] voices;
	private PrintWriter output;
	private RawInputHandler input;

	private final static String FRAME = "--------------------------------";
	private final static String EXIT_VOICE = "0\tEsci";
	private final static String INSERT_REQUEST = "Digita il numero dell'opzione desiderata > ";

	/*
	 * invariante title e menu != null
	 */
	public MenuManager(String title, String[] voices, PrintWriter output, RawInputHandler input) {
		this.title = title;
		this.voices = voices;
		this.output = output;
		this.input = input;
	}

	public int doChoice() {
		assert menuManagerInvariant() : ViewStrings.WRONG_INVARIANT;

		printMenu();
		int result = input.readIntWithBounds(INSERT_REQUEST, 0, voices.length);

		assert menuManagerInvariant() : ViewStrings.WRONG_INVARIANT;
		return result;
	}

	private void printMenu() {
		assert menuManagerInvariant() : ViewStrings.WRONG_INVARIANT;
		output.println(FRAME);
		output.println(title);
		output.println(FRAME);
		for (int i = 0; i < voices.length; i++)
			output.println((i + 1) + "\t" + voices[i]);

		output.println();
		output.println(EXIT_VOICE);
		output.println();
		assert menuManagerInvariant() : ViewStrings.WRONG_INVARIANT;
	}

	public void println(String message) {
		output.println(message);
	}

	public String readNotVoidString(String message) {
		return input.readNotVoidString(message);
	}

	public void printListOfString(Collection<String> list) {
		assert list != null;

		for (String element : list)
			output.println(element);

		output.println();
	}

	public void clearOutput() {
		for (int i = 0; i < ViewStrings.SPACING_COSTANT; ++i)
			output.println();
	}

	private boolean menuManagerInvariant() {
		boolean checkTitle = title != null;
		boolean checkVoices = voices != null;

		return checkTitle && checkVoices;
	}

}
