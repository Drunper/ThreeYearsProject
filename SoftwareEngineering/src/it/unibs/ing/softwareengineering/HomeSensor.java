package it.unibs.ing.softwareengineering;

public class HomeSensor extends Element{
	private int value;
	
	
	
	public HomeSensor(String name, String descr) {
		super(name, descr);
	}

	
	public int getValue() {
		return this.value;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescr() {
		return this.descr;
	}
}
