package it.unibs.ing.domohouse.model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.model.components.properties.*;
import it.unibs.ing.domohouse.model.components.elements.*;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.Manager;
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
				sensorCategories.add(objectFactory.createSensorCategory(set.getString("nome_categoria_sensori"), set.getString("sigla"),
						set.getString("costruttore"), getSensorCategoryInfos(set.getString("nome_categoria_sensori")),
						getMeasurementUnits(set.getString("nome_categoria_sensori"))));
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
				infoDomainMap.put(set1.getString("nome_proprietà"),
						new DoubleInfoStrategy(set1.getDouble("minimo"), set1.getDouble("massimo"), set1.getInt("id_informazione")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		query.setQuery(QueryStrings.GET_NON_NUMERIC_INFO_OF_A_CATEGORY);
		try (ResultSet set2 = connector.executeQuery(query)) {
			while (set2.next()) {
				infoDomainMap.put(set2.getString("nome_proprietà"), new StringInfoStrategy(
						getStringInfoDomainValues(set2.getInt("id_informazione"), set2.getString("nome_proprietà")), set2.getInt("id_informazione")));
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
				actuatorCategories.add(objectFactory.createActuatorCategory(set.getString("nome_categoria_attuatori"),
						set.getString("sigla"), set.getString("costruttore"),
						getListOfModes(set.getString("nome_categoria_attuatori")),
						set.getString("modalità_di_default")));
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
			if (set.next())
				return new User(set.getString("nome_utente"));
		}
		catch(SQLException e) {
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
				housingUnits.add(new HousingUnit(selectedHouse, set.getString("descrizione"), set.getString("tipo"), user));
				loadRooms(user, selectedHouse);
				loadArtifacts(user, selectedHouse);
				loadSensors(user, selectedHouse);
				loadActuators(user, selectedHouse);
				loadRules(user, selectedHouse);
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
				rooms.add(new Room(set.getString("nome_stanza"),
						set.getString("descrizione"),
						getProperties(user, selectedHouse, set.getString("nome_stanza"), true)));
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

	private List<Artifact> loadArtifacts(String user, String selectedHouse) {
		List<Artifact> artifacts = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_ARTIFACTS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				artifacts.add(new Artifact(set.getString("nome_artefatto"),
						set.getString("descrizione"),
						getProperties(user, selectedHouse, set.getString("nome_artefatto"), false)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return artifacts;
	}

	private List<Sensor> loadSensors(String user, String selectedHouse) {
		List<Sensor> sensors = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_SENSORS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				sensors.add(objectFactory.createSensor(set.getString("nome_sensore"),
						dataFacade.getSensorCategory(set.getString("nome_categoria_sensori")), set.getBoolean("stanze_o_artefatti"),
						getDeviceObjects(user, selectedHouse, set.getString("nome_sensore"), true)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return sensors;
	}

	private List<Actuator> loadActuators(String user, String selectedHouse) {
		List<Actuator> actuators = new ArrayList<>();
		Query query = new Query(QueryStrings.GET_ACTUATORS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				actuators.add(objectFactory.createActuator(set.getString("nome_attuatore"),
						dataFacade.getActuatorCategory(set.getString("nome_categoria_attuatori")), set.getBoolean("stanze_o_artefatti"),
						getDeviceObjects(user, selectedHouse, set.getString("nome_attuatore"), false)));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return actuators;
	}

	private List<Gettable> getDeviceObjects(String user, String selectedHouse, String device, boolean sensOrAct) {
		List<Gettable> deviceObjects = new ArrayList<>();
		
		Query query = new Query("");
		if (sensOrAct)
			query.setQuery(QueryStrings.GET_MEASURED_OBJECTS);
		else
			query.setQuery(QueryStrings.GET_CONTROLLED_OBJECTS);
		query.setStringParameter(user, 1, 4);
		query.setStringParameter(selectedHouse, 2, 5);
		query.setStringParameter(device, 3, 6);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				if()
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return deviceObjects;
	}

	private void loadRules(String user, String selectedHouse) {
		Query query = new Query(QueryStrings.GET_RULES);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				State state = set.getBoolean("stato") ? new ActiveState() : new InactiveState();
				objectFabricator.createRule(user, selectedHouse, set.getString("nome_regola"),
						set.getString("testo_antecedente"), set.getString("testo_conseguente"),
						getSensorFromAntString(set.getString("testo_antecedente")),
						getActuatorsFromConsString(set.getString("testo_conseguente")), state);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<String> getSensorFromAntString(String antString) {
		List<String> sensors = new ArrayList<>();
		while (antString.contains("&&") || antString.contains("||")) {
			String condition = antString.split("\\&\\&|\\|\\|")[0];
			if (!condition.contains("time")) {
				String sensor = condition.split("\\.")[0];
				sensors.add(sensor.trim());
			}
			String temp = antString.split("\\&\\&|\\|\\|")[1];
			antString = temp;
		}
		if (!antString.contains("time"))
			sensors.add(antString.split("\\.")[0].trim());

		return sensors;
	}

	private List<String> getActuatorsFromConsString(String consString) {
		List<String> actuators = new ArrayList<>();
		String[] actions = consString.split(";");
		for (String action : actions) {
			String act = action.split(":=")[0].trim();
			if (!act.equals("start"))
				actuators.add(act);
		}

		return actuators;
	}
}
