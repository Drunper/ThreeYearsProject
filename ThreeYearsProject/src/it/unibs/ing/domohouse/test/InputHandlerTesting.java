package it.unibs.ing.domohouse.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;

import it.unibs.ing.domohouse.controller.ControllerStrings;
import it.unibs.ing.domohouse.controller.inputhandler.MaintainerRoomInputHandler;
import it.unibs.ing.domohouse.controller.inputhandler.MaintainerUnitInputHandler;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.MenuManager;
import it.unibs.ing.domohouse.view.RawInputHandler;

public class InputHandlerTesting {

	private ByteArrayOutputStream output = new ByteArrayOutputStream();
	private PrintWriter writer = new PrintWriter(output, true);

	@Test
	public void testCheckCategoryAssociationsCase1() {
		Scanner input = buildInput();
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerRoomInputHandler maintainerRoomInputHandler = new MaintainerRoomInputHandler(dataFacade, writer,
					rawInputHandler);
			assertTrue(!maintainerRoomInputHandler.checkCategoryAssociations(selectedUser, "Casa", "igrometri", true)
					&& getOutput().contains(ControllerStrings.NO_ROOMS));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckCategoryAssociationsCase2() {
		Scanner input = buildInput();
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerRoomInputHandler maintainerRoomInputHandler = new MaintainerRoomInputHandler(dataFacade, writer,
					rawInputHandler);
			assertTrue(!maintainerRoomInputHandler.checkCategoryAssociations(selectedUser, "Casa2", "igrometri", true)
					&& getOutput().contains(ControllerStrings.NO_ROOM_TO_ASSOC));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckCategoryAssociationsCase3() {
		Scanner input = buildInput();
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerRoomInputHandler maintainerRoomInputHandler = new MaintainerRoomInputHandler(dataFacade, writer,
					rawInputHandler);
			assertTrue(maintainerRoomInputHandler.checkCategoryAssociations(selectedUser, "Casa2", "termometri", true));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckCategoryAssociationsCase4() {
		Scanner input = buildInput();
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerRoomInputHandler maintainerRoomInputHandler = new MaintainerRoomInputHandler(dataFacade, writer,
					rawInputHandler);
			assertTrue(!maintainerRoomInputHandler.checkCategoryAssociations(selectedUser, "Casa", "igrometri", false)
					&& getOutput().contains(ControllerStrings.NO_ARTIFACTS));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckCategoryAssociationsCase5() {
		Scanner input = buildInput();
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerRoomInputHandler maintainerRoomInputHandler = new MaintainerRoomInputHandler(dataFacade, writer,
					rawInputHandler);
			assertTrue(!maintainerRoomInputHandler.checkCategoryAssociations(selectedUser, "Casa2", "deumidificatori",
					false) && getOutput().contains(ControllerStrings.NO_ARTIFACT_TO_ASSOC));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckCategoryAssociationsCase6() {
		Scanner input = buildInput();
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerRoomInputHandler maintainerRoomInputHandler = new MaintainerRoomInputHandler(dataFacade, writer,
					rawInputHandler);
			assertTrue(maintainerRoomInputHandler.checkCategoryAssociations(selectedUser, "Casa2", "termoregolatori",
					false));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadRoomFromUserCase1() {
		Scanner input = buildInput("Cucina", "Cucina", "^", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			maintainerUnitInputHandler.readRoomFromUser("signor Verdi", "Casa2", view);
			assertTrue(!dataFacade.hasRoom(selectedUser, "Casa2", "Cucina"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadRoomFromUserCase2() {
		Scanner input = buildInput("Soggiorno", "Cucina", "Cucina", "^", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			maintainerUnitInputHandler.readRoomFromUser("signor Verdi", "Casa2", view);
			assertTrue(!dataFacade.hasRoom(selectedUser, "Casa2", "Cucina")
					&& getOutput().contains(ControllerStrings.NAME_ALREADY_EXISTENT));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadRoomFromUserCase3() {
		Scanner input = buildInput("Cucina", "Cucina", "^", "s");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			maintainerUnitInputHandler.readRoomFromUser("signor Verdi", "Casa2", view);
			assertTrue(dataFacade.hasRoom(selectedUser, "Casa2", "Cucina"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadRoomFromUserCase4() {
		Scanner input = buildInput("Cucina", "Cucina", "pressione","s", "900", "^", "s");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			maintainerUnitInputHandler.readRoomFromUser("signor Verdi", "Casa2", view);
			assertTrue(dataFacade.hasRoom(selectedUser, "Casa2", "Cucina")
					&& dataFacade.getRoom(selectedUser, "Casa2", "Cucina").hasProperty("pressione") && dataFacade
							.getRoom(selectedUser, "Casa2", "Cucina").getProperty("pressione").equalsIgnoreCase("900"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadRoomFromUserCase5() {
		Scanner input = buildInput("Cucina", "Cucina", "temperatura", "s", "35", "^", "s");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			maintainerUnitInputHandler.readRoomFromUser("signor Verdi", "Casa2", view);
			assertTrue(dataFacade.hasRoom(selectedUser, "Casa2", "Cucina")
					&& dataFacade.getRoom(selectedUser, "Casa2", "Cucina").hasProperty("temperatura")
					&& dataFacade.getRoom(selectedUser, "Casa2", "Cucina").getProperty("temperatura")
							.equalsIgnoreCase("35"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadRoomFromUserCase6() {
		Scanner input = buildInput("Cucina", "Cucina", "temperatura", "n", "^", "s");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			maintainerUnitInputHandler.readRoomFromUser("signor Verdi", "Casa2", view);
			assertTrue(dataFacade.hasRoom(selectedUser, "Casa2", "Cucina")
					&& dataFacade.getRoom(selectedUser, "Casa2", "Cucina").hasProperty("temperatura") && dataFacade
							.getRoom(selectedUser, "Casa2", "Cucina").getProperty("temperatura").equalsIgnoreCase("0"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadRoomFromUserCase7() {
		Scanner input = buildInput("Cucina", "Cucina", "temperatura", "n", "temperatura", "^", "s");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Verdi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			maintainerUnitInputHandler.readRoomFromUser("signor Verdi", "Casa2", view);
			assertTrue(dataFacade.hasRoom(selectedUser, "Casa2", "Cucina")
					&& dataFacade.getRoom(selectedUser, "Casa2", "Cucina").hasProperty("temperatura") && dataFacade
							.getRoom(selectedUser, "Casa2", "Cucina").getProperty("temperatura").equalsIgnoreCase("0")
					&& getOutput().contains(ControllerStrings.ERROR_PROPERTY_ALREADY_INSERTED));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadAntecedentCase1() {
		Scanner input = buildInput("n", ">", "3.00", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Bianchi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			Set<String> sensors = new HashSet<>();
			String antString = maintainerUnitInputHandler.readAntecedent(selectedUser, "Casa", view, sensors);
			assertTrue(sensors.isEmpty() && antString.equalsIgnoreCase("time > 3.00"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadAntecedentCase2() {
		Scanner input = buildInput("n", "a", ">", "3.00", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Bianchi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			Set<String> sensors = new HashSet<>();
			String antString = maintainerUnitInputHandler.readAntecedent(selectedUser, "Casa", view, sensors);
			assertTrue(sensors.isEmpty() && antString.equalsIgnoreCase("time > 3.00")
					&& getOutput().contains(ControllerStrings.ERROR_OPERATOR));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadAntecedentCase3() {
		Scanner input = buildInput("s", "i1_igrometri", "umidita", ">", "n", "30", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Bianchi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			Set<String> sensors = new HashSet<>();
			String antString = maintainerUnitInputHandler.readAntecedent(selectedUser, "Casa", view, sensors);
			assertTrue(sensors.contains("i1_igrometri") && antString.equalsIgnoreCase("i1_igrometri.umidita > 30"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testReadAntecedentCase4() {
		Scanner input = buildInput("s", "i1_igrometri", "umidita", ">", "n", "30", "s", "&&", "s", "t1_termometri",
				"temperatura", ">", "n", "20", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Bianchi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			Set<String> sensors = new HashSet<>();
			String antString = maintainerUnitInputHandler.readAntecedent(selectedUser, "Casa", view, sensors);
			assertTrue(sensors.contains("i1_igrometri") && sensors.contains("t1_termometri")
					&& antString.equalsIgnoreCase("i1_igrometri.umidita > 30 && t1_termometri.temperatura > 20"));
		}
		catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testReadAntecedentCase5() {
		Scanner input = buildInput("s", "i1_igrometri", "umidita", ">", "n", "30", "s", "||", "s", "t1_termometri",
				"temperatura", ">", "n", "20", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Bianchi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			Set<String> sensors = new HashSet<>();
			String antString = maintainerUnitInputHandler.readAntecedent(selectedUser, "Casa", view, sensors);
			assertTrue(sensors.contains("i1_igrometri") && sensors.contains("t1_termometri")
					&& antString.equalsIgnoreCase("i1_igrometri.umidita > 30 || t1_termometri.temperatura > 20"));
		}
		catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testReadAntecedentCase6() {
		Scanner input = buildInput("s", "i1_igrometri", "umidita", ">", "n", "30", "s", "adsa", "&&", "s", "t1_termometri",
				"temperatura", ">", "n", "20", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Bianchi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			Set<String> sensors = new HashSet<>();
			String antString = maintainerUnitInputHandler.readAntecedent(selectedUser, "Casa", view, sensors);
			assertTrue(sensors.contains("i1_igrometri") && sensors.contains("t1_termometri")
					&& antString.equalsIgnoreCase("i1_igrometri.umidita > 30 && t1_termometri.temperatura > 20"));
		}
		catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testReadAntecedentCase7() {
		Scanner input = buildInput("n", ">", "3.00", "s", "&&", "n", "n");
		RawInputHandler rawInputHandler = new RawInputHandler(input, writer);
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test", "root", "");
		try {
			connector.openConnection();
			DataFacade dataFacade = new DataFacade(connector);
			OperatingModesManager.fillOperatingModes();
			dataFacade.loadCategories();
			String selectedUser = "signor Bianchi";
			dataFacade.hasUser(selectedUser);
			dataFacade.loadHousingUnits(selectedUser);
			MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade, writer,
					rawInputHandler);
			String[] voices = {};
			MenuManager view = new MenuManager("", voices, writer, rawInputHandler);
			Set<String> sensors = new HashSet<>();
			String antString = maintainerUnitInputHandler.readAntecedent(selectedUser, "Casa", view, sensors);
			assertTrue(sensors.isEmpty() && getOutput().contains(ControllerStrings.ERROR_CONDITION_ALREADY_INSERTED)
					&& antString.equalsIgnoreCase("time > 3.00"));
		}
		catch (Exception e) {
			fail();
		}
	}
		
	private Scanner buildInput(String... inputs) {
		Scanner input = new Scanner(String.join(System.getProperty("line.separator"), inputs));
		input.useDelimiter(System.getProperty("line.separator"));
		return input;
	}

	private String getOutput() {
		return output.toString();
	}
}
