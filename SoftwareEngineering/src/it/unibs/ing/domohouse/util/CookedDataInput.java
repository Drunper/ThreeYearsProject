package it.unibs.ing.domohouse.util;

import java.util.ArrayList;

import it.unibs.ing.domohouse.components.Actuator;
import it.unibs.ing.domohouse.components.ActuatorCategory;
import it.unibs.ing.domohouse.components.Artifact;
import it.unibs.ing.domohouse.components.HousingUnit;
import it.unibs.ing.domohouse.components.NumericSensor;
import it.unibs.ing.domohouse.components.Room;
import it.unibs.ing.domohouse.components.Sensor;
import it.unibs.ing.domohouse.components.SensorCategory;

public class CookedDataInput {

	//work in progress
	private AssociationManager associationManager;
	private Manager sensCatManager;
	private Manager actCatManager;
	private Manager sensorManager;
	private Manager actuatorManager;
	private Manager roomManager;
	private Manager artifactManager;
	
	public CookedDataInput() {
		associationManager = new AssociationManager();
		sensCatManager = new Manager();
		actCatManager = new Manager();
		sensorManager = new Manager();
		actuatorManager = new Manager();
		roomManager = new Manager();
		artifactManager = new Manager();
	}
	
	public void readArtifactFromUser(Room location) {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString("Inserisci il nome dell'artefatto");
			if (associationManager.hasEntry(name))
				System.out.println("Nome già assegnato a stanza/artefatto, prego reinserire altro nome");
		}
		while(associationManager.hasEntry(name));
		String descr = RawDataInput.readNotVoidString("Inserisci la descrizione dell'artefatto");
		if (RawDataInput.yesOrNo("Procedere con la creazione e salvataggio?"))
		{
			Association assoc = new Association(name);
			Artifact artifact = createArtifact(name, descr);
			assoc.setIsElementARoom(false);
			location.addArtifact(artifact);
			artifactManager.addEntry(artifact);
			associationManager.addAssociation(assoc);
		}
	}
	
	public void readNumericSensorFromUser(Room location) {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString("Inserisci il nome del sensore");
			if (sensorManager.hasEntry(name))
				System.out.println("Nome già assegnato a un sensore, prego reinserire altro nome");
		}
		while(sensorManager.hasEntry(name));
		String category;
		do
		{
			category = RawDataInput.readNotVoidString("Inserisci il nome della categoria");
			if (!sensCatManager.hasEntry(category))
				System.out.println("Categoria non esistente, prego reinserire altro nome");
		}
		while(!sensCatManager.hasEntry(name));
		boolean roomOrArtifact = RawDataInput.yesOrNo("Vuoi associare il sensore a stanze?(No assocerà il sensore ad artefatti)");
		ArrayList<String> objectList = new ArrayList<>();
		do
		{
			String toAssoc;
			Association temp = null;
			do
			{
				if (roomOrArtifact)
					toAssoc = RawDataInput.readNotVoidString("Inserisci il nome della stanza da associare al sensore");
				else
					toAssoc = RawDataInput.readNotVoidString("Inserisci il nome dell'artefatto da associare al sensore");
				if (!associationManager.hasEntry(toAssoc))
					System.out.println("Artefatto/stanza non presente, prego reinserire");
				else
				{
					temp = associationManager.getAssociation(toAssoc);
					if (roomOrArtifact && !temp.isElementARoom())
						System.out.println("L'elemento a cui si vuole associare il sensore non è una stanza!");
					else if (!roomOrArtifact && temp.isElementARoom())
						System.out.println("L'elemento a cui si vuole associare il sensore non è un artefatto!");
					else if (temp.isAssociated(category))
						System.out.println("Un sensore di questa categoria è già associato all'artefatto in questione");
				}
			}
			while(!associationManager.hasEntry(toAssoc) || (roomOrArtifact && !temp.isElementARoom()) || 
					(!roomOrArtifact && temp.isElementARoom()) || temp.isAssociated(category));
			objectList.add(toAssoc);
		}
		while(RawDataInput.yesOrNo("Associare sensore ad altro oggetto?"));
		if (RawDataInput.yesOrNo("Procedere con la creazione e salvataggio?"))
		{
			Sensor sensor = createNumericSensor(name, category);
			location.addSensor(sensor);
			for(String object : objectList)
			{
				associationManager.getAssociation(object).addAssociation(category);
				if (roomOrArtifact)
					sensor.addEntry((Room)roomManager.getElementByName(object));
				else
					sensor.addEntry((Artifact)artifactManager.getElementByName(object));
			}
		}
	}
	
	public void readActuatorFromUser(Room location) {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString("Inserisci il nome dell'attuatore");
			if (actuatorManager.hasEntry(name))
				System.out.println("Nome già assegnato a un attuatore, prego reinserire altro nome");
		}
		while(actuatorManager.hasEntry(name));
		String category;
		do
		{
			category = RawDataInput.readNotVoidString("Inserisci il nome della categoria");
			if (!actCatManager.hasEntry(category))
				System.out.println("Categoria non esistente, prego reinserire altro nome");
		}
		while(!actCatManager.hasEntry(name));
		boolean roomOrArtifact = RawDataInput.yesOrNo("Vuoi associare l'attuatore a stanze?(No assocerà l'attuatore ad artefatti)");
		ArrayList<String> objectList = new ArrayList<>();
		do
		{
			String toAssoc;
			Association temp = null;
			do
			{
				if (roomOrArtifact)
					toAssoc = RawDataInput.readNotVoidString("Inserisci il nome della stanza da associare all'attuatore");
				else
					toAssoc = RawDataInput.readNotVoidString("Inserisci il nome dell'artefatto da associare all'attuatore");
				if (!associationManager.hasEntry(toAssoc))
					System.out.println("Artefatto/stanza non presente, prego reinserire");
				else
				{
					temp = associationManager.getAssociation(toAssoc);
					if (roomOrArtifact && !temp.isElementARoom())
						System.out.println("L'elemento a cui si vuole associare l'attuatore non è una stanza!");
					else if (!roomOrArtifact && temp.isElementARoom())
						System.out.println("L'elemento a cui si vuole associare l'attuatore non è un artefatto!");
					else if (temp.isAssociated(category))
						System.out.println("Un attuatore di questa categoria è già associato all'artefatto in questione");
				}
			}
			while(!associationManager.hasEntry(toAssoc) || (roomOrArtifact && !temp.isElementARoom()) || 
					(!roomOrArtifact && temp.isElementARoom()) || temp.isAssociated(category));
			objectList.add(toAssoc);
		}
		while(RawDataInput.yesOrNo("Associare sensore ad altro oggetto?"));
		if (RawDataInput.yesOrNo("Procedere con la creazione e salvataggio?"))
		{
			Actuator actuator = createActuator(name, category);
			location.addActuator(actuator);
			for(String object : objectList)
			{
				associationManager.getAssociation(object).addAssociation(category);
				if (roomOrArtifact)
					actuator.addEntry(roomManager.getElementByName(object));
				else
					actuator.addEntry(artifactManager.getElementByName(object));
			}
		}
	}
	
	public void readRoomFromUser(HousingUnit house) {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString("Inserisci il nome della stanza");
			if (house.hasEntry(name) || associationManager.hasEntry(name))
				System.out.println("Nome già inserito, prego reinserire altro nome");
		}
		while(house.hasEntry(name));
		String descr = RawDataInput.readNotVoidString("Inserisci la descrizione della stanza");
		if (RawDataInput.yesOrNo("Procedere con la creazione e salvataggio?"))
		{
			Association assoc = new Association(name);
			Room room = createRoom(name, descr);
			assoc.setIsElementARoom(true);
			house.addEntry(room);
			associationManager.addAssociation(assoc);
			roomManager.addEntry(room);
		}
	}
	
	public void readSensorCategoryFromUser() {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString("Inserisci il nome della categoria di sensori");
			if (sensCatManager.hasEntry(name))
				System.out.println("Nome già inserito, prego reinserire altro nome");
		}
		while(sensCatManager.hasEntry(name));
		String abbreviation = RawDataInput.readNotVoidString("Inserisci la sigla della categoria");
		String constructor = RawDataInput.readNotVoidString("Inserisci il costruttore");
		String domain = RawDataInput.readNotVoidString("Inserisci il dominio dell'informazione");
		String detectableInfo = RawDataInput.readNotVoidString("Inserisci l'informazione rilevabile dalla categoria");
		
		if (RawDataInput.yesOrNo("Procedere con la creazione e salvataggio?"))
		{
			SensorCategory cat = createSensorCategory(name, abbreviation, constructor, domain);
			sensCatManager.addEntry(cat);
			cat.putInfo(detectableInfo);
		}
	}
	
	public void readActuatorCategoryFromUser() {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString("Inserisci il nome della categoria di attuatori");
			if (actCatManager.hasEntry(name))
				System.out.println("Nome già inserito, prego reinserire altro nome");
		}
		while(actCatManager.hasEntry(name));
		String abbreviation = RawDataInput.readNotVoidString("Inserisci la sigla della categoria");
		String constructor = RawDataInput.readNotVoidString("Inserisci il costruttore");
		ArrayList<String> listOfModes = new ArrayList<>();
		String temp;
		do
		{
			temp = RawDataInput.readNotVoidString("Inserisci una modalità operativa (^ per terminare)");
			if(!temp.equalsIgnoreCase("^"))
				listOfModes.add(temp);
		}
		while(!temp.equalsIgnoreCase("^"));
		String defaultMode = RawDataInput.readNotVoidString("Inserisci la modalità di default (tra quelle già inserite)");
		
		if (RawDataInput.yesOrNo("Procedere con la creazione e salvataggio?"))
		{
			ActuatorCategory cat = createActuatorCategory(name, abbreviation, constructor, listOfModes, defaultMode);
			actCatManager.addEntry(cat);
		}
	}

	public void changeHouseDescription(HousingUnit home) {
		String descr = RawDataInput.readNotVoidString("Inserisci la descrizione dell'unità immobiliare");
		if (RawDataInput.yesOrNo("Salvare le modifiche?"))
		{
			home.setDescr(descr);
		}
	}

	public void changeRoomDescription(Room room) {
		String descr = RawDataInput.readNotVoidString("Inserisci la descrizione della stanza");
		if (RawDataInput.yesOrNo("Salvare le modifiche?"))
		{
			room.setDescr(descr);
		}
	}
	private Artifact createArtifact(String name, String descr) {
		return new Artifact(name, descr);
	}
	
	private NumericSensor createNumericSensor(String name, String category) {
		return new NumericSensor(name, (SensorCategory)sensCatManager.getElementByName(category));
	}
	
	private Actuator createActuator(String name, String category) {
		return new Actuator(name, (ActuatorCategory)actCatManager.getElementByName(category));
	}
	
	private Room createRoom(String name, String descr) {
		return new Room(name, descr);
	}
	
	private SensorCategory createSensorCategory(String name, String abbreviation, String constructor, String domain) {
		String descr = abbreviation+':'+constructor+':'+domain;
		return new SensorCategory(name, descr);
	}
	
	private ActuatorCategory createActuatorCategory(String name, String abbreviation, String constructor,
			ArrayList<String> listOfModes, String defaultMode) {
		String descr = abbreviation+':'+constructor+':'+defaultMode;
		for(String toConcat : listOfModes)
		{
			descr = descr+':'+toConcat;
		}
		return new ActuatorCategory(name, descr);
	}
}
