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

public class InputHandler {

	//work in progress
	private AssociationManager associationManager;
	private Manager sensCatManager;
	private Manager actCatManager;
	private Manager sensorManager;
	private Manager actuatorManager;
	private Manager roomManager;
	private Manager artifactManager;
	
	public InputHandler() {
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
			name = RawDataInput.readNotVoidString(Strings.ARTIFACT_INPUT_NAME);
			if (associationManager.hasEntry(name))
				System.out.println(Strings.ARTIFACT_ROOM_NAME_ASSIGNED);
		}
		while(associationManager.hasEntry(name));
		String descr = RawDataInput.readNotVoidString(Strings.ARTIFACT_INPUT_DESCRIPTION);
		if (RawDataInput.yesOrNo(Strings.PROCEED_WITH_CREATION))
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
			name = RawDataInput.readNotVoidString(Strings.SENSOR_INPUT_NAME);
			if (sensorManager.hasEntry(name))
				System.out.println(Strings.SENSOR_NAME_ASSIGNED);
		}
		while(sensorManager.hasEntry(name));
		String category;
		do
		{
			category = RawDataInput.readNotVoidString(Strings.INSERT_CATEGORY);
			if (!sensCatManager.hasEntry(category))
				System.out.println(Strings.CATEGORY_NON_EXISTENT);
		}
		while(!sensCatManager.hasEntry(name));
		boolean roomOrArtifact = RawDataInput.yesOrNo(Strings.SENSOR_ARTIFACT_OR_ROOM_ASSOCIATION);
		ArrayList<String> objectList = new ArrayList<>();
		do
		{
			String toAssoc;
			Association temp = null;
			do
			{
				if (roomOrArtifact)
					toAssoc = RawDataInput.readNotVoidString(Strings.SENSOR_ROOM_ASSOCIATION);
				else
					toAssoc = RawDataInput.readNotVoidString(Strings.SENSOR_ARTIFACT_ASSOCIATION);
				if (!associationManager.hasEntry(toAssoc))
					System.out.println(Strings.ROOM_OR_ARTIFACT_NON_EXISTENT);
				else
				{
					temp = associationManager.getAssociation(toAssoc);
					if (roomOrArtifact && !temp.isElementARoom())
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_ROOM);
					else if (!roomOrArtifact && temp.isElementARoom())
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_ARTIFACT);
					else if (temp.isAssociated(category))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_CATEGORY);
				}
			}
			while(!associationManager.hasEntry(toAssoc) || (roomOrArtifact && !temp.isElementARoom()) || 
					(!roomOrArtifact && temp.isElementARoom()) || temp.isAssociated(category));
			objectList.add(toAssoc);
		}
		while(RawDataInput.yesOrNo(Strings.SENSOR_ANOTHER_ASSOCIATION));
		if (RawDataInput.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			Sensor sensor = createNumericSensor(name, category);
			sensor.setMeasuringRoom(roomOrArtifact);
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
			name = RawDataInput.readNotVoidString(Strings.ACTUATOR_INPUT_NAME);
			if (actuatorManager.hasEntry(name))
				System.out.println(Strings.ACTUATOR_NAME_ASSIGNED);
		}
		while(actuatorManager.hasEntry(name));
		String category;
		do
		{
			category = RawDataInput.readNotVoidString(Strings.INSERT_CATEGORY);
			if (!actCatManager.hasEntry(category))
				System.out.println(Strings.CATEGORY_NON_EXISTENT);
		}
		while(!actCatManager.hasEntry(name));
		boolean roomOrArtifact = RawDataInput.yesOrNo(Strings.ACTUATOR_ARTIFACT_OR_ROOM_ASSOCIATION);
		ArrayList<String> objectList = new ArrayList<>();
		do
		{
			String toAssoc;
			Association temp = null;
			do
			{
				if (roomOrArtifact)
					toAssoc = RawDataInput.readNotVoidString(Strings.ACTUATOR_ROOM_ASSOCIATION);
				else
					toAssoc = RawDataInput.readNotVoidString(Strings.ACTUATOR_ARTIFACT_ASSOCIATION);
				if (!associationManager.hasEntry(toAssoc))
					System.out.println(Strings.ROOM_OR_ARTIFACT_NON_EXISTENT);
				else
				{
					temp = associationManager.getAssociation(toAssoc);
					if (roomOrArtifact && !temp.isElementARoom())
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_ROOM);
					else if (!roomOrArtifact && temp.isElementARoom())
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_ARTIFACT);
					else if (temp.isAssociated(category))
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_CATEGORY);
				}
			}
			while(!associationManager.hasEntry(toAssoc) || (roomOrArtifact && !temp.isElementARoom()) || 
					(!roomOrArtifact && temp.isElementARoom()) || temp.isAssociated(category));
			objectList.add(toAssoc);
		}
		while(RawDataInput.yesOrNo(Strings.ACTUATOR_ANOTHER_ASSOCIATION));
		if (RawDataInput.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			Actuator actuator = createActuator(name, category);
			location.addActuator(actuator);
			for(String object : objectList)
			{
				associationManager.getAssociation(object).addAssociation(category);
				if (roomOrArtifact)
					actuator.addEntry((Room)roomManager.getElementByName(object));
				else
					actuator.addEntry((Artifact)artifactManager.getElementByName(object));
			}
		}
	}
	
	public void readRoomFromUser(HousingUnit house) {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString(Strings.ROOM_INPUT_NAME);
			if (house.hasRoom(name) || associationManager.hasEntry(name))
				System.out.println(Strings.NAME_ALREADY_EXISTENT);
		}
		while(house.hasRoom(name));
		String descr = RawDataInput.readNotVoidString(Strings.ROOM_INPUT_DESCRIPTION);
		if (RawDataInput.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			Association assoc = new Association(name);
			Room room = createRoom(name, descr);
			assoc.setIsElementARoom(true);
			house.addRoom(room);
			associationManager.addAssociation(assoc);
			roomManager.addEntry(room);
		}
	}
	
	public void readSensorCategoryFromUser() {
		String name;
		do
		{
			name = RawDataInput.readNotVoidString(Strings.SENSOR_CATEGORY_INPUT_NAME);
			if (sensCatManager.hasEntry(name))
				System.out.println(Strings.NAME_ALREADY_EXISTENT);
		}
		while(sensCatManager.hasEntry(name));
		String abbreviation = RawDataInput.readNotVoidString(Strings.INPUT_CATEGORY_ABBREVIATION);
		String constructor = RawDataInput.readNotVoidString(Strings.INPUT_CATEGORY_MANUFACTURER);
		String domain = RawDataInput.readNotVoidString(Strings.SENSOR_CATEGORY_INPUT_INFO_DOMAIN);
		String detectableInfo = RawDataInput.readNotVoidString("Inserisci l'informazione rilevabile dalla categoria");
		
		if (RawDataInput.yesOrNo(Strings.PROCEED_WITH_CREATION))
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
			name = RawDataInput.readNotVoidString(Strings.ACTUATOR_CATEGORY_INPUT_NAME);
			if (actCatManager.hasEntry(name))
				System.out.println(Strings.NAME_ALREADY_EXISTENT);
		}
		while(actCatManager.hasEntry(name));
		String abbreviation = RawDataInput.readNotVoidString(Strings.INPUT_CATEGORY_ABBREVIATION);
		String constructor = RawDataInput.readNotVoidString(Strings.INPUT_CATEGORY_MANUFACTURER);
		ArrayList<String> listOfModes = new ArrayList<>();
		String temp;
		do
		{
			temp = RawDataInput.readNotVoidString(Strings.ACTUATOR_CATEGORY_INPUT_OPERATING_MODE);
			if(!temp.equalsIgnoreCase(Strings.BACK_CHARACTER))
				listOfModes.add(temp);
		}
		while(!temp.equalsIgnoreCase(Strings.BACK_CHARACTER));
		String defaultMode = RawDataInput.readNotVoidString(Strings.ACTUATOR_CATEGORY_INPUT_DEFAULT_MODE);
		
		if (RawDataInput.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			ActuatorCategory cat = createActuatorCategory(name, abbreviation, constructor, listOfModes, defaultMode);
			actCatManager.addEntry(cat);
		}
	}

	public void changeHouseDescription(HousingUnit home) {
		String descr = RawDataInput.readNotVoidString(Strings.HOUSE_INPUT_DESCRIPTION);
		if (RawDataInput.yesOrNo(Strings.PROCEED_WITH_SAVING))
		{
			home.setDescr(descr);
		}
	}

	public void changeRoomDescription(Room room) {
		String descr = RawDataInput.readNotVoidString(Strings.ROOM_INPUT_DESCRIPTION);
		if (RawDataInput.yesOrNo(Strings.PROCEED_WITH_SAVING))
		{
			room.setDescr(descr);
		}
	}
	private Artifact createArtifact(String name, String descr) {
		return new Artifact(name, descr);
	}
	
	private NumericSensor createNumericSensor(String name, String category) {
		String realName = name + "_" + category;
		return new NumericSensor(realName, (SensorCategory)sensCatManager.getElementByName(category));
	}
	
	private Actuator createActuator(String name, String category) {
		String realName = name + "_" + category;
		return new Actuator(realName, (ActuatorCategory)actCatManager.getElementByName(category));
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
