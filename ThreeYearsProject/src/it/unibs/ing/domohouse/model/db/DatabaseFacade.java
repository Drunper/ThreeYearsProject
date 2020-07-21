package it.unibs.ing.domohouse.model.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.User;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;

public class DatabaseFacade {

	private Connector connector;
	private DatabaseLoader databaseLoader;
	private List<Saveable> saveables;
	
	public DatabaseFacade(Connector connector, DatabaseLoader databaseLoader) {
		this.connector = connector;
		this.databaseLoader = databaseLoader;
		saveables = new ArrayList<>();
	}
	
	public void update(Query query) {
		connector.executeQueryWithoutResult(query);
	}
	
	public List<SensorCategory> loadSensorCategories() {
		return databaseLoader.loadSensorCategories();
	}

	public List<ActuatorCategory> loadActuatorCategories() {
		return databaseLoader.loadActuatorCategories();
	}
	
	public User loadUser(String user) {		
		return databaseLoader.loadUser(user);
	}

	public List<HousingUnit> loadHousingUnits(String user) {
		return databaseLoader.loadHousingUnits(user);
	}
	
	public Map<String, String> getAllProperties() {
		return databaseLoader.getAllProperties();
	}
	
	public void addSaveable(Saveable toAdd) {
		saveables.add(toAdd);
	}

	public void saveData() {
		for(Saveable saveable : saveables)
			update(saveable.getUpdateQuery());
		saveables.removeAll(saveables);
	}

	public void addProperty(String propertyName, String defaultValue) {
		Query query = new Query(QueryStrings.INSERT_PROPERTY);
		query.setStringParameter(1, propertyName);
		query.setIntegerParameter(2, 0); //temporaneo, ma in futuro potrebbe essere rimosso
		query.setStringParameter(3, defaultValue);
		connector.executeQueryWithoutResult(query);
	}
}
