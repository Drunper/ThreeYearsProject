package it.unibs.ing.domohouse.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.SerializableBiConsumer;
import it.unibs.ing.domohouse.interfaces.SerializableConsumer;

public class OperatingModesHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7032900220446234368L;
	private static HashMap<String, SerializableConsumer<Gettable>> operatingModesMap = new HashMap<>();
	private static HashMap<String, SerializableBiConsumer<Gettable, Object>> parametricOperatingModesMap = new HashMap<>();
	
	/*
	 * struttura di supporto per effettuare controlli di inserimento
	 * Una migliore scelta implementativa sarebbe quella di utilizzare strutture proprie al posto di
	 * Consumer per poterle adattare al nostro caso. Ma questo richiederebbe una notevole mole di lavoro
	 * che risulterebbe la migliore scelta se e solo se avessimo tempo. Dunque, secondo me, conviene proseguire in questo
	 * modo e implementare il nostro consumer solo in caso avanzi tempo alla fine della v5
	 * 
	 * paramMap avr‡ come key il nome della modalit‡ operativa 
	 * e come valore i parametri che chiede in ingresso.
	 * 
	 * Ad esempio: 
	 * key = "modalit‡Op1"
	 * valore = "String:4"
	 * 
	 * Questo vuol dire che la modalit‡Op1 avr‡ come parametri di ingresso 4 stringhe. Successivamente
	 * vedremo come questo viene elaborato
	 */
	
	private static HashMap<String, String> paramMap = new HashMap<String, String>();
	
	/*
	 * invariante operatingModesMap != null
	 */
	public static void fillOperatingModes() {
		
		SerializableConsumer<Gettable> idle = g -> { };
		
		SerializableConsumer<Gettable> aumentoTemperatura10gradi = g -> {
			double temp = g.getNumericProperty("temperatura");
			temp = temp + 10;
			g.setNumericProperty("temperatura", temp);
		};
		
		SerializableConsumer<Gettable> diminuizioneTemperatura10gradi = g ->{
			double temp = g.getNumericProperty("temperatura");
			temp = temp - 10;
			g.setNumericProperty("temperatura", temp);
		};
		
		SerializableConsumer<Gettable> aumentoTemperatura5gradi = g ->{
			double temp = g.getNumericProperty("temperatura");
			temp = temp + 5;
			g.setNumericProperty("temperatura", temp);
		};
			
		SerializableConsumer<Gettable> diminuizioneTemperatura5gradi = g ->{
				double temp = g.getNumericProperty("temperatura");
				temp = temp - 5;
				g.setNumericProperty("temperatura", temp);
			};
			
		SerializableConsumer<Gettable> aumentoTemperatura1gradi = g ->{
				double temp = g.getNumericProperty("temperatura");
				temp = temp + 1;
				g.setNumericProperty("temperatura", temp);
			};
				
		SerializableConsumer<Gettable> diminuizioneTemperatura1gradi = g ->{
					double temp = g.getNumericProperty("temperatura");
					temp = temp - 1;
					g.setNumericProperty("temperatura", temp);
				};
				
		SerializableConsumer<Gettable> diminuizioneUmidita = g ->{
					double umidita = g.getNumericProperty("umidit‡");
					umidita = umidita - 2;
					g.setNumericProperty("umidit‡", umidita);
				};
		
		SerializableConsumer<Gettable> aumentoUmidita = g ->{
					double umidita = g.getNumericProperty("umidit‡");
					umidita = umidita + 2;
					g.setNumericProperty("umidit‡", umidita);
				};
		
		SerializableBiConsumer<Gettable, Object> mantenimentoTemperatura = (g, paramList) ->{
			//Otterremo un arrayList di double contenente un valore ma meglio comunque fare controlli
			if(paramList instanceof ArrayList && ((ArrayList) paramList).get(0) instanceof Double) {
				//In questo caso l'utente avr‡ inserito solamente un valore nell'arraylist e andremo a prendere quello
				double num = ((ArrayList<Double>) paramList).get(0);
				g.setNumericProperty("temperatura", num);
			}
		};
		
		operatingModesMap.put("idle", idle);
		operatingModesMap.put("aumentoTemperatura10gradi", aumentoTemperatura10gradi);
		operatingModesMap.put("diminuizioneTemperatura10gradi", diminuizioneTemperatura10gradi);
		operatingModesMap.put("diminuizioneTemperatura5gradi", diminuizioneTemperatura5gradi);
		operatingModesMap.put("aumentoTemperatura5gradi", aumentoTemperatura5gradi);
		operatingModesMap.put("diminuizioneTemperatura1gradi", diminuizioneTemperatura1gradi);
		operatingModesMap.put("aumentoTemperatura1gradi", aumentoTemperatura1gradi);
		operatingModesMap.put("aumentoUmidita", aumentoUmidita);
		operatingModesMap.put("diminuizioneUmidita", diminuizioneUmidita);
		
		parametricOperatingModesMap.put("mantenimentoTemperatura", mantenimentoTemperatura);
		paramMap.put("mantenimentoTemperatura", "Double:1"); 
		
		
	}
		
	public static SerializableBiConsumer<Gettable, Object> getParametricOperatingMode(String name){
		assert name != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert parametricOperatingModesMap.containsKey(name) : "operatingModesMap non contiente " + name;
		
		SerializableBiConsumer<Gettable, Object> g = parametricOperatingModesMap.get(name);
		
		assert g != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";
		return g;
	}
	
	//Aggiunto getParameterInfo
	public static String getParameterInfo(String name) {
		return paramMap.get(name);
	}
	
	
	public static SerializableConsumer<Gettable> getOperatingMode(String name) {
		assert name != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert operatingModesMap.containsKey(name) : "operatingModesMap non contiente " + name;
		
		SerializableConsumer<Gettable> g = operatingModesMap.get(name);
		
		assert g != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";
		return g;
	}
	
	/*
	 * Siccome Ë stato aggiunta un altra hashMap il metodo hasOperatingMode, che veniva chiamato da dataHandler
	 * per il controllo nell'inserimento di mod op quando veniva creata una categoria di attuatori, Ë stato modificato
	 * per controllare che esista la mod op o in operatingModesMap (contenente mod non op) o in parametricModesMap (contenente mod op).
	 * 
	 * Sono infine stati aggiunti dei metodo hasParametricOperatingMode e hasNonParametricOperatingMode che vengono usati
	 * per poter inserire correttamente le modalit‡ operative nelle strutture dati
	 * della categoria di attuatori in creazione (anche li abbiamo operatingModesMap e paramOperatingModesMap) 
	 */
	public static boolean hasOperatingMode(String name) {
		assert name != null;
		
		if(operatingModesMap.containsKey(name) || parametricOperatingModesMap.containsKey(name)) return true;
		return false;
	}
	
	public static boolean hasParametricOperatingMode(String name) {
		assert name != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";	
		return parametricOperatingModesMap.containsKey(name);
	}
	public static boolean hasNonParametricOperatingMode(String name) {
		assert name != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return operatingModesMap.containsKey(name);
	}

	private static boolean operatingModesHandlerInvariant() {
		if(operatingModesMap != null) return true;
		return false;
	}
}
