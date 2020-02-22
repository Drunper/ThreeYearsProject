package it.unibs.ing.domohouse.util;

import java.io.Serializable;
import java.util.HashMap;
import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.SerializableConsumer;

public class OperatingModesHandler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7032900220446234368L;
	private static HashMap<String, SerializableConsumer<Gettable>> operatingModesMap = new HashMap<>();
	/*
	 * invariante operatingModesMap != null
	 */
	public static void fillOperatingModes() {
		
		SerializableConsumer<Gettable> idle = g -> { };
		
		SerializableConsumer<Gettable> aumentoTemperatura10gradi = g -> {
			double temp = g.getNumericProperty("temperatura");
			temp = temp +10;
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
					double umidita = g.getNumericProperty("umidità");
					umidita = umidita - 2;
					g.setNumericProperty("umidità", umidita);
				};
		
		SerializableConsumer<Gettable> aumentoUmidita = g ->{
					double umidita = g.getNumericProperty("umidità");
					umidita = umidita + 2;
					g.setNumericProperty("umidità", umidita);
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
		
		
		/*
		 * Qua è necessario aggiungere altre modalità operative come fatto sopra.
		 * Devono essere non parametriche.
		 */
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
	
	public static boolean hasOperatingMode(String name) {
		assert name != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return operatingModesMap.containsKey(name);
	}

	private static boolean operatingModesHandlerInvariant() {
		if(operatingModesMap != null) return true;
		return false;
	}
}
