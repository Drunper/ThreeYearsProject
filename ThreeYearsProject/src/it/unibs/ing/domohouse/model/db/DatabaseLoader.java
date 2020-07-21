package it.unibs.ing.domohouse.model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.model.components.properties.*;
import it.unibs.ing.domohouse.model.components.rule.Rule;
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

	public List<SensorCategory> loadSensorCategories() {
		List<SensorCategory> sensorCategories = new ArrayList<>();
		try (ResultSet set = connector.executeQuery(QueryStrings.GET_SENSOR_CATEGORIES)) {
			while (set.next()) {
				SensorCategory cat = objectFactory.createSensorCategory(set.getString("nome_categoria_sensori"),
						set.getString("sigla"), set.getString("costruttore"),
						getSensorCategoryInfos(set.getString("nome_categoria_sensori")),
						getMeasurementUnits(set.getString("nome_categoria_sensori")));
				Saveable saveable = new SaveableSensorCategory(cat, new OldObjectState());
				cat.setSaveable(saveable);
				dataFacade.addSaveable(saveable);
				sensorCategories.add(cat);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return sensorCategories;
	}

	private Map<String, InfoStrategy> getSensorCategoryInfos(String categoryName) {
		Map<String, InfoStrategy> infoDomainMap = new HashMap<>();

		Query query = new Query(QueryStrings.GET_NUMERIC_INFO_OF_A_CATEGORY);
		query.setStringParameter(1, categoryName);
		try (ResultSet set1 = connector.executeQuery(query)) {
			while (set1.next()) {
				infoDomainMap.put(set1.getString("nome_proprietà"), new DoubleInfoStrategy(set1.getDouble("minimo"),
						set1.getDouble("massimo"), set1.getInt("id_informazione")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		query.setQuery(QueryStrings.GET_NON_NUMERIC_INFO_OF_A_CATEGORY);
		try (ResultSet set2 = connector.executeQuery(query)) {
			while (set2.next()) {
				infoDomainMap.put(set2.getString("nome_proprietà"), new StringInfoStrategy(
						getStringInfoDomainValues(set2.getInt("id_informazione"), set2.getString("nome_proprietà")),
						set2.getInt("id_informazione")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return infoDomainMap;
	}

	private Map<String, String> getMeasurementUnits(String categoryName) {
		Map<String, String> measurementUnitMap = new HashMap<>();

		Query query = new Query(QueryStrings.GET_MEASUREMENT_UNIT);
		query.setStringParameter(1, categoryName);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				measurementUnitMap.put(set.getString("nome_proprietà"), set.getString("unità_di_misura"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return measurementUnitMap;
	}

	private List<String> getStringInfoDomainValues(int infoID, String propertyName) {
		List<String> domain = new ArrayList<>();

		Query query = new Query(QueryStrings.GET_MEASUREMENT_UNIT);
		query.setIntegerParameter(1, infoID);
		query.setStringParameter(2, propertyName);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				domain.add(set.getString("nome_valore"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return domain;
	}

	public List<ActuatorCategory> loadActuatorCategories() {
		List<ActuatorCategory> actuatorCategories = new ArrayList<>();
		try (ResultSet set = connector.executeQuery(QueryStrings.GET_ACTUATOR_CATEGORIES)) {
			while (set.next()) {
				ActuatorCategory cat = objectFactory.createActuatorCategory(set.getString("nome_categoria_attuatori"),
						set.getString("sigla"), set.getString("costruttore"),
						getListOfModes(set.getString("nome_categoria_attuatori")),
						set.getString("modalità_di_default"));
				Saveable saveable = new SaveableActuatorCategory(cat, new OldObjectState());
				dataFacade.addSaveable(saveable);
				cat.setSaveable(saveable);
				actuatorCategories.add(cat);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return actuatorCategories;
	}

	private List<String> getListOfModes(String categoryName) {
		List<String> listOfModes = new ArrayList<>();

		Query query = new Query(QueryStrings.GET_OPERATING_MODES_OF_A_CATEGORY);
		query.setStringParameter(1, categoryName);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				listOfModes.add(set.getString("nome_modalità"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return listOfModes;
	}

	public User loadUser(String user) {
		Query query = new Query(QueryStrings.GET_USER);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query)) {
			if (set.next()) {
				User userObject = new User(set.getString("nome_utente"));
				Saveable saveable = new SaveableUser(userObject, new OldObjectState());
				dataFacade.addSaveable(saveable);
				userObject.setSaveable(saveable);
				return userObject;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<HousingUnit> loadHousingUnits(String user) {
		List<HousingUnit> housingUnits = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_USER_HOUSING_UNITS);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				String selectedHouse = set.getString("nome_unità");
				HousingUnit housingUnit = new HousingUnit(selectedHouse, set.getString("descrizione"),
						set.getString("tipo"), user);
				Saveable saveable = new SaveableHousingUnit(housingUnit, new OldObjectState()); 
				housingUnit.setSaveable(saveable);
				dataFacade.addSaveable(saveable);
				housingUnits.add(housingUnit);
				List<Room> rooms = loadRooms(user, selectedHouse);
				for (Room room : rooms)
					housingUnit.addRoom(room);

				for (Room room : rooms) {
					for (Artifact artifact : loadArtifacts(user, selectedHouse, room.getName()))
						room.addArtifact(artifact);
				}
				
				for (Room room : rooms) {
					for (Sensor sensor : loadSensors(user, selectedHouse, room.getName()))
						room.addSensor(sensor);
					for (Actuator actuator : loadActuators(user, selectedHouse, room.getName()))
						room.addActuator(actuator);
				}
				
				for (Rule rule : loadRules(user, selectedHouse))
					housingUnit.addRule(rule);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return housingUnits;
	}

	private List<Room> loadRooms(String user, String selectedHouse) {
		List<Room> rooms = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_ROOMS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				Room room = new Room(set.getString("nome_stanza"), set.getString("descrizione"),
						getProperties(user, selectedHouse, set.getString("nome_stanza"), true));
				Saveable saveable = new SaveableRoom(user, selectedHouse, room, new OldObjectState());
				room.setSaveable(saveable);
				dataFacade.addSaveable(saveable);
				rooms.add(room);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rooms;
	}

	private Map<String, String> getProperties(String user, String selectedHouse, String object,
			boolean roomOrArtifact) {
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
			e.printStackTrace();
		}

		return propertyMap;
	}

	private List<Artifact> loadArtifacts(String user, String selectedHouse, String location) {
		List<Artifact> artifacts = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_ARTIFACTS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		query.setStringParameter(3, location);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				Artifact artifact = new Artifact(set.getString("nome_artefatto"), set.getString("descrizione"),
						getProperties(user, selectedHouse, set.getString("nome_artefatto"), false));
				Saveable saveable = new SaveableArtifact(user, selectedHouse, location, artifact, new OldObjectState());
				dataFacade.addSaveable(saveable);
				artifact.setSaveable(
						saveable);
				artifacts.add(new Artifact(set.getString("nome_artefatto"), set.getString("descrizione"),
						getProperties(user, selectedHouse, set.getString("nome_artefatto"), false)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return artifacts;
	}

	private List<Sensor> loadSensors(String user, String selectedHouse, String location) {
		List<Sensor> sensors = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_SENSORS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		query.setStringParameter(3, location);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				Sensor sensor = objectFactory.createSensor(set.getString("nome_sensore"),
						dataFacade.getSensorCategory(set.getString("nome_categoria_sensori")),
						set.getBoolean("stanze_o_artefatti"), getDeviceObjects(user, selectedHouse,
								set.getString("nome_sensore"), true, set.getBoolean("stanze_o_artefatti")));
				Saveable saveable = new SaveableSensor(user, selectedHouse, location, sensor, new OldObjectState());
				dataFacade.addSaveable(saveable);
				sensor.setSaveable(saveable);
				sensors.add(sensor);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return sensors;
	}

	private List<Actuator> loadActuators(String user, String selectedHouse, String location) {
		List<Actuator> actuators = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_ACTUATORS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		query.setStringParameter(3, location);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				Actuator actuator = objectFactory.createActuator(set.getString("nome_attuatore"),
						dataFacade.getActuatorCategory(set.getString("nome_categoria_attuatori")),
						set.getBoolean("stanze_o_artefatti"), getDeviceObjects(user, selectedHouse,
								set.getString("nome_attuatore"), false, set.getBoolean("stanze_o_artefatti")));
				Saveable saveable = new SaveableActuator(user, selectedHouse, location, actuator, new OldObjectState());
				dataFacade.addSaveable(saveable);
				actuator.setSaveable(
						saveable);
				actuators.add(actuator);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return actuators;
	}

	private List<Gettable> getDeviceObjects(String user, String selectedHouse, String device, boolean sensOrAct,
			boolean roomOrArtifact) {
		List<Gettable> deviceObjects = new ArrayList<>();

		String queryString = sensOrAct
				? (roomOrArtifact ? QueryStrings.GET_MEASURED_ROOMS : QueryStrings.GET_MEASURED_ARTIFACTS)
				: (roomOrArtifact ? QueryStrings.GET_CONTROLLED_ROOMS : QueryStrings.GET_CONTROLLED_ARTIFACTS);
		Query query = new Query(queryString);
		query.setStringParameter(user, 1);
		query.setStringParameter(selectedHouse, 2);
		query.setStringParameter(device, 3);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				if (roomOrArtifact)
					deviceObjects.add(dataFacade.getRoom(user, selectedHouse, set.getString("nome_stanza")));
				else
					deviceObjects.add(dataFacade.getArtifact(user, selectedHouse, set.getString("nome_artefatto")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return deviceObjects;
	}

	private List<Rule> loadRules(String user, String selectedHouse) {
		List<Rule> rules = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_RULES);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				State state = set.getBoolean("stato") ? new ActiveState() : new InactiveState();
				Rule rule = objectFactory.createRule(set.getString("nome_regola"), set.getString("testo_antecedente"),
						set.getString("testo_conseguente"),
						getSensorFromAntString(user, selectedHouse, set.getString("testo_antecedente")),
						getActuatorsFromConsString(user, selectedHouse, set.getString("testo_conseguente")), state);
				Saveable saveable = new SaveableRule(user, selectedHouse, rule, new OldObjectState());
				dataFacade.addSaveable(saveable);
				rule.setSaveable(saveable);
				rules.add(rule);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rules;
	}

	private List<Sensor> getSensorFromAntString(String user, String selectedHouse, String antString) {
		List<Sensor> sensors = new ArrayList<>();
		while (antString.contains("&&") || antString.contains("||")) {
			String condition = antString.split("\\&\\&|\\|\\|")[0];
			if (!condition.contains("time")) {
				String sensor = condition.split("\\.")[0];
				sensors.add(dataFacade.getSensor(user, selectedHouse, sensor.trim()));
			}
			String temp = antString.split("\\&\\&|\\|\\|")[1];
			antString = temp;
		}
		if (!antString.contains("time"))
			sensors.add(dataFacade.getSensor(user, selectedHouse, antString.split("\\.")[0].trim()));

		return sensors;
	}

	private List<Actuator> getActuatorsFromConsString(String user, String selectedHouse, String consString) {
		List<Actuator> actuators = new ArrayList<>();
		String[] actions = consString.split(";");
		for (String action : actions) {
			String act = action.split(":=")[0].trim();
			if (!act.equals("start"))
				actuators.add(dataFacade.getActuator(user, selectedHouse, act));
		}

		return actuators;
	}
}
