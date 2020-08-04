package it.unibs.ing.domohouse.model.util;

public interface Authenticator {
	boolean checkMaintainerPassword(String maintainer, String password) throws Exception;
	void addMaintainer(String maintainer, String password) throws Exception;
	boolean checkUser(String user) throws Exception;
}
