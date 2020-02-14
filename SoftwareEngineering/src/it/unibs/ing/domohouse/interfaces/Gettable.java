package it.unibs.ing.domohouse.interfaces;

public interface Gettable extends Manageable {

	public double getNumericProperty(String variableName); 
	public boolean hasNumericProperty(String variableName);
	public boolean hasNonNumericProperty(String variableName);
	public String getNonNumericProperty(String variableName); 
	public void setNumericProperty (String variableName, double newValue); 
	public void setNonNumericProperty (String variableName, String newValue); 
}
