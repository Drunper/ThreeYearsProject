package it.unibs.ing.domohouse.model.util;

public interface Loader {

	void loadSensorCategories();
	void loadActuatorCategories();
	void loadUser(String user);
	void loadHousingUnits(String user);
}
