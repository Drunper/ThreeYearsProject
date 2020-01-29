package it.unibs.ing.softwareengineering;

public class HousingUnit extends Manager {
	
	private String name;
	private String descr;
	
	public HousingUnit(String name, String descr) {
		this.name = name;
		this.descr = descr;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String toString() {
		String roomNames = String.join(":", this.namesList());
		return name+':'+descr+':'+roomNames;
	}
}
