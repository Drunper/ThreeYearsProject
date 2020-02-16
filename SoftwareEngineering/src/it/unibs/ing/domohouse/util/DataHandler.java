package it.unibs.ing.domohouse.util;

import java.io.Serializable;

import it.unibs.ing.domohouse.components.*;

public class DataHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 830399600665259268L;
	private Manager sensorCategoryManager;
	private Manager actuatorCategoryManager;
	private HousingUnit housingUnit;
	/*
	 * invariante sensorCategoryManager != null, actuatorCategoryManager != null
	 */
	public DataHandler () {
		sensorCategoryManager = new Manager();
		actuatorCategoryManager = new Manager();
		/*
		housingUnit = new HousingUnit("Casa di Ivan", "Boh"); 
		SensorCategory temperatura = new SensorCategory("sensori di temperatura", "TEMP:Tommaso Ebhardt:idle:endD");
		SensorCategory antifiamma = new SensorCategory("sensori antifiamma", "ANTFLM:Herbert Asbury:idle:endD");
		SensorCategory antifumo = new SensorCategory("sensori di rilevamento fumo", "ANTFM:Douglas Adams:idle:endD");
		SensorCategory movimento = new SensorCategory("sensori di movimento", "MVM:Sun Tzu:idle:endD");
		
		ActuatorCategory sprinkler = new ActuatorCategory("sprinkler", "Sprink:Sprinkler spa:idle:endD");
		ActuatorCategory interruttori = new ActuatorCategory("interruttori di accensione", "INTACC:Edward Snowden:idle:endD"); 
		ActuatorCategory allarme_acustico = new ActuatorCategory("allarme acustico", "ALLRM:Jordan Belfort:idle:endD");
		
		Room soggiorno = new Room("Soggiorno", "Sala in cui si vive penso");
		Room cucina = new Room("Cucina", "Sala in cui di solito si cucina ma ognuno può vivere come gli pare, se si mette a dormire li dentro a me non frega niente");
		Room camera = new Room("Camera", "Si dorme");
		soggiorno.setNumericProperty("temperatura", 20);
		soggiorno.setNumericProperty("umidità", 53);
		soggiorno.setNumericProperty("pressione", 872);
		soggiorno.setNumericProperty("vento", 0);
		cucina.setNumericProperty("temperatura", 20);
		cucina.setNumericProperty("umidità", 53);
		cucina.setNumericProperty("pressione", 872);
		cucina.setNumericProperty("vento", 0);
		camera.setNumericProperty("temperatura", 20);
		camera.setNumericProperty("umidità", 53);
		camera.setNumericProperty("pressione", 872);
		camera.setNumericProperty("vento", 0);
		
		housingUnit.addRoom(soggiorno);
		housingUnit.addRoom(cucina);
		housingUnit.addRoom(camera);
		
		Artifact lampadario_camera = new Artifact("lampadarioCamera", "sono un bel lampadario ammuffito");
		Artifact lampadario_cucina = new Artifact("lampadarioCucina", "sono un lampadario abbronzato");
		Artifact forno = new Artifact("Forno da cucina", "Proprio un bel forno di quelli costosi e luccicanti");
		Artifact lampadario_soggiorno = new Artifact("lampadarioMurano", "Sono un lampa-dario");
		Artifact portaOmbrelli = new Artifact("portaOmbrelli", "servo solo per far numero e non ho molto senso");
		
		
		Sensor temperino = new NumericSensor("t1_temperatura", temperatura);
		Sensor temperino2 = new NumericSensor("t2_temperatura", temperatura);
		Sensor sensore_antifiamma = new NumericSensor("ant1_antifiamma", antifiamma); //soggiorno
		Sensor sensore_antifumo = new NumericSensor("antfum1_antifumo", antifumo);//soggiorno
		Sensor sensore_antifiamma1 = new NumericSensor("ant2_antifiamma", antifiamma); //cucina
		Sensor sensore_antifumo1 = new NumericSensor("antifum2_antifumo", antifumo); //cucina
		Sensor sensore_movimento = new NumericSensor("mov1_movimento", movimento); //soggiorno
		
		Actuator attuatorino1 = new Actuator("a1_interruttori", interruttori);
		Actuator attuatorino2 = new Actuator("a2_interruttori", interruttori);
		Actuator sprinkler1 = new Actuator("s1_sprinkler", sprinkler);
		Actuator sprinkler2 = new Actuator("s2_sprinkler", sprinkler);
		Actuator allarme1 = new Actuator("all1_allarme", allarme_acustico);
		Actuator allarme2 = new Actuator("all2_allarme", allarme_acustico);
		Actuator allarme3 = new Actuator("all3_allarme", allarme_acustico);
		
		
		sensorCategoryManager.addEntry(temperatura);
		sensorCategoryManager.addEntry(antifiamma);
		sensorCategoryManager.addEntry(antifumo);
		sensorCategoryManager.addEntry(movimento);
		actuatorCategoryManager.addEntry(interruttori);
		actuatorCategoryManager.addEntry(sprinkler);
		actuatorCategoryManager.addEntry(allarme_acustico);
		
		soggiorno.addActuator(attuatorino1);
		soggiorno.addActuator(attuatorino2);
		soggiorno.addArtifact(portaOmbrelli);
		soggiorno.addArtifact(lampadario_soggiorno);
		soggiorno.addSensor(temperino);
		soggiorno.addSensor(temperino2);
		soggiorno.addActuator(allarme1);
		soggiorno.addSensor(sensore_movimento);
		
		cucina.addArtifact(forno);
		cucina.addArtifact(lampadario_cucina);
		cucina.addSensor(sensore_antifiamma);
		cucina.addSensor(sensore_antifumo);
		cucina.addActuator(sprinkler1);
		cucina.addActuator(allarme2);
		
		camera.addSensor(sensore_antifiamma1);
		camera.addSensor(sensore_antifumo1);
		camera.addActuator(sprinkler2);
		camera.addActuator(allarme3);
		camera.addArtifact(lampadario_camera);

		housingUnit.addSensor("Soggiorno", temperino);
		housingUnit.addSensor("Soggiorno", temperino2);
		housingUnit.addSensor("Soggiorno", sensore_movimento);
		housingUnit.addActuator(attuatorino1, "Soggiorno");
		housingUnit.addActuator(attuatorino2, "Soggiorno");
		housingUnit.addActuator(allarme1, "Soggiorno");
		housingUnit.addArtifact(lampadario_soggiorno, "Soggiorno");
		housingUnit.addArtifact(portaOmbrelli, "Soggiorno");
		housingUnit.addSensor("Camera", sensore_antifiamma1);
		housingUnit.addSensor("Camera", sensore_antifumo1);
		housingUnit.addActuator(sprinkler2, "Camera");
		housingUnit.addActuator(allarme3, "Camera");
		housingUnit.addArtifact(lampadario_camera, "Camera");
		housingUnit.addSensor("Cucina", sensore_antifiamma);
		housingUnit.addSensor("Cucina", sensore_antifumo);
		housingUnit.addActuator(sprinkler1, "Cucina");
		housingUnit.addActuator(allarme2, "Cucina");
		housingUnit.addArtifact(forno, "Cucina");
		housingUnit.addArtifact(lampadario_cucina, "Cucina");
		
		temperino.addEntry(soggiorno);
		temperino2.addEntry(soggiorno);
		attuatorino1.addEntry(soggiorno);
		attuatorino2.addEntry(soggiorno);
		allarme1.addEntry(soggiorno);
		sensore_movimento.addEntry(soggiorno);
		sensore_antifiamma1.addEntry(camera);
		sensore_antifumo1.addEntry(camera);
		sprinkler2.addEntry(camera);
		allarme3.addEntry(camera);
		sensore_antifiamma.addEntry(cucina);
		sensore_antifumo.addEntry(cucina);
		sprinkler1.addEntry(cucina);
		allarme2.addEntry(cucina);
*/
	}


	public void addHouse(HousingUnit toAdd) {
		assert toAdd != null;
		housingUnit = toAdd;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public String getHousingUnitString() {
		assert housingUnit != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return housingUnit.toString();
	}

	public String[] getRoomList() {
		assert housingUnit != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return housingUnit.roomList();
	}

	public String[] getSensorCategoryList() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return sensorCategoryManager.namesList();
	}

	public String getSensorCategoryString(String selectedSensCategory) {
		assert selectedSensCategory != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert sensorCategoryManager.hasEntry(selectedSensCategory) : "sensorCategoryManager di dataHandler non contiene " + selectedSensCategory;
		
		String result = sensorCategoryManager.getElementString(selectedSensCategory);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public String[] getActuatorCategoryList() {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return actuatorCategoryManager.namesList();
	}

	public String getActuatorCategoryString(String selectedActuCategory) {
		assert selectedActuCategory != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert actuatorCategoryManager.hasEntry(selectedActuCategory) : "actuatorCategoryManager non contiene " + selectedActuCategory;
		
		String result =  actuatorCategoryManager.getElementString(selectedActuCategory);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public boolean hasSensorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return sensorCategoryManager.hasEntry(category);
	}
	
	public boolean hasActuatorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return actuatorCategoryManager.hasEntry(category);
	}

	public void addSensorCategory(SensorCategory cat) {
		assert cat != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = sensorCategoryManager.size();
		
		sensorCategoryManager.addEntry(cat);
		
		assert sensorCategoryManager.size() >= pre_size;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void addActuatorCategory(ActuatorCategory cat) {
		assert cat != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		int pre_size = actuatorCategoryManager.size();
		
		actuatorCategoryManager.addEntry(cat);
		
		assert actuatorCategoryManager.size() >= pre_size;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public SensorCategory getSensorCategory(String category) {
		assert category != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert sensorCategoryManager.hasEntry(category) : "sensorCategoryManager non contiene " + category;
		
		SensorCategory s = (SensorCategory)sensorCategoryManager.getElementByName(category);
		
		assert s != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return s;
	}

	public ActuatorCategory getActuatorCategory(String category) {
		assert category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert actuatorCategoryManager.hasEntry(category) : "actuatorCategoryManager non contiene " + category;
		
		ActuatorCategory act = (ActuatorCategory)actuatorCategoryManager.getElementByName(category);
		
		assert act != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return act;
	}

	public String getRoomString(String selectedRoom) {
		assert selectedRoom != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		assert housingUnit.hasRoom(selectedRoom) : "La casa " + housingUnit.getName() + " non contiene la stanza " + selectedRoom;
		
		String result =  housingUnit.getRoomString(selectedRoom);
		
		assert result != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result;
	}

	public String[] getSensorNames(String selectedRoom) {

		assert selectedRoom != null && housingUnit != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit.hasRoom(selectedRoom) : "La casa + " + housingUnit.getName() + " non contiene la stanza " + selectedRoom;
		
		Room room = housingUnit.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return room.getSensorsNames();
	}

	public String getSensorString(String selectedSensor) {
		assert selectedSensor != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		
		String result = housingUnit.getSensorString(selectedSensor);
		
		assert result != null; 
		return result;
	}

	public String[] getActuatorNames(String selectedRoom) {

		assert selectedRoom != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		assert housingUnit.hasRoom(selectedRoom) : "La casa " + housingUnit.getName() + " non contiene la stanza " + selectedRoom;
		
		Room room = housingUnit.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return room.getActuatorsNames();
	}

	public String getActuatorString(String selectedActuator) {
		assert selectedActuator != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		
		String result =  housingUnit.getActuatorString(selectedActuator);
		
		assert result != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return result; 
	}
	

	public String[] getArtifactNames(String selectedRoom) {
		assert selectedRoom != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		assert housingUnit.hasRoom(selectedRoom) : "La casa " + housingUnit.getName() + " non contiente la stanza " + selectedRoom + " e dunque non può restituire i nomi degli artefatti";
		
		Room room = housingUnit.getRoom(selectedRoom);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return room.getArtifactsNames();
	}

	public String getArtifactString(String selectedArtifact) {
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		String s = housingUnit.getArtifactString(selectedArtifact);
		
		assert s != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return s; 
	}

	public void changeHouseDescription(String descr) {
		assert descr != null;
		assert housingUnit != null;
		
		housingUnit.setDescr(descr);
		
		assert housingUnit.getDescr() != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public boolean hasRoom(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasRoom(name);
	}

	public void addRoom(Room toAdd) {
		assert toAdd != null;
		assert housingUnit != null;
		
		housingUnit.addRoom(toAdd);
		
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void changeRoomDescription(String selectedRoom, String descr) {
		assert selectedRoom != null && descr != null; 
		assert housingUnit != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit.hasRoom(selectedRoom) : "La casa " + housingUnit.getName() + " non contiente la stanza " + selectedRoom;
		
		housingUnit.setRoomDescription(selectedRoom, descr);

		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public void addSensor(String location, Sensor sensor) {
		assert location != null && sensor != null && sensor.getName() != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		assert housingUnit.hasRoom(location);
		
		
		housingUnit.addSensor(location, sensor);
		
		assert housingUnit.getRoom(location).getSensorByName(sensor.getName()) != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public boolean hasRoomOrArtifact(String name) {
		assert name != null; 
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasRoomOrArtifact(name);
	}

	public boolean hasSensor(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasSensor(name);
	}
	
	public boolean hasActuator(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasActuator(name);
	}

	public boolean hasArtifact(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.hasArtifact(name);
	}

	public boolean isElementARoom(String toAssoc) {
		assert toAssoc != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String toAssoc, String category) {
		assert toAssoc != null && category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		return housingUnit.isAssociated(toAssoc, category);
	}

	public void addAssociation(String object, String category) {
		assert object != null && category != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		housingUnit.addAssociation(object, category);
	
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}

	public Room getRoom(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		assert housingUnit.hasRoom(name) : "La casa " + housingUnit.getName() + " non contiene la stanza " + name;
		
		Room room = housingUnit.getRoom(name);
		
		assert room != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
		return room;
	}

	public Artifact getArtifact(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		
		Artifact art = housingUnit.getArtifact(name);
		
		assert art != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return art;
	}

	public void addArtifact(String location, Artifact toAdd) {
		assert location != null && toAdd != null && toAdd.getName() != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null; 
		assert housingUnit.hasRoom(location);
		
		housingUnit.addArtifact(toAdd, location);
		
		assert housingUnit.hasArtifact(toAdd.getName());
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
	}
	
	public void addActuator(String location, Actuator toAdd) {
		assert location != null && toAdd != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		assert housingUnit != null;
		assert housingUnit.hasRoom(location);
		
		housingUnit.addActuator(toAdd, location);
		
		assert housingUnit.getRoom(location).getActuatorByName(toAdd.getName()) != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		
	}

	public boolean hasOperatingMode(String name) {
		assert name != null;
		assert dataHandlerInvariant() : "Invariante di classe non soddisfatto";
		return OperatingModesHandler.hasOperatingMode(name);
	}
	
	public boolean dataHandlerInvariant() {
		boolean checkManagers = sensorCategoryManager != null && actuatorCategoryManager != null;
		return checkManagers; 
	}
}