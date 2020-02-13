package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.HashMap;

import it.unibs.ing.domohouse.interfaces.Manageable;

public class SensorCategory implements Manageable, Serializable {
	
	private String name;
	private String text;
	private HashMap<String, String> infoDomainMap;
	
	/*
	 * invariante di classe: detectableInfos.size() > 0 && name != null && text != null
	 * ancora da implementare
	 */
	public SensorCategory(String name, String text) {
		this.name = name;
		this.text = text;
		infoDomainMap = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return text;
	}

	public void setDescr(String text) {
		this.text = text;
	}
	
	public double [] getDomain(String info) {
		//PatternSyntaxException
		String domain = infoDomainMap.get(info);
		String [] bounds = domain.split("-");
		double [] numericBounds = new double[2];
		numericBounds[0] = Double.parseDouble(bounds[0]);
		numericBounds[1] = Double.parseDouble(bounds[1]);
		return numericBounds;
	}
	
	// daAdattare nel caso di più informazioni rilevabili
	public void putInfo(String detectableInfo, String domain) {
		infoDomainMap.put(detectableInfo, domain);
	}

	public String getMeasurementUnit(String info) {
		String domain = infoDomainMap.get(info);
		String measurementUnit = domain.split("(")[1].split(")")[0];
		return measurementUnit;
	}
	
	public String [] getDetectableInfoList() {
		return infoDomainMap.keySet().toArray(new String[0]);
	}

	public String toString() {
		return name+':'+text+':'+String.join(":", getDetectableInfoList());
	}
}
