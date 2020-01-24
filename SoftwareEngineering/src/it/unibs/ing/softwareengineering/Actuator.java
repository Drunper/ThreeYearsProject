package it.unibs.ing.softwareengineering;

public abstract class Actuator {

	public abstract String getName();
	public abstract String getDescr();
	public abstract ActuatorCategory getCategory();
	public abstract void setName(String newName);
	public abstract void setDescr(String newDescr);
}
