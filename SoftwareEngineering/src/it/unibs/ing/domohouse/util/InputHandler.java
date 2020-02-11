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

	private DataHandler dataHandler;
	//work in progress
	
	public InputHandler(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	public void readHouseFromUser() {
		String name = RawInputHandler.readNotVoidString(Strings.ARTIFACT_INPUT_NAME);
		String descr = RawInputHandler.readNotVoidString(Strings.ARTIFACT_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createHouse(name, descr);
		}
	}
	
	public void readArtifactFromUser(String location) {
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.ARTIFACT_INPUT_NAME);
			if (dataHandler.hasRoomOrArtifact(name))
				System.out.println(Strings.ARTIFACT_ROOM_NAME_ASSIGNED);
		}
		while(dataHandler.hasRoomOrArtifact(name));
		String descr = RawInputHandler.readNotVoidString(Strings.ARTIFACT_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createArtifact(name, descr, location);
		}
	}
	
	public void readNumericSensorFromUser(String location) {
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.SENSOR_INPUT_NAME);
			if (dataHandler.hasSensor(name))
				System.out.println(Strings.SENSOR_NAME_ASSIGNED);
		}
		while(dataHandler.hasSensor(name));
		String category;
		do
		{
			category = RawInputHandler.readNotVoidString(Strings.INSERT_CATEGORY);
			if (!dataHandler.hasSensorCategory(category))
				System.out.println(Strings.CATEGORY_NON_EXISTENT);
		}
		while(!dataHandler.hasSensorCategory(category));
		boolean roomOrArtifact = RawInputHandler.yesOrNo(Strings.SENSOR_ARTIFACT_OR_ROOM_ASSOCIATION);
		ArrayList<String> objectList = new ArrayList<>();
		do
		{
			String toAssoc;
			do
			{
				if (roomOrArtifact)
					toAssoc = RawInputHandler.readNotVoidString(Strings.SENSOR_ROOM_ASSOCIATION);
				else
					toAssoc = RawInputHandler.readNotVoidString(Strings.SENSOR_ARTIFACT_ASSOCIATION);
				if (!dataHandler.hasRoomOrArtifact(toAssoc))
					System.out.println(Strings.ROOM_OR_ARTIFACT_NON_EXISTENT);
				else
				{
					if (roomOrArtifact && !dataHandler.isElementARoom(toAssoc))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_ROOM);
					else if (!roomOrArtifact && dataHandler.isElementARoom(toAssoc))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_ARTIFACT);
					else if (dataHandler.isAssociated(toAssoc, category))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_CATEGORY);
				}
			}
			while(!dataHandler.hasRoomOrArtifact(toAssoc) || (roomOrArtifact && !dataHandler.isElementARoom(toAssoc)) 
					|| (!roomOrArtifact && dataHandler.isElementARoom(toAssoc)) || dataHandler.isAssociated(toAssoc, category));
			objectList.add(toAssoc);
		}
		while(RawInputHandler.yesOrNo(Strings.SENSOR_ANOTHER_ASSOCIATION));
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createNumericSensor(name, category, roomOrArtifact, objectList, location);
		}
	}
	
	public void readActuatorFromUser(String location) {
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.ACTUATOR_INPUT_NAME);
			if (dataHandler.hasActuator(name))
				System.out.println(Strings.ACTUATOR_NAME_ASSIGNED);
		}
		while(dataHandler.hasActuator(name));
		String category;
		do
		{
			category = RawInputHandler.readNotVoidString(Strings.INSERT_CATEGORY);
			if (!dataHandler.hasActuatorCategory(category))
				System.out.println(Strings.CATEGORY_NON_EXISTENT);
		}
		while(!dataHandler.hasActuatorCategory(name));
		boolean roomOrArtifact = RawInputHandler.yesOrNo(Strings.ACTUATOR_ARTIFACT_OR_ROOM_ASSOCIATION);
		ArrayList<String> objectList = new ArrayList<>();
		do
		{
			String toAssoc;
			do
			{
				if (roomOrArtifact)
					toAssoc = RawInputHandler.readNotVoidString(Strings.ACTUATOR_ROOM_ASSOCIATION);
				else
					toAssoc = RawInputHandler.readNotVoidString(Strings.ACTUATOR_ARTIFACT_ASSOCIATION);
				if (!dataHandler.hasRoomOrArtifact(toAssoc))
					System.out.println(Strings.ROOM_OR_ARTIFACT_NON_EXISTENT);
				else
				{
					if (roomOrArtifact && !dataHandler.isElementARoom(toAssoc))
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_ROOM);
					else if (!roomOrArtifact && dataHandler.isElementARoom(toAssoc))
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_ARTIFACT);
					else if (dataHandler.isAssociated(toAssoc, category))
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_CATEGORY);
				}
			}
			while(!dataHandler.hasRoomOrArtifact(toAssoc) || (roomOrArtifact && !dataHandler.isElementARoom(toAssoc)) 
					|| (!roomOrArtifact && dataHandler.isElementARoom(toAssoc)) || dataHandler.isAssociated(toAssoc, category));
			objectList.add(toAssoc);
		}
		while(RawInputHandler.yesOrNo(Strings.ACTUATOR_ANOTHER_ASSOCIATION));
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createActuator(name, category, roomOrArtifact, objectList, location);
		}
	}
	
	public void readRoomFromUser() {
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.ROOM_INPUT_NAME);
			if (dataHandler.hasRoomOrArtifact(name))
				System.out.println(Strings.NAME_ALREADY_EXISTENT);
		}
		while(dataHandler.hasRoomOrArtifact(name));
		String descr = RawInputHandler.readNotVoidString(Strings.ROOM_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createRoom(name, descr);
		}
	}
	
	public void readSensorCategoryFromUser() {
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.SENSOR_CATEGORY_INPUT_NAME);
			if (dataHandler.hasSensorCategory(name))
				System.out.println(Strings.NAME_ALREADY_EXISTENT);
		}
		while(dataHandler.hasSensorCategory(name));
		String abbreviation = RawInputHandler.readNotVoidString(Strings.INPUT_CATEGORY_ABBREVIATION);
		String constructor = RawInputHandler.readNotVoidString(Strings.INPUT_CATEGORY_MANUFACTURER);
		String domain = RawInputHandler.readNotVoidString(Strings.SENSOR_CATEGORY_INPUT_INFO_DOMAIN);
		String detectableInfo = RawInputHandler.readNotVoidString(Strings.SENSOR_CATEGORY_INPUT_INFO);
		
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createSensorCategory(name, abbreviation, constructor, domain, detectableInfo);
		}
	}
	
	public void readActuatorCategoryFromUser() {
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.ACTUATOR_CATEGORY_INPUT_NAME);
			if (dataHandler.hasActuatorCategory(name))
				System.out.println(Strings.NAME_ALREADY_EXISTENT);
		}
		while(dataHandler.hasActuatorCategory(name));
		String abbreviation = RawInputHandler.readNotVoidString(Strings.INPUT_CATEGORY_ABBREVIATION);
		String constructor = RawInputHandler.readNotVoidString(Strings.INPUT_CATEGORY_MANUFACTURER);
		ArrayList<String> listOfModes = new ArrayList<>();
		String temp;
		do
		{
			temp = RawInputHandler.readNotVoidString(Strings.ACTUATOR_CATEGORY_INPUT_OPERATING_MODE);
			if(!temp.equalsIgnoreCase(Strings.BACK_CHARACTER))
			{
				if (dataHandler.hasOperatingMode(temp))
					listOfModes.add(temp);
				else
					System.out.println(Strings.OPERATING_MODE_NOT_SUPPORTED);
			}
		}
		while(!temp.equalsIgnoreCase(Strings.BACK_CHARACTER));
		String defaultMode = RawInputHandler.readNotVoidString(Strings.ACTUATOR_CATEGORY_INPUT_DEFAULT_MODE);
		
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createActuatorCategory(name, abbreviation, constructor, listOfModes, defaultMode);
		}
	}

	public void changeHouseDescription() {
		String descr = RawInputHandler.readNotVoidString(Strings.HOUSE_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_SAVING))
		{
			dataHandler.changeHouseDescription(descr);
		}
	}

	public void changeRoomDescription(String selectedRoom) {
		String descr = RawInputHandler.readNotVoidString(Strings.ROOM_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_SAVING))
		{
			dataHandler.changeRoomDescription(selectedRoom, descr);
		}
	}
	
	public void createHouse(String name, String descr) {
		HousingUnit house = new HousingUnit(name, descr);
		dataHandler.addHouse(house);
	}
	
	public void createArtifact(String name, String descr, String location) {
		Artifact arti = new Artifact(name, descr);
		dataHandler.addArtifact(location, arti);
	}
	
	public void createNumericSensor(String name, String category, boolean roomOrArtifact, ArrayList<String> objectList, String location) {
		String realName = name + "_" + category;
		Sensor sensor = new NumericSensor(realName, dataHandler.getSensorCategory(category));
		sensor.setMeasuringRoom(roomOrArtifact);
		dataHandler.addSensor(location, sensor);
		for(String object : objectList)
		{
			dataHandler.addAssociation(object, category);
			if (roomOrArtifact)
				sensor.addEntry(dataHandler.getRoom(object));
			else
				sensor.addEntry(dataHandler.getArtifact(object));
		}
	}
	
	public void createActuator(String name, String category, boolean roomOrArtifact, ArrayList<String> objectList, String location) {
		String realName = name + "_" + category;
		Actuator actuator = new Actuator(realName, dataHandler.getActuatorCategory(category));
		actuator.setControllingRoom(roomOrArtifact);
		dataHandler.addActuator(location, actuator);
		for(String object : objectList)
		{
			dataHandler.addAssociation(object, category);
			if (roomOrArtifact)
				actuator.addEntry(dataHandler.getRoom(object));
			else
				actuator.addEntry(dataHandler.getArtifact(object));
		}
	}
	
	public void createRoom(String name, String descr) {
		Room room = new Room(name, descr);
		dataHandler.addRoom(room);
	}
	
	public void createSensorCategory(String name, String abbreviation, String constructor, String domain, String detectableInfo) {
		String descr = abbreviation+':'+constructor+':'+domain;
		SensorCategory cat = new SensorCategory(name, descr);
		dataHandler.addSensorCategory(cat);
		cat.putInfo(detectableInfo);
	}
	
	public void createActuatorCategory(String name, String abbreviation, String manufacturer,
			ArrayList<String> listOfModes, String defaultMode) {
		String descr = abbreviation+':'+manufacturer+':'+defaultMode;
		for(String toConcat : listOfModes)
		{
			descr = descr+':'+toConcat;
		}
		ActuatorCategory cat = new ActuatorCategory(name, descr);
		for(String toAdd : listOfModes)
		{
			cat.putOperatingMode(toAdd, OperatingModesHandler.getOperatingMode(toAdd));
		}
		dataHandler.addActuatorCategory(cat);
	}
	
}
