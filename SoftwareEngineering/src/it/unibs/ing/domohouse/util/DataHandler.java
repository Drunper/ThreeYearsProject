package it.unibs.ing.domohouse.util;

import java.io.Serializable;

import it.unibs.ing.domohouse.components.*;
import it.unibs.ing.domohouse.interfaces.Manageable;

public class DataHandler implements Serializable {

	private static final long serialVersionUID = 830399600665259268L;
	private Manager sensorCategoryManager;
	private Manager actuatorCategoryManager;
	private Manager housingUnitManager;	
	/*
	 * invariante sensorCategoryManager != null, actuatorCategoryManager != null, housingUnitManager != null
	 */
	public DataHandler () {
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		housingUnitManager = new Manager();		
	}
	
	public boolean hasHouse(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return housingUnitManager.hasEntry(selectedHouse);
	}
	
	public String[] getHouseList() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return housingUnitManager.namesList();
	}

	public void addHouse(HousingUnit toAdd) {
		assert toAdd != null;
		housingUnitManager.addEntry(toAdd);
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public String getHousingUnitString(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return housingUnitManager.getElementString(selectedHouse);
	}

	public String[] getRoomList(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert (HousingUnit) housingUnitManager.getElementByName(selectedHouse) != null;
		
		HousingUnit selected = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		
		return selected.roomList();
	}

	public String[] getSensorCategoryList() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return sensorCategoryManager.namesList();
	}
	
	//Cast brutale
	public boolean hasNumericSensorCategory() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		for(String cat : sensorCategoryManager.namesList()) {
			Manageable m = sensorCategoryManager.getElementByName(cat);
			SensorCategory s = (SensorCategory) m;	
			if(s.getIsNumeric()) return true;
		}	
		return false;
	}
	
	public boolean hasNonNumericSensorCategory() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		for(String cat : sensorCategoryManager.namesList()) {
			Manageable m = sensorCategoryManager.getElementByName(cat);
			SensorCategory s = (SensorCategory) m;	
			if(s.getIsNumeric() == false) return true;
		}	
		return false;
	}

	public String getSensorCategoryString(String selectedSensCategory) {
		assert selectedSensCategory != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert sensorCategoryManager.hasEntry(selectedSensCategory) : "sensorCategoryManager di dataHandler non contiene " + selectedSensCategory;
		
		String result = sensorCategoryManager.getElementString(selectedSensCategory);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public String[] getActuatorCategoryList() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return actuatorCategoryManager.namesList();
	}

	public String getActuatorCategoryString(String selectedActuCategory) {
		assert selectedActuCategory != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert actuatorCategoryManager.hasEntry(selectedActuCategory) : "actuatorCategoryManager non contiene " + selectedActuCategory;
		
		String result =  actuatorCategoryManager.getElementString(selectedActuCategory);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public boolean hasSensorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return sensorCategoryManager.hasEntry(category);
	}
	
	public boolean hasActuatorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return actuatorCategoryManager.hasEntry(category);
	}

	public void addSensorCategory(SensorCategory cat) {
		assert cat != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = sensorCategoryManager.size();
		
		sensorCategoryManager.addEntry(cat);
		
		assert sensorCategoryManager.size() >= pre_size;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void addActuatorCategory(ActuatorCategory cat) {
		assert cat != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = actuatorCategoryManager.size();
		
		actuatorCategoryManager.addEntry(cat);
		
		assert actuatorCategoryManager.size() >= pre_size;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public SensorCategory getSensorCategory(String category) {
		assert category != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert sensorCategoryManager.hasEntry(category) : "sensorCategoryManager non contiene " + category;
		
		SensorCategory s = (SensorCategory)sensorCategoryManager.getElementByName(category);
		
		assert s != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return s;
	}

	public ActuatorCategory getActuatorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert actuatorCategoryManager.hasEntry(category) : "actuatorCategoryManager non contiene " + category;
		
		ActuatorCategory act = (ActuatorCategory)actuatorCategoryManager.getElementByName(category);
		
		assert act != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return act;
	}

	public String getRoomString(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null;
		assert selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		String result =  _selectedHouse.getRoomString(selectedRoom);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public String[] getSensorNames(String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert selectedRoom != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return room.getSensorsNames();
	}

	public String getSensorString(String selectedHouse, String selectedSensor) {
		assert selectedHouse != null;
		assert selectedSensor != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		String result = _selectedHouse.getSensorString(selectedSensor);
		
		assert result != null; 
		return result;
	}

	public String[] getActuatorNames(String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert selectedRoom != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return room.getActuatorsNames();
	}

	public String getActuatorString(String selectedHouse, String selectedActuator) {
		assert selectedHouse != null;
		assert selectedActuator != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";

		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		String result =  _selectedHouse.getActuatorString(selectedActuator);
		
		assert result != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result; 
	}
	

	public String[] getArtifactNames(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return room.getArtifactsNames();
	}

	public String getArtifactString(String selectedHouse, String selectedArtifact) {
		assert selectedHouse != null && selectedArtifact != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		String s = _selectedHouse.getArtifactString(selectedArtifact);
		
		assert s != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return s; 
	}

	public void changeHouseDescription(String selectedHouse, String descr) {
		assert descr != null && selectedHouse != null;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.setDescr(descr);
		
		assert _selectedHouse.getDescr() != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public boolean hasRoom(String selectedHouse, String name) {
		assert name != null && selectedHouse != null ;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		
		return _selectedHouse.hasRoom(name);
	}

	public void addRoom(String selectedHouse, Room toAdd) {
		assert toAdd != null && selectedHouse != null;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addRoom(toAdd);
		
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void changeRoomDescription(String selectedHouse, String selectedRoom, String descr) {
		assert selectedHouse != null;
		assert selectedRoom != null && descr != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.setRoomDescription(selectedRoom, descr);

		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void addSensor(String selectedHouse, String location, Sensor sensor) {
		assert selectedHouse != null;
		assert location != null && sensor != null && sensor.getName() != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addSensor(location, sensor);
		
	    assert _selectedHouse.getRoom(location).getSensorByName(sensor.getName()) != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public boolean hasRoomOrArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null ; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		
		return _selectedHouse.hasRoomOrArtifact(name);
	}

	public boolean hasSensor(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasSensor(name);
	}
	
	public boolean hasActuator(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasActuator(name);
	}

	public boolean hasArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasArtifact(name);
	}

	public boolean isElementARoom(String selectedHouse, String toAssoc) {
		assert toAssoc != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String selectedHouse, String toAssoc, String category) {
		assert toAssoc != null && category != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isAssociated(toAssoc, category);
	}

	public void addAssociation(String selectedHouse, String object, String category) {
		assert object != null && category != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnitManager.getElementByName(selectedHouse) != null;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addAssociation(object, category);
	
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public Room getRoom(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnitManager.getElementByName(selectedHouse) != null;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(name);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return room;
	}

	public Artifact getArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Artifact art = _selectedHouse.getArtifact(name);
		
		assert art != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return art;
	}

	public void addArtifact(String selectedHouse, String location, Artifact toAdd) {
		assert location != null && toAdd != null && toAdd.getName() != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addArtifact(toAdd, location);
		
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void addActuator(String selectedHouse, String location, Actuator toAdd) {
		assert location != null && toAdd != null && selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addActuator(toAdd, location);
		
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
	}

	public boolean hasOperatingMode(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return OperatingModesHandler.hasOperatingMode(name);
	}
	
	public boolean isThereRoomOrArtifact(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isThereRoomOrArtifact();
	}
	
	public boolean isThereRoom(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isThereRoom();
	}
	
	public boolean isThereArtifact(String selectedHouse) {
		assert selectedHouse != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isThereArtifact();
	}
	public boolean dataHandlerInvariant() {
		boolean checkManagers = sensorCategoryManager != null && actuatorCategoryManager != null && housingUnitManager != null;
		return checkManagers; 
	}
}