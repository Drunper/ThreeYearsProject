package it.unibs.ing.domohouse.model.util;

import java.io.*;
import java.util.*;

import it.unibs.ing.domohouse.model.components.properties.ActiveState;
import it.unibs.ing.domohouse.model.components.properties.InfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.OperatingModesManager;
import it.unibs.ing.domohouse.model.components.properties.DoubleInfoStrategy;
import it.unibs.ing.domohouse.model.components.properties.InactiveState;
import it.unibs.ing.domohouse.model.components.properties.State;
import it.unibs.ing.domohouse.model.components.properties.StringInfoStrategy;
import it.unibs.ing.domohouse.model.ModelStrings;

public class LibImporter {
	private static final String DOUBLE_REGEX = "^[-+]?\\d+(\\.{0,1}(\\d+?))?$";

	private DataFacade dataFacade;
	private String errorString;

	public LibImporter(DataFacade dataFacade) {
		this.dataFacade = dataFacade;
	}

	public boolean importFile(String user) throws Exception {
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		String filePath = ModelStrings.LIB_PATH + ModelStrings.LIB_NAME;
		File file = new File(filePath);
		if (!file.isFile() || !file.canRead()) {
			errorString = "Errore! Il file non � esiste o � corrotto";
			return false;
		}
		errorString = "Sono presenti errori alle righe: ";
		boolean flag = true;
		int numberOfLine = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				numberOfLine++;
				if (readLineOfFile(user, line) == false) {
					flag = false;
					errorString = errorString + numberOfLine + ",";
				}
			}
			errorString = errorString.substring(0, errorString.length() - 1);
		}
		catch (IOException e) {
			throw new Exception("Errore I/O durante la lettura del file", e);
		}

		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return flag;
	}

	public String getErrorsString() {
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.errorString;
	}

	private boolean readLineOfFile(String user, String line) throws Exception {
		assert line != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		if (!line.contains(ModelStrings.SEPARATOR))
			return false;
		if (line.indexOf(ModelStrings.SEPARATOR) == line.length())
			return false;

		String keyword = line.split(ModelStrings.SEPARATOR)[0];

		switch (keyword) {
			case "actuator":
				return importActuator(user, line);
			case "actuator_category":
				return importActuatorCategory(user, line);
			case "artifact":
				return importArtifact(user, line);
			case "housing_unit":
				return importHousingUnit(user, line);
			case "sensor":
				return importSensor(user, line);
			case "sensor_category":
				return importSensorCategory(user, line);
			case "room":
				return importRoom(user, line);
			case "rule":
				return importRule(user, line);
			default:
				return false;
		}

	}

	// actuator:selectedHouse, name, category, true/false, element1;element2, room
	// return true se ha importato correttamente l'actuator, return false altrimenti
	private boolean importActuator(String user, String importedText) {
		assert importedText != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		String parameters = importedText.split(ModelStrings.SEPARATOR)[1]; // parameters = selectedHouse, name,
																			// category, // true/false,
																			// element1;element2, room
		String selectedHouse;
		String name;
		String category;
		String bool;
		List<String> elements = new ArrayList<>();
		String location;

		if (checkTokens(6, parameters)) {
			String[] tokens = tokenTrimmer(parameters.split(","));
			selectedHouse = tokens[0];
			name = tokens[1];
			name = name.replace(ModelStrings.UNDERSCORE, ModelStrings.NULL_CHARACTER); // il nome non pu� contenere
																						// underscore
			category = tokens[2];
			bool = tokens[3];
			elements = fromStringToList(tokens[4]);
			location = tokens[5];
			if (dataFacade.hasHousingUnit(user, selectedHouse) && dataFacade.hasRoom(user, selectedHouse, location)
					&& dataFacade.hasActuatorCategory(category)
					&& !dataFacade.hasActuator(user, selectedHouse, name + ModelStrings.UNDERSCORE + category)
					&& isBoolean(bool)) {
				boolean roomOrArtifact = Boolean.parseBoolean(bool);
				
				if (addElementsAssociations(user, selectedHouse, category, elements, roomOrArtifact)) {
					dataFacade.addActuator(user, selectedHouse, location, name + ModelStrings.UNDERSCORE + category,
							category, roomOrArtifact, elements);

					assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
					return true;
				}
			}
		}
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return false;
	}

	// actuator_category:name,abbr,constr, mode1;mode2;mode3 ,mode1
	private boolean importActuatorCategory(String user, String importedText) {
		assert importedText != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		String parameters = importedText.split(ModelStrings.SEPARATOR)[1]; // parameters =
																			// name,abbr,constr,mode1;mode2;mode3,mode1
		String name;
		String abbreviation;
		String constructor;
		List<String> listOfModes = new ArrayList<>();
		String defaultMode;
		if (checkTokens(5, parameters)) {
			String[] tokens = tokenTrimmer(parameters.split(","));
			name = tokens[0];
			name = name.replace(ModelStrings.UNDERSCORE, ModelStrings.NULL_CHARACTER); // il nome non pu� contenere
																						// underscore
			abbreviation = tokens[1];
			constructor = tokens[2];
			listOfModes = fromStringToList(tokens[3]);
			defaultMode = tokens[4];

			if (!dataFacade.hasActuatorCategory(name)) {
				for (String op : listOfModes) {
					if (!dataFacade.hasOperatingMode(op))
						return false;
				}

				if (!listOfModes.contains(defaultMode))
					return false;

				dataFacade.addActuatorCategory(name, abbreviation, constructor, listOfModes, defaultMode);

				assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
				return true;
			}
		}

		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return false;
	}

	// artifact:selectedHouse,name,descr,location
	private boolean importArtifact(String user, String importedText) throws Exception {
		assert importedText != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		String parameters = importedText.split(ModelStrings.SEPARATOR)[1]; // parameters =
																			// selectedHouse,name,descr,location
		String selectedHouse;
		String name;
		String descr;
		String location;
		if (checkTokens(5, parameters)) {
			String[] tokens = tokenTrimmer(parameters.split(","));
			selectedHouse = tokens[0];
			name = tokens[1];
			descr = tokens[2];
			location = tokens[3];

			String[] propertyTokens = tokens[4].split(";");

			Map<String, String> propertiesMap = new HashMap<>();

			for (String propertyToken : propertyTokens) {
				String propertyName = propertyToken.split("#")[ModelStrings.FIRST_TOKEN];
				String propertyValue = propertyToken.split("#")[ModelStrings.SECOND_TOKEN];
				if (!dataFacade.hasProperty(propertyName))
					dataFacade.addProperty(propertyName, propertyValue);
				propertiesMap.put(propertyName, propertyValue);
			}

			if (dataFacade.hasHousingUnit(user, selectedHouse) && !dataFacade.hasArtifact(user, selectedHouse, name)) {
				dataFacade.addArtifact(user, selectedHouse, location, name, descr, propertiesMap);
				assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
				return true;
			}
		}
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return false;
	}

	// housing_unit:name,descr
	private boolean importHousingUnit(String user, String importedText) {
		assert importedText != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		String parameters = importedText.split(ModelStrings.SEPARATOR)[1]; // parameters = name,descr,type
		String selectedHouse;
		String descr;
		String type;
		if (checkTokens(3, parameters)) {
			String[] tokens = tokenTrimmer(parameters.split(","));
			selectedHouse = tokens[0];
			descr = tokens[1];
			type = tokens[2];
			if (!dataFacade.hasHousingUnit(user, selectedHouse)) {
				dataFacade.addHousingUnit(user, selectedHouse, descr, type);
				assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
				return true;
			}
		}
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return false;
	}

	/*
	 * sensor_category:name,abbreviation,manufacturer,detectableInfo;double;domain-
	 * detectableInfo;double;domain... [domain deve essere del tipo -> "1#10#gradi"
	 * ] sensor_category:name,abbreviation,manufacturer,detectableInfo;type;domain1;
	 * domain2;domain3...
	 */
	private boolean importSensorCategory(String user, String importedText) throws Exception {
		assert importedText != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		String parameters = importedText.split(ModelStrings.SEPARATOR)[1]; // parameters =
																			// name,abbreviation,constructor,domain1;domain2,detectableInfo
		String name;
		String abbreviation;
		String manufacturer;
		String[] infos;
		Map<String, InfoStrategy> infoDomainMap = new HashMap<>();
		Map<String, String> measurementUnitMap = new HashMap<>();
		if (checkTokens(4, parameters)) {
			String[] tokens = tokenTrimmer(parameters.split(","));
			name = tokens[0];
			name = name.replace(ModelStrings.UNDERSCORE, ModelStrings.NULL_CHARACTER); // il nome non pu� contenere
																						// underscore
			abbreviation = tokens[1];
			manufacturer = tokens[2];
			infos = tokens[3].split("\\-");
			for (String infoString : infos) {
				int ID = Integer.parseInt(infoString.split(";")[ModelStrings.FIRST_TOKEN]);
				String infoName = infoString.split(";")[ModelStrings.SECOND_TOKEN];
				if (infoString.split(";")[ModelStrings.THIRD_TOKEN].equalsIgnoreCase("double")) {
					if (dataFacade.hasNumericInfoStrategy(ID)) {
						infoDomainMap.put(infoName, dataFacade.getNumericInfoStrategy(ID));
						measurementUnitMap.put(infoName,
								infoString.split(";")[ModelStrings.FOURTH_TOKEN].split("#")[ModelStrings.THIRD_TOKEN]);
					}
					else {
						String domain = infoString.split(";")[ModelStrings.FOURTH_TOKEN];
						int num = 0;
						for (int i = 0; i < domain.length(); i++) {
							if (domain.charAt(i) == '#')
								num++;
						}

						double min;
						double max;
						String measurementUnit;
						if (num == 2) {
							String s_min = domain.split("#")[0];
							String s_max = domain.split("#")[1];
							measurementUnit = domain.split("#")[2];
							if (s_min.matches(DOUBLE_REGEX) && s_max.matches(DOUBLE_REGEX)
									&& measurementUnit.length() > 0) {
								min = Double.parseDouble(s_min);
								max = Double.parseDouble(s_max);
							}
							else
								return false;

							if (min > max)
								return false;
							if (!dataFacade.hasProperty(infoName))
								dataFacade.addProperty(infoName, s_min);
							DoubleInfoStrategy info = new DoubleInfoStrategy(min, max, ID, infoName);
							dataFacade.addNumericInfoStrategy(ID, info);
							infoDomainMap.put(infoName, info);
							measurementUnitMap.put(infoName, measurementUnit);
						}
						else
							return false;
					}
				}
				else {
					if (dataFacade.hasNonNumericInfoStrategy(ID))
						infoDomainMap.put(infoName, dataFacade.getNonNumericInfoStrategy(ID));
					else {
						List<String> domainValues = new ArrayList<>();
						for (int i = ModelStrings.FOURTH_TOKEN; i < infoString.split(";").length; i++) {
							String temp = infoString.split(";")[i];
							if (domainValues.contains(temp))
								return false;
							else
								domainValues.add(temp);
						}
						if (!dataFacade.hasProperty(infoName))
							dataFacade.addProperty(infoName, domainValues.get(ModelStrings.FIRST_TOKEN));
						StringInfoStrategy info = new StringInfoStrategy(domainValues, ID, infoName);
						dataFacade.addNonNumericInfoStrategy(ID, info);
						infoDomainMap.put(infoName, info);
					}
				}
			}

			if (!dataFacade.hasSensorCategory(name)) {
				dataFacade.addSensorCategory(name, abbreviation, manufacturer, infoDomainMap, measurementUnitMap);
				assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
				return true;
			}
		}
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return false;
	}

	// numeric_sensor:selectedHouse, name, cate1;cate2, true/false,
	// element1;element2, location)
	private boolean importSensor(String user, String importedText) {
		assert importedText != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		String parameters = importedText.split(ModelStrings.SEPARATOR)[1]; // parameters =
																			// selectedHouse,name,cate1;cate2,true/false,element1;element2,location
		String selectedHouse;
		String name;
		String category;
		String bool;
		List<String> elements = new ArrayList<>();
		String location;
		if (checkTokens(6, parameters)) {
			String[] tokens = tokenTrimmer(parameters.split(","));
			selectedHouse = tokens[0];
			name = tokens[1];
			name = name.replace(ModelStrings.UNDERSCORE, ModelStrings.NULL_CHARACTER); // il nome non pu� contenere
																						// underscore
			category = tokens[2];
			bool = tokens[3];
			elements = fromStringToList(tokens[4]);
			location = tokens[5];
			if (!dataFacade.hasSensorCategory(category))
				return false;

			if (dataFacade.hasHousingUnit(user, selectedHouse) && dataFacade.hasRoom(user, selectedHouse, location)
					&& !dataFacade.hasSensor(user, selectedHouse, name + ModelStrings.UNDERSCORE + category)
					&& isBoolean(bool)) {
				boolean roomOrArtifact = Boolean.parseBoolean(bool);

				if (addElementsAssociations(user, selectedHouse, category, elements, roomOrArtifact)) {
					dataFacade.addSensor(user, selectedHouse, location, name + ModelStrings.UNDERSCORE + category,
							category, roomOrArtifact, elements);
					assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
					return true;
				}
			}
		}
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return false;
	}

	private boolean addElementsAssociations(String user, String selectedHouse, String category, List<String> elements,
			boolean roomOrArtifact) {
		if (roomOrArtifact) {
			for (String room : elements) {
				if (!dataFacade.hasRoom(user, selectedHouse, room))
					return false;
				if (dataFacade.isRoomAssociated(user, selectedHouse, room, category))
					return false;
			}

			for (String room : elements) {
				dataFacade.addRoomAssociation(user, selectedHouse, room, category);
			}
			return true;
		}
		else {
			for (String artifact : elements) {
				if (!dataFacade.hasArtifact(user, selectedHouse, artifact))
					return false;
				if (dataFacade.isArtifactAssociated(user, selectedHouse, artifact, category))
					return false;
			}
			for (String art : elements) {
				dataFacade.addRoomAssociation(user, selectedHouse, art, category);
			}
			return true;
		}
	}

	// room:selectedHouse,name,descr,temp,umidita,pressione,vento,pres_pers
	private boolean importRoom(String user, String importedText) throws Exception {
		assert importedText != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		String parameters = importedText.split(ModelStrings.SEPARATOR)[1];
		String selectedHouse;
		String name;
		String descr;

		if (checkTokens(4, parameters)) {
			String[] tokens = tokenTrimmer(parameters.split(","));
			selectedHouse = tokens[0];
			name = tokens[1];
			descr = tokens[2];

			String[] propertyTokens = tokens[3].split(";");

			Map<String, String> propertiesMap = new HashMap<>();

			for (String propertyToken : propertyTokens) {
				String propertyName = propertyToken.split("#")[ModelStrings.FIRST_TOKEN];
				String propertyValue = propertyToken.split("#")[ModelStrings.SECOND_TOKEN];
				if (!dataFacade.hasProperty(propertyName))
					dataFacade.addProperty(propertyName, propertyValue);
				propertiesMap.put(propertyName, propertyValue);
			}

			dataFacade.addRoom(user, selectedHouse, name, descr, propertiesMap);
			assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
			return true;

		}
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return false;
	}

	/*
	 * Controlla nomi Controlla consistenza tra array sensor, attuatore e antString
	 * consString Rule r = new Rule(dataFacade.getHousingUnit(user, selectedHouse),
	 * name, antString, consString, sensors, act, state); Chiamare metodo
	 * dataFacade.getHousingUnit(user, selectedHouse).addRule(r)
	 */
	private boolean importRule(String user, String importedText) throws Exception {
		assert importedText != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		if (!importedText.contains(":="))
			return false;
		importedText = importedText.replace(":=", "=");
		String parameters = importedText.split(ModelStrings.SEPARATOR)[1]; // parameters = selectedHouse, name,
																			// antString,
																			// consString, true/false
		parameters = parameters.replace("=", ":=");
		String selectedHouse;
		String rule_name;
		String antString;
		String consString;
		String state;
		// rule:selectedHouse, name, s1_sensori_termici.temperatura>10 && s2_sensore di
		// colore.colore == "verde" || time == 10.00, a1_categoria :=
		// mantenimentoTemperatura(19), true
		if (checkTokens(5, parameters)) {
			String[] tokens = tokenTrimmer(parameters.split(","));
			selectedHouse = tokens[0];
			rule_name = tokens[1];
			antString = tokens[2];
			consString = tokens[3];
			state = tokens[4];
			State rule_state;
			Set<String> sensors = new HashSet<>();
			Set<String> actuators = new HashSet<>();

			if (dataFacade.hasHousingUnit(user, selectedHouse) && !dataFacade.hasRule(user, selectedHouse, rule_name)) {
				// procedo a controllare antString, consString e costruire ArrayList<String>
				// sensors, e String actuator
				String toElaborate = antString;

				if (!logicChecker(toElaborate))
					return false;
				// impsens1_importedsensor.temperatura>10 && time > 10.00
				String[] ruleConditions = tokenTrimmer(toElaborate.split("\\&\\&|\\|\\|"));

				for (String condition : ruleConditions) {
					if (!verifySyntaxRuleCondition(user, selectedHouse, condition))
						return false;
				}

				sensors = getSensorFromAntString(antString);

				if (!verifyConsSyntax(user, selectedHouse, consString))
					return false;

				actuators = getActuatorsFromConsString(consString);

				if (isBoolean(state))
					rule_state = Boolean.parseBoolean(state) ? new ActiveState() : new InactiveState();
				else
					return false;
				consString = consString.replace("; ", ", ");
				dataFacade.addRule(user, selectedHouse, rule_name, antString, consString, sensors, actuators,
						rule_state);
				assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
				return true;
			}
		}
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return false;
	}

	private boolean checkTokens(int num_tokens, String parameters) {
		assert num_tokens >= 0 && parameters != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		int comma = 0;
		int num_elements = 0;
		for (int i = 0; i < parameters.length(); i++) {
			if (parameters.charAt(i) == ',')
				comma++;
			if (i > 0 && parameters.charAt(i - 1) == ',' && parameters.charAt(i) == ',')
				return false;
		}
		comma++;
		num_elements = comma;
		if (num_tokens == num_elements) {
			if (parameters.lastIndexOf(',') == parameters.length())
				return false;
			return true;
		}
		else
			return false;
	}

	private String[] tokenTrimmer(String[] tokens) {
		String[] result = new String[tokens.length];
		for (int i = 0; i < tokens.length; i++)
			result[i] = tokens[i].trim();
		return result;
	}

	private List<String> fromStringToList(String elements) {
		assert elements != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		List<String> result;
		if (elements.contains(";")) {
			String[] res = elements.split(";");
			result = new ArrayList<>(Arrays.asList(res));
		}
		else {
			result = new ArrayList<>();
			result.add(elements);
		}

		assert result != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return result;
	}

	private boolean isBoolean(String bool) {
		assert bool != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		if (bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("false"))
			return true;
		return false;
	}

	@SuppressWarnings("unused")
	private boolean verifySyntaxRuleCondition(String user, String selectedHouse, String condition) {
		assert selectedHouse != null && condition != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		// controlli preliminari (contiene operatore, valore, time, punto...)
		String test;
		try {
			if (condition.contains("time")) {
				test = condition.split("<|>|>=|<=|==|!=")[0];
				test = condition.split("<|>|>=|<=|==|!=")[1];
			}
			else {
				test = condition.split("\\.")[0];
				test = condition.split("\\.")[1].split("<|>|>=|<=|==|!=")[0];
				test = condition.split("\\.")[1].split("<|>|>=|<=|==|!=")[1];
			}
		}
		catch (Exception e) {
			return false;
		}

		if (!condition.contains("time")) {

			String sensor = condition.split("\\.")[0]; // s1_sensori_termici
			String info = condition.split("\\.")[1].split("<|>|>=|<=|==|!=")[0].trim();// temperatura
			String value = condition.split("\\.")[1].split("<|>|>=|<=|==|!=")[1].trim();// 10

			if (!value.matches("^[-+]?\\d+(\\.{0,1}(\\d+?))?$") && !value.matches("^[a-zA-Z]+$"))
				return false;

			if (dataFacade.hasSensor(user, selectedHouse, sensor)) {

				List<String> catList = dataFacade.getSensorCategoriesByInfo(info);

				boolean sensorCategoryHasInfo = false;
				String category = dataFacade.getCategoryOfASensor(user, selectedHouse, sensor);
				if (catList.contains(category))
					sensorCategoryHasInfo = true;
				if (!sensorCategoryHasInfo)
					return false;
			}
			else
				return false;
		}
		else {
			String time = condition.split("<|>|>=|<=|==|!=")[1].trim(); // 10.00
			if (time.contains(".")) {
				int hour;
				int minute;
				try {
					hour = Integer.parseInt(time.split("\\.")[0]);
					minute = Integer.parseInt(time.split("\\.")[1]);
				}
				catch (Exception e) {
					hour = -1;
					minute = -1;
				}
				if (hour < 0 || hour > 23 || minute < 0 || minute > 59)
					return false;
			}
			else
				return false;
		}
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return true;
	}

	// start := 4.00;trm1_termoregolatore := mantenimentoTemperatura(20);l1_luciRGB
	// := cambiaColore(verde)
	private boolean verifyConsSyntax(String user, String selectedHouse, String consString) {
		boolean hasStart = false;
		for (String action : consString.split(";")) {
			if (action.contains("start")) {
				if (hasStart)
					return false;
				if (verifyStartSyntax(action))
					hasStart = true;
			}
			else if (!verifyActionSyntax(user, selectedHouse, action))
				return false;
		}
		return true;
	}

	private boolean verifyStartSyntax(String startString) {
		String time = startString.split(":=")[1]; // 10.00
		if (time.contains(".")) {
			int hour;
			int minute;
			try {
				hour = Integer.parseInt(time.split("\\.")[0]);
				minute = Integer.parseInt(time.split("\\.")[1]);
			}
			catch (Exception ex) {
				hour = -1;
				minute = -1;
			}
			if (hour < 0 || hour > 23 || minute < 0 || minute > 59)
				return false;
			else
				return true;
		}
		else
			return false;
	}

	private boolean verifyActionSyntax(String user, String selectedHouse, String action) {
		String actuator = action.split(":=")[0].trim();
		if (!dataFacade.hasActuator(user, selectedHouse, actuator))
			return false;
		String opMode = action.split(":=")[1].trim();
		if (opMode.contains("(")) {
			if (!opMode.contains(")"))
				return false;
			else {
				String parameters = opMode.split("\\(|\\)")[1];
				opMode = opMode.split("\\(")[0];
				if (!OperatingModesManager.hasOperatingMode(opMode))
					return false;
				int paramNumbers = OperatingModesManager.getOperatingMode(opMode).getNumberOfParameters();
				if (paramNumbers != parameters.split(",").length)
					return false;
			}
		}
		else if (opMode.contains(")"))
			return false;
		else if (!OperatingModesManager.hasOperatingMode(opMode))
			return false;
		return true;
	}

	private Set<String> getSensorFromAntString(String antString) {
		assert antString != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		Set<String> sensors = new HashSet<>();
		// sens1_termometri.temperatura > 10 && time < 10 || s1.colore == verde
		while (antString.contains("&&") || antString.contains("||")) {
			String condition = antString.split("\\&\\&|\\|\\|")[0];
			if (!condition.contains("time")) {
				String sensor = condition.split("\\.")[0];
				sensors.add(sensor.trim());
			}
			String temp = antString.split("\\&\\&|\\|\\|")[1];
			antString = temp;
		}
		if (!antString.contains("time"))
			sensors.add(antString.split("\\.")[0].trim());

		assert sensors != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return sensors;
	}

	private Set<String> getActuatorsFromConsString(String consString) {
		assert consString != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;

		Set<String> actuators = new HashSet<>();
		// sens1_termometri.temperatura > 10 && time < 10 || s1.colore == verde
		String[] actions = consString.split(";");
		for (String action : actions) {
			String act = action.split(":=")[0].trim();
			if (!act.equals("start"))
				actuators.add(act);
		}

		assert actuators != null;
		assert libImporterInvariant() : ModelStrings.WRONG_INVARIANT;
		return actuators;
	}

	private boolean logicChecker(String toElaborate) {
		for (int i = 0; i < toElaborate.length(); i++)
			if (toElaborate.charAt(i) == '&') {
				if (toElaborate.charAt(i + 1) != '&')
					return false;
				else if (!(toElaborate.charAt(i + 2) != '&' && toElaborate.charAt(i + 2) != '|'))
					return false;
				else
					i = i + 1;
			}
			else if (toElaborate.charAt(i) == '|') {
				if (toElaborate.charAt(i + 1) != '|')
					return false;
				else if (!(toElaborate.charAt(i + 2) != '&' && toElaborate.charAt(i + 2) != '|'))
					return false;
				else
					i = i + 1;
			}
		return true;
	}

	private boolean libImporterInvariant() {
		return dataFacade != null;
	}
}
