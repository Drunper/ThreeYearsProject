package it.unibs.ing.domohouse.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLoader {

	private InputHandler inputHandler;
	/*
	 * Cartelle in cui mettere i file txt. SE vogliamo usare l'applicazione su altri OS dobbiamo adattare queste stringhe
	 * ottenendo il separatore usato dal file system. A quel punto non saranno più costanti. Bisogna informarsi su questo
	 * ambito.
	 */
	private static final String SENSOR_CATEGORIES_PATH = "C:\\test\\data\\categories\\sensor";
	private static final String ACTUATOR_CATEGORIES_PATH = "C:\\test\\data\\categories\\actuator";
	private static final String HOUSES_PATH = "C:\\test\\data\\houses";
	private static final String ROOMS_PATH = "C:\\test\\data\\rooms";
	private static final String SENSOR_PATH = "C:\\test\\data\\sensors";
	private static final String ACTUATOR_PATH = "C:\\test\\data\\actuators";
	private static final String ARTIFACT_PATH = "C:\\test\\data\\artifacts";

	
	public FileLoader(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	/*
	 * Ottiene la lista dei file presenti in una data cartella
	 */
	private List<File> getFiles(String path) {
		try(Stream<Path> files = Files.walk(Paths.get("/path/to/folder"))){
			return files
	                .filter(Files::isRegularFile)
	                .map(Path::toFile)
	                .collect(Collectors.toList());
		}
		catch(IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/*
	 * Lettura da file (txt) per l'inizializzazione del programma. Utile a fini di test e in fase di presentazione.
	 */
	public void createBasicFiles() {
		readSensorCategories();
		readActuatorCategories();
		readHouse();
		readRooms();
		readSensors();
		readActuators();
		readArtifacts();
	}

	private void readSensorCategories() {
		List<File> filesInFolder = getFiles(SENSOR_CATEGORIES_PATH);
		
		/*
		 * Possibile soluzione del problema di inizializzazione del programma da file.
		 * I file di testo devono essere formattati in un formato specifico. In questo caso:
		 * Nome:xxxx
		 * Sigla:yyyy
		 * Costruttore:zzzz
		 * Dominio_info:wwwww
		 * Info:uuuu
		 * 
		 * Nel caso della versione 2 (in cui ci possono essere più informazioni rilevabili è necessario ripensare tutto.
		 */
		for(File file : filesInFolder)
		{
			String name = null;
			String abbreviation = null;
			String manufacturer = null;
			String domain = null;
			String detectableInfo = null;
			try (BufferedReader bReader = new BufferedReader(new FileReader(file))) {
				String line;
				
			    while ((line = bReader.readLine()) != null) 
			    {
			    	String [] splitLine = line.split(":");
			    	switch(splitLine[Strings.FIRST_TOKEN])
			    	{
			    		case "Nome":
			    			name = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Sigla":
			    			abbreviation = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Costruttore":
			    			manufacturer = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Dominio_info":
			    			domain = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Info":
			    			detectableInfo = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    	}
			    }
			 
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputHandler.createSensorCategory(name, abbreviation, manufacturer, domain, detectableInfo);
		}	
	}

	private void readActuatorCategories() {
		List<File> filesInFolder = getFiles(ACTUATOR_CATEGORIES_PATH);
	}
	
	private void readHouse() {
		// TODO legge nome, descrizione della casa
	}
	
	private void readRooms() {
		// TODO legge tutte le stanze e le mette nella casa
	}

	private void readSensors() {
		// TODO Auto-generated method stub
	}

	private void readActuators() {
		// TODO vedi sopra
	}
	
	private void readArtifacts() {
		// TODO legge tutti gli artefatti e li mette nelle stanze corrette
	}
	
	private Object ReadObjectFromFile(String filepath) {
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filepath))){
            return objectIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
