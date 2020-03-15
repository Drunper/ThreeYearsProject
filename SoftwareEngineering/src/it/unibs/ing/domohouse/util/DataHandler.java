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
		
	public boolean hasHousingUnit(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		return housingUnitManager.hasElement(selectedHouse);
	}
	
	public String [] getHousingUnitsList() {
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return housingUnitManager.getListOfElements();
	}
	
	public HousingUnit getHousingUnit(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
	}

	public void addHousingUnit(HousingUnit toAdd) {
		assert toAdd != null;
		housingUnitManager.addElement(toAdd);
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String getHousingUnitString(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return housingUnitManager.getElementString(selectedHouse);
	}

	public String [] getRoomsList(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		assert (HousingUnit) housingUnitManager.getElementByName(selectedHouse) != null;
		
		HousingUnit selected = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		
		return selected.getRoomList();
	}

	public String [] getSensorCategoryList() {
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return sensorCategoryManager.getListOfElements();
	}
	
	public String [] getCategoriesOfASensor(String selectedHouse, String selectedSensor) {
		assert selectedHouse != null && selectedSensor != null; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return house.getCategoriesOfASensor(selectedSensor);
	}
	
	public SensorCategory getSensorCategoryByInfo(String info) {
		assert info != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		for(String cat : sensorCategoryManager.getListOfElements()) {
			SensorCategory sensCat = getSensorCategory(cat);
			
			if(sensCat.getIsNumeric()) {
				NumericSensorCategory numSensCat = (NumericSensorCategory) sensCat;
				String [] detectableInfoList = numSensCat.getDetectableInfoList();
				for(String inf : detectableInfoList) {
					if(info.equalsIgnoreCase(inf)) {
						assert numSensCat != null;
						return numSensCat;
					}
				}
			}
			else {
				NonNumericSensorCategory nonNumSensCat = (NonNumericSensorCategory) sensCat;
				String [] detectableInfoList = nonNumSensCat.getDetectableInfoList();
				for(String inf : detectableInfoList) {
					if(info.equalsIgnoreCase(inf)) {
						assert nonNumSensCat != null;
						return nonNumSensCat;
					}
				}
				
			}
		}
		return null;
	}
	
	public boolean hasNumericSensorCategory() {
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		for(String cat : sensorCategoryManager.getListOfElements()) {
			Manageable m = sensorCategoryManager.getElementByName(cat);
			SensorCategory s = (SensorCategory) m;	
			if(s.getIsNumeric()) 
				return true;
		}	
		return false;
	}
	
	public boolean hasNonNumericSensorCategory() {
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		for(String cat : sensorCategoryManager.getListOfElements()) {
			Manageable m = sensorCategoryManager.getElementByName(cat);
			SensorCategory s = (SensorCategory) m;	
			if(s.getIsNumeric() == false) 
				return true;
		}	
		return false;
	}

	public String getSensorCategoryString(String selectedSensCategory) {
		assert selectedSensCategory != null; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		assert sensorCategoryManager.hasElement(selectedSensCategory) : "sensorCategoryManager di dataHandler non contiene " + selectedSensCategory;
		
		String result = sensorCategoryManager.getElementString(selectedSensCategory);
		
		assert result != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}

	public String [] getActuatorCategoryList() {
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return actuatorCategoryManager.getListOfElements();
	}

	public String getActuatorCategoryString(String selectedActuCategory) {
		assert selectedActuCategory != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		assert actuatorCategoryManager.hasElement(selectedActuCategory) : "actuatorCategoryManager non contiene " + selectedActuCategory;
		
		String result =  actuatorCategoryManager.getElementString(selectedActuCategory);
		
		assert result != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}

	public boolean hasSensorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return sensorCategoryManager.hasElement(category);
	}
	
	public boolean hasActuatorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return actuatorCategoryManager.hasElement(category);
	}

	public void addSensorCategory(SensorCategory cat) {
		assert cat != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = sensorCategoryManager.size();
		
		sensorCategoryManager.addElement(cat);
		
		assert sensorCategoryManager.size() >= pre_size;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}

	public void addActuatorCategory(ActuatorCategory cat) {
		assert cat != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		int pre_size = actuatorCategoryManager.size();
		
		actuatorCategoryManager.addElement(cat);
		
		assert actuatorCategoryManager.size() >= pre_size;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}

	public SensorCategory getSensorCategory(String category) {
		assert category != null; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		assert sensorCategoryManager.hasElement(category) : "sensorCategoryManager non contiene " + category;
		
		SensorCategory s = (SensorCategory)sensorCategoryManager.getElementByName(category);
		
		assert s != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		return s;
	}

	public ActuatorCategory getActuatorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		assert actuatorCategoryManager.hasElement(category) : "actuatorCategoryManager non contiene " + category;
		
		ActuatorCategory act = (ActuatorCategory)actuatorCategoryManager.getElementByName(category);
		
		assert act != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return act;
	}

	public String getRoomString(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null;
		assert selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		String result =  _selectedHouse.getRoomString(selectedRoom);
		
		assert result != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return result;
	}

	public String[] getSensorNames(String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert selectedRoom != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return room.getSensorsNames();
	}

	public String getSensorString(String selectedHouse, String selectedSensor) {
		assert selectedHouse != null;
		assert selectedSensor != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		String result = _selectedHouse.getSensorString(selectedSensor);
		
		assert result != null; 
		return result;
	}

	public String[] getActuatorNames(String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert selectedRoom != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		return room.getActuatorsNames();
	}

	public String getActuatorString(String selectedHouse, String selectedActuator) {
		assert selectedHouse != null;
		assert selectedActuator != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;

		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		String result =  _selectedHouse.getActuatorString(selectedActuator);
		
		assert result != null; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return result; 
	}
	
	public String[] getArtifactNames(String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return room.getArtifactsNames();
	}

	public String getArtifactString(String selectedHouse, String selectedArtifact) {
		assert selectedHouse != null && selectedArtifact != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		String s = _selectedHouse.getArtifactString(selectedArtifact);
		
		assert s != null; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return s; 
	}

	public void changeHouseDescription(String selectedHouse, String descr) {
		assert descr != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.setDescr(descr);
		
		assert _selectedHouse.getDescr() != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}

	public boolean hasRoom(String selectedHouse, String name) {
		assert name != null && selectedHouse != null ;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasRoom(name);
	}

	public void addRoom(String selectedHouse, Room toAdd) {
		assert toAdd != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addRoom(toAdd);
		
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}

	public void changeRoomDescription(String selectedHouse, String selectedRoom, String descr) {
		assert selectedHouse != null;
		assert selectedRoom != null && descr != null; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.setRoomDescription(selectedRoom, descr);

		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}

	public void addSensor(String selectedHouse, String location, Sensor sensor) {
		assert selectedHouse != null;
		assert location != null && sensor != null && sensor.getName() != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addSensor(location, sensor);
		
	    assert _selectedHouse.getRoom(location).getSensorByName(sensor.getName()) != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}

	public boolean hasRoomOrArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null ; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasRoomOrArtifact(name);
	}

	public boolean hasSensor(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasSensor(name);
	}
	
	public boolean hasActuator(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasActuator(name);
	}

	public boolean hasArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.hasArtifact(name);
	}
	
	public boolean hasRule(String selectedHouse, String rule) {
		assert selectedHouse != null && rule != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		
		assert _selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return _selectedHouse.hasRule(rule);
	}
	
	public boolean isElementARoom(String selectedHouse, String toAssoc) {
		assert toAssoc != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String selectedHouse, String toAssoc, String category) {
		assert toAssoc != null && category != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		return _selectedHouse.isAssociatedWith(toAssoc, category);
	}

	public void addAssociation(String selectedHouse, String object, String category) {
		assert object != null && category != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		assert housingUnitManager.getElementByName(selectedHouse) != null;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addAssociationWith(object, category);
	
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}

	public Room getRoom(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		assert housingUnitManager.getElementByName(selectedHouse) != null;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Room room = _selectedHouse.getRoom(name);
		
		assert room != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		return room;
	}

	public Artifact getArtifact(String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		Artifact art = _selectedHouse.getArtifact(name);
		
		assert art != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return art;
	}

	public void addArtifact(String selectedHouse, String location, Artifact toAdd) {
		assert location != null && toAdd != null && toAdd.getName() != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addArtifact(toAdd, location);
		
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void addActuator(String selectedHouse, String location, Actuator toAdd) {
		assert location != null && toAdd != null && selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		_selectedHouse.addActuator(toAdd, location);
		
		assert _selectedHouse != null; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;	
	}

	public boolean hasOperatingMode(String name) {
		assert name != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return OperatingModesHandler.hasOperatingMode(name);
	}
	
	public boolean doesRoomOrArtifactExist(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		
		assert _selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return _selectedHouse.doesRoomOrArtifactExist();
	}
	
	public boolean doesRoomExist(String selectedHouse) {
		assert selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		
		assert _selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return _selectedHouse.doesRoomExist();
	}
	
	public boolean doesArtifactExist(String selectedHouse) {
		assert selectedHouse != null; 
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		HousingUnit _selectedHouse =  (HousingUnit) housingUnitManager.getElementByName(selectedHouse);
		
		assert _selectedHouse != null;
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		return _selectedHouse.doesArtifactExist();
	}
	
	public void updateRulesState() {
		assert dataHandlerInvariant() : Strings.WRONG_INVARIANT;
		
		for(String houseName : housingUnitManager.getListOfElements()) {
			HousingUnit house = (HousingUnit) housingUnitManager.getElementByName(houseName);
			house.updateRulesState();
		}
	}
	
	public boolean dataHandlerInvariant() {
		return sensorCategoryManager != null && actuatorCategoryManager != null && housingUnitManager != null; 
	}
}