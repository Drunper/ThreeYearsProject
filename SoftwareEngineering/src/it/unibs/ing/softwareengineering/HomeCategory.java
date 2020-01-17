package it.unibs.ing.softwareengineering;

import java.util.*;

public abstract class HomeCategory {
	protected String name;
	protected String descr;
	protected ArrayList<Element> elements;
	
	public HomeCategory(String name, String descr) {
		this.name = name;
		this.descr = descr;
	}
	
	public abstract String getName();
	public abstract String getDescr();
	public abstract Element getElementFromList(int index);
	public abstract int getListSize();
	public abstract void addElementToCategory(Element e);

	
	
}
