package it.unibs.ing.domohouse.model.components.properties;

import java.io.Serializable;
import java.util.List;

import it.unibs.ing.domohouse.model.ModelStrings;

public class StringInfoStrategy implements InfoStrategy, Serializable {

	private static final long serialVersionUID = 8997364281663305737L;
	private List<String> domain;

	public StringInfoStrategy(List<String> domain) {
		this.domain = domain;
	}

	@Override
	public String getSingleValue(List<String> values) {
		String firstValue = values.get(ModelStrings.FIRST_TOKEN);
		if (domain.contains(firstValue))
			return firstValue;
		else
			return "N/A";
	}

	public String toString() {
		return String.join(ModelStrings.COMMA_WITH_SPACE, domain);
	}
}
