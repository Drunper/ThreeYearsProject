package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.function.Consumer;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.Manageable;
import it.unibs.ing.domohouse.util.Manager;

public class Actuator implements Manageable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8133324316964731994L;
	private String name;
	private Manager controlledObjects;
	private ActuatorCategory category;
	private String operatingMode;
	private String defaultMode;
	private boolean state;
	private boolean controllingRoom;

	/*
	 * invariante: controlledObjects size > 0, name != null
	 */

	public Actuator(String name, ActuatorCategory category) {
		super();
		this.name = name;
		this.category = category;
		defaultMode = category.getDefaultMode();
		operatingMode = defaultMode;
		controlledObjects = new Manager();
		state = true;
	}
	
	public String getName() {
		assert name.contains("_") : "Il nome dell'attuatore non contiente il carattere \"_\"";
		String s = name.split("_")[0];
		assert !s.contains("_");
		assert actuatorInvariant();
		return s;
	}

	public void setName(String newName) {
		assert newName.length() > 0 : "Il nuovo nome dell'attuatore non contiene caratteri";
		this.name = newName;
		assert name.length() > 0 && name != null;
		assert actuatorInvariant();
	}
	
	//Fatto per chiarezza, solo un artefatto o una stanza può essere controllato dall'attuatore
	public void addEntry(Room toAdd) {
		assert toAdd != null : "toAdd è null";
		int size = controlledObjects.size();
		controlledObjects.addEntry(toAdd);
		assert controlledObjects.size() == size + 1;
		assert actuatorInvariant();
	}
	
	public void addEntry(Artifact toAdd) {
		assert toAdd != null : "toAdd è null";
		int size = controlledObjects.size();
		controlledObjects.addEntry(toAdd);
		assert controlledObjects.size() == size + 1;
		assert actuatorInvariant();
	}
	
	public boolean isControllingRoom() {
		assert actuatorInvariant();
		return controllingRoom;
	}

	public void setControllingRoom(boolean controllingRoom) {
		this.controllingRoom = controllingRoom;
		assert actuatorInvariant();
	}
	
	public void setOperatingMode(String operatingMode) {
		assert category != null && category.getOperatingMode(operatingMode) != null;
		this.operatingMode = operatingMode;
		Consumer<Gettable> mode = category.getOperatingMode(operatingMode);
		for(String object : controlledObjects.namesList())
		{
			mode.accept((Gettable)controlledObjects.getElementByName(object));
		}
		operatingMode = defaultMode;
		assert actuatorInvariant();
	}
	
	public String toString() {
		assert name != null && name.length() > 0 && category != null 
				&& category.getName().length() > 0 && controlledObjects != null;
		String unformattedText = name+':'+category.getName()+':';
		String status;
		if (state) 
			status = "acceso";
		else
			status = "spento";
		for(String measuredObject : controlledObjects.namesList())
		{
			unformattedText = unformattedText+"ce:"+measuredObject+':';
		}
		unformattedText = unformattedText+operatingMode+':'+status;
		assert unformattedText.contains(":");
		assert unformattedText.contains("acceso") || unformattedText.contains("spento") : "unformattedText non contiene informazioni riguardo allo stato";
		assert actuatorInvariant();
		return unformattedText;
	}
	
	private boolean actuatorInvariant() {
		if(controlledObjects != null && name != null && name.length() > 0) return true;
		return false;
	}

}
