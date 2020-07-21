package it.unibs.ing.domohouse.model.db;

import java.util.List;

import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.User;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;

public class DatabaseFacade {

	private Connector connector;
	private DatabaseLoader databaseLoader;
	
	public DatabaseFacade(Connector connector, DatabaseLoader databaseLoader) {
		this.connector = connector;
		this.databaseLoader = databaseLoader;
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
}
