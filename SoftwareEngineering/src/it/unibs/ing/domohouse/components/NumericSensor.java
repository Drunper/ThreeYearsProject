package it.unibs.ing.domohouse.components;

import it.unibs.ing.domohouse.interfaces.Gettable;

public class NumericSensor extends Sensor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8260369897935564520L;
	private double lowerBound;
	private double upperBound;
	
	public NumericSensor(String name, SensorCategory category) {
		super(name, category);
	}
	
	private void setBounds(String variableName) {
		assert variableName != null && variableName.length() > 0;
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		
		double [] bounds = category.getDomain(variableName);
		lowerBound = bounds[0];
		upperBound = bounds[1];
	
		assert upperBound >= lowerBound;
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String [] getMeasurements() {
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		
		String [] infos = category.getDetectableInfoList();
		int size = infos.length;
		String [] measurements = new String[size];
		for (int i = 0; i < size; i++)
		{
			measurements[i] = addMeasurementUnit(getMeasurementOf(infos[i]), infos[i]);
		}
		assert measurements != null;
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		return measurements;
	}
	
	private double getMeasurementOf(String variableName) {
		assert variableName != null && variableName.length() > 0;
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		
		setBounds(variableName);
		double measure = 0;
		double valueFromObject;
		String [] objectNames = measuredObjectsList();
		for (String name : objectNames)
		{
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
		
		//assert measure >= lowerBound; ??
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		return measure;
	}
	
	private String addMeasurementUnit(double value, String variableName) {
		assert variableName != null && variableName.length() > 0;
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		
		String valueWithUnit = value + " " + category.getMeasurementUnit(variableName);
		
		assert valueWithUnit != null && valueWithUnit.length() > 0;
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		return valueWithUnit;
	}
	
	public String toString() {
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		
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
		unformattedText = unformattedText+status;
		
		assert unformattedText.length() > 0;
		assert unformattedText.contains("acceso") || unformattedText.contains("spento");
		assert numericSensorInvariant() : "Invariante della classe non soddisfatto";
		
		return unformattedText;
	}
	
	private boolean numericSensorInvariant() {
		return super.sensorInvariant();
	}
	
}
