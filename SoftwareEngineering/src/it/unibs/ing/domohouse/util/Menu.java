package it.unibs.ing.domohouse.util;

import it.unibs.ing.domohouse.components.Clock;

/*
 * Classe utilizzata nel corso di Fondamenti di Programmazione. Autore sconosciuto. Utilizzata solamente a fini didattici.
 * 
 */
public class Menu
{
	private String title;
	private String [] voices;
	
	private final static String FRAME = "--------------------------------";
	private final static String EXIT_VOICE = "0\tEsci";
	private final static String INSERT_REQUEST = "Digita il numero dell'opzione desiderata > ";
	/*
	 * invariante title e menu != null
	 */
	public Menu (String title, String [] voices)
	{
		this.title = title;
		this.voices = voices;
	}
	
	public int select ()
	{
		assert menuInvariant() : "Invariante di classe non soddisfatto";
		
		printMenu();
		int result = RawInputHandler.readIntWithBounds(INSERT_REQUEST, 0, voices.length);
		
		assert menuInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}
			
	public void printMenu ()
	{
		assert menuInvariant() : "Invariante di classe non soddisfatto";
		System.out.println(FRAME);
		System.out.println(title);
		System.out.println(FRAME);
		System.out.println(Clock.getCurrentTime());
		System.out.println(FRAME);
	    for (int i=0; i<voices.length; i++)
	    {
	    	System.out.println( (i+1) + "\t" + voices[i]);
	    }
	    System.out.println();
	    System.out.println(EXIT_VOICE);
		System.out.println();
		assert menuInvariant() : "Invariante di classe non soddisfatto";
	}
	
	private boolean menuInvariant() {
		boolean checkTitle = title != null;
		boolean checkVoices = voices != null;
		
		if(checkTitle && checkVoices) return true;
		return false;
	}
	  	
}
	
