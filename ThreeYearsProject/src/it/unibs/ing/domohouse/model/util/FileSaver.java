package it.unibs.ing.domohouse.model.util;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.ObjectOutputStream;

import it.unibs.ing.domohouse.model.ModelStrings;

public class FileSaver implements Saver {

	public void saveDataFacade(DataFacade data) {
		String filePath = ModelStrings.DATA_FACADE_PATH + ModelStrings.DATA_FACADE_NAME_FILE;
		createDirs(ModelStrings.DATA_FACADE_PATH);
		try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath))) {
			objectOut.writeObject(data);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void createDirs(String path) {
		File file = new File(path);
		file.mkdirs();
		assert file.isDirectory() : "Errore nella creazione della directory" + file.getAbsolutePath();
	}

	public void createConfigFile() {
		String configFile = ModelStrings.CONFIG_FILE_PATH + ModelStrings.CONFIG_FILE_NAME;
		createDirs(ModelStrings.CONFIG_FILE_PATH);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
			writer.write(ModelStrings.DEFAULT_CLOCK_STRATEGY);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
