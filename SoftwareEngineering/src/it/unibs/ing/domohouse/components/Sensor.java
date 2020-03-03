package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.ing.domohouse.interfaces.Gettable;
import it.unibs.ing.domohouse.interfaces.Manageable;
import it.unibs.ing.domohouse.util.Manager;

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
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		return name;
	}

	public void setName(String name) {
		assert name != null && name.length() > 0;
		this.name = name;
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void addEntry(Room toAdd) {
		assert toAdd != null;
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = measuredObjects.size();
		
		measuredObjects.addEntry(toAdd);

		assert measuredObjects.size() >= pre_size;
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void addEntry(Artifact toAdd) {
		assert toAdd != null; 
		int pre_size = measuredObjects.size();
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		
		measuredObjects.addEntry(toAdd);
		
		assert measuredObjects.size() >= pre_size;
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String [] measuredObjectsList() {
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		return measuredObjects.namesList();
	}
	
	public Gettable getElementByName(String name) {
		assert name != null && name.length() > 0;
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		
		Gettable g = (Gettable)measuredObjects.getElementByName(name);
		
		assert g != null;
		
		return g;
	}

	public boolean isMeasuringRoom() {
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		return measuringRoom;
	}

	public void setMeasuringRoom(boolean measuringRoom) {
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		this.measuringRoom = measuringRoom;
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
	}

	public boolean isState() {
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		return state;
	}

	public void setState(boolean state) {
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		this.state = state;
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String [] getCategories() {
		assert sensorInvariant() : "Invariante della classe non soddisfatto";
		
		String [] categoriesName = new String[categoryList.size()];
		for(int i = 0; i < categoriesName.length; i++) {
			categoriesName[i] = categoryList.get(i).getName();
		}
		return categoriesName;
	}
	
	public boolean isNumeric() {
		return categoryList.get(0).getIsNumeric();
	}
	
	protected boolean sensorInvariant() {
		boolean checkName = name != null && name.length() > 0;
		boolean checkCategory = categoryList != null;
		boolean checkManager = measuredObjects != null;
		
		if(checkName && checkCategory && checkManager) return true;
		return false;
	}
}
