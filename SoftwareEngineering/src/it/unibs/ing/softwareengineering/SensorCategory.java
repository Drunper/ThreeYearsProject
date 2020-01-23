package it.unibs.ing.softwareengineering;

import java.util.ArrayList;
import java.util.function.Predicate;

public class SensorCategory {
	
	private String name;
	private String text;
	private ArrayList<String> detectableInfos;
	
	/*
	 * invariante di classe: detectableInfos.size() > 0 && name != null && text != null
	 * ancora da implementare
	 */
	public SensorCategory(String name, String text, ArrayList<String> detectableInfos) {
		this.name = name;
		this.text = text;
		this.detectableInfos = detectableInfos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void addDetectableInfo (String toAdd) {
		detectableInfos.add(toAdd);
	}
	
	public void removeDetectableInfo (String toRemove) {
		detectableInfos.removeIf(isEqual(toRemove));
	}
	
	/*
	 * Da implementare.
	 * Il metodo restituisce l'estremo inferiore e quello superiore, ovvero gli estremi del range di rilevazione del sensore
	 */
	public double [] getBounds() {
		double [] bounds = new double[2];
		return bounds;
	}

	private static Predicate<String> isEqual(String toCompare) {
        return s -> s.equalsIgnoreCase(toCompare);
    }
}
