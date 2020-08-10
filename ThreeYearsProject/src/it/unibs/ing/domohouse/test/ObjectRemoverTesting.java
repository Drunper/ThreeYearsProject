package it.unibs.ing.domohouse.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import it.unibs.ing.domohouse.model.components.properties.InfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.ObjectRemover;

public class ObjectRemoverTesting {

	@Test
	public void testRemoveRuleCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);
			Set<String> involvedSensors = new HashSet<>();
			involvedSensors.add("i1_igrometri");
			Set<String> involvedActuators = new HashSet<>();
			involvedActuators.add("deum1_deumidificatori");
			dataFacade.addRule(user, selectedHouse, "regola1", "i1_igrometri.umidita > 30",
					"deum1_deumidificatori := idle", involvedSensors, involvedActuators);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRule(user, selectedHouse, "regola2");
			assertTrue(dataFacade.getEnabledRulesList().size() == 1);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveRuleCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);
			Set<String> involvedSensors = new HashSet<>();
			involvedSensors.add("i1_igrometri");
			Set<String> involvedActuators = new HashSet<>();
			involvedActuators.add("deum1_deumidificatori");
			dataFacade.addRule(user, selectedHouse, "regola1", "i1_igrometri.umidita > 30",
					"deum1_deumidificatori := idle", involvedSensors, involvedActuators);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRule(user, selectedHouse, "regola1");
			assertTrue(!dataFacade.hasRule(user, selectedHouse, "regola1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveUserCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			dataFacade.addUser(user);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeUser("pippo");

			assertTrue(dataFacade.getUserSet().size() == 1);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveUserCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			dataFacade.addUser(user);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeUser(user);

			assertTrue(!dataFacade.hasUser(user));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveHousingUnitCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeHousingUnit(user, "casa2");
			assertTrue(dataFacade.getHousingUnitSet(user).size() == 1);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveHousingUnitCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeHousingUnit(user, selectedHouse);
			assertTrue(!dataFacade.hasHousingUnit(user, selectedHouse));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveSensorCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeSensor(user, selectedHouse, selectedRoom, "i2_igrometri");
			assertTrue(dataFacade.getSensorSet(user, selectedHouse).size() == 1);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveSensorCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeSensor(user, selectedHouse, selectedRoom, "i1_igrometri");
			assertTrue(!dataFacade.hasSensor(user, selectedHouse, "i1_igrometri")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, selectedRoom, "igrometri"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveSensorCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);
			Set<String> involvedSensors = new HashSet<>();
			involvedSensors.add("i1_igrometri");
			Set<String> involvedActuators = new HashSet<>();
			involvedActuators.add("deum1_deumidificatori");
			dataFacade.addRule(user, selectedHouse, "regola1", "i1_igrometri.umidita > 30",
					"deum1_deumidificatori := idle", involvedSensors, involvedActuators);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeSensor(user, selectedHouse, selectedRoom, "i1_igrometri");
			assertTrue(!dataFacade.hasSensor(user, selectedHouse, "i1_igrometri")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, selectedRoom, "igrometri")
					&& !dataFacade.hasRule(user, selectedHouse, "regola1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveSensorCase4() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			String selectedArtifact = "artefatto";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, selectedArtifact, "descr", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedArtifact);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", false, objectList);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", false,
					objectList);
			Set<String> involvedSensors = new HashSet<>();
			involvedSensors.add("i1_igrometri");
			Set<String> involvedActuators = new HashSet<>();
			involvedActuators.add("deum1_deumidificatori");
			dataFacade.addRule(user, selectedHouse, "regola1", "i1_igrometri.umidita > 30",
					"deum1_deumidificatori := idle", involvedSensors, involvedActuators);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeSensor(user, selectedHouse, selectedRoom, "i1_igrometri");
			assertTrue(!dataFacade.hasSensor(user, selectedHouse, "i1_igrometri")
					&& !dataFacade.isArtifactAssociated(user, selectedHouse, selectedArtifact, "igrometri")
					&& !dataFacade.hasRule(user, selectedHouse, "regola1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveActuatorCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeActuator(user, selectedHouse, selectedRoom, "deum2_deumidificatori");
			assertTrue(dataFacade.getActuatorSet(user, selectedHouse).size() == 1);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveActuatorCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori");
			assertTrue(!dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, selectedRoom, "deumidificatori"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveActuatorCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);
			Set<String> involvedSensors = new HashSet<>();
			involvedSensors.add("i1_igrometri");
			Set<String> involvedActuators = new HashSet<>();
			involvedActuators.add("deum1_deumidificatori");
			dataFacade.addRule(user, selectedHouse, "regola1", "i1_igrometri.umidita > 30",
					"deum1_deumidificatori := idle", involvedSensors, involvedActuators);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori");
			assertTrue(!dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori")
					&& !dataFacade.isRoomAssociated(user, selectedHouse, selectedRoom, "deumidificatori")
					&& !dataFacade.hasRule(user, selectedHouse, "regola1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveActuatorCase4() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			String selectedArtifact = "artefatto";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, selectedArtifact, "descr", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedArtifact);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", false, objectList);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", false,
					objectList);
			Set<String> involvedSensors = new HashSet<>();
			involvedSensors.add("i1_igrometri");
			Set<String> involvedActuators = new HashSet<>();
			involvedActuators.add("deum1_deumidificatori");
			dataFacade.addRule(user, selectedHouse, "regola1", "i1_igrometri.umidita > 30",
					"deum1_deumidificatori := idle", involvedSensors, involvedActuators);
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori");
			assertTrue(!dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori")
					&& !dataFacade.isArtifactAssociated(user, selectedHouse, selectedArtifact, "deumidificatori")
					&& !dataFacade.hasRule(user, selectedHouse, "regola1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveSensorCategoryCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			int numbersOfCategory = dataFacade.getSensorCategorySet().size();
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeSensorCategory("anemometri");
			assertTrue(dataFacade.getSensorCategorySet().size() == numbersOfCategory);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveSensorCategoryCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			Map<String, InfoStrategy> infoDomainMap = new HashMap<>();
			Map<String, String> measurementUnitMap = new HashMap<>();
			dataFacade.addSensorCategory("anemometri", "ANM", "Sensors&Co", infoDomainMap, measurementUnitMap);

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeSensorCategory("anemometri");
			assertTrue(!dataFacade.hasSensorCategory("anemometri")
					&& dataFacade.hasSensor(user, selectedHouse, "i1_igrometri"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveSensorCategoryCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			Map<String, InfoStrategy> infoDomainMap = new HashMap<>();
			Map<String, String> measurementUnitMap = new HashMap<>();
			dataFacade.addSensorCategory("anemometri", "ANM", "Sensors&Co", infoDomainMap, measurementUnitMap);

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "a1_anemometri", "anemometri", true, objectList);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeSensorCategory("anemometri");
			assertTrue(!dataFacade.hasSensorCategory("anemometri")
					&& !dataFacade.hasSensor(user, selectedHouse, "a1_anemometri"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveActuatorCategoryCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			int numbersOfCategory = dataFacade.getActuatorCategorySet().size();
			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeActuatorCategory("apriporte");
			assertTrue(dataFacade.getActuatorCategorySet().size() == numbersOfCategory);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveActuatorCategoryCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			List<String> listOfModes = new ArrayList<>();
			listOfModes.add("idle");
			dataFacade.addActuatorCategory("apriporte", "APR", "Actuators&Co", listOfModes, "idle");

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeActuatorCategory("apriporte");
			assertTrue(!dataFacade.hasActuatorCategory("apriporte")
					&& dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveActuatorCategoryCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			List<String> listOfModes = new ArrayList<>();
			listOfModes.add("idle");
			dataFacade.addActuatorCategory("apriporte", "APR", "Actuators&Co", listOfModes, "idle");

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			List<String> objectList = new ArrayList<>();
			objectList.add(selectedRoom);
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "a1_apriporte", "apriporte", true, objectList);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeActuatorCategory("apriporte");
			assertTrue(!dataFacade.hasActuatorCategory("apriporte")
					&& !dataFacade.hasSensor(user, selectedHouse, "a1_apriporte"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveRoomCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRoom(user, selectedHouse, "cucina");
			assertTrue(dataFacade.getRoomsSet(user, selectedHouse).size() == 1);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveRoomCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRoom(user, selectedHouse, selectedRoom);
			assertTrue(!dataFacade.hasRoom(user, selectedHouse, selectedRoom));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveRoomCase2WithObjectsInside() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addRoom(user, selectedHouse, "cucina", "cucina", new HashMap<>());
			dataFacade.addRoom(user, selectedHouse, "bagno", "bagno", new HashMap<>());

			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto1", "descr", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto2", "descr", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("cucina");
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList1);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList1);

			List<String> objectList2 = new ArrayList<>();
			objectList2.add("bagno");
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "trmg1_termoregolatori", "termoregolatori", true,
					objectList2);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "t1_termometri", "termometri", true, objectList2);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRoom(user, selectedHouse, selectedRoom);
			boolean check = !dataFacade.hasRoom(user, selectedHouse, selectedRoom)
					&& !dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori")
					&& !dataFacade.hasActuator(user, selectedHouse, "trmg1_termoregolatori")
					&& !dataFacade.hasSensor(user, selectedHouse, "i1_igrometri")
					&& !dataFacade.hasSensor(user, selectedHouse, "t1_termometri");
			assertTrue(check);
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void removeRoomTestCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addRoom(user, selectedHouse, "cucina", "cucina", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("cucina");
			objectList1.add("soggiorno");
			dataFacade.addSensor(user, selectedHouse, "cucina", "i1_igrometri", "igrometri", true, objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRoom(user, selectedHouse, selectedRoom);
			assertTrue(!dataFacade.hasRoom(user, selectedHouse, selectedRoom)
					&& dataFacade.hasSensor(user, selectedHouse, "i1_igrometri")
					&& !dataFacade.getSensor(user, selectedHouse, "i1_igrometri").isMeasuringObject("soggiorno"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void removeRoomTestCase4() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addRoom(user, selectedHouse, "cucina", "cucina", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("soggiorno");
			dataFacade.addSensor(user, selectedHouse, "cucina", "i1_igrometri", "igrometri", true, objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRoom(user, selectedHouse, selectedRoom);
			assertTrue(!dataFacade.hasRoom(user, selectedHouse, selectedRoom)
					&& !dataFacade.hasSensor(user, selectedHouse, "i1_igrometri"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void removeRoomTestCase5() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addRoom(user, selectedHouse, "cucina", "cucina", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("cucina");
			objectList1.add("soggiorno");
			dataFacade.addActuator(user, selectedHouse, "cucina", "deum1_deumidificatori", "deumidificatori", true,
					objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRoom(user, selectedHouse, selectedRoom);
			assertTrue(!dataFacade.hasRoom(user, selectedHouse, selectedRoom)
					&& dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori")
					&& !dataFacade.getActuator(user, selectedHouse, "deum1_deumidificatori").isControllingObject("soggiorno"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void removeRoomTestCase6() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addRoom(user, selectedHouse, "cucina", "cucina", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("soggiorno");
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeRoom(user, selectedHouse, selectedRoom);
			assertTrue(!dataFacade.hasRoom(user, selectedHouse, selectedRoom)
					&& !dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveArtifactCase1() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());

			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto1", "descr", new HashMap<>());

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeArtifact(user, selectedHouse, selectedRoom, "artefatto2");

			assertTrue(dataFacade.hasArtifact(user, selectedHouse, "artefatto1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveArtifactCase2() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());

			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto1", "descr", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("soggiorno");
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", true,
					objectList1);
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", true, objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeArtifact(user, selectedHouse, selectedRoom, "artefatto1");

			assertTrue(!dataFacade.hasArtifact(user, selectedHouse, "artefatto1") && dataFacade.hasSensor(user, selectedHouse, "i1_igrometri") && dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveArtifactCase3() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto1", "descr", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto2", "descr", new HashMap<>());			
			
			List<String> objectList1 = new ArrayList<>();
			objectList1.add("artefatto1");
			objectList1.add("artefatto2");
			dataFacade.addSensor(user, selectedHouse, "soggiorno", "i1_igrometri", "igrometri", false, objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeArtifact(user, selectedHouse, selectedRoom, "artefatto1");
			assertTrue(!dataFacade.hasArtifact(user, selectedHouse, "artefatto1")
					&& dataFacade.hasSensor(user, selectedHouse, "i1_igrometri")
					&& !dataFacade.getSensor(user, selectedHouse, "i1_igrometri").isMeasuringObject("artefatto1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveArtifactCase4() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto1", "descr", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("artefatto1");
			dataFacade.addSensor(user, selectedHouse, selectedRoom, "i1_igrometri", "igrometri", false, objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeArtifact(user, selectedHouse, selectedRoom, "artefatto1");
			assertTrue(!dataFacade.hasArtifact(user, selectedHouse, "artefatto1")
					&& !dataFacade.hasSensor(user, selectedHouse, "i1_igrometri"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveArtifactCase5() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto1", "descr", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto2", "descr", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("artefatto1");
			objectList1.add("artefatto2");
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", false,
					objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeArtifact(user, selectedHouse, selectedRoom, "artefatto1");
			assertTrue(!dataFacade.hasArtifact(user, selectedHouse, "artefatto1")
					&& dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori")
					&& !dataFacade.getActuator(user, selectedHouse, "deum1_deumidificatori").isControllingObject("artefatto1"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testRemoveArtifactCase6() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();

			String user = "pluto";
			String selectedHouse = "casa";
			String selectedRoom = "soggiorno";
			dataFacade.addUser(user);
			dataFacade.addHousingUnit(user, selectedHouse, "casina", "residenziale");
			dataFacade.addRoom(user, selectedHouse, selectedRoom, "soggiorno", new HashMap<>());
			dataFacade.addArtifact(user, selectedHouse, selectedRoom, "artefatto1", "descr", new HashMap<>());

			List<String> objectList1 = new ArrayList<>();
			objectList1.add("artefatto1");
			dataFacade.addActuator(user, selectedHouse, selectedRoom, "deum1_deumidificatori", "deumidificatori", false,
					objectList1);

			ObjectRemover objectRemover = new ObjectRemover(dataFacade);
			objectRemover.removeArtifact(user, selectedHouse, selectedRoom, "artefatto1");
			assertTrue(!dataFacade.hasArtifact(user, selectedHouse, "artefatto1")
					&& !dataFacade.hasActuator(user, selectedHouse, "deum1_deumidificatori"));
		}
		catch (Exception e) {
			fail();
		}
	}
}
