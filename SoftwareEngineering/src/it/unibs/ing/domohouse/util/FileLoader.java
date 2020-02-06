package it.unibs.ing.domohouse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLoader {

	private DataHandler dataHandler;
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

	
	public FileLoader(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
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
		/* TODO 
		 * Bisogna fare delle operazioni sui file, che dovranno essere formattati in maniera particolare
		 * al fine di agevolare l'operazione. Questo va fatto su ogni metodo praticamente.
		 */
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
