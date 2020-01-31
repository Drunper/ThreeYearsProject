package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;

import it.unibs.ing.domohouse.interfaces.*;
import it.unibs.ing.domohouse.util.OperatingModesHandler;

public class ActuatorCategory implements Manageable, Serializable {

	private String name;
	private String text;
	private HashMap<String, Consumer<Gettable>> operatingModesMap;
	
	public ActuatorCategory(String name, String text) {
		this.name = name;
		this.text = text;
		operatingModesMap = new HashMap<>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescr() {
		return this.text;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescr(String text) {
		this.text = text;
	}
	
	public String [] listOfOperatingModes() {
		return operatingModesMap.keySet().toArray(new String[0]);
	}
	
	public void putOperatingMode(String name) {
		operatingModesMap.put(name, OperatingModesHandler.getOperatingMode(name));
	}
	
	public Consumer<Gettable >getOperatingMode(String name) {
		return operatingModesMap.get(name);
	}
	
	public String getDefaultMode() {
		return text.split(":")[2];
	}
	
	public String toString() {
		return name+':'+text+':'+String.join(":", listOfOperatingModes());
	}
}
