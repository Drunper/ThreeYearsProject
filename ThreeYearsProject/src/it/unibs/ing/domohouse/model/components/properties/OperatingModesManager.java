package it.unibs.ing.domohouse.model.components.properties;

import java.util.HashMap;
import java.util.Map;

import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.components.elements.Gettable;

public class OperatingModesManager  {

	private static Map<String, OperatingMode> operatingModesMap = new HashMap<>();

	public static void fillOperatingModes() {

		OperatingMode idle = new OperatingMode() {
			
			@Override
			public void operate(Gettable object) {
			}
		};

		OperatingMode aumentoTemperatura10gradi = new OperatingMode() {

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp + 10));
			}
		};

		OperatingMode diminuzioneTemperatura10gradi = new OperatingMode() {

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp - 10));
			}
		};

		OperatingMode aumentoTemperatura5gradi = new OperatingMode() {

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp + 5));
			}
		};

		OperatingMode diminuzioneTemperatura5gradi = new OperatingMode() {

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp - 5));
			}
		};

		OperatingMode aumentoTemperatura1gradi = new OperatingMode() {

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp + 1));
			}
		};

		OperatingMode diminuzioneTemperatura1gradi = new OperatingMode() {

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp - 1));
			}
		};

		OperatingMode diminuzioneUmidita = new OperatingMode() {

			@Override
			public void operate(Gettable object) {
				double umidita = Double.parseDouble(object.getProperty("umidita"));
				object.setProperty("umidita", String.valueOf(umidita - 2));
			}
		};

		OperatingMode aumentoUmidita = new OperatingMode() {

			@Override
			public void operate(Gettable object) {
				double umidita = Double.parseDouble(object.getProperty("umidita"));
				object.setProperty("umidita", String.valueOf(umidita + 2));
			}
		};

		OperatingMode mantenimentoTemperatura = new OperatingMode() {

			@Override
			public void operate(Gettable object) {

				double num = Double.parseDouble(this.getParameterValue("temperatura"));
				object.setProperty("temperatura", String.valueOf(num));
			}
		};
		mantenimentoTemperatura.setParameterName("temperatura");

		OperatingMode coloreLuci = new OperatingMode() {

			@Override
			public void operate(Gettable object) {

				String color = this.getParameterValue("coloreLuce");
				object.setProperty("coloreLuce", color);
			}
		};
		coloreLuci.setParameterName("coloreLuce");

		operatingModesMap.put("idle", idle);
		operatingModesMap.put("aumentoTemperatura10gradi", aumentoTemperatura10gradi);
		operatingModesMap.put("diminuzioneTemperatura10gradi", diminuzioneTemperatura10gradi);
		operatingModesMap.put("diminuzioneTemperatura5gradi", diminuzioneTemperatura5gradi);
		operatingModesMap.put("aumentoTemperatura5gradi", aumentoTemperatura5gradi);
		operatingModesMap.put("diminuzioneTemperatura1gradi", diminuzioneTemperatura1gradi);
		operatingModesMap.put("aumentoTemperatura1gradi", aumentoTemperatura1gradi);
		operatingModesMap.put("aumentoUmidita", aumentoUmidita);
		operatingModesMap.put("diminuzioneUmidita", diminuzioneUmidita);
		operatingModesMap.put("mantenimentoTemperatura", mantenimentoTemperatura);
		operatingModesMap.put("cambiaColore", coloreLuci);
	}

	public static OperatingMode getOperatingMode(String name) {
		assert name != null;
		assert operatingModesHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		assert operatingModesMap.containsKey(name) : "operatingModesMap non contiente " + name;

		OperatingMode op = operatingModesMap.get(name);

		assert op != null;
		assert operatingModesHandlerInvariant() : ModelStrings.WRONG_INVARIANT;
		return op;
	}

	public static boolean hasOperatingMode(String name) {
		assert name != null;
		assert operatingModesHandlerInvariant() : ModelStrings.WRONG_INVARIANT;

		return operatingModesMap.containsKey(name);
	}

	private static boolean operatingModesHandlerInvariant() {
		return operatingModesMap != null;
	}
}
