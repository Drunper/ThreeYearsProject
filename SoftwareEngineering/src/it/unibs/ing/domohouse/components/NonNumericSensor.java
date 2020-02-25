package it.unibs.ing.domohouse.components;

import it.unibs.ing.domohouse.interfaces.Gettable;

public class NonNumericSensor extends Sensor {


	private static final long serialVersionUID = 1067004464682653014L;

	public NonNumericSensor(String name, NonNumericSensorCategory category) {
		super(name, category);

	}
	
	public String[] getMeasurements() {	
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
	 * Attualmente pensiamo al nonNumericSensor come un sensore che può rilevare l'informazione di un
	 * solo elemento(stanza o artefatto). Per una futura implementazione si può pensare ai sensori non numerici
	 * che possono rilevare più oggetti come l'unione di più singoli sensori non numerici.
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
		
		if(hasProperty) return valueFromObject; //se è possibili rilevare informazioni le rileva
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
		
		unformattedText = name +':'+category.getName()+':';
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
