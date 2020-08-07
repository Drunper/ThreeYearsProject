package it.unibs.ing.domohouse.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.Test;

import it.unibs.ing.domohouse.controller.ControllerStrings;
import it.unibs.ing.domohouse.controller.modules.MasterController;
import it.unibs.ing.domohouse.view.ViewStrings;

public class FunctionalityTesting {

	private ByteArrayOutputStream output = new ByteArrayOutputStream();
	private PrintWriter writer = new PrintWriter(output, true);

	@Test(timeout = 800)
	public void shouldExit() {
		Scanner input = buildInput("0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
	}

	@Test
	public void shouldSayUserNonExistent() {
		Scanner input = buildInput("1", "signor Blu", "0");
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
		Scanner input = buildInput("1", "signor Bianchi", "1", "importedHome", "2", "Soggiorno", "1", "0", "0", "0",
				"0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Stanza\r\n" + "Nome: Soggiorno\r\n" + "Descrizione: Il soggiorno importato\r\n"
				+ "Nella stanza sono presenti i seguenti elementi:\r\n" + "impsens2_importedsensor2 - Sensore\r\n"
				+ "impsens1_importedsensor - Sensore\r\n" + "i1_igrometri - Sensore\r\n"
				+ "act1_importedactuator - Attuatore\r\n" + "importedartifact - Artefatto"));
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
				"Categoria di sensori\r\n" + "Nome: igrometri\r\n" + "Sigla: IGRM\r\n" + "Costruttore: Sensors&Co\r\n"
						+ "Informazioni rilevabili:\r\n" + "Nome informazione: umidita Dominio_info: 0.0 -to- 100.0"));
	}

	@Test
	public void shouldShowActuatorCategoryCorrectly() {
		Scanner input = buildInput("1", "signor Bianchi", "3", "termoregolatori", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Categoria di attuatori\r\n" + "Nome: termoregolatori\r\n" + "Sigla: TRMRG\r\n"
				+ "Costruttore: Actuators&Co\r\n" + "Modalità di default: idle\r\n" + "Modalità operative:\r\n"
				+ "mantenimentoTemperatura\r\n" + "idle\r\n" + "aumentoTemperatura5gradi"));
	}

	@Test
	public void addUser() {
		Scanner input = buildInput("2", "prova", "pippo123456", "1", "signor Verdi", "s", "3", "signor Verdi", "0",
				"0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Non sono presenti unità immobiliari nel sistema. E' necessario l'intervento del manutentore."));
	}

	@Test
	public void removeUser() {
		Scanner input = buildInput("2", "prova", "pippo123456", "2", "signor Gialli", "3", "signor Gialli", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("L'utente inserito non esiste"));
	}

	@Test
	public void addHousingUnit() {
		Scanner input = buildInput("2", "prova", "pippo123456", "4", "signor Rossi", "Casetta", "Casetta",
				"residenziale", "s", "3", "signor Rossi", "Casetta", "1", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Unità immobiliare\r\n" + "Nome: Casetta\r\n" + "Descrizione: Casetta\r\n"
				+ "Tipo unità immobiliare: residenziale\r\n" + "Sono presenti le seguenti stanze:"));
	}

	@Test
	public void removeHousingUnit() {
		Scanner input = buildInput("2", "prova", "pippo123456", "5", "signor Rossi", "Casa in montagna", "3", "signor Rossi",
				"Casa in montagna", "Casa1", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Attenzione! La casa selezionata non esiste, assicurati di aver digitato correttamente il nome. Inserisci il nome della casa su cui vuoi operare"));
	}

	@Test
	public void insertSensorCategory() {
		Scanner input = buildInput("2", "prova", "pippo123456", "8", "termoigrometri", "TRIG", "Sensors&Co", "s", "s",
				"1", "°C", "s", "s", "s", "5", "%", "n", "s", "6", "termoigrometri", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Categoria di sensori\r\n" + "Nome: termoigrometri\r\n" + "Sigla: TRIG\r\n"
				+ "Costruttore: Sensors&Co\r\n" + "Informazioni rilevabili:\r\n"
				+ "Nome informazione: umidita Dominio_info: 0.0 -to- 100.0\r\n"
				+ "Nome informazione: temperatura Dominio_info: 0.0 -to- 100.0"));
	}

	@Test
	public void insertActuatorCategory() {
		Scanner input = buildInput("2", "prova", "pippo123456", "9", "termoumidificatori", "TRMU", "Actuators&Co",
				"idle", "aumentoTemperatura5gradi", "diminuzioneTemperatura5gradi", "aumentoUmidita",
				"diminuzioneUmidita", "^", "idle", "s", "7", "termoumidificatori", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Categoria di attuatori\r\n" + "Nome: termoumidificatori\r\n"
				+ "Sigla: TRMU\r\n" + "Costruttore: Actuators&Co\r\n" + "Modalità di default: idle\r\n"
				+ "Modalità operative:\r\n" + "diminuzioneUmidita\r\n" + "aumentoUmidita\r\n" + "idle\r\n"
				+ "aumentoTemperatura5gradi\r\n" + "diminuzioneTemperatura5gradi"));
	}

	@Test
	public void removeSensorCategory() {
		Scanner input = buildInput("2", "prova", "pippo123456", "10", "termobarometri", "6", "termobarometri",
				"termometri", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Attenzione! La categoria di sensori selezionata non esiste. Inserisci la categoria di sensori che vuoi visualizzare"));
	}

	@Test
	public void removeActuatorCategory() {
		Scanner input = buildInput("2", "prova", "pippo123456", "11", "termoregolatorimini", "7", "termoregolatorimini",
				"termoregolatori", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Attenzione! La categoria di attuatori selezionata non esiste. Inserisci la categoria di attuatori che vuoi visualizzare"));
	}

	@Test
	public void modifyHousingUnitDescription() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa al mare", "2", "Questa è la casa al mare del signor Rossi",
				"s", "1", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Unità immobiliare\r\n" + 
				"Nome: Casa al mare\r\n" + 
				"Descrizione: Questa è la casa al mare del signor Rossi\r\n" + 
				"Tipo unità immobiliare: residenziale\r\n" + 
				"Sono presenti le seguenti stanze:\r\n" + 
				"Soggiorno\r\n" + 
				"Bagno\r\n" + 
				"Cucina"));
	}

	@Test
	public void showRoom() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa al mare", "3", "Soggiorno", "1",
				"0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Stanza\r\n" + "Nome: Soggiorno\r\n" + "Descrizione: Soggiorno della casa al mare\r\n"
				+ "Nella stanza sono presenti i seguenti elementi:\r\n"));
	}

	@Test
	public void insertRoom() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "4", "Bagno",
				"Descrizione bagno", "temperatura", "s", "30", "^", "s", "3", "Bagno", "1", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Stanza\r\n" + "Nome: Bagno\r\n" + "Descrizione: Descrizione bagno\r\n"
				+ "Nella stanza sono presenti i seguenti elementi:"));
	}

	@Test
	public void removeRoom() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "5", "Cucina", "3",
				"Cucina", "Soggiorno", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Attenzione! La stanza selezionata non esite, assicurati di aver digitato correttamente il nome. Inserisci il nome della stanza su cui vuoi operare"));
	}

	@Test
	public void addRule() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "6", "regola1", "s",
				"i1_igrometri", "umidita", ">", "30", "n", "deum1_deumidificatori", "diminuzioneUmidita", "n", "n", "7",
				"0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"[if]i1_igrometri.umidita > 30.0[then]deum1_deumidificatori := diminuzioneUmidita	Stato: attiva"));
	}

	@Test
	public void activateOrDeactivateRule() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Bianchi", "importedHome", "9", "importedRule", "8", "0",
				"0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"[if]time > 10.0 && impsens1_importedsensor.temperatura > 10.0[then]act1_importedactuator := mantenimentoTemperatura(9)	Stato: disattiva"));
	}

	@Test
	public void removeRule() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Bianchi", "importedHome", "10", "importedRule2", "8", "0",
				"0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertFalse(getOutput()
				.contains("[if]impsens1_importedsensor.pressione > 20.0[then]act1_importedactuator := mantenimentoTemperatura(32)"));
	}

	@Test
	public void showSensor() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "3", "Soggiorno", "2",
				"i1_igrometri", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Sensore\r\n" + "Nome: i1_igrometri\r\n" + "Categoria: igrometri\r\n"
				+ "Lista delle stanze o artefatti misurati:\r\n" + "Soggiorno\r\n"
				+ "Ultimo valore rilevato: 25.0 %\r\n" + "Stato: acceso"));
	}

	@Test
	public void showActuator() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "3", "Soggiorno", "3",
				"deum1_deumidificatori", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Attuatore\r\n" + "Nome: deum1_deumidificatori\r\n"
				+ "Categoria: deumidificatori\r\n" + "Lista delle stanze o artefatti controllati:\r\n" + "Soggiorno\r\n"
				+ "Modalità operativa: idle\r\n" + "Stato: acceso"));
	}

	@Test
	public void operateActuator() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "3", "Soggiorno", "4",
				"deum1_deumidificatori", "aumentoUmidita", "2", "i1_igrometri", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Sensore\r\n" + "Nome: i1_igrometri\r\n" + "Categoria: igrometri\r\n"
				+ "Lista delle stanze o artefatti misurati:\r\n" + "Soggiorno\r\n"
				+ "Ultimo valore rilevato: 27.0 %\r\n" + "Stato: acceso"));
	}

	@Test
	public void showArtifact() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "3", "Soggiorno", "5",
				"Finestra del soggiorno", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains(
				"Artefatto\r\n" + "Nome: Finestra del soggiorno\r\n" + "Descrizione: La finestra del Soggiorno."));
	}

	@Test
	public void modifyRoomDescription() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa al mare", "3", "Bagno", "6",
				"Descrizione bagno", "s", "1", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Stanza\r\n" + "Nome: Bagno\r\n" + "Descrizione: Descrizione bagno\r\n"
				+ "Nella stanza sono presenti i seguenti elementi:\r\n"));
	}

	@Test
	public void insertSensor() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "3", "Soggiorno", "7",
				"termometri", "t1", "s", "Soggiorno", "n", "s", "2", "t1_termometri", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Sensore\r\n" + "Nome: t1_termometri\r\n"
				+ "Categoria: termometri\r\n" + "Lista delle stanze o artefatti misurati:\r\n" + "Soggiorno\r\n"
				+ "Ultimo valore rilevato: 0.0 °C\r\n" + "Stato: acceso"));
	}

	@Test
	public void insertActuator() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "3", "Soggiorno", "8",
				"termoregolatori", "trmg1", "s", "Soggiorno", "n", "s", "3", "trmg1_termoregolatori", "0", "0", "0",
				"0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Attuatore\r\n" + "Nome: trmg1_termoregolatori\r\n"
				+ "Categoria: termoregolatori\r\n" + "Lista delle stanze o artefatti controllati:\r\n" + "Soggiorno\r\n"
				+ "Modalità operativa: idle\r\n" + "Stato: acceso"));
	}

	@Test
	public void insertArtifact() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa1", "3", "Soggiorno", "9",
				"Porta del soggiorno", "La porta del soggiorno", "apertura", "n", "^", "s", "5", "Porta del soggiorno", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Artefatto\r\n" + "Nome: Porta del soggiorno\r\n"
				+ "Descrizione: La porta del soggiorno"));
	}
	
	@Test
	public void removeSensor() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa al mare", "3", "Cucina", "10", "t1_termometri", "2", "t1_termometri", "i1_igrometri", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Attenzione! Il sensore selezionato non esiste. Inserisci il nome del sensore che vuoi visualizzare"));
	}
	
	@Test
	public void removeActuator() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa al mare", "3", "Cucina", "11", "trmg1_termoregolatori", "3", "trmg1_termoregolatori", "deum1_deumidificatori", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Attenzione! L'attuatore selezionato non esiste. Inserisci il nome dell'attuatore che vuoi visualizzare"));
	}
	
	@Test
	public void removeArtifact() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa al mare", "3", "Cucina", "12", "Porta della cucina", "5", "Porta della cucina", "Finestra della cucina", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Attenzione! L'artefatto selezionato non esiste. Inserisci il nome dell'artefatto che vuoi visualizzare"));
	}
	
	@Test
	public void activateOrDeactivateDevice() {
		Scanner input = buildInput("2", "prova", "pippo123456", "3", "signor Rossi", "Casa al mare", "3", "Cucina", "13", "s", "i1_igrometri", "2", "i1_igrometri", "0", "0", "0", "0");
		MasterController controller = new MasterController(input, writer);
		controller.start();
		assertTrue(getOutput().contains("Sensore\r\n" + 
				"Nome: i1_igrometri\r\n" + 
				"Categoria: igrometri\r\n" + 
				"Lista delle stanze o artefatti misurati:\r\n" + 
				"Cucina\r\n" + 
				"Ultimo valore rilevato: 25.0 %\r\n" + 
				"Stato: spento"));
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
