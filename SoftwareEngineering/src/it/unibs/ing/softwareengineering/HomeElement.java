package it.unibs.ing.softwareengineering;

public abstract class HomeElement {
	protected String name;
	protected String descr;
	
	
	public abstract void setName(String name);
	public abstract void setDescr(String descr);
	
	public abstract String getName();
	public abstract String getDescr();
	
}
