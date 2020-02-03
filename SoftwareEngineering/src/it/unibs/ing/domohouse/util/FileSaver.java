package it.unibs.ing.domohouse.util;

import it.unibs.ing.domohouse.components.*;
import java.io.FileOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;

public class FileSaver {

	private static final String thePath = "C:\\test\\data\\";
	private static final String [] directoryNames = {"sensors", "actuators", "artifacts", "rooms", "houses", 
			"categories\\sensor", "categories\\actuator"};

	private String [] directoryPaths = new String[7];


	private void writeObjectToFile(String directory, String fileName, Object obj) {
		String filePath = directory+"\\"+fileName + ".dat";
		try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath))){ 			
			objectOut.writeObject(obj);
			objectOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void writeSensorToFile(Sensor sensor) {
		writeObjectToFile(directoryPaths[0], sensor.getName(), sensor);
	}
	
	public void writeActuatorToFile(Actuator actuator) {
		writeObjectToFile(directoryPaths[1], actuator.getName(), actuator);
	}
	
	public void writeArtifactToFile(Artifact artifact) {
		writeObjectToFile(directoryPaths[2], artifact.getName(), artifact);
	}
	
	public void writeRoomToFile(Room room) {
		writeObjectToFile(directoryPaths[3], room.getName(), room);
	}
	
	public void writeHouseToFile(HousingUnit house) {
		writeObjectToFile(directoryPaths[4], house.getName(), house);
	}
	
	public void writeSensorCategoryToFile(SensorCategory category) {
		writeObjectToFile(directoryPaths[5], category.getName(), category);
	}
	
	public void writeActuatorCategoryToFile(ActuatorCategory category) {
		writeObjectToFile(directoryPaths[6], category.getName(), category);
	}
	
	public void createDirs() {
		File file;
		for(int i = 0; i < directoryNames.length; i++) {
			file = new File(thePath+directoryNames[i]);
			file.mkdirs();
			directoryPaths[i] = file.getAbsolutePath();
		}
	}
}
