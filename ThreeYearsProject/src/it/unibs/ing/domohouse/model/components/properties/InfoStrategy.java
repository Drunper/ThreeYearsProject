package it.unibs.ing.domohouse.model.components.properties;

import java.util.List;

public interface InfoStrategy {
	
	String getSingleValue(List<String> values);
	int getID();
}
