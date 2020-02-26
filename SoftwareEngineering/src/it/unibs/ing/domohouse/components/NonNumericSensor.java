package it.unibs.ing.domohouse.components;

import java.util.ArrayList;

import it.unibs.ing.domohouse.interfaces.Gettable;

public class NonNumericSensor extends Sensor {


	private static final long serialVersionUID = 1067004464682653014L;

	public NonNumericSensor(String name, ArrayList <SensorCategory> categoryList) {
		super(name, categoryList);

	}
	
	public String[] getMeasurements() {
		ArrayList<String[]> result = new ArrayList<>();
		String[] measurements;
		for(SensorCategory elem : categoryList) result.add(getMeasurements(elem));
		
		int bound = 0;
		
		for(String[] elem : result) bound = bound + elem.length;
		measurements = new String[bound];
		
		int index = 0;
		for(int i = 0; i<result.size(); i++) {
			for(int j = 0; j<result.get(i).length; j++) {
				measurements[j + index] = result.get(i)[j];
			}
			index = index + result.get(i).length;
		}	
		return measurements;
	}
	
	private String[] getMeasurements(SensorCategory category) {	
		String [] infos = ((NonNumericSensorCategory) category).getDetectableInfoList();
		int size = infos.length;
		String [] measurements = new String[size];
		for (int i = 0; i < size; i++)
		{
			measurements[i] = getMeasurementOf(infos[i]);
		}
		return measurements;
	}
	
	
	/*
	 * Attualmente pensiamo al nonNumericSensor come un sensore che pu� rilevare l'informazione di un
	 * solo elemento(stanza o artefatto). Per una futura implementazione si pu� pensare ai sensori non numerici
	 * che possono rilevare pi� oggetti come l'unione di pi� singoli sensori non numerici.
	 */
	
	//da migliorare
	private String getMeasurementOf(String variableName) {
		assert variableName != null && variableName.length() > 0;
		
		boolean hasProperty = false;
		String valueFromObject = "";
		String [] objectNames = measuredObjectsList();
		for (String name : objectNames)
		{
			Gettable element = getElementByName(name);
			hasProperty = element.hasNonNumericProperty(variableName);
			if(hasProperty) {
			valueFromObject = element.getNonNumericProperty(variableName);
			}
		}
		
		if(hasProperty) return valueFromObject; //se � possibili rilevare informazioni le rileva
		else return "N/A"; //altrimenti rilascia N/A
	}

	public String toString() {
		
		String unformattedText;
		String status;
		if (isState()) 
			status = "acceso";
		else
			status = "spento";
		
		String name = getName().split("_")[0];
		
		String result = ""; 
		for(SensorCategory elem : categoryList) result = result + elem.getName() + ", ";
		
		int end = result.length() - 2;
		result = result.substring(0, end);
		
		unformattedText = name +':'+result+':';
		for(String measuredObject : measuredObjectsList())
		{
			unformattedText = unformattedText+"me:"+measuredObject+':';
		}
		for(String value : getMeasurements())
		{
			unformattedText = unformattedText+"mv:"+value+':';
		}
		unformattedText = unformattedText+status;
		
		assert unformattedText.length() > 0;
		assert unformattedText.contains("acceso") || unformattedText.contains("spento");
		
		return unformattedText;
	}

}
