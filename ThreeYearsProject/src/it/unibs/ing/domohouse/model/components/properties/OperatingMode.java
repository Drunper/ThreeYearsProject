package it.unibs.ing.domohouse.model.components.properties;

import java.util.ArrayList;
import java.util.List;

import it.unibs.ing.domohouse.model.components.elements.Gettable;

public abstract class OperatingMode  {

	private List<String> parametersNames;
	private List<String> parametersValues;

	public OperatingMode() {
		parametersNames = new ArrayList<>();
		parametersValues = new ArrayList<>();
	}

	public void setParameterName(String parameterName) {
		parametersNames.add(parameterName);
	}

	public void setParamaterValue(String parameterValue) {
		if (parametersValues.size() == parametersNames.size())
			parametersValues.clear();
		parametersValues.add(parameterValue);
	}

	public String getParameterValue(String parameterName) {
		return parametersValues.get(parametersNames.indexOf(parameterName));
	}

	public List<String> getParametersList() {
		return parametersNames;
	}

	public int getNumberOfParameters() {
		return parametersNames.size();
	}

	public boolean isParametric() {
		return !parametersNames.isEmpty();
	}

	public abstract void operate(Gettable object);
}
