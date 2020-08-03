package it.unibs.ing.domohouse.controller.inputhandler2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibs.ing.domohouse.controller.ControllerStrings;
import it.unibs.ing.domohouse.model.components.properties.DoubleInfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.InfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.StringInfoStrategy;
import it.unibs.ing.domohouse.model.util.DataFacade;
import it.unibs.ing.domohouse.view.MenuManager;

public class MaintainerInputHandler {

	private DataFacade dataFacade;

	public MaintainerInputHandler(DataFacade dataFacade) {
		this.dataFacade = dataFacade;
	}

	public boolean insertUser(String user) {
		if (dataFacade.hasUser(user))
			return false;
		else
			dataFacade.addUser(user);
		return true;
	}

	public boolean insertSensorCategory(String name, String abbreviation, String manufacturer, List<Integer> IDs,
			Map<Integer, String> measurementUnits) {
		if (dataFacade.hasSensorCategory(name))
			return false;

		Map<String, InfoStrategy> infoDomainMap = new HashMap<>();
		Map<String, String> measurementUnitMap = new HashMap<>();

		if (!insertInfoStrategies(IDs, infoDomainMap, measurementUnitMap, measurementUnits))
			return false;

		dataFacade.addSensorCategory(name, abbreviation, manufacturer, infoDomainMap, measurementUnitMap);
		return true;
	}

	private boolean insertInfoStrategies(List<Integer> IDs, Map<String, InfoStrategy> infoDomainMap,
			Map<String, String> measurementUnitMap, Map<Integer, String> measurementUnits) {
		for (int ID : IDs)
			if (dataFacade.hasNumericInfoStrategy(ID)) {
				DoubleInfoStrategy infoStrategy = dataFacade.getNumericInfoStrategy(ID);
				infoDomainMap.put(infoStrategy.getMeasuredProperty(), infoStrategy);
				measurementUnitMap.put(infoStrategy.getMeasuredProperty(), measurementUnits.get(ID));
			}
			else if (dataFacade.hasNonNumericInfoStrategy(ID)) {
				StringInfoStrategy infoStrategy = dataFacade.getNonNumericInfoStrategy(ID);
				infoDomainMap.put(infoStrategy.getMeasuredProperty(), infoStrategy);
			}
			else
				return false;
		return true;
	}
}
