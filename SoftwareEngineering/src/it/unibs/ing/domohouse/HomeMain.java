package it.unibs.ing.domohouse;

import it.unibs.ing.domohouse.util.InterfaceHandler;

public class HomeMain {
	
	private static InterfaceHandler interfaceHandler;
	
	public static void main(String[] args) {
		/*
		 * Buongiorno a tutti! Qual buon vento vi porta qui? Vi avviso che è stata fatta una modifica abbastanza brutta
		 * stilisticamente da sistemare, dunque assicuratevi di avere gli occhi pronti a vedere ciò che è stato modificato.
		 * Non mi assumo nessuna responsabilità riguardo a sanguinamenti oculari. 
		 * 
		 * Sono stati aggiunti caricamento e salvataggio File DataHandler. 
		 * Sono ancora presenti gli oggetti di test momentanei. Se il programma non rileva nessun file di salvataggio
		 * utilizza quelli, dunque al primo avvio si consiglia di andare nel menu manutentore e utilizzare la voce "Salva Dati"
		 * per tenere il file per effetturare test. 
		 * Successivamente si potrebbe pensare, in caso di mancanza del file, di crearne uno nuovo e il manutentore potrà
		 * a via a via inserire manualmente le stanze e salvare. Questo potrebbe servire a sorvolare l problema di 
		 * scrittura file txt.
		 * 
		 * Ho tenuto ancora le voci di "inizializzazione da File", giovedi discuteremo sul da farsi.
		 * 
		 * Per la cosa brutta vedere i metodi aggiunti in InterfaceHandler da riga 352
		 */
		interfaceHandler = new InterfaceHandler();
		interfaceHandler.show();
	}
}
