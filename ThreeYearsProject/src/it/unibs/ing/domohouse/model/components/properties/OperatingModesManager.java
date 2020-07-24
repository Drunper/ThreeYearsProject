package it.unibs.ing.domohouse.model.components.properties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.components.elements.Gettable;

public class OperatingModesManager implements Serializable {

	private static final long serialVersionUID = -7032900220446234368L;
	private static Map<String, OperatingMode> operatingModesMap = new HashMap<>();

	public static void fillOperatingModes() {

		OperatingMode idle = new OperatingMode() {
			private static final long serialVersionUID = -5769310144511586954L;

			@Override
			public void operate(Gettable object) {
			}
		};

		OperatingMode aumentoTemperatura10gradi = new OperatingMode() {
			private static final long serialVersionUID = 1878837158320578129L;

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp + 10));
			}
		};

		OperatingMode diminuzioneTemperatura10gradi = new OperatingMode() {
			private static final long serialVersionUID = 2258350174039534211L;

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp - 10));
			}
		};

		OperatingMode aumentoTemperatura5gradi = new OperatingMode() {
			private static final long serialVersionUID = 2713001726971994419L;

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp + 5));
			}
		};

		OperatingMode diminuzioneTemperatura5gradi = new OperatingMode() {
			private static final long serialVersionUID = 7385667586097572913L;

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp - 5));
			}
		};

		OperatingMode aumentoTemperatura1gradi = new OperatingMode() {
			private static final long serialVersionUID = 109351630737603284L;

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp + 1));
			}
		};

		OperatingMode diminuzioneTemperatura1gradi = new OperatingMode() {
			private static final long serialVersionUID = -1294656652412203490L;

			@Override
			public void operate(Gettable object) {
				double temp = Double.parseDouble(object.getProperty("temperatura"));
				object.setProperty("temperatura", String.valueOf(temp - 1));
			}
		};

		OperatingMode diminuzioneUmidita = new OperatingMode() {
			private static final long serialVersionUID = 6072623877189941153L;

			@Override
			public void operate(Gettable object) {
				double umidita = Double.parseDouble(object.getProperty("umidita"));
				object.setProperty("umidità", String.valueOf(umidita - 2));
			}
		};

		OperatingMode aumentoUmidita = new OperatingMode() {
			private static final long serialVersionUID = -1052775698055305723L;

			@Override
			public void operate(Gettable object) {
				double umidita = Double.parseDouble(object.getProperty("umidita"));
				object.setProperty("umidità", String.valueOf(umidita + 2));
			}
		};

		OperatingMode mantenimentoTemperatura = new OperatingMode() {
			private static final long serialVersionUID = 5625520723117039165L;

			@Override
			public void operate(Gettable object) {

				double num = Double.parseDouble(this.getParameterValue("temperatura"));
				object.setProperty("temperatura", String.valueOf(num));
			}
		};
		mantenimentoTemperatura.setParameterName("temperatura");

		OperatingMode coloreLuci = new OperatingMode() {
			private static final long serialVersionUID = -7002837800776914351L;

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
