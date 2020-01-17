package it.unibs.ing.softwareengineering;

import java.util.*;

public class HomeSensorCategory extends HomeCategory{

	
	public HomeSensorCategory(String name, String descr) {
		super(name, descr);
		elements = new ArrayList<>();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescr() {
		return this.descr;
	}

	@Override
	public Element getElementFromList(int index) {
		return(this.elements.get(index));
	}

	@Override
	public int getListSize() {
		return(this.elements.size());
	}
	
}
