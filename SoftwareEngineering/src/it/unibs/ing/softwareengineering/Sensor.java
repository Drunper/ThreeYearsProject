package it.unibs.ing.softwareengineering;

public abstract class Sensor {

	public abstract String getName();
	public abstract String getDescr();
	public abstract SensorCategory getCategory();
	public abstract void setName(String newName);
	public abstract void setDescr(String newDescr);
}
