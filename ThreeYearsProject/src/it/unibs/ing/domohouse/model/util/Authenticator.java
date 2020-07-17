package it.unibs.ing.domohouse.model.util;

public interface Authenticator {
	boolean checkMaintainerPassword(String maintainer, String password);
	void addMaintainer(String maintainer, String password);
	boolean checkUser(String user);
}
