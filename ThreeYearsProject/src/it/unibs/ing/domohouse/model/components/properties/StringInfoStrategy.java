package it.unibs.ing.domohouse.model.components.properties;

import java.io.Serializable;
import java.util.List;

import it.unibs.ing.domohouse.model.ModelStrings;

public class StringInfoStrategy implements InfoStrategy, Serializable {

	private static final long serialVersionUID = 8997364281663305737L;
	private int ID;
	private List<String> domain;
	private String measuredProperty;

	public StringInfoStrategy(List<String> domain, int ID, String measuredProperty) {
		this.domain = domain;
		this.ID = ID;
		this.measuredProperty = measuredProperty;
	}

	@Override
	public String getSingleValue(List<String> values) {
		String firstValue = values.get(ModelStrings.FIRST_TOKEN);
		if (domain.contains(firstValue))
			return firstValue;
		else
			return "N/A";
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
		return String.join(ModelStrings.COMMA_WITH_SPACE, domain);
	}

	public List<String> getDomainValues() {
		return domain;
	}
}
