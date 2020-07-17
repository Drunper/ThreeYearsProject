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
		connector.submitParametrizedQuery(QueryStrings.GET_SENSOR_CATEGORIES);
		try (ResultSet set = connector.executeQuery()) {
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

		connector.submitParametrizedQuery(QueryStrings.GET_NUMERIC_INFO_OF_A_CATEGORY);
		connector.setStringParameter(1, categoryName);
		try (ResultSet set1 = connector.executeQuery()) {
			while (set1.next()) {
				infoDomainMap.put(set1.getString("nome_proprietà"),
						new DoubleInfoStrategy(set1.getDouble("minimo"), set1.getDouble("massimo")));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		connector.submitParametrizedQuery(QueryStrings.GET_NON_NUMERIC_INFO_OF_A_CATEGORY);
		connector.setStringParameter(1, categoryName);
		try (ResultSet set2 = connector.executeQuery()) {
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

		connector.submitParametrizedQuery(QueryStrings.GET_MEASUREMENT_UNIT);
		connector.setStringParameter(1, categoryName);
		try (ResultSet set = connector.executeQuery()) {
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

		connector.submitParametrizedQuery(QueryStrings.GET_NON_NUMERIC_DOMAIN_VALUE);
		connector.setIntParameter(1, infoID);
		connector.setStringParameter(2, propertyName);
		try (ResultSet set = connector.executeQuery()) {
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
		connector.submitParametrizedQuery(QueryStrings.GET_ACTUATOR_CATEGORIES);
		try (ResultSet set = connector.executeQuery()) {
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

		connector.submitParametrizedQuery(QueryStrings.GET_OPERATING_MODES_OF_A_CATEGORY);
		connector.setStringParameter(1, categoryName);
		try (ResultSet set = connector.executeQuery()) {
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
	public void loadHousingUnits(String user) {
		connector.submitParametrizedQuery(QueryStrings.GET_USER_HOUSING_UNITS);
		connector.setStringParameter(0, user);
		try (ResultSet set = connector.executeQuery()) {
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
		connector.submitParametrizedQuery(QueryStrings.GET_ROOMS);
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery()) {
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

		if (roomOrArtifact)
			connector.submitParametrizedQuery(QueryStrings.GET_ROOM_PROPERTIES);
		else
			connector.submitParametrizedQuery(QueryStrings.GET_ARTIFACT_PROPERTIES);
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, selectedHouse);
		connector.setStringParameter(3, object);

		try (ResultSet set = connector.executeQuery()) {
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
		connector.submitParametrizedQuery(QueryStrings.GET_ARTIFACTS);
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery()) {
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
		connector.submitParametrizedQuery(QueryStrings.GET_ARTIFACTS);
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery()) {
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
		connector.submitParametrizedQuery(QueryStrings.GET_ACTUATORS);
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, selectedHouse);
		try (ResultSet set = connector.executeQuery()) {
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
		if (sensOrAct)
			connector.submitParametrizedQuery(QueryStrings.GET_MEASURED_OBJECTS);
		else
			connector.submitParametrizedQuery(QueryStrings.GET_CONTROLLED_OBJECTS);
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, selectedHouse);
		connector.setStringParameter(3, device);
		connector.setStringParameter(4, user);
		connector.setStringParameter(5, selectedHouse);
		connector.setStringParameter(6, device);

		try (ResultSet set = connector.executeQuery()) {
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
		connector.submitParametrizedQuery(QueryStrings.GET_RULES);
		connector.setStringParameter(1, user);
		connector.setStringParameter(2, selectedHouse);

		try (ResultSet set = connector.executeQuery()) {
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
