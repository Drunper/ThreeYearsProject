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
		dataHandler.addHouse(createHouse(name, descr));
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
			dataHandler.addArtifact(location, createArtifact(name, descr));
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
			Sensor sensor = createNumericSensor(name, category);
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
			Actuator actuator = createActuator(name, category);
			dataHandler.addActuator(actuator, location);
			for(String object : objectList)
			{
				dataHandler.addAssociation(object, category);
				if (roomOrArtifact)
					actuator.addEntry(dataHandler.getRoom(object));
				else
					actuator.addEntry(dataHandler.getArtifact(object));
			}
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
			dataHandler.addRoom(createRoom(name, descr));
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
			SensorCategory cat = createSensorCategory(name, abbreviation, constructor, domain);
			dataHandler.addSensorCategory(cat);
			cat.putInfo(detectableInfo);
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
				listOfModes.add(temp);
		}
		while(!temp.equalsIgnoreCase(Strings.BACK_CHARACTER));
		String defaultMode = RawInputHandler.readNotVoidString(Strings.ACTUATOR_CATEGORY_INPUT_DEFAULT_MODE);
		
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			ActuatorCategory cat = createActuatorCategory(name, abbreviation, constructor, listOfModes, defaultMode);
			dataHandler.addActuatorCategory(cat);
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
	
	private HousingUnit createHouse(String name, String descr) {
		return new HousingUnit(name, descr);
	}
	
	private Artifact createArtifact(String name, String descr) {
		return new Artifact(name, descr);
	}
	
	private NumericSensor createNumericSensor(String name, String category) {
		String realName = name + "_" + category;
		return new NumericSensor(realName, dataHandler.getSensorCategory(category));
	}
	
	private Actuator createActuator(String name, String category) {
		String realName = name + "_" + category;
		return new Actuator(realName, dataHandler.getActuatorCategory(category));
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
