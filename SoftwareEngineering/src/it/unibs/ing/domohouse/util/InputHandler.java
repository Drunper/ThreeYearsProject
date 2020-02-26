package it.unibs.ing.domohouse.util;

import java.util.ArrayList;

import it.unibs.ing.domohouse.components.Actuator;
import it.unibs.ing.domohouse.components.ActuatorCategory;
import it.unibs.ing.domohouse.components.Artifact;
import it.unibs.ing.domohouse.components.HousingUnit;
import it.unibs.ing.domohouse.components.NonNumericSensor;
import it.unibs.ing.domohouse.components.NonNumericSensorCategory;
import it.unibs.ing.domohouse.components.NumericSensor;
import it.unibs.ing.domohouse.components.Room;
import it.unibs.ing.domohouse.components.Sensor;
import it.unibs.ing.domohouse.components.SensorCategory;
import it.unibs.ing.domohouse.components.NumericSensorCategory;

public class InputHandler {

	private DataHandler dataHandler;
	/*
	 * invariante dataHandler != null
	 */
	public InputHandler(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}
	
	public void readHouseFromUser() {
		String name;
		do {
		name = RawInputHandler.readNotVoidString(Strings.HOUSE_INPUT_NAME);
		if(dataHandler.hasHouse(name)) System.out.println(Strings.NAME_ALREADY_EXISTENT);
		}while(dataHandler.hasHouse(name));
		
		String descr = RawInputHandler.readNotVoidString(Strings.HOUSE_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createHouse(name, descr);	
		}
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void readSensorFromUser(String selectedHouse, String location) {
		boolean choice = RawInputHandler.yesOrNo(Strings.SENSOR_CHOICE);
		
		if(choice) readNumericSensorFromUser(selectedHouse, location);
		else readNonNumericSensorFromUser(selectedHouse, location);
	}
	public void readArtifactFromUser(String selectedHouse, String location) {
		assert location != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.ARTIFACT_INPUT_NAME);
			if (dataHandler.hasRoomOrArtifact(selectedHouse, name))
				System.out.println(Strings.ARTIFACT_ROOM_NAME_ASSIGNED);
		}
		while(dataHandler.hasRoomOrArtifact(selectedHouse, name));
		String descr = RawInputHandler.readNotVoidString(Strings.ARTIFACT_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createArtifact(selectedHouse, name, descr, location);
		}
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void readNonNumericSensorFromUser(String selectedHouse, String location) {
		assert location != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		if(dataHandler.hasNonNumericSensorCategory() && dataHandler.getSensorCategoryList().length != 0) {
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.SENSOR_INPUT_NAME);
			if (dataHandler.hasSensor(selectedHouse, name))
				System.out.println(Strings.SENSOR_NAME_ASSIGNED);
		}
		while(dataHandler.hasSensor(selectedHouse, name));
		
		ArrayList<String> categoryList = new ArrayList<String>();
		String category;
		do
		{ 
		do 
		{
		do
		{
		do
		{
		//da da da da
			category = RawInputHandler.readNotVoidString(Strings.INSERT_CATEGORY);
			if (!dataHandler.hasSensorCategory(category) && !category.equalsIgnoreCase(Strings.BACK_CHARACTER))
				System.out.println(Strings.CATEGORY_NON_EXISTENT);
		}
		while(!dataHandler.hasSensorCategory(category) && !category.equalsIgnoreCase(Strings.BACK_CHARACTER));
		
		if(category.equalsIgnoreCase(Strings.BACK_CHARACTER)) break; //break per uscire dal while per non generare eccezione del tipo getSensorCategory("^")
		if(dataHandler.getSensorCategory(category).getIsNumeric()) System.out.println(Strings.ERROR_TYPE_CATEGORY);
		}
		while(dataHandler.getSensorCategory(category).getIsNumeric());
			
		if(!category.equalsIgnoreCase(Strings.BACK_CHARACTER)) categoryList.add(category);
		}
		while(!category.equalsIgnoreCase(Strings.BACK_CHARACTER));
		if(categoryList.size() == 0) System.out.println(Strings.NO_CATEGORY_INSERTED);
		}
		while(categoryList.size() == 0);
		
