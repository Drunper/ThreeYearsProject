package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Consumer;

import it.unibs.ing.domohouse.interfaces.*;
import it.unibs.ing.domohouse.util.Strings;

public class ActuatorCategory implements Manageable, Serializable {

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
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return this.name;
	}
	
	public String getDescr() {
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return this.text;
	}
	
	public void setName(String newName) {
		assert newName.length() > 0 && newName != null;
		this.name = newName;
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void setDescr(String text) {
		assert text.length() > 0 && text != null;
		this.text = text;
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public String [] listOfOperatingModes() {
		assert operatingModesMap !=null && parametricOperatingModesMap != null;
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
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
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
	}
	
	public void putParametricOperatingMode(String name, SerializableBiConsumer<Gettable, Object> operatingMode) {
		assert name.length() > 0;
		int pre_size = parametricOperatingModesMap.size();
		
		parametricOperatingModesMap.put(name, operatingMode);
		
		assert parametricOperatingModesMap.size() >= pre_size;
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
	}
	
	
	public Consumer<Gettable> getOperatingMode(String name) {
		assert operatingModesMap.containsKey(name) : Strings.OPERATING_MODE_PRECONDITION;
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		return operatingModesMap.get(name);
	}
	
	public SerializableBiConsumer<Gettable, Object> getParametricOperatingMode(String name) {
		assert parametricOperatingModesMap.containsKey(name) : Strings.PARAMETRIC_OPERATING_MODE_PRECONDITION;
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
		
		return parametricOperatingModesMap.get(name);
	}
	
	
	public boolean hasOperatingMode(String operating_mode) {
		assert operatingModesMap != null && parametricOperatingModesMap != null;
		
		return parametricOperatingModesMap.containsKey(operating_mode) || operatingModesMap.containsKey(operating_mode);
	}
	
	public boolean hasParametricOperatingMode(String operating_mode) {
		assert parametricOperatingModesMap != null;
		return parametricOperatingModesMap.containsKey(operating_mode);
	}
	
	
	public boolean hasNonParametricOperatingMode(String operating_mode) {
		assert operatingModesMap !=null;
		return operatingModesMap.containsKey(operating_mode);
	}
	
	public String getDefaultMode() {
		assert text.contains(Strings.SEPARATOR);
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return text.split(Strings.SEPARATOR)[2];
	}
	
	public String toString() {
		assert actuatorCategoryInvariant() : Strings.WRONG_INVARIANT;
		return name+Strings.SEPARATOR+text;
	}
	
	private boolean actuatorCategoryInvariant() {
		return name.length() > 0 && name != null && text.length() > 0 && text != null 
				&& operatingModesMap !=null && parametricOperatingModesMap != null;
	}
}
