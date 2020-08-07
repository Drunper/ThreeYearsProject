package it.unibs.ing.domohouse.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;

import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.db.DatabaseAuthenticator;
import it.unibs.ing.domohouse.model.util.Authenticator;
import it.unibs.ing.domohouse.model.util.HashCalculator;

public class DatabaseAuthenticatorTesting {

	@Test
	public void testCheckPassword() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		try {
			Authenticator authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
			assertTrue(authenticator.checkMaintainerPassword("prova", "pippo123456"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckWrongPassword() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		try {
			Authenticator authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
			assertFalse(authenticator.checkMaintainerPassword("prova", "pippo12346"));
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAddMaintainer() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		try {
			Authenticator authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
			if (authenticator.checkMaintainerPassword("prova3", "paperino"))
				fail("prova3 già presente");
			else {
				authenticator.addMaintainer("prova3", "paperino");
				assertTrue(authenticator.checkMaintainerPassword("prova3", "paperino"));
			}
		}
		catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testCheckUser() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse", "domohouse", "^v1Iz1rFOnqx");
		try {
			Authenticator authenticator = new DatabaseAuthenticator(new HashCalculator(), connector);
			assertTrue(authenticator.checkUser("signor Rossi"));
		}
		catch (Exception e) {
			fail();
		}
	}
}
