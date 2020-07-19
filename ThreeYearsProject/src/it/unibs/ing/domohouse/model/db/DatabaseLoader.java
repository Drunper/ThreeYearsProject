package it.unibs.ing.domohouse.model.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.model.components.properties.ActiveState;
import it.unibs.ing.domohouse.model.components.properties.DoubleInfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.InactiveState;
import it.unibs.ing.domohouse.model.components.properties.InfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.State;
import it.unibs.ing.domohouse.model.components.properties.StringInfoStrategy;
import it.unibs.ing.domohouse.model.util.Loader;
import it.unibs.ing.domohouse.model.util.ObjectFabricator;

public class DatabaseLoader implements Loader {

	private Connector connector;
	private ObjectFabricator objectFabricator;

	public DatabaseLoader(Connector connector, ObjectFabricator objectFabricator) {
		this.connector = connector;
		this.objectFabricator = objectFabricator;
	}

	@Override
	public void loadSensorCategories() {
		try (ResultSet set = connector.executeQuery(QueryStrings.GET_SENSOR_CATEGORIES)) {
			while (set.next()) {
				objectFabricator.createSensorCategory(set.getString("nome_categoria_sensori"), set.getString("sigla"),
						set.getString("costruttore"), getSensorCategoryInfos(set.getString("nome_categoria_sensori")),
						getMeasurementUnits(set.getString("nome_categoria_sensori")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Map<String, InfoStrategy> getSensorCategoryInfos(String categoryName) {
		Map<String, InfoStrategy> infoDomainMap = new HashMap<>();

		Query query = new Query(QueryStrings.GET_NUMERIC_INFO_OF_A_CATEGORY);
		query.setStringParameter(1, categoryName);
		try (ResultSet set1 = connector.executeQuery(query)) {
			while (set1.next()) {
				infoDomainMap.put(set1.getString("nome_proprietà"),
						new DoubleInfoStrategy(set1.getDouble("minimo"), set1.getDouble("massimo")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		query.setQuery(QueryStrings.GET_NON_NUMERIC_INFO_OF_A_CATEGORY);
		try (ResultSet set2 = connector.executeQuery(query)) {
			while (set2.next()) {
				infoDomainMap.put(set2.getString("nome_proprietà"), new StringInfoStrategy(
						getStringInfoDomainValues(set2.getInt("id_informazione"), set2.getString("nome_proprietà"))));
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

	@Override
	public void loadActuatorCategories() {
		try (ResultSet set = connector.executeQuery(QueryStrings.GET_ACTUATOR_CATEGORIES)) {
			while (set.next()) {
				objectFabricator.createActuatorCategory(set.getString("nome_categoria_attuatori"),
						set.getString("sigla"), set.getString("costruttore"),
						getListOfModes(set.getString("nome_categoria_attuatori")),
						set.getString("modalità_di_default"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
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
	
	@Override
	public void loadUser(String user) {		
		Query query = new Query(QueryStrings.GET_USER);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query)) {
			if (set.next())
				objectFabricator.createUser(set.getString("nome_utente"));
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadHousingUnits(String user) {
		Query query = new Query(QueryStrings.GET_USER_HOUSING_UNITS);
		query.setStringParameter(1, user);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				String selectedHouse = set.getString("nome_unità");
				objectFabricator.createHouse(selectedHouse, set.getString("descrizione"), set.getString("tipo"), user);
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
	}

	private void loadRooms(String user, String selectedHouse) {
		Query query = new Query(QueryStrings.GET_ROOMS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				objectFabricator.createRoom(user, selectedHouse, set.getString("nome_stanza"),
						set.getString("descrizione"),
						getProperties(user, selectedHouse, set.getString("nome_stanza"), true));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
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

	private void loadArtifacts(String user, String selectedHouse) {
		Query query = new Query(QueryStrings.GET_ARTIFACTS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				objectFabricator.createArtifact(user, selectedHouse, set.getString("nome_artefatto"),
						set.getString("descrizione"), set.getString("posizione"),
						getProperties(user, selectedHouse, set.getString("nome_artefatto"), false));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadSensors(String user, String selectedHouse) {
		Query query = new Query(QueryStrings.GET_SENSORS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				objectFabricator.createSensor(user, selectedHouse, set.getString("nome_sensore"),
						set.getString("nome_categoria_sensori"), set.getBoolean("stanze_o_artefatti"),
						getDeviceObjects(user, selectedHouse, set.getString("nome_sensore"), true),
						set.getString("posizione"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadActuators(String user, String selectedHouse) {
		Query query = new Query(QueryStrings.GET_ACTUATORS);
		query.setStringParameter(1, user);
		query.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery(query)) {
			while (set.next()) {
				objectFabricator.createActuator(user, selectedHouse, set.getString("nome_attuatore"),
						set.getString("nome_categoria_attuatori"), set.getBoolean("stanze_o_artefatti"),
						getDeviceObjects(user, selectedHouse, set.getString("nome_attuatore"), false),
						set.getString("posizione"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private List<String> getDeviceObjects(String user, String selectedHouse, String device, boolean sensOrAct) {
		List<String> deviceObjects = new ArrayList<>();
		
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
				deviceObjects.add(set.getString("nome_oggetto"));
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
