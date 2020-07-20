package it.unibs.ing.domohouse.model.components.elements;

import java.util.Set;

public interface Gettable extends Manageable {

	boolean hasProperty(String variableName);
	boolean doesPropertyExist();
	String getProperty(String variableName); 
	void setProperty(String variableName, String newValue);
	Set<String> getPropertiesNameSet();
}
