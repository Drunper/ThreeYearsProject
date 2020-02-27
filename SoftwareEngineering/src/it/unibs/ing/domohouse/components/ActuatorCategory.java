package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;

import it.unibs.ing.domohouse.interfaces.*;

public class ActuatorCategory implements Manageable, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3544838050806873679L;
	private String name;
	private String text;
	private HashMap<String, SerializableConsumer<Gettable>> operatingModesMap;
	//Assumiamo che i parametri siano solo double o string, se si vuole aggiungere una mappa ricordarsi
	//di modificare il metodo hasOperatingMode
	private HashMap<String, SerializableBiConsumer<Gettable,Object>> parametricOperatingModesMap;

	
	/*
	 * invariante name > 0, text > 0 name != null, text != null
	 */
	public ActuatorCategory(String name, String text) {
		this.name = name;
		this.text = text;
		operatingModesMap = new HashMap<>();
		parametricOperatingModesMap = new HashMap<>();
	}
	
	public String getName() {
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return this.name;
	}
	
	public String getDescr() {
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return this.text;
	}
	
	public void setName(String newName) {
		assert newName.length() > 0 && newName != null;
		this.name = newName;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void setDescr(String text) {
		assert text.length() > 0 && text != null;
		this.text = text;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public String [] listOfOperatingModes() {
		assert operatingModesMap !=null && parametricOperatingModesMap != null;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		String[] op1 = operatingModesMap.keySet().toArray(new String[0]);
		String[] op2 = parametricOperatingModesMap.keySet().toArray(new String[0]);
		
		int dim = op1.length + op2.length;
				
		String[] result = new String[dim]; 
		
		for(int i=0; i< op1.length; i++) {
			result[i] = op1[i];
		}
		
		for(int i=0; i <op2.length; i++) {
			result[op1.length + i] = op2[i];
		}
		
		return result;
	}
	
	public void putOperatingMode(String name, SerializableConsumer<Gettable> operatingMode) {
		assert name.length() > 0;
		int pre_size = operatingModesMap.size();
		
		operatingModesMap.put(name, operatingMode);
		
		assert operatingModesMap.size() >= pre_size;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}
	
	public void putParametricOperatingMode(String name, SerializableBiConsumer<Gettable, Object> operatingMode) {
		assert name.length() > 0;
		int pre_size = parametricOperatingModesMap.size();
		
		parametricOperatingModesMap.put(name, operatingMode);
		
		assert parametricOperatingModesMap.size() >= pre_size;
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
	}
	
	
	public Consumer<Gettable> getOperatingMode(String name) {
		assert operatingModesMap.containsKey(name) : "operatingModesMap non contiene il nome richiesto";
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		return operatingModesMap.get(name);
	}
	
	public SerializableBiConsumer<Gettable, Object> getParametricOperatingMode(String name) {
		assert parametricOperatingModesMap.containsKey(name) : "doubleOperatingModesMap non contiene il nome richiesto";
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		
		return parametricOperatingModesMap.get(name);
	}
	
	
	public boolean hasOperatingMode(String op) {
		assert operatingModesMap !=null && parametricOperatingModesMap != null;
		
		if(parametricOperatingModesMap.containsKey(op) || operatingModesMap.containsKey(op)) return true;
		return false;
	}
	
	public boolean hasParametricOperatingMode(String op) {
		assert parametricOperatingModesMap != null;
		if(parametricOperatingModesMap.containsKey(op)) return true;
		return false;
	}
	
	
	public boolean hasNonParametricOperatingMode(String op) {
		assert operatingModesMap !=null;
		if(operatingModesMap.containsKey(op)) return true;
		return false;
	}
	
	public String getDefaultMode() {
		assert text.contains(":");
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return text.split(":")[2];
	}
	
	public String toString() {
		assert actuatorCategoryInvariant() : "Invariante della classe non soddisfatto";
		return name+':'+text;
	}
	
	private boolean actuatorCategoryInvariant() {
		if(name.length() > 0 && name != null && text.length() > 0 && text != null && operatingModesMap !=null && parametricOperatingModesMap != null) return true;
		return false;
	}
}
