package it.unibs.ing.softwareengineering;

import java.util.*;

/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu

*/
public class MyMenu
{


  private String titolo;
  private String [] voci;

	
  public MyMenu (String titolo, String [] voci)
  {
	this.titolo = titolo;
	this.voci = voci;
  }

  public int scegli ()
  {
	stampaMenu();
	System.out.println("Effettua la scelta desiderata: ");
	Scanner in = new Scanner(System.in);
	int num = in.nextInt();
	return num;
	
  }
		
  public void stampaMenu ()
  {
	System.out.println("--------------------------------");
	System.out.println(titolo);
	System.out.println("--------------------------------");
    for (int i=0; i<voci.length; i++)
	 {
	  System.out.println( (i+1) + "\t" + voci[i]);
	 }
    System.out.println();
	System.out.println("0\tEsci");
    System.out.println();
  }
  	
}

