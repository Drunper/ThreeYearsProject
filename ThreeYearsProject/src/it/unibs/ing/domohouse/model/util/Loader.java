package it.unibs.ing.domohouse.model.util;

public interface Loader {

	void loadSensorCategories();
	void loadActuatorCategories();
	void loadHousingUnits(String user);
}
