package it.unibs.ing.domohouse.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.unibs.ing.domohouse.model.components.elements.Actuator;
import it.unibs.ing.domohouse.model.components.elements.Artifact;
import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.Room;
import it.unibs.ing.domohouse.model.components.elements.Sensor;
import it.unibs.ing.domohouse.model.components.elements.User;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.db.DatabaseLoader;
import it.unibs.ing.domohouse.model.ModelStrings;

public class DataFacade implements Serializable {

	private static final long serialVersionUID = 830399600665259268L;
	private Manager sensorCategoryManager;
	private Manager actuatorCategoryManager;
	private Manager userManager;
	private boolean firstStart;
	private DatabaseLoader databaseLoader;
	private Connector connector;

	/*
	 * invariante sensorCategoryManager != null, actuatorCategoryManager != null,
	 * housingUnitManager != null
	 */
	public DataFacade(Connector connector) {
		this.connector = connector;
		databaseLoader = new DatabaseLoader(connector);
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		userManager = new Manager();
		this.firstStart = true;
	}

	public void setFirstStart(boolean flag) {
		this.firstStart = flag;
	}

	public boolean getFirstStart() {
		return this.firstStart;
	}
	
	public void addUser(User user) {
		userManager.addElement(user);
	}
	
	public boolean hasUser(String selectedUser) {
		return userManager.hasElement(selectedUser);
	}
	
	public User getUser(String selectedUser) {
		return (User) userManager.getElementByName(selectedUser);
	}
	
	public Set<String> getUserSet() {
		return userManager.getListOfElements();
	}
	
