package it.unibs.ing.domohouse.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.rule.RuleParser;
import it.unibs.ing.domohouse.model.db.Connector;
import it.unibs.ing.domohouse.model.db.DatabaseLoader;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.model.util.ObjectFactory;

public class DatabaseRemoverTesting {

	@Test
	public void testRemoveActuatorFromDatabase() {
		Connector connector = new Connector("jdbc:mysql://localhost:3306/domohouse_test3", "root", "");
		OperatingModesManager.fillOperatingModes();
		DataFacade dataFacade;
		try {
			connector.openConnection();
			dataFacade = new DataFacade(connector);
			dataFacade.loadCategories();
			dataFacade.hasUser("signor Bianchi");
			dataFacade.loadHousingUnits("signor Bianchi");
			dataFacade.removeActuator("signor Bianchi", "Casa", "Cucina", "deum1_deumidificatori");
			dataFacade.saveData();
			DatabaseLoader databaseLoader = new DatabaseLoader(connector, new ObjectFactory(new RuleParser()), dataFacade);
			List<String> names = databaseLoader.loadActuators("signor Bianchi", dataFacade.getHousingUnit("signor Bianchi", "Casa"), "Cucina").stream().map(a -> a.getName()).collect(Collectors.toList());
			assertTrue(!names.contains("deum1_deumidificatori") && names.contains("trmg1_termoregolatori"));
		}
		catch (Exception e) {
			fail();
		}
	}
}
