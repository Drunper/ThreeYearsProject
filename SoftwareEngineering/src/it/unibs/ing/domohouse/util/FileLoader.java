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
import java.util.ArrayList;
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
	private static final String SENSOR_CATEGORIES_PATH = "inizialization\\categories\\sensor";
	private static final String ACTUATOR_CATEGORIES_PATH = "inizialization\\categories\\actuator";
	private static final String HOUSES_PATH = "inizialization\\houses";
	private static final String ROOMS_PATH = "inizialization\\rooms";
	private static final String SENSOR_PATH = "inizialization\\sensors";
	private static final String ACTUATOR_PATH = "inizialization\\actuators";
	private static final String ARTIFACT_PATH = "inizialization\\artifacts";

	
	public FileLoader(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	/*
	 * Ottiene la lista dei file presenti in una data cartella
	 */
	private List<File> getFiles(String path) {
		try(Stream<Path> files = Files.walk(Paths.get(path))){
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
		for(File file : filesInFolder)
		{
			String name = null;
			String abbreviation = null;
			String manufacturer = null;
			String defaultMode = null;
			ArrayList<String> listOfModes = new ArrayList<>();
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
			    		case "Modalità_di_default":
			    			defaultMode = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Modalità_operative":
			    			String [] modes = splitLine[Strings.SECOND_TOKEN].split(",");
			    			for(String mode : modes)
			    				listOfModes.add(mode);
			    			break;
			    	}
			    }
			 
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputHandler.createActuatorCategory(name, abbreviation, manufacturer, listOfModes, defaultMode);
		}
	}
	
	private void readHouse() {
		List<File> filesInFolder = getFiles(HOUSES_PATH);
		for(File file : filesInFolder)
		{
			String name = null;
			String descr = null;
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
			    		case "Descrizione":
			    			descr = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    	}
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputHandler.createHouse(name, descr);
		}
	}
	
	private void readRooms() {
		List<File> filesInFolder = getFiles(ROOMS_PATH);
		for(File file : filesInFolder)
		{
			String name = null;
			String descr = null;
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
			    		case "Descrizione":
			    			descr = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    	}
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputHandler.createRoom(name, descr);
		}
	}

	private void readSensors() {
		List<File> filesInFolder = getFiles(SENSOR_PATH);
		for(File file : filesInFolder)
		{
			String name = null;
			String category = null;
			String location = null;
			boolean roomOrArtifact = false;
			ArrayList<String> controlledObjects = new ArrayList<>();
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
			    		case "Categoria":
			    			category = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Stanza":
			    			location = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Stanze_o_artefatti":
			    			if(splitLine[Strings.SECOND_TOKEN].equalsIgnoreCase("stanze"))
			    				roomOrArtifact = true;
			    			else
			    				roomOrArtifact = false;
			    			break;
			    		case "Oggetti_controllati":
			    			String [] listOfObjects = splitLine[Strings.SECOND_TOKEN].split(",");
			    			for(String object : listOfObjects)
			    				controlledObjects.add(object);
			    			break;
			    	}
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputHandler.createNumericSensor(name, category, roomOrArtifact, controlledObjects, location);
		}
	}

	private void readActuators() {
		List<File> filesInFolder = getFiles(ACTUATOR_PATH);
		for(File file : filesInFolder)
		{
			String name = null;
			String category = null;
			String location = null;
			boolean roomOrArtifact = false;
			ArrayList<String> controlledObjects = new ArrayList<>();
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
			    		case "Categoria":
			    			category = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Stanza":
			    			location = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Stanze_o_artefatti":
			    			if(splitLine[Strings.SECOND_TOKEN].equalsIgnoreCase("stanze"))
			    				roomOrArtifact = true;
			    			else
			    				roomOrArtifact = false;
			    			break;
			    		case "Oggetti_controllati":
			    			String [] listOfObjects = splitLine[Strings.SECOND_TOKEN].split(",");
			    			for(String object : listOfObjects)
			    				controlledObjects.add(object);
			    			break;
			    	}
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputHandler.createNumericSensor(name, category, roomOrArtifact, controlledObjects, location);
		}
	}
	
	private void readArtifacts() {
		List<File> filesInFolder = getFiles(ARTIFACT_PATH);
		for(File file : filesInFolder)
		{
			String name = null;
			String descr = null;
			String location = null;
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
			    		case "Descrizione":
			    			descr = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    		case "Stanza":
			    			location = splitLine[Strings.SECOND_TOKEN];
			    			break;
			    	}
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
			inputHandler.createArtifact(name, descr, location);
		}
	}
	
	/*
	private Object ReadObjectFromFile(String filepath) {
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filepath))){
            return objectIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    */
}
