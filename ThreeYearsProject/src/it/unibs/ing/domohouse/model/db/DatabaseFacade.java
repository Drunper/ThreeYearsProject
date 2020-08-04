package it.unibs.ing.domohouse.model.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.User;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.components.properties.DoubleInfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.components.properties.StringInfoStrategy;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;

public class DatabaseFacade {

	private Connector connector;
	private DatabaseLoader databaseLoader;
	private List<PersistentObject> saveables;

	public DatabaseFacade(Connector connector, DatabaseLoader databaseLoader) {
		this.connector = connector;
		this.databaseLoader = databaseLoader;
		saveables = new ArrayList<>();
	}

	public void update(Query query) throws Exception {
		try {
			connector.executeQueryWithoutResult(query);
		}
		catch (DBAccessException | DBParametersException e) {
			throw new Exception(ModelStrings.DB_UPDATE_EXCEPTION, e);
		}
	}

	public List<SensorCategory> loadSensorCategories() throws Exception {
		return databaseLoader.loadSensorCategories();
	}

	public List<ActuatorCategory> loadActuatorCategories() throws Exception {
		return databaseLoader.loadActuatorCategories();
	}

	public User loadUser(String user) throws Exception {
		return databaseLoader.loadUser(user);
	}

	public List<HousingUnit> loadHousingUnits(String user) throws Exception {
		return databaseLoader.loadHousingUnits(user);
	}

	public Map<String, String> getAllProperties() throws Exception {
		return databaseLoader.getAllProperties();
	}

	public Map<Integer, DoubleInfoStrategy> getNumericInfoStrategies() throws Exception {
		return databaseLoader.getNumericInfoStrategies();
	}

	public Map<Integer, StringInfoStrategy> getNonNumericInfoStrategies() throws Exception {
		return databaseLoader.getNonNumericInfoStrategies();
	}

	public void addSaveable(PersistentObject toAdd) {
		saveables.add(toAdd);
	}

	public void saveData() throws Exception {
		for (PersistentObject saveable : saveables)
			update(saveable.getUpdateQuery());
		saveables.removeAll(saveables);
	}

	public void addProperty(String propertyName, String defaultValue) throws Exception {
		Query query = new Query(QueryStrings.INSERT_PROPERTY);
		query.setStringParameter(1, propertyName);
		query.setStringParameter(2, null);
		query.setStringParameter(3, defaultValue);
		connector.executeQueryWithoutResult(query);
	}

	public void addNumericInfoStrategy(DoubleInfoStrategy infoStrategy) throws Exception {
		Query query = new Query(QueryStrings.INSERT_NUMERIC_INFO_STRATEGY);
		query.setIntegerParameter(1, infoStrategy.getID());
		query.setStringParameter(2, infoStrategy.getMeasuredProperty());
		query.setIntegerParameter(3, 0);
		query.setDoubleParameter(4, infoStrategy.getLowerBound());
		query.setDoubleParameter(5, infoStrategy.getUpperBound());
		connector.executeQueryWithoutResult(query);
	}

	public void addNonNumericInfoStrategy(StringInfoStrategy infoStrategy) throws Exception {
		String queryString = QueryStrings.INSERT_NON_NUMERIC_INFO_STRATEGY;

		Query query = new Query("");
		query.setIntegerParameter(1, infoStrategy.getID());
		query.setStringParameter(2, infoStrategy.getMeasuredProperty());
		query.setIntegerParameter(3, 1);

		int pos = 4;
		for (String domainValue : infoStrategy.getDomainValues()) {
			query.setIntegerParameter(pos++, infoStrategy.getID());
			query.setStringParameter(pos++, infoStrategy.getMeasuredProperty());
			query.setStringParameter(pos++, domainValue);
		}

		queryString += " " + QueryStrings.INSERT_NON_NUMERIC_DOMAIN_VALUE + String.join(", ",
				Collections.nCopies(infoStrategy.getDomainValues().size(), QueryStrings.THREE_VALUES));

		query.setQuery(queryString);
		connector.executeQueryWithoutResult(query);
	}

	public void addAssociations(String user, String house) throws Exception {
		databaseLoader.addAssociations(user, house);
	}
}
