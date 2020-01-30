package it.unibs.ing.softwareengineering;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class FileSaver {

	private static final String sensorFilepath="SoftwareEngineering\\data\\sensors";
	private static final String actuatorFilepath="SoftwareEngineering\\data\\actuators";
	private static final String artifactFilepath="SoftwareEngineering\\data\\artifacts";
	private static final String roomFilepath="SoftwareEngineering\\data\rooms";
	private static final String houseFilepath="SoftwareEngineering\\data\\houses";
	private static final String sensorCategoryFilepath="SoftwareEngineering\\data\\categories\\sensor";
	private static final String actuatorCategoryFilepath="SoftwareEngineering\\data\\categories\\actuator";
		
	private void writeObjectToFile(String directory, String fileName, Object obj) {
		String filePath = directory+"\\"+fileName;
		try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath))){ 			
			objectOut.writeObject(obj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
