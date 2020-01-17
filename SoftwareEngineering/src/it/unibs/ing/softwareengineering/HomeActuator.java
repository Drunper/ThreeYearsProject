package it.unibs.ing.softwareengineering;

public class HomeActuator extends Element{
	private boolean state;
	
	
	public HomeActuator(String name, String descr) {
		super(name, descr);
		// TODO Auto-generated constructor stub
	}

	
	public boolean getState() {
		return this.state;
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
