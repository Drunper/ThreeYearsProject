package it.unibs.ing.domohouse.model.components.elements;

public interface Gettable extends Manageable {

	boolean hasProperty(String variableName);
	String getProperty(String variableName); 
	void setProperty(String variableName, String newValue);
}
