package it.unibs.ing.domohouse.util;


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
	
	public Menu (String title, String [] voices)
	{
		this.title = title;
		this.voices = voices;
	}
	
	public int select ()
	{
		printMenu();
		return RawInputHandler.readIntWithBounds(INSERT_REQUEST, 0, voices.length);
	}
			
	public void printMenu ()
	{
		System.out.println(FRAME);
		System.out.println(title);
		System.out.println(FRAME);
	    for (int i=0; i<voices.length; i++)
	    {
	    	System.out.println( (i+1) + "\t" + voices[i]);
	    }
	    System.out.println();
	    System.out.println(EXIT_VOICE);
		System.out.println();
	}
	  	
}
	
