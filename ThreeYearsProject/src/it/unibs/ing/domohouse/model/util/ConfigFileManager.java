package it.unibs.ing.domohouse.model.util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import it.unibs.ing.domohouse.model.ModelStrings;

public class ConfigFileManager {

	private PrintWriter output;
	
	public ConfigFileManager(PrintWriter output) {
		this.output = output;
	}
	
	public void runFileFromSource(String sourceName) {
		try {
			File f = new File(sourceName);
			if (f.exists()) {
				if (Desktop.isDesktopSupported())
					Desktop.getDesktop().open(f);
				else
					output.println(ModelStrings.ERROR_HELP_FILE);
			}
			else
				output.println(ModelStrings.ERROR_HELP_FILE);
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
