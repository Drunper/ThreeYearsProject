package it.unibs.ing.domohouse.model.db;

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
	
	public void loadSensorCategories() {
		databaseLoader.loadSensorCategories();
	}

	public void loadActuatorCategories() {
		databaseLoader.loadActuatorCategories();
	}
	
	public void loadUser(String user) {		
		databaseLoader.loadUser(user);
	}

	public void loadHousingUnits(String user) {
		databaseLoader.loadHousingUnits(user);
	}
}
