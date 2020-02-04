package it.unibs.ing.domohouse.components;

import it.unibs.ing.domohouse.interfaces.Gettable;

public class NumericSensor extends Sensor {

	private double lowerBound;
	private double upperBound;
	
	public NumericSensor(String name, SensorCategory category) {
		super(name, category);
	}
	
	private void setBounds(String variableName) {
		double [] bounds = category.getDomain(variableName);
		lowerBound = bounds[0];
		upperBound = bounds[1];		
	}
	
	public String [] getMeasurements() {
		String [] infos = category.getDetectableInfoList();
		int size = infos.length;
		String [] measurements = new String[size];
		for (int i = 0; i < size; i++)
		{
			measurements[i] = addMeasurementUnit(getMeasurementOf(infos[i]), infos[i]);
		}
		return measurements;
	}
	
	private double getMeasurementOf(String variableName) {
		setBounds(variableName);
		double measure = 0;
		double valueFromObject;
		String [] objectNames = measuredObjectsList();
		for (String name : objectNames)
		{
			Gettable element = getElementByName(name);
			valueFromObject = element.getNumericProperty(variableName);
			if(valueFromObject > upperBound)
				valueFromObject = upperBound;
			else if(valueFromObject < lowerBound)
				valueFromObject = lowerBound;
			measure += valueFromObject;
		}
		// faccio la media perché non ho altre idee su come ottenere un valore che non sia completamente scorrelato da tutti gli altri
		measure = measure / objectNames.length; 
		return measure;
	}
	
	private String addMeasurementUnit(double value, String variableName) {
		String valueWithUnit = value + " " + category.getMeasurementUnit(variableName);
		return valueWithUnit;
	}
	
	public String toString() {
		String unformattedText;
		String status;
		if (isState()) 
			status = "acceso";
		else
			status = "spento";
		unformattedText = getName()+':'+category.getName()+':';
		for(String measuredObject : measuredObjectsList())
		{
			unformattedText = unformattedText+"me:"+measuredObject+':';
		}
		for(String value : getMeasurements())
		{
			unformattedText = unformattedText+"mv:"+value+':';
		}
		unformattedText = unformattedText+':'+status;
		return unformattedText;
	}
}