	public boolean hasHousingUnit(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).hasHousingUnit(selectedHouse);
	}

	public Set<String> getHousingUnitsList(String selectedUser) {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return getUser(selectedUser).getHousingUnitsList();
	}

	public boolean doesHousingUnitExist(String selectedUser) {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return getUser(selectedUser).doesHousingUnitExist();
	}

	public HousingUnit getHousingUnit(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return getUser(selectedUser).getHousingUnit(selectedHouse);
	}

	public void addHousingUnit(String selectedUser, HousingUnit toAdd) {
		assert toAdd != null;
		getUser(selectedUser).addHousingUnit(toAdd);
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Set<String> getRoomsList(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).getRoomsList(selectedHouse);
	}

	public Set<String> getSensorCategoryList() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorCategoryManager.getListOfElements();
	}

	public boolean doesSensorCategoryExist() {
		return !sensorCategoryManager.isEmpty();
	}

	public SensorCategory getSensorCategoryByInfo(String info) {
		assert info != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		for (String cat : sensorCategoryManager.getListOfElements()) {
			SensorCategory sensCat = getSensorCategory(cat);

			Set<String> detectableInfoList = sensCat.getInfoStrategySet();
			for (String inf : detectableInfoList) {
				if (info.equalsIgnoreCase(inf)) {
					assert sensCat != null;
					return sensCat;
				}
			}
		}
		return null;
	}

	public Set<String> getActuatorCategoryList() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorCategoryManager.getListOfElements();
	}

	public boolean doesActuatorCategoryExist() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return !actuatorCategoryManager.isEmpty();
	}

	public boolean hasSensorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorCategoryManager.hasElement(category);
	}

	public boolean hasActuatorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorCategoryManager.hasElement(category);
	}

	public void addSensorCategory(SensorCategory cat) {
		assert cat != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = sensorCategoryManager.size();

		sensorCategoryManager.addElement(cat);

		assert sensorCategoryManager.size() >= pre_size;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuatorCategory(ActuatorCategory cat) {
		assert cat != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = actuatorCategoryManager.size();

		actuatorCategoryManager.addElement(cat);

		assert actuatorCategoryManager.size() >= pre_size;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public SensorCategory getSensorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert sensorCategoryManager.hasElement(category) : "sensorCategoryManager non contiene " + category;

		SensorCategory s = (SensorCategory) sensorCategoryManager.getElementByName(category);

		assert s != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return s;
	}

	public ActuatorCategory getActuatorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert actuatorCategoryManager.hasElement(category) : "actuatorCategoryManager non contiene " + category;

		ActuatorCategory act = (ActuatorCategory) actuatorCategoryManager.getElementByName(category);

		assert act != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return act;
	}
	
	public String getCategoryOfASensor(String selectedUser, String selectedHouse, String selectedSensor) {
		assert selectedHouse != null && selectedSensor != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).getCategoryOfASensor(selectedHouse, selectedSensor);
	}


	public Sensor getSensor(String selectedUser, String selectedHouse, String selectedSensor) {
		return getUser(selectedUser).getSensor(selectedHouse, selectedSensor);
	}

	public Set<String> getSensorNames(String selectedUser, String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).getSensorNames(selectedHouse, selectedRoom);
	}

	public boolean doesSensorExist(String selectedUser, String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).doesSensorExist(selectedHouse, selectedRoom);
	}

	public Actuator getActuator(String selectedUser, String selectedHouse, String selectedActuator) {
		return getUser(selectedUser).getActuator(selectedHouse, selectedActuator);
	}

	public Set<String> getActuatorNames(String selectedUser, String selectedHouse, String selectedRoom) {
		assert selectedHouse != null;
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).getActuatorNames(selectedHouse, selectedRoom);
	}

	public boolean doesActuatorExist(String selectedUser, String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).doesActuatorExist(selectedHouse, selectedRoom);
	}
	
	public Set<String> getActuatorOperatingModes(String selectedUser, String selectedHouse, String selectedActuator) {
		return getUser(selectedUser).getActuatorOperatingModes(selectedHouse, selectedActuator);
	}

	public boolean doesArtifactExist(String selectedUser, String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).doesArtifactExist(selectedHouse, selectedRoom);
	}

	public Set<String> getArtifactNames(String selectedUser, String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).getArtifactNames(selectedHouse, selectedRoom);
	}

	public void changeHouseDescription(String selectedUser, String selectedHouse, String descr) {
		assert descr != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		getUser(selectedUser).changeHouseDescription(selectedHouse, descr);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasRoom(String selectedUser, String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).hasRoom(selectedHouse, selectedRoom);
	}

	public void addRoom(String selectedUser, String selectedHouse, Room toAdd) {
		assert toAdd != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		getUser(selectedUser).addRoom(selectedHouse, toAdd);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void changeRoomDescription(String selectedUser, String selectedHouse, String selectedRoom, String descr) {
		assert selectedHouse != null;
		assert selectedRoom != null && descr != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		
		getUser(selectedUser).changeRoomDescription(selectedHouse, selectedRoom, descr);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addSensor(String selectedUser, String selectedHouse, String location, Sensor sensor) {
		assert selectedHouse != null;
		assert location != null && sensor != null && sensor.getName() != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		getUser(selectedUser).addSensor(selectedHouse, location, sensor);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasRoomOrArtifact(String selectedUser, String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).hasRoomOrArtifact(selectedHouse, name);
	}

	public boolean hasSensor(String selectedUser, String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).hasSensor(selectedHouse, name);
	}

	public boolean hasActuator(String selectedUser, String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).hasActuator(selectedHouse, name);
	}

	public boolean hasArtifact(String selectedUser, String selectedHouse, String name) {
		assert name != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).hasArtifact(selectedHouse, name);
	}

	public boolean hasRule(String selectedUser, String selectedHouse, String rule) {
		assert selectedHouse != null && rule != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).hasRule(selectedHouse, rule);
	}

	public boolean isElementARoom(String selectedUser, String selectedHouse, String toAssoc) {
		assert toAssoc != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).isElementARoom(selectedHouse, toAssoc);
	}

	public boolean isAssociated(String selectedUser, String selectedHouse, String toAssoc, String category) {
		assert toAssoc != null && category != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).isAssociated(selectedHouse, toAssoc, category);
	}

	public void addAssociation(String selectedUser, String selectedHouse, String object, String category) {
		assert object != null && category != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		

		getUser(selectedUser).addAssociation(selectedHouse, object, category);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Room getRoom(String selectedUser, String selectedHouse, String selectedRoom) {
		assert selectedRoom != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).getRoom(selectedHouse, selectedRoom);
	}

	public Artifact getArtifact(String selectedUser, String selectedHouse, String selectedArtifact) {
		assert selectedArtifact != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).getArtifact(selectedHouse, selectedArtifact);
	}

	public void addArtifact(String selectedUser, String selectedHouse, String location, Artifact toAdd) {
		assert location != null && toAdd != null && toAdd.getName() != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		getUser(selectedUser).addArtifact(selectedHouse, location, toAdd);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuator(String selectedUser, String selectedHouse, String location, Actuator toAdd) {
		assert location != null && toAdd != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		
		getUser(selectedUser).addActuator(selectedHouse, location, toAdd);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public boolean hasOperatingMode(String name) {
		assert name != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return OperatingModesManager.hasOperatingMode(name);
	}

	public boolean doesRoomOrArtifactExist(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).doesRoomOrArtifactExist(selectedHouse);
	}

	public boolean doesRoomExist(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).doesRoomExist(selectedHouse);
	}

	public boolean doesArtifactExist(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).doesArtifactExist(selectedHouse);
	}

	public void updateRulesState(String selectedUser, String selectedHouse) {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		getUser(selectedUser).updateRulesState(selectedHouse);
	}

	public List<Rule> getEnabledRulesList() {
		List<Rule> rules = new ArrayList<>();
		for (String user : getUserSet())
			rules.addAll(getUser(user).getEnabledRulesList());
		return rules;
	}

	public List<String> getEnabledRulesStrings(String selectedUser, String selectedHouse) {
		return getUser(selectedUser).getEnabledRulesStrings(selectedHouse);
	}

	public List<String> getRulesStrings(String selectedUser, String selectedHouse) {
		return getUser(selectedUser).getRulesStrings(selectedHouse);
	}

	public void addRule(String selectedUser, String selectedHouse, Rule toAdd) {
		getUser(selectedUser).addRule(selectedHouse, toAdd);
	}

	public Set<String> getRulesNames(String selectedUser, String selectedHouse) {
		return getUser(selectedUser).getRulesNames(selectedHouse);
	}

	public void changeRuleState(String selectedUser, String selectedHouse, String rule) {
		getUser(selectedUser).changeRuleState(selectedHouse, rule);
	}

	public boolean doesRuleExist(String selectedUser, String selectedHouse) {
		return getUser(selectedUser).doesRuleExist(selectedHouse);
	}

	private boolean dataFacadeInvariant() {
		return sensorCategoryManager != null && actuatorCategoryManager != null && userManager != null;
	}
}