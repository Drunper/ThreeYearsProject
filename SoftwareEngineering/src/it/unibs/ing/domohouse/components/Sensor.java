package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.ArrayList;
import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.Manageable;
import it.unibs.ing.domohouse.util.Manager;
import it.unibs.ing.domohouse.util.Strings;

public class Sensor implements Manageable, Serializable{

	private static final long serialVersionUID = -3804289994000782961L;
	
	private String name;
	protected ArrayList<SensorCategory> categoryList;
	private Manager measuredObjects;
	private boolean state;
	private boolean measuringRoom;

	public Sensor(String name, ArrayList<SensorCategory> categoryList) {
		super();
		this.name = name;
		this.categoryList = categoryList;
		measuredObjects = new Manager();
		state = true;
	}
	
	public String getName() {
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		return name;
	}

	public void setName(String name) {
		assert name != null && name.length() > 0;
		this.name = name;
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void addEntry(Room toAdd) {
		assert toAdd != null;
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = measuredObjects.size();
		
		measuredObjects.addElement(toAdd);

		assert measuredObjects.size() >= pre_size;
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void addEntry(Artifact toAdd) {
		assert toAdd != null; 
		int pre_size = measuredObjects.size();
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		
		measuredObjects.addElement(toAdd);
		
		assert measuredObjects.size() >= pre_size;
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String [] measuredObjectsList() {
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		return measuredObjects.getListOfElements();
	}
	
	public Gettable getElementByName(String name) {
		assert name != null && name.length() > 0;
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		
		Gettable g = (Gettable)measuredObjects.getElementByName(name);
		
		assert g != null;
		return g;
	}

	public boolean isMeasuringRoom() {
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		return measuringRoom;
	}

	public void setMeasuringRoom(boolean measuringRoom) {
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		this.measuringRoom = measuringRoom;
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
	}

	public boolean isState() {
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		return state;
	}

	public void setState(boolean state) {
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		this.state = state;
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String [] getCategories() {
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		
		String [] categoriesName = new String[categoryList.size()];
		for(int i = 0; i < categoriesName.length; i++)
			categoriesName[i] = categoryList.get(i).getName();
		return categoriesName;
	}
	
	public boolean isNumeric() {
		assert sensorInvariant() : Strings.WRONG_INVARIANT;
		return categoryList.get(0).getIsNumeric();
	}
	
	protected boolean sensorInvariant() {
		boolean checkName = name != null && name.length() > 0;
		boolean checkCategory = categoryList != null;
		boolean checkManager = measuredObjects != null;
		
		return checkName && checkCategory && checkManager;
	}
}
