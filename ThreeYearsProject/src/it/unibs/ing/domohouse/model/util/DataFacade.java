package it.unibs.ing.domohouse.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import it.unibs.ing.domohouse.model.components.elements.*;
import it.unibs.ing.domohouse.model.components.properties.*;
import it.unibs.ing.domohouse.model.components.rule.*;
import it.unibs.ing.domohouse.model.db.*;
import it.unibs.ing.domohouse.model.db.persistent.NewObjectState;
import it.unibs.ing.domohouse.model.db.persistent.PersistentActuator;
import it.unibs.ing.domohouse.model.db.persistent.PersistentActuatorCategory;
import it.unibs.ing.domohouse.model.db.persistent.PersistentArtifact;
import it.unibs.ing.domohouse.model.db.persistent.PersistentHousingUnit;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;
import it.unibs.ing.domohouse.model.db.persistent.PersistentRoom;
import it.unibs.ing.domohouse.model.db.persistent.PersistentRule;
import it.unibs.ing.domohouse.model.db.persistent.PersistentSensor;
import it.unibs.ing.domohouse.model.db.persistent.PersistentSensorCategory;
import it.unibs.ing.domohouse.model.db.persistent.PersistentUser;
import it.unibs.ing.domohouse.model.ModelStrings;

public class DataFacade {

	private Manager sensorCategoryManager;
	private Manager actuatorCategoryManager;
	private Manager userManager;
	private Map<String, String> propertiesMap;
	private Map<Integer, DoubleInfoStrategy> numericInfoStrategiesMap;
	private Map<Integer, StringInfoStrategy> nonNumericInfoStrategiesMap;
	private DatabaseFacade databaseFacade;
	private ObjectFactory objectFactory;

	public DataFacade(Connector connector) throws Exception {
		objectFactory = new ObjectFactory(new RuleParser());
		databaseFacade = new DatabaseFacade(connector, new DatabaseLoader(connector, objectFactory, this));
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		userManager = new Manager();
		propertiesMap = databaseFacade.getAllProperties();
		numericInfoStrategiesMap = databaseFacade.getNumericInfoStrategies();
		nonNumericInfoStrategiesMap = databaseFacade.getNonNumericInfoStrategies();
	}

	public void loadCategories() throws Exception {
		for (SensorCategory cat : databaseFacade.loadSensorCategories())
			sensorCategoryManager.addElement(cat);
		for (ActuatorCategory cat : databaseFacade.loadActuatorCategories())
			actuatorCategoryManager.addElement(cat);
	}

	public void addUser(String user) {
		User userObj = new User(user);
		PersistentObject saveable = new PersistentUser(userObj, new NewObjectState());
		addSaveable(saveable);
		userObj.setSaveable(saveable);
		userManager.addElement(userObj);
	}

	public boolean hasUser(String selectedUser) throws Exception {
		if (!userManager.hasElement(selectedUser))
			userManager.addElement(databaseFacade.loadUser(selectedUser));

		return userManager.hasElement(selectedUser);
	}

	public User getUser(String selectedUser) {
		return (User) userManager.getElement(selectedUser);
	}

	public Set<String> getUserSet() {
		return userManager.getSetOfElements();
	}

	public void loadHousingUnits(String selectedUser) throws Exception {
		for (HousingUnit house : databaseFacade.loadHousingUnits(selectedUser)) {
			getUser(selectedUser).addHousingUnit(house);
			databaseFacade.addAssociations(selectedUser, house.getName());
		}
	}

