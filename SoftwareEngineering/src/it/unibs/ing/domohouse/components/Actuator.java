package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.Manageable;
import it.unibs.ing.domohouse.util.Manager;
import it.unibs.ing.domohouse.util.Strings;

public class Actuator implements Manageable, Serializable {

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
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
		return name;
	}
	
	//info per sapere se l'attuatore sta attuando una modalità operativa
	public boolean isRunning() {
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
		return this.running;
	}

	public void setName(String newName) {
		assert newName.length() > 0 : Strings.ACTUATOR_NAME_PRECONDITION;
		this.name = newName;
		assert name.length() > 0 && name != null;
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void setState(boolean flag) {
		this.state = flag;
	}
	
	public boolean isState() {
		return this.state;
	}
	
	public ActuatorCategory getCategory() {
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
		return this.category;
	}
	
	//Fatto per chiarezza, solo un artefatto o una stanza può essere controllato dall'attuatore
	public void addEntry(Room toAdd) {
		assert toAdd != null : Strings.TO_ADD_PRECONDITION;
		int size = controlledObjects.size();
		controlledObjects.addElement(toAdd);
		assert controlledObjects.size() == size + 1;
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void addEntry(Artifact toAdd) {
		assert toAdd != null : Strings.TO_ADD_PRECONDITION;
		int size = controlledObjects.size();
		controlledObjects.addElement(toAdd);
		assert controlledObjects.size() == size + 1;
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public boolean isControllingRoom() {
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
		return controllingRoom;
	}

	public void setControllingRoom(boolean controllingRoom) {
		this.controllingRoom = controllingRoom;
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void setNonParametricOperatingMode(String operatingMode) {
		assert category != null && category.getOperatingMode(operatingMode) != null;
		this.operatingMode = operatingMode;
		running = true;
		Consumer<Gettable> mode = category.getOperatingMode(operatingMode);
		for(String object : controlledObjects.getListOfElements()) {
			mode.accept((Gettable)controlledObjects.getElementByName(object));
		}
		running = false;
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void setParametricOperatingMode(String operatingMode, Object o) {
		assert category != null && category.getParametricOperatingMode(operatingMode) != null;
		this.operatingMode = operatingMode;
		running = true;
		BiConsumer<Gettable, Object> mode = category.getParametricOperatingMode(operatingMode);
		
		for(String object : controlledObjects.getListOfElements()) {
			mode.accept((Gettable)controlledObjects.getElementByName(object), o);
		}
		running = false;
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String toString() {
		assert name != null && name.length() > 0 && category != null 
				&& category.getName().length() > 0 && controlledObjects != null;
		
		String n = name.split(Strings.UNDERSCORE)[0];
		String unformattedText = n + Strings.SEPARATOR + category.getName() + Strings.SEPARATOR;
		String status;
		if (state) 
			status = Strings.ON;
		else
			status = Strings.OFF;
		for(String measuredObject : controlledObjects.getListOfElements()) {
			unformattedText = unformattedText + Strings.CONTROLLED_ELEMENT_TAG + Strings.SEPARATOR + measuredObject + Strings.SEPARATOR;
		}
		unformattedText = unformattedText+operatingMode+Strings.SEPARATOR+status;
		assert unformattedText.contains(Strings.SEPARATOR);
		assert unformattedText.contains(Strings.ON) || unformattedText.contains(Strings.OFF) : Strings.UNFORMATTED_TEXT_PRECONDITION;
		assert actuatorInvariant() : Strings.WRONG_INVARIANT;
		return unformattedText;
	}
	
	private boolean actuatorInvariant() {
		return controlledObjects != null && name != null && name.length() > 0;
	}

}