		if(dataHandler.isThereRoomOrArtifact(selectedHouse)) {
			
		boolean isThereRoom = dataHandler.isThereRoom(selectedHouse);
		boolean isThereArtifact = dataHandler.isThereArtifact(selectedHouse);
		boolean roomOrArtifact;
		
		do {	
		roomOrArtifact = RawInputHandler.yesOrNo(Strings.SENSOR_ARTIFACT_OR_ROOM_ASSOCIATION);
			
		if((roomOrArtifact && !isThereRoom)) 
			System.out.println(Strings.NO_ASSOCIABLE_ELEMENT);
		else if ((!roomOrArtifact && !isThereArtifact))
			System.out.println(Strings.NO_ASSOCIABLE_ELEMENT);
		
		}while((roomOrArtifact && !isThereRoom) || (!roomOrArtifact && !isThereArtifact));
		
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
				if (!dataHandler.hasRoomOrArtifact(selectedHouse, toAssoc))
					System.out.println(Strings.ROOM_OR_ARTIFACT_NON_EXISTENT);
				else
				{
					if (roomOrArtifact && !dataHandler.isElementARoom(selectedHouse, toAssoc))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_ROOM);
					else if (!roomOrArtifact && dataHandler.isElementARoom(selectedHouse, toAssoc))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_ARTIFACT);
					else if (dataHandler.isAssociated(selectedHouse, toAssoc, category))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_CATEGORY);
				}
			}
			while(!dataHandler.hasRoomOrArtifact(selectedHouse, toAssoc) || (roomOrArtifact && !dataHandler.isElementARoom(selectedHouse, toAssoc)) 
					|| (!roomOrArtifact && dataHandler.isElementARoom(selectedHouse, toAssoc)) || dataHandler.isAssociated(selectedHouse, toAssoc, category));
			objectList.add(toAssoc);
		}
		while(RawInputHandler.yesOrNo(Strings.SENSOR_ANOTHER_ASSOCIATION));
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createNonNumericSensor(selectedHouse, name, categoryList, roomOrArtifact, objectList, location);
		}
	}else {
		System.out.println(Strings.NO_SENSOR_ROOM_OR_ARTIFACT_ERROR);
		}
	}else{
		System.out.println(Strings.NO_SENSOR_CATEGORY_ERROR);
	}
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void readNumericSensorFromUser(String selectedHouse, String location) {
		assert location != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		if(dataHandler.hasNumericSensorCategory() && dataHandler.getSensorCategoryList().length != 0) {
			
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.SENSOR_INPUT_NAME);
			if (dataHandler.hasSensor(selectedHouse, name))
				System.out.println(Strings.SENSOR_NAME_ASSIGNED);
		}
		while(dataHandler.hasSensor(selectedHouse, name));
		
		ArrayList<String> categoryList = new ArrayList<String>();
		String category;
		do
		{
		do 
		{
		do
		{
		do
		{
			//da da da da
			category = RawInputHandler.readNotVoidString(Strings.INSERT_CATEGORY);
			if ((!dataHandler.hasSensorCategory(category) && !category.equalsIgnoreCase(Strings.BACK_CHARACTER)))
				System.out.println(Strings.CATEGORY_NON_EXISTENT);
		}
		while(!dataHandler.hasSensorCategory(category) && !category.equalsIgnoreCase(Strings.BACK_CHARACTER));
		
		if(category.equalsIgnoreCase(Strings.BACK_CHARACTER)) break; //break per uscire dal while per non generare eccezione del tipo getSensorCategory("^")
		
		if(!dataHandler.getSensorCategory(category).getIsNumeric()) System.out.println(Strings.ERROR_TYPE_CATEGORY);
		}while(!dataHandler.getSensorCategory(category).getIsNumeric());
			
		if(!category.equalsIgnoreCase(Strings.BACK_CHARACTER)) categoryList.add(category);
		}
		while(!category.equalsIgnoreCase(Strings.BACK_CHARACTER));
		
		if(categoryList.size() == 0) System.out.println(Strings.NO_CATEGORY_INSERTED);
		}
		while(categoryList.size() == 0);
			
		if(dataHandler.isThereRoomOrArtifact(selectedHouse)) {
			
		boolean isThereRoom = dataHandler.isThereRoom(selectedHouse);
		boolean isThereArtifact = dataHandler.isThereArtifact(selectedHouse);
		boolean roomOrArtifact;
		
		do {	
		roomOrArtifact = RawInputHandler.yesOrNo(Strings.SENSOR_ARTIFACT_OR_ROOM_ASSOCIATION);
			
		if((roomOrArtifact && !isThereRoom)) 
			System.out.println(Strings.NO_ASSOCIABLE_ELEMENT);
		else if ((!roomOrArtifact && !isThereArtifact))
			System.out.println(Strings.NO_ASSOCIABLE_ELEMENT);
		
		}while((roomOrArtifact && !isThereRoom) || (!roomOrArtifact && !isThereArtifact));
		
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
				if (!dataHandler.hasRoomOrArtifact(selectedHouse, toAssoc))
					System.out.println(Strings.ROOM_OR_ARTIFACT_NON_EXISTENT);
				else
				{
					if (roomOrArtifact && !dataHandler.isElementARoom(selectedHouse, toAssoc))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_ROOM);
					else if (!roomOrArtifact && dataHandler.isElementARoom(selectedHouse, toAssoc))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_ARTIFACT);
					else if (dataHandler.isAssociated(selectedHouse, toAssoc, category))
						System.out.println(Strings.SENSOR_WRONG_ASSOCIATION_CATEGORY);
				}
			}
			while(!dataHandler.hasRoomOrArtifact(selectedHouse, toAssoc) || (roomOrArtifact && !dataHandler.isElementARoom(selectedHouse, toAssoc)) 
					|| (!roomOrArtifact && dataHandler.isElementARoom(selectedHouse, toAssoc)) || dataHandler.isAssociated(selectedHouse, toAssoc, category));
			objectList.add(toAssoc);
		}
		while(RawInputHandler.yesOrNo(Strings.SENSOR_ANOTHER_ASSOCIATION));
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createNumericSensor(selectedHouse, name, categoryList, roomOrArtifact, objectList, location);
		}
	}else {
		System.out.println(Strings.NO_SENSOR_ROOM_OR_ARTIFACT_ERROR);
		}
	}else {
		System.out.println(Strings.NO_SENSOR_CATEGORY_ERROR);
	}
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void readActuatorFromUser(String selectedHouse, String location) {
		assert location != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		if(dataHandler.getActuatorCategoryList().length != 0) {
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.ACTUATOR_INPUT_NAME);
			if (dataHandler.hasActuator(selectedHouse, name))
				System.out.println(Strings.ACTUATOR_NAME_ASSIGNED);
		}
		while(dataHandler.hasActuator(selectedHouse, name));
		String category;
		do
		{
			category = RawInputHandler.readNotVoidString(Strings.INSERT_CATEGORY);
			if (!dataHandler.hasActuatorCategory(category))
				System.out.println(Strings.CATEGORY_NON_EXISTENT);
		}
		while(!dataHandler.hasActuatorCategory(category));
		
		if(dataHandler.isThereRoomOrArtifact(selectedHouse)) {
			boolean isThereRoom = dataHandler.isThereRoom(selectedHouse);
			boolean isThereArtifact = dataHandler.isThereArtifact(selectedHouse);
			boolean roomOrArtifact;
			
		do {
		roomOrArtifact = RawInputHandler.yesOrNo(Strings.ACTUATOR_ARTIFACT_OR_ROOM_ASSOCIATION);
		
		if((roomOrArtifact && !isThereRoom)) 
			System.out.println(Strings.NO_ASSOCIABLE_ELEMENT);
		else if ((!roomOrArtifact && !isThereArtifact))
			System.out.println(Strings.NO_ASSOCIABLE_ELEMENT);
		
		}while((roomOrArtifact && !isThereRoom) || (!roomOrArtifact && !isThereArtifact));
		
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
				if (!dataHandler.hasRoomOrArtifact(selectedHouse, toAssoc))
					System.out.println(Strings.ROOM_OR_ARTIFACT_NON_EXISTENT);
				else
				{
					if (roomOrArtifact && !dataHandler.isElementARoom(selectedHouse, toAssoc))
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_ROOM);
					else if (!roomOrArtifact && dataHandler.isElementARoom(selectedHouse, toAssoc))
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_ARTIFACT);
					else if (dataHandler.isAssociated(selectedHouse, toAssoc, category))
						System.out.println(Strings.ACTUATOR_WRONG_ASSOCIATION_CATEGORY);
				}
			}
			while(!dataHandler.hasRoomOrArtifact(selectedHouse, toAssoc) || (roomOrArtifact && !dataHandler.isElementARoom(selectedHouse, toAssoc)) 
					|| (!roomOrArtifact && dataHandler.isElementARoom(selectedHouse, toAssoc)) || dataHandler.isAssociated(selectedHouse, toAssoc, category));
			objectList.add(toAssoc);
		}
		while(RawInputHandler.yesOrNo(Strings.ACTUATOR_ANOTHER_ASSOCIATION));
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createActuator(selectedHouse, name, category, roomOrArtifact, objectList, location);
		}
		}else {
			System.out.println(Strings.NO_ACTUATOR_ROOM_OR_ARTIFACT_ERROR);
		}
		}else {
			System.out.println(Strings.NO_ACTUATOR_CATEGORY_ERROR);
		}
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void readRoomFromUser(String selectedHouse) {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String name;
		do
		{
			name = RawInputHandler.readNotVoidString(Strings.ROOM_INPUT_NAME);
			if (dataHandler.hasRoomOrArtifact(selectedHouse, name))
				System.out.println(Strings.NAME_ALREADY_EXISTENT);
		}
		while(dataHandler.hasRoomOrArtifact(selectedHouse, name));
		String descr = RawInputHandler.readNotVoidString(Strings.ROOM_INPUT_DESCRIPTION);
		double temp = RawInputHandler.readDouble(Strings.ROOM_INPUT_TEMPERATURE);
		double umidita = RawInputHandler.readDouble(Strings.ROOM_INPUT_HUMIDITY);
		double pressione = RawInputHandler.readDouble(Strings.ROOM_INPUT_PRESSURE);
		double vento = RawInputHandler.readDouble(Strings.ROOM_INPUT_WIND);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createRoom(selectedHouse, name, descr, temp, umidita, pressione, vento);
		}
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void readSensorCategoryFromUser() {
		boolean choice = RawInputHandler.yesOrNo(Strings.SENSOR_CATEGORY_CHOICE);
		if(choice) readNumericSensorCategoryFromUser();
		else readNonNumericSensorCategoryFromUser();
	}
	
	public void readNonNumericSensorCategoryFromUser() {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
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
		ArrayList<String> domain = new ArrayList<>();
		String s;
		do {	
			s = RawInputHandler.readNotVoidString(Strings.INPUT_NON_NUMERIC_DOMAIN);
			if(!s.equals(Strings.BACK_CHARACTER)) domain.add(s);
			
		}while(!s.equals(Strings.BACK_CHARACTER));
		
		String detectableInfo = RawInputHandler.readNotVoidString(Strings.SENSOR_CATEGORY_INPUT_INFO);
		
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createNonNumericSensorCategory(name, abbreviation, constructor, domain, detectableInfo);
		}
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	
	
	public void readNumericSensorCategoryFromUser() {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
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
		String min = RawInputHandler.readNotVoidString(Strings.INSERT_SENSOR_CATEGORY_MIN_VALUE);
		String max = RawInputHandler.readNotVoidString(Strings.INSERT_SENSOR_CATEGORY_MAX_VALUE);
		String domain = min + " -to- " + max;
		String unit_measurement = RawInputHandler.readNotVoidString(Strings.SENSOR_CATEGORY_DETECTABLE_INFO);
		domain = domain + " (" + unit_measurement + ")";
		String detectableInfo = RawInputHandler.readNotVoidString(Strings.SENSOR_CATEGORY_INPUT_INFO);
		
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_CREATION))
		{
			createNumericSensorCategory(name, abbreviation, constructor, domain, detectableInfo);
		}
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void readActuatorCategoryFromUser() {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
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
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}

	public void changeHouseDescription(String selectedHouse) {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String descr = RawInputHandler.readNotVoidString(Strings.HOUSE_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_SAVING))
		{
			dataHandler.changeHouseDescription(selectedHouse, descr);
		}
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}

	public void changeRoomDescription(String selectedHouse, String selectedRoom) {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String descr = RawInputHandler.readNotVoidString(Strings.ROOM_INPUT_DESCRIPTION);
		if (RawInputHandler.yesOrNo(Strings.PROCEED_WITH_SAVING))
		{
			dataHandler.changeRoomDescription(selectedHouse, selectedRoom, descr);
		}
		
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void createHouse(String name, String descr) {
		assert name != null && name.length() > 0;
		assert descr != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		HousingUnit house = new HousingUnit(name, descr);
		dataHandler.addHouse(house);
		
		assert house != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void createArtifact(String selectedHouse, String name, String descr, String location) {
		assert name != null && name.length() > 0;
		assert descr != null;
		assert location != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		Artifact art = new Artifact(name, descr);
		dataHandler.addArtifact(selectedHouse, location, art);
		
		assert art != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void createNumericSensor(String selectedHouse, String name, ArrayList<String> categoryList, boolean roomOrArtifact, ArrayList<String> objectList, String location) {
		assert name != null && name.length() > 0;
		assert categoryList != null && objectList != null && location != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String category = "";
		for(String elem : categoryList) category = category + "_" + elem;
			
		String realName = name + category;
		
		ArrayList<SensorCategory> catList = new ArrayList<>();
		for(String elem : categoryList) {
			catList.add(dataHandler.getSensorCategory(elem));
		}
		
		Sensor sensor = new NumericSensor(realName, catList);
		sensor.setMeasuringRoom(roomOrArtifact);
		dataHandler.addSensor(selectedHouse, location, sensor);
		for(String object : objectList)
		{
			dataHandler.addAssociation(selectedHouse, object, category);
			if (roomOrArtifact)
				sensor.addEntry(dataHandler.getRoom(selectedHouse, object));
			else
				sensor.addEntry(dataHandler.getArtifact(selectedHouse, object));
		}
		
		assert sensor != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}

	public void createNonNumericSensor(String selectedHouse, String name, ArrayList<String> categoryList, boolean roomOrArtifact, ArrayList<String> objectList, String location) {
		assert name != null && name.length() > 0;
		assert categoryList != null && objectList != null && location != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		
		String category = "";
		for(String elem : categoryList) category = category + "_" + elem;
			
		String realName = name + category;
		
		ArrayList<SensorCategory> catList = new ArrayList<>();
		for(String elem : categoryList) {
			catList.add(dataHandler.getSensorCategory(elem));
		}
		
		Sensor sensor = new NonNumericSensor(realName, catList);
		sensor.setMeasuringRoom(roomOrArtifact);
		dataHandler.addSensor(selectedHouse, location, sensor);
		for(String object : objectList)
		{
			dataHandler.addAssociation(selectedHouse, object, category);
			if (roomOrArtifact)
				sensor.addEntry(dataHandler.getRoom(selectedHouse, object));
			else
				sensor.addEntry(dataHandler.getArtifact(selectedHouse, object));
		}
		
		assert sensor != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void createActuator(String selectedHouse, String name, String category, boolean roomOrArtifact, ArrayList<String> objectList, String location) {
		assert name != null && name.length() > 0;
		assert category != null && objectList != null && location != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String realName = name + "_" + category;
		Actuator actuator = new Actuator(realName, dataHandler.getActuatorCategory(category));
		actuator.setControllingRoom(roomOrArtifact);
		dataHandler.addActuator(selectedHouse, location, actuator);
		for(String object : objectList)
		{
			dataHandler.addAssociation(selectedHouse, object, category);
			if (roomOrArtifact)
				actuator.addEntry(dataHandler.getRoom(selectedHouse, object));
			else
				actuator.addEntry(dataHandler.getArtifact(selectedHouse, object));
		}
		
		assert actuator != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void createRoom(String selectedHouse, String name, String descr, double temp, double umidita, double pressione, double vento) {
		assert name != null && descr != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		Room room = new Room(name, descr, temp, umidita, pressione, vento);
		dataHandler.addRoom(selectedHouse, room);
		
		assert room != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}

	
	public void createNumericSensorCategory(String name, String abbreviation, String constructor, String domain, String detectableInfo) {
		assert name != null && name.length() > 0;
		assert abbreviation != null && constructor != null && domain != null && detectableInfo != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String descr = abbreviation+':'+constructor+':'+domain;
		NumericSensorCategory cat = new NumericSensorCategory(name, descr);
		dataHandler.addSensorCategory(cat);
		cat.putInfo(detectableInfo, domain);
		
		assert cat != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void createNonNumericSensorCategory(String name, String abbreviation, String constructor, ArrayList<String> domain, String detectableInfo) {
		assert name != null && name.length() > 0;
		assert abbreviation != null && constructor != null && domain != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String descr = abbreviation+':'+constructor+':'+domain;
		NonNumericSensorCategory cat = new NonNumericSensorCategory(name, descr);
		dataHandler.addSensorCategory(cat);
		
		cat.putInfo(detectableInfo, domain);
		
		assert cat != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void createActuatorCategory(String name, String abbreviation, String manufacturer,
			ArrayList<String> listOfModes, String defaultMode) {
		assert name != null && name.length() > 0;
		assert abbreviation != null && manufacturer != null && listOfModes != null && defaultMode != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
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
		
		assert cat != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String safeInsertSensorCategory() {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String selectedSensCategory = RawInputHandler.readNotVoidString(Strings.INSERT_SENSOR_CATEGORY);
		if(dataHandler.hasSensorCategory(selectedSensCategory)) return selectedSensCategory;
		else do {
				selectedSensCategory = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_SENSOR_CATEGORY + " " + Strings.INSERT_SENSOR_CATEGORY);
		}while(!dataHandler.hasSensorCategory(selectedSensCategory));
		
		assert selectedSensCategory != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		return selectedSensCategory;
	}
	
	public String safeInsertActuatorCategory() {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String selectedActuCategory = RawInputHandler.readNotVoidString(Strings.INSERT_ACTUATOR_CATEGORY);
		if(dataHandler.hasActuatorCategory(selectedActuCategory)) return selectedActuCategory;
		else do {	
				selectedActuCategory = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_ACTUATOR_CATEGORY + " " + Strings.INSERT_SENSOR_CATEGORY);
		}while(!dataHandler.hasActuatorCategory(selectedActuCategory));
		
		assert selectedActuCategory != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		return selectedActuCategory;
	}
	
	public String safeInsertHouse() {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String selectedHouse = RawInputHandler.readNotVoidString(Strings.INSERT_HOUSE);
		if(dataHandler.hasHouse(selectedHouse)) return selectedHouse;
		else do {
			selectedHouse = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_HOUSE + " " + Strings.INSERT_HOUSE);
		}while(!dataHandler.hasHouse(selectedHouse));
		return selectedHouse;
	}
	
	public String safeInsertRoom(String selectedHouse) {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String selectedRoom = RawInputHandler.readNotVoidString(Strings.INSERT_ROOM);
		if(dataHandler.hasRoom(selectedHouse, selectedRoom)) return selectedRoom;
		else do{
				selectedRoom = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_ROOM + " " + Strings.INSERT_ROOM);	
		}while(!dataHandler.hasRoom(selectedHouse, selectedRoom));
		
		assert selectedRoom != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		return selectedRoom;
	}

	public String safeInsertSensor(String selectedHouse) {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String selectedSensor = RawInputHandler.readNotVoidString(Strings.INSERT_SENSOR);
		if(dataHandler.hasSensor(selectedHouse, selectedSensor)) return selectedSensor;
		else do {
				selectedSensor = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_SENSOR+ " " + Strings.INSERT_SENSOR);
		}while(!dataHandler.hasSensor(selectedHouse, selectedSensor));
		
		assert selectedSensor != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		return selectedSensor;
	}
	
	public String safeInsertActuator(String selectedHouse) {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String selectedActuator = RawInputHandler.readNotVoidString(Strings.INSERT_ACTUATOR);
		if(dataHandler.hasActuator(selectedHouse, selectedActuator)) return selectedActuator;
		else do {		
				selectedActuator = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_ACTUATOR + " " + Strings.INSERT_ACTUATOR);
		}while(!dataHandler.hasActuator(selectedHouse, selectedActuator));
		
		assert selectedActuator != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		return selectedActuator;
	}
	
	public String safeInsertArtifact(String selectedHouse) {
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		
		String selectedArtifact = RawInputHandler.readNotVoidString(Strings.INSERT_ARTIFACT);
		if(dataHandler.hasArtifact(selectedHouse, selectedArtifact)) return selectedArtifact;
		do {
				selectedArtifact = RawInputHandler.readNotVoidString(Strings.ERROR_NON_EXISTENT_ARTIFACT + " " + Strings.INSERT_ARTIFACT);
		}while(!dataHandler.hasArtifact(selectedHouse, selectedArtifact));
		
		assert selectedArtifact != null;
		assert inputHandlerInvariant() : "Invariante della classe non soddisfatto";
		return selectedArtifact;
	}
	
	private boolean inputHandlerInvariant() {
		if(dataHandler != null) return true;
		return false;
	}

}
