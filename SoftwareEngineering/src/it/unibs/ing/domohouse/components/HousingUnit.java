package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import it.unibs.ing.domohouse.interfaces.Manageable;
import it.unibs.ing.domohouse.util.Association;
import it.unibs.ing.domohouse.util.AssociationHandler;
import it.unibs.ing.domohouse.util.Manager;

public class HousingUnit implements Serializable, Manageable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4272512019548783815L;
	private String name;
	private String descr;
	private Manager roomManager;
	private Manager sensorManager; //tutti i sensori della casa, serve per il controllo dell'unicità dei nomi
	private Manager actuatorManager; //tutti gli attuatori della casa
	private Manager artifactManager; //tutti gli artefatti della casa
	private AssociationHandler associationManager; //per il controllo delle associazioni
	private ArrayList<Rule> rules = new ArrayList<>();
	private HashMap<Rule, Double> queuedRules = new HashMap<>();
	
	/*
	 * invariante name > 0, descr > 0, diversi da null. Manager != null
	 */
	
	public HousingUnit(String name, String descr) {
		this.name = name;
		this.descr = descr;
		roomManager = new Manager();
		sensorManager = new Manager();
		actuatorManager = new Manager();
		artifactManager = new Manager();
		associationManager = new AssociationHandler();
	}
	
	public String getName() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return name;
	}

	public void setName(String name) {
		assert name != null && name.length() > 0;
		this.name = name;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}

	public String getDescr() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return descr;
	}

	public void setDescr(String descr) {
		assert descr != null && descr.length() > 0;
		this.descr = descr;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void addRoom(Room toAdd) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0;
		int pre_size_roomManager = roomManager.size();
		int pre_size_associationManager = associationManager.size();
		
		roomManager.addEntry(toAdd);
		Association assoc = new Association(toAdd.getName());
		assoc.setIsElementARoom(true);
		associationManager.addAssociation(assoc);
		
		assert roomManager.size() >= pre_size_roomManager;
		assert associationManager.size() >= pre_size_associationManager;
		assert assoc.isElementARoom() == true && associationManager.hasEntry(assoc.getElementName());
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String [] roomList() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return roomManager.namesList();
	}
	
	public Room getRoom(String name) {
		assert name != null && (Room)roomManager.getElementByName(name) != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return (Room)roomManager.getElementByName(name);
	}
	
	public boolean hasRoom(String name) {
		assert name != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return roomManager.hasEntry(name);
	}

	public String getRoomString(String selectedRoom) {
		assert selectedRoom != null && selectedRoom.length() > 0;
		assert roomManager.getElementString(selectedRoom) != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		String result = roomManager.getElementString(selectedRoom);
		assert result != null && result.length() > 0;
		return result;
	}
	
	public String toString() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		String roomNames = String.join(":", roomList());
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		String result = name+':'+descr+':'+roomNames;
		assert result.length() > 0;
		return result;
	}

	public String getSensorString(String selectedSensor) {
		assert selectedSensor != null && selectedSensor.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		String result = sensorManager.getElementString(selectedSensor);
		assert result != null;
		return result;
	}
	
	public Sensor getSensor(String selectedSensor) {
		assert selectedSensor != null; 
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		Sensor sens = (Sensor) sensorManager.getElementByName(selectedSensor);
		
		assert sens != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return sens;
	}
	
	public Actuator getActuator(String selectedActuator) {
		assert selectedActuator != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		Actuator act = (Actuator) actuatorManager.getElementByName(selectedActuator);
		
		assert act != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return act;
	}

	public String getActuatorString(String selectedActuator) {
		assert selectedActuator != null && selectedActuator.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		String result = actuatorManager.getElementString(selectedActuator);
		assert result != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return result;
	}

	public String getArtifactString(String selectedArtifact) {
		assert selectedArtifact != null && selectedArtifact.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		String result = artifactManager.getElementString(selectedArtifact);
		assert result != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return result;
	}

	public void setRoomDescription(String selectedRoom, String descr) {
		assert selectedRoom != null && selectedRoom.length() > 0 && descr != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		Room room = (Room)roomManager.getElementByName(selectedRoom);
		room.setDescr(descr);
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}

	public void addSensor(String location, Sensor toAdd) {
		assert location != null && location.length() > 0;
		assert toAdd != null;
		int pre_size = sensorManager.size();
		Room room = (Room)roomManager.getElementByName(location);
		room.addSensor(toAdd);
		sensorManager.addEntry(toAdd);
		assert sensorManager.size() >= pre_size;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public boolean hasRoomOrArtifact(String name) {
		assert name != null && name.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return associationManager.hasEntry(name);
	}

	public boolean hasSensor(String name) {
		assert name != null && name.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return sensorManager.hasEntry(name);
	}

	public boolean hasActuator(String name) {
		assert name != null && name.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return actuatorManager.hasEntry(name);
	}

	public boolean hasArtifact(String name) {
		assert name != null && name.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return artifactManager.hasEntry(name);
	}

	public boolean isElementARoom(String toAssoc) {
		assert toAssoc != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return associationManager.isElementARoom(toAssoc);
	}

	public boolean isAssociated(String toAssoc, String category) {
		assert toAssoc != null && category != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return associationManager.isAssociated(toAssoc, category);
	}

	public void addAssociation(String object, String category) {
		assert object != null && category != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		associationManager.addAssociation(object, category);
	}

	public Artifact getArtifact(String name) {
		assert name != null && name.length() > 0;
		Artifact art = (Artifact)artifactManager.getElementByName(name);
		assert art != null : "artifact result è null";
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return art;
		
	}

	public void addArtifact(Artifact toAdd, String location) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0; 
		assert location != null && location.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		int pre_size_artifactManager = artifactManager.size();
		int pre_size_associationManager = artifactManager.size();
		
		Room room = (Room)roomManager.getElementByName(location);
		room.addArtifact(toAdd);
		artifactManager.addEntry(toAdd);
		associationManager.addAssociation(new Association(toAdd.getName()));
		
		assert artifactManager.size() >= pre_size_artifactManager;
		assert associationManager.size() >= pre_size_associationManager;
		assert room.hasArtifact(toAdd.getName());
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}

	public void addActuator(Actuator toAdd, String location) {
		assert toAdd != null && toAdd.getName() != null && toAdd.getName().length() > 0;
		assert location != null && location.length() > 0;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = actuatorManager.size();

		Room room = (Room)roomManager.getElementByName(location);
		room.addActuator(toAdd);
		actuatorManager.addEntry(toAdd);

		assert actuatorManager.size() >= pre_size;
		assert room.hasActuator(toAdd.getName());
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public boolean isThereRoomOrArtifact() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		if(roomManager.size() != 0 || artifactManager.size() != 0) return true;
		return false;
	}
	
	public boolean isThereRoom() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		if(roomManager.size() != 0) return true;
		return false;
	}
	
	public boolean isThereArtifact() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		if(artifactManager.size() != 0) return true;
		return false;
	}
	
	public String getNonNumericSensorValue(String selectedSensor, String info) {
		assert selectedSensor != null && info != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		Sensor sens = (Sensor) sensorManager.getElementByName(selectedSensor);
		
		if(sens.isMeasuringRoom()) {
			String [] rooms = roomManager.namesList();
			for(int i = 0; i < rooms.length; i++) {
				if(sens.getElementByName(rooms[i]) != null) {
					Room room = (Room) roomManager.getElementByName(rooms[i]);
					String result = room.getNonNumericProperty(info);
					assert result != null; 
					return result;
				}
			}
		}else {
			String [] artifacts = artifactManager.namesList();
			for(int i=0; i < artifacts.length; i++) {
				if(sens.getElementByName(artifacts[i]) != null) {
					Artifact art = (Artifact) artifactManager.getElementByName(artifacts[i]);
						String result = art.getNonNumericProperty(info);
						assert result != null;
						return result;
				}
			}
		}
		return "";
		
	}
	
	public double getSensorValue(String selectedSensor, String info) {
		assert selectedSensor != null && info != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		Sensor sens = (Sensor) sensorManager.getElementByName(selectedSensor);

		if(sens.isMeasuringRoom()) {
			String [] rooms = roomManager.namesList();
			for(int i=0; i<rooms.length; i++) {
				if(sens.getElementByName(rooms[i]) != null) {
					Room room = (Room) roomManager.getElementByName(rooms[i]);
						return room.getNumericProperty(info);
				}
			}	
		}else {
			String [] artifacts = artifactManager.namesList();
			for(int i=0; i < artifacts.length; i++) {
				if(sens.getElementByName(artifacts[i]) != null) {
					Artifact art = (Artifact) artifactManager.getElementByName(artifacts[i]);
						return art.getNumericProperty(info);
				}
			}
		}
		return 0;
	}
	
	public void addRule(Rule r) {
		assert r != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		rules.add(r);
	}
	
	public void checkRules() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		for(Rule r : rules) {
			r.checkRule();
		}
		
		for(Rule r : queuedRules.keySet()) {
			if(queuedRules.get(r) == Double.parseDouble(Clock.getCurrentTime())) {
				r.actuateConseguente();
			}
		}
	}
	
	public void addQueuedRule(Rule r, double time) {
		assert r != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = queuedRules.size();
		
		queuedRules.put(r, time);
		
		assert queuedRules.size() >= pre_size;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void removeQueuedRule(Rule r) {
		assert r != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		int pre_size = queuedRules.size();
		
		queuedRules.remove(r);
		
		assert pre_size >= queuedRules.size();
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";

	}
	
	public boolean containsQueuedRule(Rule r) {
		assert r != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";

		return queuedRules.containsKey(r);
	}
	
	public void disableRule(String selectedRule) {
		assert selectedRule != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		for(Rule r : rules) {
			if(selectedRule.equalsIgnoreCase(selectedRule)) {
				r.setState(false);
			}
		}
	}
	
	public void enableRule(String selectedRule) {
		assert selectedRule != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		for(Rule r : rules) {
			if(selectedRule.equalsIgnoreCase(selectedRule)) {
				r.setState(true);
			}
		}
	}
	
	public String []  getEnabledRulesList() {
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		ArrayList<Rule> enabledRules = new ArrayList<>();
		
		for(Rule r : rules) {
			if(r.isActive()) enabledRules.add(r);
		}
		
		String [] s = new String[enabledRules.size()];
		
		for(int i = 0; i < enabledRules.size(); i++) {
			s[i] = enabledRules.get(i).getName() + " -> " + enabledRules.get(i).getCompleteRule() + " Abilitata: " + enabledRules.get(i).isActive();
		}
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		return s;
	}
	
	public String [] getEnabledRulesListNames() {
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		ArrayList<Rule> enabledRules = new ArrayList<>();
		
		for(Rule r : rules) {
			if(r.isActive()) enabledRules.add(r);
		}
		
		String [] s = new String[enabledRules.size()];
		
		for(int i = 0; i < enabledRules.size(); i++) {
			s[i] = enabledRules.get(i).getName();
		}
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		return s;
	}
	
	public String [] getDisabledRulesList() {
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		ArrayList<Rule> disabledRules = new ArrayList<>();
		
		for(Rule r : rules) {
			if(!r.isActive()) disabledRules.add(r);
		}
		
		String [] s = new String[disabledRules.size()];
		
		for(int i = 0; i < disabledRules.size(); i++) {
			s[i] = disabledRules.get(i).getName() + " -> " + disabledRules.get(i).getCompleteRule() + " Abilitata: " + disabledRules.get(i).isActive();
		}
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		return s;
	}
	
	public String [] getDisabledRulesListNames() {
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		ArrayList<Rule> enabledRules = new ArrayList<>();
		
		for(Rule r : rules) {
			if(!r.isActive()) enabledRules.add(r);
		}
		
		String [] s = new String[enabledRules.size()];
		
		for(int i = 0; i < enabledRules.size(); i++) {
			s[i] = enabledRules.get(i).getName();
		}
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		return s;
	}
	
	public String [] getAllRulesList() {
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		String [] s = new String[rules.size()];
		
		for(int i = 0; i < rules.size(); i++) {
			s[i] = rules.get(i).getName() + " -> " + rules.get(i).getCompleteRule() + " Abilitata: " + rules.get(i).isActive();
		}
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		return s;
	}
	
	public boolean hasRule(String selectedRule) {
		assert selectedRule != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		for(Rule r : rules) {
			if(r.getName().equalsIgnoreCase(selectedRule)) return true;
		}
		return false;
	}
	
	public String[] getSensorNames() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return sensorManager.namesList();
	}
	
	public String[] getActuatorNames() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		return actuatorManager.namesList();
	}
	
	public String[] getCategoriesOfASensor(String selectedSensor) {
		assert selectedSensor != null;
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
		
		Sensor _selectedSensor = (Sensor) sensorManager.getElementByName(selectedSensor);
		
		assert _selectedSensor != null;
		return _selectedSensor.getCategories();
	}
	
	public void updateRulesState() {
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";

		boolean flag = true;
		for(Rule r : rules) {
			for(String s : r.getInvolvedSensors()) {
				Sensor sens = (Sensor) sensorManager.getElementByName(s);
				if(!sens.isState()) flag = false;
			}
			
			String actuator = r.getInvolvedActuator();
			Actuator act = (Actuator) actuatorManager.getElementByName(actuator);
			if(!act.isState()) flag = false;
			
			if(flag) r.setState(true);
			else r.setState(false);
		}
		
		assert housingUnitInvariant() : "Invariante della classe non soddisfatto";
	}
	
	
	private boolean housingUnitInvariant() {
		boolean checkName = name != null && name.length() > 0 ;
		boolean checkDescr = descr != null;
		boolean checkManagers = roomManager != null && sensorManager != null  && actuatorManager !=null 
				&& artifactManager != null && associationManager != null;
		boolean checkRules = rules != null && queuedRules != null;
		if(checkName && checkDescr && checkManagers && checkRules) return true;
		return false;
	}


}
