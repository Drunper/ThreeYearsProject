package it.unibs.ing.domohouse.model.components.properties;

import java.io.Serializable;
import java.util.List;

public class DoubleInfoStrategy implements InfoStrategy, Serializable {

	private static final long serialVersionUID = -2688053215017124980L;
	private double lowerBound;
	private double upperBound;
	private int ID;

	public DoubleInfoStrategy(double lowerBound, double upperBound, int ID) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.ID = ID;
	}

	@Override
	public String getSingleValue(List<String> values) {
		double result = 0;
		for (int i = 0; i < values.size(); i++) {
			double value = Double.parseDouble(values.get(i));
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

	public String toString() {
		return lowerBound + " -to- " + upperBound;
	}
}
