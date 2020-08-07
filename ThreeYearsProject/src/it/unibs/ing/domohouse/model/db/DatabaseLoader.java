package it.unibs.ing.domohouse.model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.model.components.properties.*;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.db.persistent.OldObjectState;
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
import it.unibs.ing.domohouse.model.components.elements.*;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.ObjectFactory;

public class DatabaseLoader {

	private Connector connector;
	private ObjectFactory objectFactory;
	private DataFacade dataFacade;

	public DatabaseLoader(Connector connector, ObjectFactory objectFactory, DataFacade dataFacade) {
		this.connector = connector;
		this.objectFactory = objectFactory;
		this.dataFacade = dataFacade;
	}

	public List<SensorCategory> loadSensorCategories() throws Exception {
		List<SensorCategory> sensorCategories = new ArrayList<>();
		try (ResultSet set = connector.executeQuery(QueryStrings.GET_SENSOR_CATEGORIES)) {
			while (set.next()) {
				Map<String, String> measurementUnitMap = new HashMap<>();
				SensorCategory cat = objectFactory.createSensorCategory(set.getString("nome_categoria_sensori"),
						set.getString("sigla"), set.getString("costruttore"),
						getSensorCategoryInfos(set.getString("nome_categoria_sensori"), measurementUnitMap),
						measurementUnitMap);
				PersistentObject saveable = new PersistentSensorCategory(cat, new OldObjectState());
				cat.setSaveable(saveable);
				dataFacade.addSaveable(saveable);
				sensorCategories.add(cat);
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return sensorCategories;
	}

	private Map<String, InfoStrategy> getSensorCategoryInfos(String categoryName,
			Map<String, String> measurementUnitMap) throws Exception {
		Map<String, InfoStrategy> infoDomainMap = new HashMap<>();

		Query query = new Query(QueryStrings.GET_NUMERIC_INFO_OF_A_CATEGORY);
		query.setStringParameter(1, categoryName);
		try (ResultSet set1 = connector.executeQuery(query)) {
			while (set1.next()) {
				infoDomainMap.put(set1.getString("nome_proprietà"), new DoubleInfoStrategy(set1.getDouble("minimo"),
						set1.getDouble("massimo"), set1.getInt("id_informazione"), set1.getString("nome_proprietà")));
				measurementUnitMap.put(set1.getString("nome_proprietà"), set1.getString("unità_di_misura"));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}

		query.setQuery(QueryStrings.GET_NON_NUMERIC_INFO_OF_A_CATEGORY);
		try (ResultSet set2 = connector.executeQuery(query)) {
			while (set2.next()) {
				infoDomainMap.put(set2.getString("nome_proprietà"),
						new StringInfoStrategy(
								getStringInfoDomainValues(set2.getInt("id_informazione"),
										set2.getString("nome_proprietà")),
								set2.getInt("id_informazione"), set2.getString("nome_proprietà")));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return infoDomainMap;
	}

	private List<String> getStringInfoDomainValues(int infoID, String propertyName) throws Exception {
		List<String> domain = new ArrayList<>();

		Query query = new Query(QueryStrings.GET_NON_NUMERIC_DOMAIN_VALUE);
		query.setIntegerParameter(1, infoID);
		query.setStringParameter(2, propertyName);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				domain.add(set.getString("nome_valore"));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}

		return domain;
	}

	public List<ActuatorCategory> loadActuatorCategories() throws Exception {
		List<ActuatorCategory> actuatorCategories = new ArrayList<>();
		try (ResultSet set = connector.executeQuery(QueryStrings.GET_ACTUATOR_CATEGORIES)) {
			while (set.next()) {
				ActuatorCategory cat = objectFactory.createActuatorCategory(set.getString("nome_categoria_attuatori"),
						set.getString("sigla"), set.getString("costruttore"),
						getListOfModes(set.getString("nome_categoria_attuatori")),
						set.getString("modalità_di_default"));
				PersistentObject saveable = new PersistentActuatorCategory(cat, new OldObjectState());
				dataFacade.addSaveable(saveable);
				cat.setSaveable(saveable);
				actuatorCategories.add(cat);
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return actuatorCategories;
	}

	private List<String> getListOfModes(String categoryName) throws Exception {
		List<String> listOfModes = new ArrayList<>();

		Query query = new Query(QueryStrings.GET_OPERATING_MODES_OF_A_CATEGORY);
		query.setStringParameter(1, categoryName);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				listOfModes.add(set.getString("nome_modalità"));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}

		return listOfModes;
	}

	public User loadUser(String user) throws Exception {
		Query query = new Query(QueryStrings.GET_USER);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query)) {
			if (set.next()) {
				User userObject = new User(set.getString("nome_utente"));
				PersistentObject saveable = new PersistentUser(userObject, new OldObjectState());
				dataFacade.addSaveable(saveable);
				userObject.setSaveable(saveable);
				return userObject;
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return null;
	}

	public List<HousingUnit> loadHousingUnits(String user) throws Exception {
		List<HousingUnit> housingUnits = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_USER_HOUSING_UNITS);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				String selectedHouse = set.getString("nome_unità");
				HousingUnit housingUnit = new HousingUnit(selectedHouse, set.getString("descrizione"),
						set.getString("tipo"), user);
				PersistentObject saveable = new PersistentHousingUnit(housingUnit, new OldObjectState());
				housingUnit.setSaveable(saveable);
				dataFacade.addSaveable(saveable);
				housingUnits.add(housingUnit);
				List<Room> rooms = loadRooms(user, selectedHouse);
				for (Room room : rooms)
					housingUnit.addRoom(room);

				for (Room room : rooms) {
					for (Artifact artifact : loadArtifacts(user, selectedHouse, room.getName()))
						housingUnit.addArtifact(artifact, room.getName());
				}

				for (Room room : rooms) {
					for (Sensor sensor : loadSensors(user, housingUnit, room.getName()))
						housingUnit.addSensor(room.getName(), sensor);
					for (Actuator actuator : loadActuators(user, housingUnit, room.getName()))
						housingUnit.addActuator(actuator, room.getName());
				}

				for (Rule rule : loadRules(user, housingUnit))
					housingUnit.addRule(rule);
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return housingUnits;
	}

	private List<Room> loadRooms(String user, String selectedHouse) throws Exception {
		List<Room> rooms = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_ROOMS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				Room room = new Room(set.getString("nome_stanza"), set.getString("descrizione"),
						getProperties(user, selectedHouse, set.getString("nome_stanza"), true));
				PersistentObject saveable = new PersistentRoom(user, selectedHouse, room, new OldObjectState());
				room.setSaveable(saveable);
				dataFacade.addSaveable(saveable);
				rooms.add(room);
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return rooms;
	}

	public void addAssociations(String user, String selectedHouse) throws Exception {
		Query query = new Query("");
		query.setStringParameter(1, selectedHouse);
		query.setStringParameter(2, user);
		for (String room : dataFacade.getRoomsSet(user, selectedHouse)) {
			query.setStringParameter(3, room);
			query.setQuery(QueryStrings.GET_ASSOCIATED_SENSOR_CATEGORY_ROOM);
			try (ResultSet set = connector.executeQuery(query)) {
				while (set.next())
					dataFacade.addRoomAssociation(user, selectedHouse, room, set.getString("nome_categoria_sensori"));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			query.setQuery(QueryStrings.GET_ASSOCIATED_ACTUATOR_CATEGORY_ROOM);
			try (ResultSet set = connector.executeQuery(query)) {
				while (set.next())
					dataFacade.addRoomAssociation(user, selectedHouse, room, set.getString("nome_categoria_attuatori"));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}

		for (String artifact : dataFacade.getArtifactSet(user, selectedHouse)) {
			query.setStringParameter(3, artifact);
			query.setQuery(QueryStrings.GET_ASSOCIATED_SENSOR_CATEGORY_ARTIFACT);
			try (ResultSet set = connector.executeQuery(query)) {
				while (set.next())
					dataFacade.addArtifactAssociation(user, selectedHouse, artifact, set.getString("nome_categoria_sensori"));
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			query.setQuery(QueryStrings.GET_ASSOCIATED_ACTUATOR_CATEGORY_ARTIFACT);
			try (ResultSet set = connector.executeQuery(query)) {
				while (set.next())
					dataFacade.addArtifactAssociation(user, selectedHouse, artifact, set.getString("nome_categoria_attuatori"));
			}
			catch (SQLException e) {
				throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
			}
		}
	}

	public Map<String, String> getAllProperties() throws Exception {
		Map<String, String> propertyMap = new HashMap<>();
		try (ResultSet set = connector.executeQuery(QueryStrings.GET_ALL_PROPERTIES)) {
			while (set.next()) {
				propertyMap.put(set.getString("nome_proprietà"), set.getString("valore_di_default"));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return propertyMap;
	}

	private Map<String, String> getProperties(String user, String selectedHouse, String object,
			boolean roomOrArtifact) throws Exception {
		Map<String, String> propertyMap = new HashMap<>();

		Query query = new Query("");
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		query.setStringParameter(3, object);
		if (roomOrArtifact)
			query.setQuery(QueryStrings.GET_ROOM_PROPERTIES);
		else
			query.setQuery(QueryStrings.GET_ARTIFACT_PROPERTIES);

		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				propertyMap.put(set.getString("nome_proprietà"), set.getString("valore_di_default"));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}

		return propertyMap;
	}

	private List<Artifact> loadArtifacts(String user, String selectedHouse, String location) throws Exception {
		List<Artifact> artifacts = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_ARTIFACTS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		query.setStringParameter(3, location);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				Artifact artifact = new Artifact(set.getString("nome_artefatto"), set.getString("descrizione"),
						getProperties(user, selectedHouse, set.getString("nome_artefatto"), false));
				PersistentObject saveable = new PersistentArtifact(user, selectedHouse, location, artifact,
						new OldObjectState());
				dataFacade.addSaveable(saveable);
				artifact.setSaveable(saveable);
				artifacts.add(artifact);
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return artifacts;
	}

	private List<Sensor> loadSensors(String user, HousingUnit selectedHouse, String location) throws Exception {
		List<Sensor> sensors = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_SENSORS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse.getName());
		query.setStringParameter(3, location);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				Sensor sensor = objectFactory.createSensor(set.getString("nome_sensore"),
						dataFacade.getSensorCategory(set.getString("nome_categoria_sensori")),
						set.getBoolean("stanze_o_artefatti"), getDeviceObjects(user, selectedHouse,
								set.getString("nome_sensore"), true, set.getBoolean("stanze_o_artefatti")));

				PersistentObject saveable = new PersistentSensor(user, selectedHouse.getName(), location, sensor,
						new OldObjectState());
				dataFacade.addSaveable(saveable);
				sensor.setSaveable(saveable);
				sensors.add(sensor);
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return sensors;
	}

	private List<Actuator> loadActuators(String user, HousingUnit selectedHouse, String location) throws Exception {
		List<Actuator> actuators = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_ACTUATORS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse.getName());
		query.setStringParameter(3, location);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				Actuator actuator = objectFactory.createActuator(set.getString("nome_attuatore"),
						dataFacade.getActuatorCategory(set.getString("nome_categoria_attuatori")),
						set.getBoolean("stanze_o_artefatti"), getDeviceObjects(user, selectedHouse,
								set.getString("nome_attuatore"), false, set.getBoolean("stanze_o_artefatti")));
				PersistentObject saveable = new PersistentActuator(user, selectedHouse.getName(), location, actuator,
						new OldObjectState());
				dataFacade.addSaveable(saveable);
				actuator.setSaveable(saveable);
				actuators.add(actuator);
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return actuators;
	}

	private List<Gettable> getDeviceObjects(String user, HousingUnit selectedHouse, String device, boolean sensOrAct,
			boolean roomOrArtifact) throws Exception {
		List<Gettable> deviceObjects = new ArrayList<>();

		String queryString = sensOrAct
				? (roomOrArtifact ? QueryStrings.GET_MEASURED_ROOMS : QueryStrings.GET_MEASURED_ARTIFACTS)
				: (roomOrArtifact ? QueryStrings.GET_CONTROLLED_ROOMS : QueryStrings.GET_CONTROLLED_ARTIFACTS);
		Query query = new Query(queryString);
		query.setStringParameter(user, 1);
		query.setStringParameter(selectedHouse.getName(), 2);
		query.setStringParameter(device, 3);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				if (roomOrArtifact)
					deviceObjects.add(selectedHouse.getRoom(set.getString("nome_stanza")));
				else
					deviceObjects.add(selectedHouse.getArtifact(set.getString("nome_artefatto")));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}

		return deviceObjects;
	}

	private List<Rule> loadRules(String user, HousingUnit selectedHouse) throws Exception {
		List<Rule> rules = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_RULES);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse.getName());
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				State state = set.getBoolean("stato") ? new ActiveState() : new InactiveState();
				Rule rule = objectFactory.createRule(set.getString("nome_regola"), set.getString("testo_antecedente"),
						set.getString("testo_conseguente"),
						getSensorFromAntString(user, selectedHouse, set.getString("testo_antecedente")),
						getActuatorsFromConsString(user, selectedHouse, set.getString("testo_conseguente")), state);
				PersistentObject saveable = new PersistentRule(user, selectedHouse.getName(), rule,
						new OldObjectState());
				dataFacade.addSaveable(saveable);
				rule.setSaveable(saveable);
				rules.add(rule);
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}
		return rules;
	}

	private List<Sensor> getSensorFromAntString(String user, HousingUnit selectedHouse, String antString) {
		List<Sensor> sensors = new ArrayList<>();
		while (antString.contains("&&") || antString.contains("||")) {
			String condition = antString.split("\\&\\&|\\|\\|")[0];
			if (!condition.contains("time")) {
				String sensor = condition.split("\\.")[0];
				sensors.add(selectedHouse.getSensor(sensor.trim()));
			}
			String temp = antString.split("\\&\\&|\\|\\|")[1];
			antString = temp;
		}
		if (!antString.contains("time"))
			sensors.add(selectedHouse.getSensor(antString.split("\\.")[0].trim()));

		return sensors;
	}

	private List<Actuator> getActuatorsFromConsString(String user, HousingUnit selectedHouse, String consString) {
		List<Actuator> actuators = new ArrayList<>();
		String[] actions = consString.split(";");
		for (String action : actions) {
			String act = action.split(":=")[0].trim();
			if (!act.equals("start"))
				actuators.add(selectedHouse.getActuator(act));
		}

		return actuators;
	}

	public Map<Integer, DoubleInfoStrategy> getNumericInfoStrategies() throws Exception {
		Map<Integer, DoubleInfoStrategy> map = new HashMap<>();

		try (ResultSet set = connector.executeQuery(QueryStrings.GET_NUMERIC_INFOS)) {
			while (set.next()) {
				map.put(set.getInt("id_informazione"), new DoubleInfoStrategy(set.getDouble("minimo"),
						set.getDouble("massimo"), set.getInt("id_informazione"), set.getString("nome_proprietà")));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}

		return map;
	}

	public Map<Integer, StringInfoStrategy> getNonNumericInfoStrategies() throws Exception {
		Map<Integer, StringInfoStrategy> map = new HashMap<>();

		try (ResultSet set = connector.executeQuery(QueryStrings.GET_NON_NUMERIC_INFOS)) {
			while (set.next()) {
				map.put(set.getInt("id_informazione"),
						new StringInfoStrategy(
								getStringInfoDomainValues(set.getInt("id_informazione"), set.getString("nome_proprietà")),
								set.getInt("id_informazione"), set.getString("nome_proprietà")));
			}
		}
		catch (SQLException e) {
			throw new Exception(ModelStrings.DB_QUERY_RESULT_EXCEPTION, e);
		}

		return map;
	}
}
