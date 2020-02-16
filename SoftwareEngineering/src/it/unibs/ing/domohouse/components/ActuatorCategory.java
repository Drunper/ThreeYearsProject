package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;
import it.unibs.ing.domohouse.interfaces.*;

public class ActuatorCategory implements Manageable, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3544838050806873679L;
	private String name;
	private String text;
	private HashMap<String, SerializableConsumer<Gettable>> operatingModesMap;
	
	/*
	 * invariante name > 0, text > 0
	 */
	public ActuatorCategory(String name, String text) {
		this.name = name;
		this.text = text;
		operatingModesMap = new HashMap<>();
	}
	
	public String getName() {
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return this.name;
	}
	
	public String getDescr() {
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return this.text;
	}
	
	public void setName(String newName) {
		assert newName.length() > 0 && newName != null;
		this.name = newName;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void setDescr(String text) {
		assert text.length() > 0 && text != null;
		this.text = text;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String [] listOfOperatingModes() {
		assert operatingModesMap !=null;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return operatingModesMap.keySet().toArray(new String[0]);
	}
	
	public void putOperatingMode(String name, SerializableConsumer<Gettable> operatingMode) {
		assert name.length() > 0;
		int pre_size = operatingModesMap.size();
		operatingModesMap.put(name, operatingMode);
		assert operatingModesMap.size() >= pre_size;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public Consumer<Gettable> getOperatingMode(String name) {
		assert operatingModesMap.containsKey(name) : "operatingModesMap non contiene il nome richiesto";
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return operatingModesMap.get(name);
	}
	
	public String getDefaultMode() {
		assert text.contains(":");
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return text.split(":")[2];
	}
	
	public String toString() {
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return name+':'+text;
	}
	
	private boolean actuatorCategoryInvariant() {
		if(name.length() > 0 && name != null && text.length() > 0 && text != null && operatingModesMap != null) return true;
		return false;
	}
}
