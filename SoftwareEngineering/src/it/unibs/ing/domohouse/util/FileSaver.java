package it.unibs.ing.domohouse.util;
import java.io.Serializable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class FileSaver {

	public static final String sensorFilepath="data\\sensors";
	public static final String actuatorFilepath="data\\actuators";
	public static final String artifactFilepath="data\\artifacts";
	public static final String roomFilepath="data\\rooms";
	public static final String houseFilepath="data\\houses";
	public static final String sensorCategoryFilepath="data\\categories\\sensor";
	public static final String actuatorCategoryFilepath="data\\categories\\actuator";
		
	public void writeObjectToFile(String directory, String fileName, Object obj) {
		
		String filePath = directory+"\\"+fileName + ".dat";
		
		try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath))){ 			
			objectOut.writeObject(obj);
			objectOut.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void makeDir() {
		
	}
}
