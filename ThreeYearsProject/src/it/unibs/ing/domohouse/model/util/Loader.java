package it.unibs.ing.domohouse.model.util;

public interface Loader {

	DataFacade loadDataFacade();
	void runFileFromSource(String sourceName);
	boolean loadConfigFile();
}
