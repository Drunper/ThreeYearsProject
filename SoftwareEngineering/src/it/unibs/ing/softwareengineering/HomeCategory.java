package it.unibs.ing.softwareengineering;

import java.util.*;

public abstract class HomeCategory {
	protected String name;
	protected String descr;
	protected ArrayList<HomeElement> elements;
	
	
	public abstract String getName();
	public abstract String getDescr();
	public abstract HomeElement getElementFromList(int index);
	public abstract int getListSize();

	
	
}
