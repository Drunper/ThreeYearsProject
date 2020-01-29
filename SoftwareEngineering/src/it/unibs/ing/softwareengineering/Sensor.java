package it.unibs.ing.softwareengineering;


public class Sensor extends Manager implements Manageable {
	
	private String name;
	protected SensorCategory category;
	private boolean state;
	private boolean measuringRoom;

	public Sensor(String name, SensorCategory category) {
		super();
		this.name = name;
		this.category = category;
		state = true;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMeasuringRoom() {
		return measuringRoom;
	}

	public void setMeasuringRoom(boolean measuringRoom) {
		this.measuringRoom = measuringRoom;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
