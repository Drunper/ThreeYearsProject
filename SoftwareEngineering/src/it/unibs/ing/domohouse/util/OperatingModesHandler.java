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
	
	public static void fillOperatingModes() {
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
		 * Devono essere non parametriche. Se vengono in mente altre opzioni per risolvere
		 * il problema delle modalità operative ditemi pure.
		 */
	}
	
	public static SerializableConsumer<Gettable> getOperatingMode(String name) {
		return operatingModesMap.get(name);
	}
	
	public static boolean hasOperatingMode(String name) {
		return operatingModesMap.containsKey(name);
	}
}
