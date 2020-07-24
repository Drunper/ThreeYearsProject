package it.unibs.ing.domohouse.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.Test;


import it.unibs.ing.domohouse.controller.ControllerStrings;
import it.unibs.ing.domohouse.controller.modules.MasterController;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.db.DatabaseAuthenticator;
import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.model.util.HashCalculator;
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
	public void shouldSayUserNonExistent() {
		Scanner input = buildInput("1", "signor Verdi", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(ControllerStrings.ERROR_NON_EXISTENT_USER));
	}

	@Test
	public void shouldShowHouseDescription() {
		Scanner input = buildInput("1", "signor Bianchi", "1", "importedHome", "1", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("la casa importata automaticamente"));
	}
	
	@Test
	public void shouldShowRoomDescription() {
		Scanner input = buildInput("1", "signor Bianchi", "1", "importedHome", "2", "Soggiorno", "1", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Stanza\r\n" + 
				"Nome: Soggiorno\r\n" + 
				"Descrizione: Il soggiorno importato\r\n" + 
				"Nella stanza sono presenti i seguenti elementi:\r\n" + 
				"impsens2_importedsensor2 - Sensore\r\n" + 
				"impsens1_importedsensor - Sensore\r\n" + 
				"i1_igrometri - Sensore\r\n" + 
				"act1_importedactuator - Attuatore\r\n" + 
				"importedartifact - Artefatto"));
	}

	@Test
	public void shouldAskForOperatingModeParameters() {
		Scanner input = buildInput("1", "signor Bianchi", "1", "importedHome", "2", "Soggiorno", "4",
				"act1_importedactuator", "mantenimentoTemperatura", "60", "2", "impsens1_importedsensor", "0", "0", "0",
				"0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(ControllerStrings.INPUT_PARAMETER_VALUE + "temperatura"));
	}

	@Test
	public void shouldUseParametersCorrectly() {
		Scanner input = buildInput("1", "signor Bianchi", "1", "importedHome", "2", "Soggiorno", "4",
				"act1_importedactuator", "mantenimentoTemperatura", "60", "2", "impsens1_importedsensor", "4",
				"act1_importedactuator", "mantenimentoTemperatura", "50", "2", "impsens1_importedsensor", "0", "0", "0",
				"0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(ViewStrings.LAST_MEASURED_VALUE + "50.0 gradi"));
	}
	

	@Test
	public void shouldShowSensorCategoryCorrectly() {
		Scanner input = buildInput("1", "signor Bianchi", "2", "igrometri", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Categoria di sensori\r\n" + 
				"Nome: igrometri\r\n" + 
				"Sigla: IGRM\r\n" + 
				"Costruttore: Sensors&Co\r\n" + 
				"Informazioni rilevabili:\r\n" + 
				"Nome informazione: umidita Dominio_info: 0.0 -to- 100.0"));
	}
	
	@Test
	public void shouldShowActuatorCategoryCorrectly() {
		Scanner input = buildInput("1", "signor Bianchi", "3", "termoregolatori", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Categoria di attuatori\r\n" + 
				"Nome: termoregolatori\r\n" + 
				"Sigla: TRMRG\r\n" + 
				"Costruttore: Actuators&Co\r\n" + 
				"Modalità di default: idle\r\n" + 
				"Modalità operative:\r\n" + 
				"mantenimentoTemperatura\r\n" + 
				"idle\r\n" + 
				"aumentoTemperatura5gradi"));
	}
	
	@Test
	public void shouldCreateDeviceCorrectly() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Bianchi", "importedHome", "3", "Soggiorno",
				"7", "igrometri", "i1", "s",
				"Soggiorno", "n", "s", "2", "i1_igrometri", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Nome: i1_igrometri\r\n" + "Categoria: igrometri\r\n" + "Lista delle stanze o artefatti misurati:\r\n"
						+ "Soggiorno\r\n" + "Ultimo valore rilevato: 25.0 %\r\n" + "Stato: acceso"));
	}
	
	@Test
	public void testCheckPassword() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		Authenticator authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
		assertTrue(authenticator.checkMaintainerPassword("prova", "pippo123456"));
	}
	
	@Test
	public void testCheckWrongPassword() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		Authenticator authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
		assertFalse(authenticator.checkMaintainerPassword("prova", "pippo12346"));
	}
	
	@Test
	public void testAddMaintainer() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		Authenticator authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
		if(authenticator.checkMaintainerPassword("prova3", "paperino"))
			fail("prova3 già presente");
		else {
			authenticator.addMaintainer("prova3", "paperino");
			assertTrue(authenticator.checkMaintainerPassword("prova3", "paperino"));
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
