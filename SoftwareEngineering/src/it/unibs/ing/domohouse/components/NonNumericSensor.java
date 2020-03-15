package it.unibs.ing.domohouse.components;

import java.util.ArrayList;
import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.util.Strings;

public class NonNumericSensor extends Sensor {

	private static final long serialVersionUID = 1067004464682653014L;

	public NonNumericSensor(String name, ArrayList <SensorCategory> categoryList) {
		super(name, categoryList);
	}
	
	public String[] getMeasurements() {
		assert  nonNumericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		ArrayList<String[]> result = new ArrayList<>();
		String[] measurements;
		for(SensorCategory elem : categoryList) 
			result.add(getMeasurements(elem));
		
		int bound = 0;
		
		for(String[] elem : result) 
			bound = bound + elem.length;
		measurements = new String[bound];
		
		int index = 0;
		for(int i = 0; i<result.size(); i++) {
			for(int j = 0; j<result.get(i).length; j++) 
				measurements[j + index] = result.get(i)[j];
			index = index + result.get(i).length;
		}
		
		assert measurements != null;
		assert nonNumericSensorInvariant() : Strings.WRONG_INVARIANT;
		return measurements;
	}
	
	private String[] getMeasurements(SensorCategory category) {	
		assert category != null;
		assert  nonNumericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		String [] infos = ((NonNumericSensorCategory) category).getDetectableInfoList();
		int size = infos.length;
		String [] measurements = new String[size];
		for (int i = 0; i < size; i++)
			measurements[i] = getMeasurementOf(infos[i]);
		
		assert measurements != null;
		assert  nonNumericSensorInvariant() : Strings.WRONG_INVARIANT;
		return measurements;
	}

	/*
	 * Attualmente pensiamo al nonNumericSensor come un sensore che può rilevare l'informazione di un
	 * solo elemento(stanza o artefatto). Per una futura implementazione si può pensare ai sensori non numerici
	 * che possono rilevare più oggetti come l'unione di più singoli sensori non numerici.
	 */
	
	private String getMeasurementOf(String variableName) {
		assert variableName != null;
		assert  nonNumericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		assert variableName != null && variableName.length() > 0;
		
		boolean hasProperty = false;
		String valueFromObject = Strings.NULL_CHARACTER;
		String [] objectNames = measuredObjectsList();
		for (String name : objectNames) {
			Gettable element = getElementByName(name);
			hasProperty = element.hasNonNumericProperty(variableName);
			if(hasProperty) {
				valueFromObject = element.getNonNumericProperty(variableName);
			}
		}
		
		assert  nonNumericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		if(hasProperty && checkDomainConsistency(variableName, valueFromObject)) 
			return valueFromObject; //se è possibili rilevare informazioni le rileva
		else 
			return "N/A"; //altrimenti rilascia N/A
	}
	
	private boolean checkDomainConsistency(String variableName, String valueFromObject) {
		assert variableName != null && valueFromObject != null;
		assert  nonNumericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		boolean flag = false;
		for(SensorCategory s : categoryList) {
			NonNumericSensorCategory cat = (NonNumericSensorCategory) s;
			if(cat.contains(variableName, valueFromObject)) 
				flag = true;
		}
		return flag;
	}

	public String toString() {
		assert  nonNumericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		String unformattedText;
		String status;
		if (isState()) 
			status = Strings.ON;
		else
			status = Strings.OFF;
		
		String name = getName().split(Strings.UNDERSCORE)[0];
		
		String result = Strings.NULL_CHARACTER; 
		for(SensorCategory elem : categoryList) 
			result = result + elem.getName() + Strings.COMMA_WITH_SPACE;
		
		int end = result.length() - 2;
		result = result.substring(0, end);
		
		unformattedText = name + Strings.SEPARATOR + result + Strings.SEPARATOR;
		for(String measuredObject : measuredObjectsList())
			unformattedText = unformattedText + Strings.MEASURED_ELEMENT_TAG + Strings.SEPARATOR + measuredObject + Strings.SEPARATOR;
		for(String value : getMeasurements())
			unformattedText = unformattedText + Strings.MEASURED_VALUE_TAG + Strings.SEPARATOR + value + Strings.SEPARATOR;
		unformattedText = unformattedText+status;
		
		assert unformattedText.length() > 0;
		assert unformattedText.contains(Strings.ON) || unformattedText.contains(Strings.OFF);
		
		return unformattedText;
	}
	
	private boolean nonNumericSensorInvariant() {
		return super.sensorInvariant();
	}


}
