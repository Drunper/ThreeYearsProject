package it.unibs.ing.domohouse.util;

import java.util.HashMap;
import java.util.function.Consumer;

import it.unibs.ing.domohouse.interfaces.Gettable;

public class OperatingModesHandler {

	private static HashMap<String, Consumer<Gettable>> operatingModesMap = new HashMap<>();
	
	public static void fillOperatingModes() {
		Consumer<Gettable> aumentoTemperatura10gradi = g -> {
			double temp = g.getNumericProperty("temperatura");
			temp = temp +10;
			g.setNumericProperty("temperatura", temp);
		};
		operatingModesMap.put("aumentoTemperatura10gradi", aumentoTemperatura10gradi);
		/*
		 * Qua è necessario aggiungere altre modalità operative come fatto sopra.
		 * Devono essere non parametriche. Se vengono in mente altre opzioni per risolvere
		 * il problema delle modalità operative ditemi pure.
		 */
	}
	
	public static Consumer<Gettable> getOperatingMode(String name) {
		return operatingModesMap.get(name);
	}
	
	public static boolean hasOperatingMode(String name) {
		return operatingModesMap.containsKey(name);
	}
}
