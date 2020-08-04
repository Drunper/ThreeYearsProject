package it.unibs.ing.domohouse.model.components.properties;

import java.io.Serializable;
import java.util.List;

public class DoubleInfoStrategy implements InfoStrategy, Serializable {

	private static final long serialVersionUID = -2688053215017124980L;
	private double lowerBound;
	private double upperBound;
	private int ID;
	private String measuredProperty;

	public DoubleInfoStrategy(double lowerBound, double upperBound, int ID, String measuredProperty) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.ID = ID;
		this.measuredProperty = measuredProperty;
	}

	@Override
	public String getSingleValue(List<String> values) {
		double result = 0;
		for (int i = 0; i < values.size(); i++) {
			double value;
			try {
				value = Double.parseDouble(values.get(i));
			}
			catch (NumberFormatException e) {
				//TOLOG
				value = 0;
			}
			if (value < lowerBound)
				value = lowerBound;
			else if (value > upperBound)
				value = upperBound;
			result += value;
		}
		result /= values.size();
		return String.valueOf(result);
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getMeasuredProperty() {
		return measuredProperty;
	}

	public String toString() {
		return lowerBound + " -to- " + upperBound;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public double getUpperBound() {
		return upperBound;
	}
}
