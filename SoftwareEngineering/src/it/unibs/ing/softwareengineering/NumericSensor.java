package it.unibs.ing.softwareengineering;

public class NumericSensor extends Sensor {

	private String name;
	private String text;
	private SensorCategory category;
	private String measuredVariable;
	private double detectedValue;
	private double lowerBound;
	private double upperBound;
	private Artifact attachedArtif; // serve un nome migliore
	private Room room; // anche qui se possibile
	private boolean roomMeasurement;
	private boolean state;
	
	public NumericSensor(String name, String text, SensorCategory category, double detectedValue, Artifact artif) {
		super();
		this.name = name;
		this.text = text;
		this.category = category;
		this.detectedValue = detectedValue;
		this.attachedArtif = artif;
		lowerBound = category.getBounds()[0]; // togliere magic numbers
		upperBound = category.getBounds()[1];
		roomMeasurement = false;
		state = true;
	}

	public NumericSensor(String name, String text, SensorCategory category, double detectedValue, Room room) {
		super();
		this.name = name;
		this.text = text;
		this.category = category;
		this.detectedValue = detectedValue;
		this.room = room;
		roomMeasurement = true;
		state = true;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescr() {
		return this.text;
	}
	
	public void setDescr(String text) {
		this.text = text;
	}
	
	public SensorCategory getCategory() {
		return this.category;
	}
	
	public Artifact getControlledArtifact() {
		return this.attachedArtif;
	}
	
	public boolean isOn() {
		return this.state;
	}
	
	
	/*
	 * Serve un check per verificare che la misura sia nell'intervallo specificato
	 * 
	 */
	public double getMeasurement() {
		if (roomMeasurement)
			return room.getMeasure(measuredVariable);
		else
			return attachedArtif.getMeasure(measuredVariable);
	}
}
