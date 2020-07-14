package it.unibs.ing.domohouse.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import it.unibs.ing.domohouse.controller.ControllerStrings;
import it.unibs.ing.domohouse.controller.inputhandler.MaintainerUnitInputHandler;
import it.unibs.ing.domohouse.controller.modules.MasterController;
import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.Room;
import it.unibs.ing.domohouse.model.components.rule.RuleParser;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.ObjectFabricator;
import it.unibs.ing.domohouse.view.RawInputHandler;
import it.unibs.ing.domohouse.view.ViewStrings;

public class DomoTest {

	private ByteArrayOutputStream output = new ByteArrayOutputStream();
	private PrintWriter writer = new PrintWriter(output, true);

	// Blackbox section

	@Test(timeout = 800)
	public void shouldExit() {
		Scanner input = buildInput("0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
	}

	@Test
	public void shouldAskForMaintainer() {
		File dataFacade = new File(ModelStrings.DATA_FACADE_PATH + ModelStrings.DATA_FACADE_NAME_FILE);
		if (dataFacade.exists())
			dataFacade.delete();
		Scanner input = buildInput("1", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(ControllerStrings.USER_FIRST_ACCESS_PROHIBITED));
	}

	@Test
	public void shouldNotAskForMaintainer() {
		Scanner input = buildInput("2", "prova", "pippo123456", "8", "7", "0", "1", "pluto", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(!getOutput().contains(ControllerStrings.USER_FIRST_ACCESS_PROHIBITED));
	}

	@Test
	public void shouldShowHouseDescription() {
		Scanner input = buildInput("2", "prova", "pippo123456", "8", "1", "importedHome", "1", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("la casa importata automaticamente"));
	}

	@Test
	public void shouldAskForOperatingModeParameters() {
		Scanner input = buildInput("2", "prova", "pippo123456", "8", "1", "importedHome", "3", "Soggiorno", "4",
				"act1_importedactuator", "mantenimentoTemperatura", "60", "2", "impsens1_importedsensor", "0", "0", "0",
				"0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(ControllerStrings.INPUT_PARAMETER_VALUE + "temperatura"));
	}

	@Test
	public void shouldUseParametersCorrectly() {
		Scanner input = buildInput("2", "prova", "pippo123456", "8", "1", "importedHome", "3", "Soggiorno", "4",
				"act1_importedactuator", "mantenimentoTemperatura", "60", "2", "impsens1_importedsensor", "4",
				"act1_importedactuator", "mantenimentoTemperatura", "50", "2", "impsens1_importedsensor", "0", "0", "0",
				"0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(ViewStrings.LAST_MEASURED_VALUE + "50.0 gradi"));
	}

	@Test
	public void shouldCreateDeviceCorrectly() {
		Scanner input = buildInput("2", "prova", "pippo123456", "5", "igrometri", "igr", "Sensors&Co", "s", "umidità",
				"0", "100", "%", "n", "s", "2", "casa", "descrizione", "s", "1", "casa", "4", "soggiorno",
				"descrizione", "20", "20", "20", "20", "n", "s", "3", "soggiorno", "7", "igrometri", "i1", "s",
				"soggiorno", "n", "s", "2", "i1_igrometri", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Nome: i1_igrometri\r\n" + "Categoria: igrometri\r\n" + "Lista delle stanze o artefatti misurati:\r\n"
						+ "soggiorno\r\n" + "Ultimo valore rilevato: 20.0 %\r\n" + "Stato: acceso"));
	}

	// Whitebox section

	@Test
	public void shouldCreateRoomPathOne() {
		Scanner rawInput = buildInput("soggiorno", "descrizione", "20", "20", "20", "20", "s", "s");
		RawInputHandler input = new RawInputHandler(rawInput, writer);
		DataFacade dataFacade = new DataFacade();

		dataFacade.addHousingUnit(new HousingUnit("testHouse", "descrizione", "residenziale", "signor Rossi"));

		RuleParser ruleParser = new RuleParser(dataFacade);
		ObjectFabricator objectFabricator = new ObjectFabricator(dataFacade, ruleParser);
		MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade,
				objectFabricator, writer, input);
		maintainerUnitInputHandler.readRoomFromUser("testHouse");

		assertTrue(dataFacade.hasRoom("testHouse", "soggiorno") && dataFacade.getRoom("testHouse", "soggiorno")
				.getProperty("presenza_persone").equals("presenza di persone"));
	}

	@Test
	public void shouldCreateRoomPathThree() {
		Scanner rawInput = buildInput("soggiorno", "bagno", "descrizione", "20", "20", "20", "20", "s", "s");
		RawInputHandler input = new RawInputHandler(rawInput, writer);
		DataFacade dataFacade = new DataFacade();

		dataFacade.addHousingUnit(new HousingUnit("testHouse", "descrizione", "residenziale", "signor Rossi"));
		Map<String, String> propertyMap = new HashMap<>();
		Room room = new Room("soggiorno", "descrizione", propertyMap);
		dataFacade.addRoom("testHouse", room);

		RuleParser ruleParser = new RuleParser(dataFacade);
		ObjectFabricator objectFabricator = new ObjectFabricator(dataFacade, ruleParser);
		MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade,
				objectFabricator, writer, input);
		maintainerUnitInputHandler.readRoomFromUser("testHouse");

		assertTrue(dataFacade.hasRoom("testHouse", "soggiorno") && dataFacade.hasRoom("testHouse", "bagno")
				&& dataFacade.getRoom("testHouse", "bagno").getProperty("presenza_persone")
						.equals("presenza di persone"));
	}

	@Test
	public void shouldCreateRoomPathFour() {
		Scanner rawInput = buildInput("soggiorno", "descrizione", "20", "20", "20", "20", "n", "s");
		RawInputHandler input = new RawInputHandler(rawInput, writer);
		DataFacade dataFacade = new DataFacade();

		dataFacade.addHousingUnit(new HousingUnit("testHouse", "descrizione", "residenziale", "signor Rossi"));

		RuleParser ruleParser = new RuleParser(dataFacade);
		ObjectFabricator objectFabricator = new ObjectFabricator(dataFacade, ruleParser);
		MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade,
				objectFabricator, writer, input);
		maintainerUnitInputHandler.readRoomFromUser("testHouse");

		assertTrue(dataFacade.hasRoom("testHouse", "soggiorno") && dataFacade.getRoom("testHouse", "soggiorno")
				.getProperty("presenza_persone").equals("assenza di persone"));
	}

	@Test
	public void shouldCreateRoomPathFive() {
		Scanner rawInput = buildInput("soggiorno", "descrizione", "20", "20", "20", "20", "s", "n");
		RawInputHandler input = new RawInputHandler(rawInput, writer);
		DataFacade dataFacade = new DataFacade();

		dataFacade.addHousingUnit(new HousingUnit("testHouse", "descrizione", "residenziale", "signor Rossi"));

		RuleParser ruleParser = new RuleParser(dataFacade);
		ObjectFabricator objectFabricator = new ObjectFabricator(dataFacade, ruleParser);
		MaintainerUnitInputHandler maintainerUnitInputHandler = new MaintainerUnitInputHandler(dataFacade,
				objectFabricator, writer, input);
		maintainerUnitInputHandler.readRoomFromUser("testHouse");

		assertFalse(dataFacade.hasRoom("testHouse", "soggiorno"));
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
