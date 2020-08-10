package it.unibs.ing.domohouse.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import it.unibs.ing.domohouse.model.components.elements.Actuator;
import it.unibs.ing.domohouse.model.components.elements.Artifact;
import it.unibs.ing.domohouse.model.components.elements.Gettable;
import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.Room;
import it.unibs.ing.domohouse.model.components.elements.Sensor;
import it.unibs.ing.domohouse.model.components.elements.User;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;
import it.unibs.ing.domohouse.model.components.properties.DoubleInfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.InfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;
import it.unibs.ing.domohouse.model.components.properties.StringInfoStrategy;
import it.unibs.ing.domohouse.model.components.rule.Rule;
import it.unibs.ing.domohouse.model.components.rule.RuleParser;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.db.DatabaseLoader;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.ObjectFactory;

public class DatabaseLoaderTesting {

	@Test
	public void testloadSensorCategoriesCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test2", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<SensorCategory> list = databaseLoader.loadSensorCategories();
			assertTrue(list.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testloadSensorCategoriesCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<SensorCategory> list = databaseLoader.loadSensorCategories();
			boolean check = true;
			List<String> categories = new ArrayList<>();
			categories.add("igrometri");
			categories.add("termometri");
			categories.add("sensoriDiApertura");
			for (SensorCategory category : list) {
				if (!categories.contains(category.getName()))
					check = false;
			}
			assertTrue(check && list.size() == 3);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testloadActuatorCategoriesCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test2", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<ActuatorCategory> list = databaseLoader.loadActuatorCategories();
			assertTrue(list.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testloadActuatorCategoriesCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<ActuatorCategory> list = databaseLoader.loadActuatorCategories();
			boolean check = true;
			List<String> categories = new ArrayList<>();
			categories.add("deumidificatori");
			categories.add("termoregolatori");
			for (ActuatorCategory category : list) {
				if (!categories.contains(category.getName()))
					check = false;
			}
			assertTrue(check && list.size() == 2);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetSensorCategoryInfosCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<String, String> measurementUnitMap = new HashMap<>();
			Map<String, InfoStrategy> infoStrategyMap = databaseLoader.getSensorCategoryInfos("igrometri",
					measurementUnitMap);
			assertTrue(infoStrategyMap.get("umidita") instanceof DoubleInfoStrategy && measurementUnitMap.size() == 1);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetSensorCategoryInfosCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<String, String> measurementUnitMap = new HashMap<>();
			Map<String, InfoStrategy> infoStrategyMap = databaseLoader.getSensorCategoryInfos("sensoriDiApertura",
					measurementUnitMap);
			assertTrue(infoStrategyMap.get("apertura") instanceof StringInfoStrategy && measurementUnitMap.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetListOfModesCase() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<String> listOfModes = databaseLoader.getListOfModes("termoregolatori");
			assertTrue(listOfModes.contains("idle") && listOfModes.contains("mantenimentoTemperatura")
					&& listOfModes.contains("aumentoTemperatura5gradi"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadUserCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			User user = databaseLoader.loadUser("pippo");
			assertTrue(user != null);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadUserCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			User user = databaseLoader.loadUser("pluto");
			assertTrue(user == null);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadHousingUnitsCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<HousingUnit> list = databaseLoader.loadHousingUnits("pluto");
			assertTrue(list.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadHousingUnitsCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<HousingUnit> list = databaseLoader.loadHousingUnits("paperino");
			assertTrue(list.get(0).getName().equalsIgnoreCase("Casa")
					&& list.get(0).getDescr().equalsIgnoreCase("Casa di paperino"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadHousingUnitsCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<HousingUnit> list = databaseLoader.loadHousingUnits("signor Rossi");
			Set<String> roomsSet = list.get(0).getRoomSet();
			boolean check = roomsSet.size() == 2 && roomsSet.contains("Soggiorno") && roomsSet.contains("Cucina");
			assertTrue(list.get(0).getName().equalsIgnoreCase("Casa")
					&& list.get(0).getDescr().equalsIgnoreCase("Casa del signor Rossi") && check);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadHousingUnitsCase4() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<HousingUnit> list = databaseLoader.loadHousingUnits("signor Bianchi");
			Set<String> roomsSet = list.get(0).getRoomSet();
			boolean check = roomsSet.size() == 2 && roomsSet.contains("Soggiorno") && roomsSet.contains("Cucina");
			assertTrue(list.get(0).getName().equalsIgnoreCase("Casa")
					&& list.get(0).getDescr().equalsIgnoreCase("Casa del signor Bianchi") && check
					&& list.get(0).getRule("regola1") != null);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadRoomsCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Room> list = databaseLoader.loadRooms("paperino", "Casa");
			assertTrue(list.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadRoomsCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Room> list = databaseLoader.loadRooms("signor Rossi", "Casa");
			List<String> names = list.stream().map(s -> s.getName()).collect(Collectors.toList());
			assertTrue(list.size() == 2 && names.contains("Soggiorno") && names.contains("Cucina"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadArtifactsCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Artifact> list = databaseLoader.loadArtifacts("signor Rossi", "Casa", "Soggiorno");
			assertTrue(list.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadArtifactsCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Artifact> list = databaseLoader.loadArtifacts("signor Gialli", "Casa2", "Soggiorno");
			List<String> names = list.stream().map(s -> s.getName()).collect(Collectors.toList());
			assertTrue(list.size() == 2 && names.contains("artefatto1") && names.contains("artefatto2"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetDeviceObjectsCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			String user = "signor Bianchi";
			dataFacade.addUser(user);
			String selectedHouse = "Casa";
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			String selectedRoom = "Soggiorno";
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Gettable> deviceObjects = databaseLoader.getDeviceObjects(user,
					dataFacade.getHousingUnit(user, selectedHouse), "i1_igrometri", true, true);
			assertTrue(deviceObjects.get(0).getName().equalsIgnoreCase(selectedRoom));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetDeviceObjectsCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			String user = "signor Gialli";
			String selectedHouse = "Casa2";
			String selectedRoom = "Soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto1", "artefatto1", new HashMap<>());
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Gettable> deviceObjects = databaseLoader.getDeviceObjects(user,
					dataFacade.getHousingUnit(user, selectedHouse), "i1_igrometri", true, false);
			assertTrue(deviceObjects.get(0).getName().equalsIgnoreCase("artefatto1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetDeviceObjectsCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			String user = "signor Bianchi";
			String selectedHouse = "Casa";
			String selectedRoom = "Cucina";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Gettable> deviceObjects = databaseLoader.getDeviceObjects(user,
					dataFacade.getHousingUnit(user, selectedHouse), "deum1_deumidificatori", false, true);
			assertTrue(deviceObjects.get(0).getName().equalsIgnoreCase(selectedRoom));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetDeviceObjectsCase4() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			String user = "signor Gialli";
			String selectedHouse = "Casa2";
			String selectedRoom = "Cucina";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto3", "artefatto3", new HashMap<>());
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Gettable> deviceObjects = databaseLoader.getDeviceObjects(user,
					dataFacade.getHousingUnit(user, selectedHouse), "deum1_deumidificatori", false, false);
			assertTrue(deviceObjects.get(0).getName().equalsIgnoreCase("artefatto3"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadRulesCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			String selectedUser = "signor Gialli";
			dataFacade.addUser(selectedUser);
			dataFacade.addHousingUnit(selectedUser, "Casa", "Casa", "residenziale");
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Rule> list = databaseLoader.loadRules(selectedUser, dataFacade.getHousingUnit(selectedUser, "Casa"));
			assertTrue(list.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testLoadRulesCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String selectedUser = "signor Bianchi";
			String selectedHouse = "Casa";
			dataFacade.addUser(selectedUser);
			dataFacade.addHousingUnit(selectedUser, selectedHouse, selectedHouse, "residenziale");
			dataFacade.addRoom(selectedUser, selectedHouse, "Cucina", "Cucina", new HashMap<>());
			dataFacade.addRoom(selectedUser, selectedHouse, "Soggiorno", "Soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add("Soggiorno");
			List<String> objectList2 = new ArrayList<>();
			objectList2.add("Cucina");
			dataFacade.addSensor(selectedUser, selectedHouse, "Soggiorno", "t1_termometri", "termometri", true,
					objectList);
			dataFacade.addActuator(selectedUser, selectedHouse, "Cucina", "trmg1_termoregolatori", "termoregolatori",
					true, objectList2);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			List<Rule> list = databaseLoader.loadRules(selectedUser,
					dataFacade.getHousingUnit(selectedUser, selectedHouse));
			assertTrue(list.get(0).getName().equalsIgnoreCase("regola1")
					&& list.get(0).getAntecedentText().equals("t1_termometri.temperatura > 30.0")
					&& list.get(0).getConsequentText().equals("trmg1_termoregolatori := mantenimentoTemperatura(25)"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetSensorFromAntStringCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			String user = "signor Rossi";
			String selectedHouse = "Casa2";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Set<Sensor> sensors = databaseLoader.getSensorFromAntString(user,
					dataFacade.getHousingUnit(user, selectedHouse), "time > 3.00");
			assertTrue(sensors.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetSensorFromAntStringCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "signor Rossi";
			String selectedHouse = "Casa2";
			String selectedRoom = "Cucina";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Set<Sensor> sensors = databaseLoader.getSensorFromAntString(user,
					dataFacade.getHousingUnit(user, selectedHouse), "i1_igrometri.umidita > 30 || time > 3.00");
			assertTrue(sensors.toArray(new Sensor[0])[0].getName().equalsIgnoreCase("i1_igrometri"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetSensorFromAntStringCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "signor Rossi";
			String selectedHouse = "Casa2";
			String selectedRoom = "Cucina";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Set<Sensor> sensors = databaseLoader.getSensorFromAntString(user,
					dataFacade.getHousingUnit(user, selectedHouse), "time > 3.00 && i1_igrometri.umidita > 30");
			assertTrue(sensors.toArray(new Sensor[0])[0].getName().equalsIgnoreCase("i1_igrometri"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetSensorFromAntStringCase4() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "signor Rossi";
			String selectedHouse = "Casa2";
			String selectedRoom = "Cucina";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Set<Sensor> sensors = databaseLoader.getSensorFromAntString(user,
					dataFacade.getHousingUnit(user, selectedHouse), "i1_igrometri.umidita > 30");
			assertTrue(sensors.toArray(new Sensor[0])[0].getName().equalsIgnoreCase("i1_igrometri"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetActuatorFromConsStringCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "signor Rossi";
			String selectedHouse = "Casa2";
			String selectedRoom = "Cucina";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "trmg1_termoregolatori", "termoregolatori", true,
					objectList);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Set<Actuator> actuators = databaseLoader.getActuatorsFromConsString(user,
					dataFacade.getHousingUnit(user, selectedHouse),
					"deum1_deumidificatori := aumentoUmidita; trmg1_termoregolatori := mantenimentoTemperatura(5)");
			Set<String> names = actuators.stream().map(s -> s.getName()).collect(Collectors.toSet());
			assertTrue(names.contains("deum1_deumidificatori") && names.contains("trmg1_termoregolatori"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetActuatorFromConsStringCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "signor Rossi";
			String selectedHouse = "Casa2";
			String selectedRoom = "Cucina";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "Casa del signor Bianchi", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, selectedRoom, new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "trmg1_termoregolatori", "termoregolatori", true,
					objectList);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Set<Actuator> actuators = databaseLoader.getActuatorsFromConsString(user,
					dataFacade.getHousingUnit(user, selectedHouse),
					"start := 3.00; deum1_deumidificatori := aumentoUmidita; trmg1_termoregolatori := mantenimentoTemperatura(5)");
			Set<String> names = actuators.stream().map(s -> s.getName()).collect(Collectors.toSet());
			assertTrue(names.contains("deum1_deumidificatori") && names.contains("trmg1_termoregolatori"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetNumericInfoStrategiesCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test2", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<Integer, DoubleInfoStrategy> infoStrategiesMap = databaseLoader.getNumericInfoStrategies();
			assertTrue(infoStrategiesMap.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetNumericInfoStrategiesCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<Integer, DoubleInfoStrategy> infoStrategiesMap = databaseLoader.getNumericInfoStrategies();
			assertTrue(infoStrategiesMap.containsKey(1) && infoStrategiesMap.containsKey(2));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetNonNumericInfoStrategiesCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test2", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<Integer, StringInfoStrategy> infoStrategiesMap = databaseLoader.getNonNumericInfoStrategies();
			assertTrue(infoStrategiesMap.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetNonNumericInfoStrategiesCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<Integer, StringInfoStrategy> infoStrategiesMap = databaseLoader.getNonNumericInfoStrategies();
			assertTrue(infoStrategiesMap.containsKey(3));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetPropertiesCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<String, String> propertiesMap = databaseLoader.getProperties("signor Gialli", "Casa", "Soggiorno",
					true);
			assertTrue(propertiesMap.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetPropertiesCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<String, String> propertiesMap = databaseLoader.getProperties("signor Gialli", "Casa", "artefatto1",
					false);
			assertTrue(propertiesMap.isEmpty());
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testGetPropertiesCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			Map<String, String> propertiesMap = databaseLoader.getProperties("signor Gialli", "Casa", "Bagno", true);
			assertTrue(propertiesMap.containsKey("umidita"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAddAssociationsCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "signor Gialli";
			String selectedHouse = "Casa";
			String room = "Soggiorno";
			String room2 = "Cucina";
			dataFacade.addUser(user);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			for (HousingUnit house : databaseLoader.loadHousingUnits(user)) {
				dataFacade.getUser(user).addHousingUnit(house);
			}
			databaseLoader.addAssociations(user, selectedHouse);
			boolean check = !dataFacade.isRoomAssociated(user, selectedHouse, room, "termometri")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room, "igrometri")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room, "sensoriDiApertura")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room, "deumidificatori")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room, "termoregolatori")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room2, "termometri")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room2, "igrometri")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room2, "sensoriDiApertura")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room2, "deumidificatori")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, room2, "termoregolatori");
			assertTrue(check);
		}
		catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testAddAssociationsCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "signor Bianchi";
			String selectedHouse = "Casa";
			String room = "Soggiorno";
			String room2 = "Cucina";
			dataFacade.addUser(user);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			for (HousingUnit house : databaseLoader.loadHousingUnits(user)) {
				dataFacade.getUser(user).addHousingUnit(house);
			}
			databaseLoader.addAssociations(user, selectedHouse);
			boolean check = dataFacade.isRoomAssociated(user, selectedHouse, room, "termometri")
					&& dataFacade.isRoomAssociated(user, selectedHouse, room, "igrometri")
					&& dataFacade.isRoomAssociated(user, selectedHouse, room2, "deumidificatori")
					&& dataFacade.isRoomAssociated(user, selectedHouse, room2, "termoregolatori");
			assertTrue(check);
		}
		catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testAddAssociationsCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "signor Gialli";
			String selectedHouse = "Casa2";
			dataFacade.addUser(user);
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()),
					dataFacade);
			for (HousingUnit house : databaseLoader.loadHousingUnits(user)) {
				dataFacade.getUser(user).addHousingUnit(house);
			}
			databaseLoader.addAssociations(user, selectedHouse);
			boolean check = dataFacade.isArtifactAssociated(user, selectedHouse, "artefatto2", "termometri")
					&& dataFacade.isArtifactAssociated(user, selectedHouse, "artefatto1", "igrometri")
					&& dataFacade.isArtifactAssociated(user, selectedHouse, "artefatto3", "deumidificatori")
					&& dataFacade.isArtifactAssociated(user, selectedHouse, "artefatto4", "termoregolatori");
			assertTrue(check);
		}
		catch (Exception e) {
			fail();
		}
	}
}