	public boolean hasHousingUnit(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).hasHousingUnit(selectedHouse);
	}

	public Set<String> getHousingUnitSet(String selectedUser) {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return getUser(selectedUser).getHousingUnitsList();
	}

	public boolean doesHousingUnitExist(String selectedUser) throws Exception {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		if (!getUser(selectedUser).doesHousingUnitExist())
			loadHousingUnits(selectedUser);

		return getUser(selectedUser).doesHousingUnitExist();
	}

	public HousingUnit getHousingUnit(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return getUser(selectedUser).getHousingUnit(selectedHouse);
	}

	public void addHousingUnit(String selectedUser, String selectedHouse, String descr, String type) {
		HousingUnit house = new HousingUnit(selectedHouse, descr, type, selectedUser);
		PersistentObject saveable = new PersistentHousingUnit(house, new NewObjectState());
		addSaveable(saveable);
		house.setSaveable(saveable);
		getUser(selectedUser).addHousingUnit(house);
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public Set<String> getRoomsSet(String selectedUser, String selectedHouse) {
		assert selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return getUser(selectedUser).getRoomsList(selectedHouse);
	}

	public Set<String> getSensorCategoryList() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensorCategoryManager.getSetOfElements();
	}

	public boolean doesSensorCategoryExist() {
		return !sensorCategoryManager.isEmpty();
	}

	public List<String> getSensorCategoriesByInfo(String info) {
		assert info != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		List<String> catList = new ArrayList<>();
		for (String cat : sensorCategoryManager.getSetOfElements()) {
			SensorCategory sensCat = getSensorCategory(cat);

			for (String inf : sensCat.getInfoStrategySet()) {
				if (info.equalsIgnoreCase(inf)) {
					assert sensCat != null;
					catList.add(cat);
				}
			}
		}
		return catList;
	}

	public Set<String> getActuatorCategoryList() {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuatorCategoryManager.getSetOfElements();
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

	public void addSensorCategory(String name, String abbreviation, String manufacturer,
			Map<String, InfoStrategy> infoDomainMap, Map<String, String> measurementUnitMap) {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = sensorCategoryManager.size();

		SensorCategory cat = objectFactory.createSensorCategory(name, abbreviation, manufacturer, infoDomainMap,
				measurementUnitMap);
		PersistentObject saveable = new PersistentSensorCategory(cat, new NewObjectState());
		addSaveable(saveable);
		cat.setSaveable(saveable);
		sensorCategoryManager.addElement(cat);

		assert sensorCategoryManager.size() >= pre_size;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuatorCategory(String name, String abbreviation, String manufacturer, List<String> listOfModes,
			String defaultMode) {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		int pre_size = actuatorCategoryManager.size();

		ActuatorCategory cat = objectFactory.createActuatorCategory(name, abbreviation, manufacturer, listOfModes,
				defaultMode);
		PersistentObject saveable = new PersistentActuatorCategory(cat, new NewObjectState());
		addSaveable(saveable);
		cat.setSaveable(saveable);
		actuatorCategoryManager.addElement(cat);
		assert actuatorCategoryManager.size() >= pre_size;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public SensorCategory getSensorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert sensorCategoryManager.hasElement(category) : "sensorCategoryManager non contiene " + category;

		SensorCategory s = (SensorCategory) sensorCategoryManager.getElement(category);

		assert s != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		return s;
	}

	public ActuatorCategory getActuatorCategory(String category) {
		assert category != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
		assert actuatorCategoryManager.hasElement(category) : "actuatorCategoryManager non contiene " + category;

		ActuatorCategory act = (ActuatorCategory) actuatorCategoryManager.getElement(category);

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

	public void addRoom(String selectedUser, String selectedHouse, String selectedRoom, String descr,
			Map<String, String> propertiesMap) {
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		Room room = new Room(selectedRoom, descr, propertiesMap);
		PersistentObject saveable = new PersistentRoom(selectedUser, selectedHouse, room, new NewObjectState());
		addSaveable(saveable);
		room.setSaveable(saveable);
		getUser(selectedUser).addRoom(selectedHouse, room);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void changeRoomDescription(String selectedUser, String selectedHouse, String selectedRoom, String descr) {
		assert selectedHouse != null;
		assert selectedRoom != null && descr != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		getUser(selectedUser).changeRoomDescription(selectedHouse, selectedRoom, descr);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addSensor(String selectedUser, String selectedHouse, String location, String name, String category,
			boolean roomOrArtifact, List<String> objectList) {
		assert selectedHouse != null;
		assert location != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		Sensor sens = objectFactory.createSensor(name, getSensorCategory(category), roomOrArtifact,
				getDevicesObjectList(selectedUser, selectedHouse, roomOrArtifact, objectList));

		PersistentObject saveable = new PersistentSensor(selectedUser, selectedHouse, location, sens, new NewObjectState());
		addSaveable(saveable);
		sens.setSaveable(saveable);
		getUser(selectedUser).addSensor(selectedHouse, location, sens);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	private List<Gettable> getDevicesObjectList(String selectedUser, String selectedHouse, boolean roomOrArtifact,
			List<String> objectList) {
		List<Gettable> gettableList = objectList.stream().map(s -> {
			if (roomOrArtifact)
				return getRoom(selectedUser, selectedHouse, s);
			else
				return getArtifact(selectedUser, selectedHouse, s);
		}).collect(Collectors.toList());
		return gettableList;
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

	public void addArtifact(String selectedUser, String selectedHouse, String location, String selectedArtifact,
			String descr, Map<String, String> propertiesMap) {
		assert location != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		Artifact artifact = new Artifact(selectedArtifact, descr, propertiesMap);
		PersistentObject saveable = new PersistentArtifact(selectedUser, selectedHouse, location, artifact, new NewObjectState());
		addSaveable(saveable);
		artifact.setSaveable(saveable);
		getUser(selectedUser).addArtifact(selectedHouse, location, artifact);

		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;
	}

	public void addActuator(String selectedUser, String selectedHouse, String location, String selectedActuator,
			String category, boolean roomOrArtifact, List<String> objectList) {
		assert location != null && selectedHouse != null;
		assert dataFacadeInvariant() : ModelStrings.WRONG_INVARIANT;

		Actuator act = objectFactory.createActuator(selectedActuator, getActuatorCategory(category), roomOrArtifact,
				getDevicesObjectList(selectedUser, selectedHouse, roomOrArtifact, objectList));
		PersistentObject saveable = new PersistentActuator(selectedUser, selectedHouse, location, act, new NewObjectState());
		addSaveable(saveable);
		act.setSaveable(saveable);
		getUser(selectedUser).addActuator(selectedHouse, location, act);

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

	public void addRule(String selectedUser, String selectedHouse, String name, String antString, String consString,
			List<String> involvedSensors, List<String> involvedActuators, State state) {
		Rule rule = objectFactory.createRule(name, antString, consString,
				getRuleSensorsList(selectedUser, selectedHouse, involvedSensors),
				getRuleActuatorsList(selectedUser, selectedHouse, involvedActuators), state);
		PersistentObject saveable = new PersistentRule(selectedUser, selectedHouse, rule, new NewObjectState());
		addSaveable(saveable);
		rule.setSaveable(saveable);
		getUser(selectedUser).addRule(selectedHouse, rule);
	}

	public void addRule(String selectedUser, String selectedHouse, String name, String antString, String consString,
			List<String> involvedSensors, List<String> involvedActuators) {
		Rule rule = objectFactory.createRule(name, antString, consString,
				getRuleSensorsList(selectedUser, selectedHouse, involvedSensors),
				getRuleActuatorsList(selectedUser, selectedHouse, involvedActuators));
		PersistentObject saveable = new PersistentRule(selectedUser, selectedHouse, rule, new NewObjectState());
		addSaveable(saveable);
		rule.setSaveable(saveable);
		getUser(selectedUser).addRule(selectedHouse, rule);
	}

	private List<Actuator> getRuleActuatorsList(String selectedUser, String selectedHouse,
			List<String> involvedActuators) {
		List<Actuator> actuators = involvedActuators.stream().map(s -> getActuator(selectedUser, selectedHouse, s))
				.collect(Collectors.toList());
		return actuators;
	}

	private List<Sensor> getRuleSensorsList(String selectedUser, String selectedHouse, List<String> involvedSensors) {
		List<Sensor> sensors = involvedSensors.stream().map(s -> getSensor(selectedUser, selectedHouse, s))
				.collect(Collectors.toList());
		return sensors;
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

	public void saveData() throws Exception {
		databaseFacade.saveData();
	}

	public void addSaveable(PersistentObject saveable) {
		databaseFacade.addSaveable(saveable);
	}
	
	public Set<String> getPropertiesSet() {
		return propertiesMap.keySet();
	}
	
	public boolean hasProperty(String property) {
		return propertiesMap.containsKey(property);
	}
	
	public void addProperty(String propertyName, String defaultValue) throws Exception {
		propertiesMap.put(propertyName, defaultValue);
		databaseFacade.addProperty(propertyName, defaultValue);
	}

	public String getPropertyDefaultValue(String propertyName) {
		return propertiesMap.get(propertyName);
	}
	
	public Set<String> getNumericInfoStrategySet() {
		Set<String> set = new HashSet<>();
		for(Entry<Integer, DoubleInfoStrategy> entry : numericInfoStrategiesMap.entrySet()) {
			set.add(entry.getKey() + " " + entry.getValue().getMeasuredProperty() + " " + entry.getValue().toString());
		}
		return set;
	}
	
	public Set<String> getNonNumericInfoStrategySet() {
		Set<String> set = new HashSet<>();
		for(Entry<Integer, StringInfoStrategy> entry : nonNumericInfoStrategiesMap.entrySet()) {
			set.add(entry.getKey() + " " + entry.getValue().getMeasuredProperty() + " " + entry.getValue().toString());
		}
		return set;
	}
	
	public int getCurrentMaxID() {
		return nonNumericInfoStrategiesMap.size() + numericInfoStrategiesMap.size();
	}
	
	public boolean hasNumericInfoStrategy(int ID) {
		return numericInfoStrategiesMap.containsKey(ID);
	}
	
	public boolean hasNonNumericInfoStrategy(int ID) {
		return nonNumericInfoStrategiesMap.containsKey(ID);
	}
	
	public void addNumericInfoStrategy(int ID, DoubleInfoStrategy infoStrategy) throws Exception {
		numericInfoStrategiesMap.put(ID, infoStrategy);
		databaseFacade.addNumericInfoStrategy(infoStrategy);
	}
	
	public void addNonNumericInfoStrategy(int ID, StringInfoStrategy infoStrategy) throws Exception {
		nonNumericInfoStrategiesMap.put(ID, infoStrategy);
		databaseFacade.addNonNumericInfoStrategy(infoStrategy);
	}

	public DoubleInfoStrategy getNumericInfoStrategy(int ID) {
		return numericInfoStrategiesMap.get(ID);
	}
	
	public StringInfoStrategy getNonNumericInfoStrategy(int ID) {
		return nonNumericInfoStrategiesMap.get(ID);
	}

	public Set<String> getArtifactSet(String user, String selectedHouse) {
		return getUser(user).getArtifactSet(selectedHouse);
	}
	
	public Set<String> getSensorSet(String user, String selectedHouse) {
		return getUser(user).getSensorSet(selectedHouse);
	}
	
	public Set<String> getActuatorSet(String user, String selectedHouse) {
		return getUser(user).getActuatorSet(selectedHouse);
	}

	public void removeRule(String user, String selectedHouse, String selectedRule) {
		getUser(user).removeRule(selectedHouse, selectedRule);
	}

	public boolean ruleContainsSensor(String user, String selectedHouse, String selectedRule, String selectedSensor) {
		return getUser(user).ruleContainsSensor(selectedHouse, selectedRule, selectedSensor);
	}
	
	public boolean ruleContainsActuator(String user, String selectedHouse, String selectedRule, String selectedActuator) {
		return getUser(user).ruleContainsActuator(selectedHouse, selectedRule, selectedActuator);
	}

	public boolean isMeasuringRoom(String user, String selectedHouse, String selectedSensor) {
		return getUser(user).isMeasuringRoom(selectedHouse, selectedSensor);
	}
	
	public boolean isControllingRoom(String user, String selectedHouse, String selectedActuator) {
		return getUser(user).isControllingRoom(selectedHouse, selectedActuator);
	}

	public Set<String> getMeasuredObjectSet(String user, String selectedHouse, String selectedSensor) {
		return getUser(user).getMeasuredObjectSet(selectedHouse, selectedSensor);
	}

	public void removeRoomAssociationWithCategory(String user, String selectedHouse, String room, String category) {
		getUser(user).removeRoomAssociationWithCategory(selectedHouse, room, category);
	}

	public void removeArtifactAssociationWithCategory(String user, String selectedHouse, String artifact,
			String category) {
		getUser(user).removeArtifactAssociationWithCategory(selectedHouse, artifact, category);
	}

	public void removeSensor(String user, String selectedHouse, String selectedRoom, String selectedSensor) {
		getUser(user).removeSensor(selectedHouse, selectedRoom, selectedSensor);
	}

	public boolean isSensorInstanceOf(String user, String selectedHouse, String selectedSensor,
			String selectedCategory) {
		return getUser(user).isSensorInstanceOf(selectedHouse, selectedSensor, selectedCategory);
	}

	public String getRoomOfSensor(String user, String selectedHouse, String selectedSensor) {
		return getUser(user).getRoomOfSensor(selectedHouse, selectedSensor);
	}

	public void removeSensorCategory(String selectedCategory) {
		sensorCategoryManager.removeElement(selectedCategory);		
	}

	public String getCategoryOfAnActuator(String user, String selectedHouse, String selectedActuator) {
		return getUser(user).getCategoryOfAnActuator(selectedHouse, selectedActuator);
	}

	public Set<String> getControlledObjectSet(String user, String selectedHouse, String selectedActuator) {
		return getUser(user).getControlledObjectSet(selectedHouse, selectedActuator);
	}

	public void removeActuator(String user, String selectedHouse, String selectedRoom, String selectedActuator) {
		getUser(user).removeActuator(selectedHouse, selectedRoom, selectedActuator);
	}

	public boolean isActuatorInstanceOf(String user, String selectedHouse, String selectedActuator,
			String selectedCategory) {
		return getUser(user).isActuatorInstanceOf(selectedHouse, selectedActuator, selectedCategory);
	}

	public String getRoomOfActuator(String user, String selectedHouse, String selectedActuator) {
		return getUser(user).getRoomOfActuator(selectedHouse, selectedActuator);
	}

	public void removeActuatorCategory(String selectedCategory) {
		actuatorCategoryManager.removeElement(selectedCategory);
	}

	public boolean isSensorAssociatedWith(String user, String selectedHouse, String selectedSensor,
			String object) {
		return getUser(user).isSensorAssociatedWith(selectedHouse, selectedSensor, object);
	}

	public void removeSensorAssociation(String user, String selectedHouse, String selectedSensor, String object) {
		getUser(user).removeSensorAssociation(selectedHouse, selectedSensor, object);
	}

	public boolean isSensorNotAssociated(String user, String selectedHouse, String selectedSensor) {
		return getUser(user).isSensorNotAssociated(selectedHouse, selectedSensor);
	}

	public boolean isActuatorAssociatedWith(String user, String selectedHouse, String selectedActuator,
			String object) {
		return getUser(user).isActuatorAssociatedWith(selectedHouse, selectedActuator, object);
	}

	public void removeActuatorAssociation(String user, String selectedHouse, String selectedActuator,
			String object) {
		getUser(user).removeActuatorAssociation(selectedHouse, selectedActuator, object);
	}

	public boolean isActuatorNotAssociated(String user, String selectedHouse, String selectedActuator) {
		return getUser(user).isActuatorNotAssociated(selectedHouse, selectedActuator);
	}

	public void removeRoomAssociation(String user, String selectedHouse, String selectedRoom) {
		getUser(user).removeRoomAssociation(selectedHouse, selectedRoom);
	}

	public void removeRoom(String user, String selectedHouse, String selectedRoom) {
		getUser(user).removeRoom(selectedHouse, selectedRoom);
	}

	public void removeArtifactAssociation(String user, String selectedHouse, String selectedArtifact) {
		getUser(user).removeArtifactAssociation(selectedHouse, selectedArtifact);		
	}

	public void removeArtifact(String user, String selectedHouse, String selectedRoom, String selectedArtifact) {
		getUser(user).removeArtifact(selectedHouse, selectedRoom, selectedArtifact);
	}

	public void removeHousingUnit(String user, String selectedHouse) {
		getUser(user).removeHousingUnit(selectedHouse);		
	}

	public void removeUser(String user) {
		userManager.removeElement(user);
	}
}