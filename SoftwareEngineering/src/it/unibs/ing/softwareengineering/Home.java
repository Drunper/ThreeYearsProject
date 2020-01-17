package it.unibs.ing.softwareengineering;

public class Home extends Element{

	public Home(String name, String descr) {
		super(name, descr);
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
