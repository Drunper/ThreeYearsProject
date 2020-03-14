package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.function.BiConsumer;
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
	private boolean running;

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
		running = false;
	}
	
	public String getName() {
		assert actuatorInvariant();
		return name;
	}
	
	//info per sapere se l'attuatore sta attuando una modalità operativa
	public boolean isRunning() {
		assert actuatorInvariant();
		return this.running;
	}

	public void setName(String newName) {
		assert newName.length() > 0 : "Il nuovo nome dell'attuatore non contiene caratteri";
		this.name = newName;
		assert name.length() > 0 && name != null;
		assert actuatorInvariant();
	}
	
	public void setState(boolean flag) {
		this.state = flag;
	}
	
	public boolean isState() {
		return this.state;
	}
	
	public ActuatorCategory getCategory() {
		assert actuatorInvariant();
		return this.category;
	}
	
	//Fatto per chiarezza, solo un artefatto o una stanza può essere controllato dall'attuatore
	public void addEntry(Room toAdd) {
		assert toAdd != null : "toAdd è null";
		int size = controlledObjects.size();
		controlledObjects.addElement(toAdd);
		assert controlledObjects.size() == size + 1;
		assert actuatorInvariant();
	}
	
	public void addEntry(Artifact toAdd) {
		assert toAdd != null : "toAdd è null";
		int size = controlledObjects.size();
		controlledObjects.addElement(toAdd);
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
	
	public void setNonParametricOperatingMode(String operatingMode) {
		assert category != null && category.getOperatingMode(operatingMode) != null;
		this.operatingMode = operatingMode;
		running = true;
		Consumer<Gettable> mode = category.getOperatingMode(operatingMode);
		for(String object : controlledObjects.getListOfElements())
		{
			mode.accept((Gettable)controlledObjects.getElementByName(object));
		}
		running = false;
		assert actuatorInvariant();
	}
	
	public void setParametricOperatingMode(String operatingMode, Object o) {
		assert category != null && category.getParametricOperatingMode(operatingMode) != null;
		this.operatingMode = operatingMode;
		running = true;
		BiConsumer<Gettable, Object> mode = category.getParametricOperatingMode(operatingMode);
		
		for(String object : controlledObjects.getListOfElements())
		{
			mode.accept((Gettable)controlledObjects.getElementByName(object), o);
		}
		running = false;
		assert actuatorInvariant();
	}
	
	
	public String toString() {
		assert name != null && name.length() > 0 && category != null 
				&& category.getName().length() > 0 && controlledObjects != null;
		
		String n = name.split("_")[0];
		String unformattedText = n +':'+category.getName()+':';
		String status;
		if (state) 
			status = "acceso";
		else
			status = "spento";
		for(String measuredObject : controlledObjects.getListOfElements())
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
