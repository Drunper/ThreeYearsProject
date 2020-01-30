package it.unibs.ing.softwareengineering;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class FileSaver {

	public static final String sensorFilepath="SoftwareEngineering\\data\\sensors";
	public static final String actuatorFilepath="SoftwareEngineering\\data\\actuators";
	public static final String artifactFilepath="SoftwareEngineering\\data\\artifacts";
	public static final String roomFilepath="SoftwareEngineering\\data\rooms";
	public static final String houseFilepath="SoftwareEngineering\\data\\houses";
	public static final String sensorCategoryFilepath="SoftwareEngineering\\data\\categories\\sensor";
	public static final String actuatorCategoryFilepath="SoftwareEngineering\\data\\categories\\actuator";
		
	public void writeObjectToFile(String directory, String fileName, Object obj) {
		String filePath = directory+"\\"+fileName;
		try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath))){ 			
			objectOut.writeObject(obj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
