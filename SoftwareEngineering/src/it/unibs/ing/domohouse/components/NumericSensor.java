package it.unibs.ing.domohouse.components;

import java.util.ArrayList;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.util.Strings;

public class NumericSensor extends Sensor {


	private static final long serialVersionUID = 8260369897935564520L;
	private double lowerBound;
	private double upperBound;
	
	public NumericSensor(String name, ArrayList<SensorCategory> categoryList) {
		super(name, categoryList);
	}
	
	private void setBounds(String variableName, SensorCategory category) {
		assert variableName != null && variableName.length() > 0;
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		double [] bounds = ((NumericSensorCategory) category).getDomain(variableName);
		lowerBound = bounds[0];
		upperBound = bounds[1];
	
		assert upperBound >= lowerBound;
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String[] getMeasurements() {
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		
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
	
	private String [] getMeasurements(SensorCategory category) {
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		String [] infos = ((NumericSensorCategory) category).getDetectableInfoList();
		int size = infos.length;
		String [] measurements = new String[size];
		for (int i = 0; i < size; i++)
			measurements[i] = addMeasurementUnit(getMeasurementOf(infos[i],category), infos[i], category);
		assert measurements != null;
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		return measurements;
	}
	
	private double getMeasurementOf(String variableName, SensorCategory category) {
		assert variableName != null && variableName.length() > 0;
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		setBounds(variableName, category);
		double measure = 0;
		double valueFromObject;
		String [] objectNames = measuredObjectsList();
		for (String name : objectNames) {
			Gettable element = getElementByName(name);
			if(element.hasNumericProperty(variableName)) { //Se è possibile rilevare informazioni allora le rileva
				valueFromObject = element.getNumericProperty(variableName);
				if(valueFromObject > upperBound)
					valueFromObject = upperBound;
				else if(valueFromObject < lowerBound)
					valueFromObject = lowerBound;
				measure += valueFromObject;
			}
			else measure += lowerBound; //se non è possibile rilevare informazioni allora si assume che ci sia il minimo
		}
		// faccio la media perché non ho altre idee su come ottenere un valore che non sia completamente scorrelato da tutti gli altri
		measure = measure / objectNames.length; 
		
		assert measure >= lowerBound;
		assert measure <= upperBound;
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		return measure;
	}
	
	
	private String addMeasurementUnit(double value, String variableName, SensorCategory category) {
		assert variableName != null && variableName.length() > 0;
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		String valueWithUnit = value + Strings.SPACE + ((NumericSensorCategory) category).getMeasurementUnit(variableName);
		
		assert valueWithUnit != null && valueWithUnit.length() > 0;
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		return valueWithUnit;
	}
	

	
	public String toString() {
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		String unformattedText;
		String status;
		if (isState()) 
			status = Strings.ON;
		else
			status = Strings.OFF;
		
		String name = getName().split(Strings.UNDERSCORE)[0];

		String result = Strings.NULL_CHARACTER; 
		for(SensorCategory elem : categoryList) result = result + elem.getName() + Strings.COMMA_WITH_SPACE;
		
		int end = result.length() - 2;
		result = result.substring(0, end);
		
		
		unformattedText = name +Strings.SEPARATOR+result+Strings.SEPARATOR;
		for(String measuredObject : measuredObjectsList())
			unformattedText = unformattedText + Strings.MEASURED_ELEMENT_TAG + Strings.SEPARATOR + measuredObject+Strings.SEPARATOR;
		for(String value : getMeasurements())
			unformattedText = unformattedText + Strings.MEASURED_VALUE_TAG + Strings.SEPARATOR + value + Strings.SEPARATOR;
		unformattedText = unformattedText+status;
		
		assert unformattedText.length() > 0;
		assert unformattedText.contains(Strings.ON) || unformattedText.contains(Strings.OFF);
		assert numericSensorInvariant() : Strings.WRONG_INVARIANT;
		
		return unformattedText;
	}
	
	private boolean numericSensorInvariant() {
		return super.sensorInvariant();
	}
}
