package it.unibs.ing.softwareengineering;

public interface Gettable extends Manageable {

	public double getNumericProperty(String variableName); 
	public String getNonNumericProperty(String variableName); 
	public void setNumericProperty (String variableName, double newValue); 
	public void setNonNumericProperty (String variableName, String newValue); 
}
