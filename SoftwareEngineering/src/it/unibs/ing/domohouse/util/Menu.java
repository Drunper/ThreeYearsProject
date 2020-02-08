package it.unibs.ing.domohouse.util;


/*
 * Classe utilizzata nel corso di Fondamenti di Programmazione. Autore sconosciuto. Utilizzata solamente a fini didattici.
 * 
 */
public class Menu
{
	private String titolo;
	private String [] voci;
	
	private final static String FRAME = "--------------------------------";
	private final static String EXIT_VOICE = "0\tEsci";
	private final static String INSERT_REQUEST = "Digita il numero dell'opzione desiderata > ";
	
	public Menu (String titolo, String [] voci)
	{
		this.titolo = titolo;
		this.voci = voci;
	}
	
	public int select ()
	{
		stampaMenu();
		return RawInputHandler.readIntWithBounds(INSERT_REQUEST, 0, voci.length);
	}
			
	public void stampaMenu ()
	{
		System.out.println(FRAME);
		System.out.println(titolo);
		System.out.println(FRAME);
	    for (int i=0; i<voci.length; i++)
	    {
	    	System.out.println( (i+1) + "\t" + voci[i]);
	    }
	    System.out.println();
	    System.out.println(EXIT_VOICE);
		System.out.println();
	}
	  	
}
	
