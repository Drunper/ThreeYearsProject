package it.unibs.ing.domohouse.util;

import java.util.ArrayList;

import it.unibs.ing.domohouse.components.ActuatorCategory;
import it.unibs.ing.domohouse.components.Artifact;
import it.unibs.ing.domohouse.components.HousingUnit;
import it.unibs.ing.domohouse.components.NonParametricActuator;
import it.unibs.ing.domohouse.components.NumericSensor;
import it.unibs.ing.domohouse.components.Room;
import it.unibs.ing.domohouse.components.SensorCategory;

public class CookedDataInput {

	//work in progress
	private AssociationManager associationManager;
	private Manager sensCatManager;
	private Manager actCatManager;
	private Manager sensorManager;
	private Manager actuatorManager;
	
	public CookedDataInput() {
		associationManager = new AssociationManager();
		sensCatManager = new Manager();
		actCatManager = new Manager();
		sensorManager = new Manager();
		actuatorManager = new Manager();
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
			assoc.setIsElementARoom(false);
			location.addArtifact(createArtifact(name, descr));
			associationManager.addAssociation(assoc);
		}
	}
	
	//work in progress
	public void readNumericSensorFromUser(Room location) {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString("Inserisci il nome del sensore");
			if (sensorManager.hasEntry(name))
				System.out.println("Nome già assegnato a un sensore, prego reinserire altro nome");
		}
		while(sensorManager.hasEntry(name));
		ArrayList<Association> associationList = new ArrayList<>();
		boolean roomOrArtifact = RawDataInput.yesOrNo("Vuoi associare il sensore a stanze?(No assocerà il sensore ad artefatti)");
		do
		{
			String toAssoc;
			Association temp;
			do
			{
				toAssoc = RawDataInput.readNotVoidString("Inserisci il nome dell'artefatto/stanza da associare al sensore");
				if (!associationManager.hasEntry(toAssoc))
					System.out.println("Artefatto/stanza non presente, prego reinserire");
				else
				{
					temp = associationManager.getAssociation(toAssoc);
					if (roomOrArtifact && !temp.isElementARoom())
						System.out.println("L'elemento a cui si vuole associare il sensore non è una stanza!");
					else if (!roomOrArtifact && temp.isElementARoom())
						System.out.println("L'elemtno a cui si vuole associare il sensore non è un artefatto!");
						
				}
			}
			while(!associationManager.hasEntry(toAssoc));
			objToAssoc.add(toAssoc);
		}
		while(RawDataInput.yesOrNo("Associare sensore ad altro oggetto?"));
		String category;
		Association objectAssoc = null;
		boolean stopOperation = false;
		do
		{
			category = RawDataInput.readNotVoidString("Inserisci il nome della categoria");
			if (!sensCatManager.hasEntry(category))
				System.out.println("Categoria non esistente, prego reinserire altro nome");
			else
			{
				for(String toAssoc : objToAssoc)
				{
					objectAssoc = associationManager.getAssociation(toAssoc);
					if (objectAssoc.isAssociated(category))
					{
						System.out.println("L'artefatto/stanza è già associato con un sensore di tale categoria, inserimento sensore annullato");
						stopOperation = true;
					}
				}
			}
		}
		while(!sensCatManager.hasEntry(name));
		if(stopOperation) {
			return;
		}
		if (RawDataInput.yesOrNo("Procedere con la creazione e salvataggio?"))
		{
			NumericSensor sens = createNumericSensor(name, category); 
			sensorManager.addEntry(sens);
			location.addSensor(sens);
			objectAssoc.addAssociation(category);
		}
	}
	
	//work in progress
	public void readNonParametricActuatorFromUser(Room location) {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString("Inserisci il nome dell'attuatore");
			if (actuatorManager.hasEntry(name))
				System.out.println("Nome già assegnato a un attuatore, prego reinserire altro nome");
		}
		while(actuatorManager.hasEntry(name));
		String toAssoc;
		do
		{
			toAssoc = RawDataInput.readNotVoidString("Inserisci il nome dell'artefatto/stanza da associare all'attuatore");
			if (!associationManager.hasEntry(toAssoc))
				System.out.println("Artefatto/stanza non presente, prego reinserire");
		}
		while(!associationManager.hasEntry(toAssoc));
		String category;
		Association objectAssoc = associationManager.getAssociation(toAssoc);
		do
		{
			category = RawDataInput.readNotVoidString("Inserisci il nome della categoria");
			if (!actCatManager.hasEntry(category))
				System.out.println("Categoria non esistente, prego reinserire altro nome");
			if (objectAssoc.isAssociated(category))
				System.out.println("L'artefatto/stanza è già associato con un attuatore di tale categoria, prego reinserire altro nome");
		}
		while(!sensCatManager.hasEntry(name) || objectAssoc.isAssociated(category));
		if (RawDataInput.yesOrNo("Procedere con la creazione e salvataggio?"))
		{
			NonParametricActuator actuator = createNonParametricActuator(name, category); 
			actuatorManager.addEntry(actuator);
			location.addActuator(actuator);
			objectAssoc.addAssociation(category);
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
			assoc.setIsElementARoom(true);
			house.addEntry(createRoom(name, descr));
			associationManager.addAssociation(assoc);
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

	private Artifact createArtifact(String name, String descr) {
		return new Artifact(name, descr);
	}
	
	private NumericSensor createNumericSensor(String name, String category) {
		return new NumericSensor(name, (SensorCategory)sensCatManager.getElementByName(category));
	}
	
	private NonParametricActuator createNonParametricActuator(String name, String category) {
		return new NonParametricActuator(name, (ActuatorCategory)actCatManager.getElementByName(category));
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
