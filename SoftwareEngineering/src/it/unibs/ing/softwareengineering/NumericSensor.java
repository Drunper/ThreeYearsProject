package it.unibs.ing.softwareengineering;

public class NumericSensor extends Sensor {

	private String name;
	private SensorCategory category;
	private String measuredVariable;
	private double detectedValue;
	private double lowerBound;
	private double upperBound;
	private Artifact artif; // serve un nome migliore
	private Room room; // anche qui se possibile
	private boolean roomMeasurement;
	private boolean state;
	
	public NumericSensor(String name, SensorCategory category, double detectedValue, Artifact artif) {
		super();
		this.name = name;
		this.category = category;
		this.detectedValue = detectedValue;
		this.artif = artif;
		lowerBound = category.getBounds()[0]; // togliere magic numbers
		upperBound = category.getBounds()[1];
		roomMeasurement = false;
		state = true;
	}

	public NumericSensor(String name, SensorCategory category, double detectedValue, Room room) {
		super();
		this.name = name;
		this.category = category;
		this.detectedValue = detectedValue;
		this.room = room;
		roomMeasurement = true;
		state = true;
	}
	
	/*
	 * Serve un check per verificare che la misura sia nell'intervallo specificato
	 * 
	 */
	public double getMeasurement() {
		if (roomMeasurement)
			return room.getMeasure(measuredVariable);
		else
			return artif.getMeasure(measuredVariable);
	}
}
