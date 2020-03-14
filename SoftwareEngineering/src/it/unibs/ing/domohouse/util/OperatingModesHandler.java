package it.unibs.ing.domohouse.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.domohouse.components.Actuator;
import it.unibs.ing.domohouse.components.Artifact;
import it.unibs.ing.domohouse.components.Room;
import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.SerializableBiConsumer;
import it.unibs.ing.domohouse.interfaces.SerializableConsumer;

public class OperatingModesHandler implements Serializable{


	private static final long serialVersionUID = -7032900220446234368L;
	private static HashMap<String, SerializableConsumer<Gettable>> operatingModesMap = new HashMap<>();
	private static HashMap<String, SerializableBiConsumer<Gettable, Object>> parametricOperatingModesMap = new HashMap<>();
	
	/*
	 * struttura di supporto per effettuare controlli di inserimento
	 * 
	 * paramMap avr‡ come key il nome della modalit‡ operativa 
	 * e come valore i parametri che chiede in ingresso.
	 * 
	 * Ad esempio: 
	 * key = "modalit‡Op1"
	 * valore = "String:4"
	 * 
	 * Questo vuol dire che la modalit‡Op1 avr‡ come parametri di ingresso 4 stringhe.
	 */
	private static HashMap<String, String> paramMap = new HashMap<String, String>();
	
	/*
	 * invariante operatingModesMap != null, parametricOperatingModesMap != null, paramMap != null
	 */
	@SuppressWarnings("unchecked")
	public static void fillOperatingModes() {
		
		SerializableConsumer<Gettable> idle = g -> { };
		
		SerializableConsumer<Gettable> aumentoTemperatura10gradi = g -> {
			double temp = g.getNumericProperty("temperatura");
			temp = temp + 10;
			g.setNumericProperty("temperatura", temp);
		};
		
		SerializableConsumer<Gettable> diminuzioneTemperatura10gradi = g ->{
			double temp = g.getNumericProperty("temperatura");
			temp = temp - 10;
			g.setNumericProperty("temperatura", temp);
		};
		
		SerializableConsumer<Gettable> aumentoTemperatura5gradi = g ->{
			double temp = g.getNumericProperty("temperatura");
			temp = temp + 5;
			g.setNumericProperty("temperatura", temp);
		};
			
		SerializableConsumer<Gettable> diminuzioneTemperatura5gradi = g ->{
				double temp = g.getNumericProperty("temperatura");
				temp = temp - 5;
				g.setNumericProperty("temperatura", temp);
			};
			
		SerializableConsumer<Gettable> aumentoTemperatura1gradi = g ->{
				double temp = g.getNumericProperty("temperatura");
				temp = temp + 1;
				g.setNumericProperty("temperatura", temp);
			};
				
		SerializableConsumer<Gettable> diminuzioneTemperatura1gradi = g ->{
					double temp = g.getNumericProperty("temperatura");
					temp = temp - 1;
					g.setNumericProperty("temperatura", temp);
				};
				
		SerializableConsumer<Gettable> diminuzioneUmidita = g ->{
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
			//In questo caso l'utente avr‡ inserito solamente un valore nell'arraylist e andremo a prendere quello
				if(paramList instanceof ArrayList && ((ArrayList<?>) paramList).get(0) instanceof Double) {
				double num = ((ArrayList<Double>) paramList).get(0);
				g.setNumericProperty("temperatura", num);
				}
		};
		
		SerializableBiConsumer<Gettable, Object> coloreLuci = (g, paramList) ->{
			 //In questo caso l'utente avr‡ inserito solamente un valore nell'arraylist e andremo a prendere quello
			if(paramList instanceof ArrayList && ((ArrayList<?>) paramList).get(0) instanceof String) {
				String color = ((ArrayList<String>) paramList).get(0);
				g.setNonNumericProperty("coloreLuce", color);
			}

		};
		
		SerializableConsumer<Gettable> spegnimento = (g) ->{
			if(g instanceof Room) {
				Room room = (Room) g;
				for(String act : room.getActuatorsNames()) {
					Actuator actuator = room.getActuatorByName(act);
					if(actuator.isRunning()) {
						actuator.setState(false);
					}
				}
			}else if(g instanceof Artifact) {
				Artifact artifact = (Artifact) g;
				for(String act : artifact.getControllerActuatorsNames()) {
					Actuator actuator = artifact.getActuatorByName(act);
					if(actuator.isRunning()) {
						actuator.setState(false);
					}
				}
			}
		};
		

		
		
		operatingModesMap.put("idle", idle);
		operatingModesMap.put("aumentoTemperatura10gradi", aumentoTemperatura10gradi);
		operatingModesMap.put("diminuzioneTemperatura10gradi", diminuzioneTemperatura10gradi);
		operatingModesMap.put("diminuzioneTemperatura5gradi", diminuzioneTemperatura5gradi);
		operatingModesMap.put("aumentoTemperatura5gradi", aumentoTemperatura5gradi);
		operatingModesMap.put("diminuzioneTemperatura1gradi", diminuzioneTemperatura1gradi);
		operatingModesMap.put("aumentoTemperatura1gradi", aumentoTemperatura1gradi);
		operatingModesMap.put("aumentoUmidita", aumentoUmidita);
		operatingModesMap.put("diminuzioneUmidita", diminuzioneUmidita);
		operatingModesMap.put("spegnimento", spegnimento);
		
		parametricOperatingModesMap.put("mantenimentoTemperatura", mantenimentoTemperatura);
		paramMap.put("mantenimentoTemperatura", "Double:1"); 
		parametricOperatingModesMap.put("cambiaColore", coloreLuci);
		paramMap.put("cambiaColore", "String:1");
		
		
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
	

	public static String getParameterInfo(String name) {
		assert name != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";
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
	
	public static boolean hasOperatingMode(String name) {
		assert name != null;
		assert operatingModesHandlerInvariant() : "Invariante di classe non soddisfatto";
		
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
		if(operatingModesMap != null && parametricOperatingModesMap != null && paramMap != null) return true;
		return false;
	}
}
