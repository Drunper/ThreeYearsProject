package it.unibs.ing.softwareengineering;

public abstract class Element {
	protected String name;
	protected String descr;
	
	public Element(String name, String descr) {
		this.name = name;
		this.descr = descr;
	}
	
	public abstract void setName(String name);
	public abstract void setDescr(String descr);
	
	public abstract String getName();
	public abstract String getDescr();
	
}
