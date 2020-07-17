package it.unibs.ing.domohouse.model.file;

import java.io.FileInputStream;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.Loader;

public class FileLoader {

	public DataFacade loadDataFacade() {
		return safeReadDataFacade();
	}

	private DataFacade safeReadDataFacade() {
		String filePath = ModelStrings.DATA_FACADE_PATH + ModelStrings.DATA_FACADE_NAME_FILE;

		File f = new File(filePath);
		if (f.isFile() && f.canRead()) {
			try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(f))) {
				return (DataFacade) objectIn.readObject();
			}
			catch (IOException ex) {
				System.out.println(ModelStrings.ERROR_LOAD_FILE);
				ex.printStackTrace();
			}
			catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public void runFileFromSource(String sourceName) {
		try {
			File f = new File(sourceName);
			if (f.exists()) {
				if (Desktop.isDesktopSupported())
					Desktop.getDesktop().open(f);
				else
					System.out.println(ModelStrings.ERROR_HELP_FILE);
			}
			else
				System.out.println(ModelStrings.ERROR_HELP_FILE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean loadConfigFile() {
		String configFile = ModelStrings.CONFIG_FILE_PATH + ModelStrings.CONFIG_FILE_NAME;
		
		File f = new File(configFile);
		if(f.isFile() && f.canRead()) {
			try (FileInputStream fileInput = new FileInputStream(f)) {
				System.getProperties().load(fileInput);
				return true;
			}
			catch (IOException e) {
				return false;
			}
		}
		return false;
	}
}
