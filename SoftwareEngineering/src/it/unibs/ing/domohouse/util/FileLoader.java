package it.unibs.ing.domohouse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class FileLoader {

	public static final String sensorFilepath="data\\sensors";
	public static final String actuatorFilepath="data\\actuators";
	public static final String artifactFilepath="data\\artifacts";
	public static final String roomFilepath="data\\rooms";
	public static final String houseFilepath="data\\houses";
	public static final String sensorCategoryFilepath="data\\categories\\sensor";
	public static final String actuatorCategoryFilepath="data\\categories\\actuator";
	
	public Object ReadObjectFromFile(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Object obj = objectIn.readObject();
         
            objectIn.close();
            return obj;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

	
}
