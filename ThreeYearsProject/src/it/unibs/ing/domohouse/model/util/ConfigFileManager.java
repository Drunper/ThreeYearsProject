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
		if (f.isFile() && f.canRead()) {
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

	public String getDBUserName() {
		return System.getProperty("db.username");
	}

	public String getDBpassword() {
		return System.getProperty("db.password");
	}

	public String getDBURL() {
		String url = System.getProperty("db.driver") + ":" + System.getProperty("db.dbms") + "://"
				+ System.getProperty("db.ip") + ":" + System.getProperty("db.port") + "/" + System.getProperty("db.name");
		if(System.getProperty("db.allowMultiQueries") != null)
			url += "?allowMultiQueries=" + System.getProperty("db.allowMultiQueries");
		return url;
	}
	
	public String getIP() {
		return System.getProperty("db.ip");
	}
	
	public String getPort() {
		return System.getProperty("db.port");
	}
	
	public String getDBName() {
		return System.getProperty("db.name");
	}

	private void createDirs(String path) {
		File file = new File(path);
		file.mkdirs();
		assert file.isDirectory() : "Errore nella creazione della directory" + file.getAbsolutePath();
	}
	
	public void updateConfigFile(String username, String password, String ip, String port, String dbName) {
		String configFile = ModelStrings.CONFIG_FILE_PATH + ModelStrings.CONFIG_FILE_NAME;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile, false))) {
			writer.write(ModelStrings.DEFAULT_CLOCK_STRATEGY);
			writer.newLine();
			writer.write("db.username="+username);
			writer.newLine();
			writer.write("db.password="+password);
			writer.newLine();
			writer.write("db.driver=jdbc");
			writer.newLine();
			writer.write("db.dbms=mysql");
			writer.newLine();
			writer.write("db.ip="+ip);
			writer.newLine();
			writer.write("db.port="+port);
			writer.newLine();
			writer.write("db.name="+dbName);
			writer.newLine();
			writer.write("db.allowMultiQueries=true");
			writer.newLine();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void createConfigFile() {
		String configFile = ModelStrings.CONFIG_FILE_PATH + ModelStrings.CONFIG_FILE_NAME;
		createDirs(ModelStrings.CONFIG_FILE_PATH);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
			writer.write(ModelStrings.DEFAULT_CLOCK_STRATEGY);
			writer.newLine();
			writer.write("db.username=domohouse");
			writer.newLine();
			writer.write("db.password=^v1Iz1rFOnqx");
			writer.newLine();
			writer.write("db.driver=jdbc");
			writer.newLine();
			writer.write("db.dbms=mysql");
			writer.newLine();
			writer.write("db.ip=localhost");
			writer.newLine();
			writer.write("db.port=3306");
			writer.newLine();
			writer.write("db.name=domohouse");
			writer.newLine();
			writer.write("db.allowMultiQueries=true");
			writer.newLine();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
