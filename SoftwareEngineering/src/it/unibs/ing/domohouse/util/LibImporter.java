package it.unibs.ing.domohouse.util;

import java.io.File;
import java.util.ArrayList;

import it.unibs.ing.domohouse.components.Rule;
import it.unibs.ing.domohouse.components.SensorCategory;

public class LibImporter {
	private static final String DOUBLE_REGEX = "^[-+]?\\d+(\\.{0,1}(\\d+?))?$";
	
	private DataHandler dataHandler;
	private InputHandler inputHandler; 
	
	public LibImporter(DataHandler dataHandler, InputHandler inputHandler) {
		this.dataHandler = dataHandler;
		this.inputHandler = inputHandler;
	}
	
	//Per ora manutentore dovrà inserire elementi e dispositivi secondo un certo ordine
	//per ogni riga di file eliminare gli spazi e controllare che ci siano i 2 punti e che i 2 punti non siano l'ultimo carattere della riga
	
	private boolean readLineOfFile(String line) {
		if(!line.contains(":")) return false;
		if(!(line.indexOf(":") == line.length())) return false;
		
		String keyword = line.split(":")[0];
		
		switch(keyword){
			case "actuator": return importActuator(line);
			case "actuator_category": return importActuatorCategory(line);
			case "artifact": return importArtifact(line);
			case "housing_unit": return importHousingUnit(line);
			case "non_numeric_sensor": return importNonNumericSensor(line);
			case "numeric_sensor": return importNumericSensor(line);
			case "non_numeric_sensor_category": return importNonNumericSensorCategory(line);
			case "numeric_sensor_category": return importNumericSensorCategory(line);
			case "room": return importRoom(line);
			case "rule": return importRule(line);
			default: return false;
		}
		
	}
	
	
	
	//actuator:selectedHouse, name, category, true/false, element1;element2, room
	//return true se ha importato correttamente l'actuator, return false altrimenti
	private boolean importActuator(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = selectedHouse, name, category, true/false, element1;element2, room
		String selectedHouse;
		String name;
		String category;
		String bool;
		ArrayList<String> elements = new ArrayList<>();
		String location;
		
		if(checkTokens(6, parameters)){
			selectedHouse = parameters.split(",")[0];
			name = parameters.split(",")[1];
			category = parameters.split(",")[2];
			bool = parameters.split(",")[3];
			elements = fromStringToArrayList(parameters.split(",")[4]);
			location = parameters.split(",")[5];
			
			if(verifyHousingUnit(selectedHouse) && verifyRoom(selectedHouse, location) && verifyActuatorCategory(category) && verifyActuator(selectedHouse, name + "_" + category) && isBoolean(bool)) {
				if(bool.equalsIgnoreCase("true")) {
					//controllare che esistano stanze da associare
					for(String room : elements) {
						if(!verifyRoom(selectedHouse, room)) return false;
					}
					
					for(String room : elements) {
						dataHandler.addAssociation(selectedHouse, room, category);
					}
					
					inputHandler.createActuator(selectedHouse, name, category, true, elements, location);
					return true;
					
				}else {
					//controllare che esistano artefatti da associare
					for(String art : elements) {
						if(!verifyArtifact(selectedHouse, art)) return false;
					}
					
					for(String art : elements) {
						dataHandler.addAssociation(selectedHouse, art, category);
					}
					
					inputHandler.createActuator(selectedHouse, name, category, false, elements, location);
					return true;
				}
			}
		}
		
		return false;
	}
	
	//actuator_category:name,abbr,constr, mode1;mode2;mode3 ,mode1
	private boolean importActuatorCategory(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = name,abbr,constr,mode1;mode2;mode3,mode1
		String name;
		String abbreviation;
		String constructor;
		ArrayList<String> listOfModes = new ArrayList<>();
		String defaultMode;
		if(checkTokens(5, parameters)) {
			name = parameters.split(",")[0];
			abbreviation = parameters.split(",")[1];
			constructor = parameters.split(",")[2];
			listOfModes = fromStringToArrayList(parameters.split(",")[3]);
			defaultMode = parameters.split(",")[4];
			
			if(verifyActuatorCategory(name)) {
				for(String op : listOfModes) {
					if(!verifyOperatingMode(op)) return false;
				}
							
				if(!listOfModes.contains(defaultMode)) return false;
				
				inputHandler.createActuatorCategory(name, abbreviation, constructor , listOfModes, defaultMode);
				return true;
			}
		}
		return false;
	}
	
	//artifact:selectedHouse,name,descr,location
	private boolean importArtifact(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = selectedHouse,name,descr,location
		String selectedHouse;
		String name;
		String descr;
		String location;
		if(checkTokens(4, parameters)) {
			selectedHouse = parameters.split(",")[0];
			name = parameters.split(",")[1];
			descr = parameters.split(",")[2];
			location = parameters.split(",")[3];
			if(verifyHousingUnit(selectedHouse) && verifyArtifact(selectedHouse, name)) {
				inputHandler.createArtifact(selectedHouse, name, descr, location);
				return true;
			}
		}
		return false;	
	}
	
	//housing_unit:name,descr
	private boolean importHousingUnit(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = name,descr
		String name;
		String descr;
		if(checkTokens(2, parameters)) {
			name = parameters.split(",")[0];
			descr = parameters.split(",")[1];
			if(verifyHousingUnit(name)) {
				inputHandler.createHouse(name, descr);
				return true;
			}
		}
		return false;	
	}
	
	//non_numeric_sensor:selectedHouse,name,cate1;cate2,true/false,element1;element2,location
	private boolean importNonNumericSensor(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = selectedHouse,name,cate1;cate2,true/false,element1;element2,location
		String selectedHouse;
		String name;
		String categories = "";
		ArrayList<String> categoryList = new ArrayList<>();
		String bool;
		ArrayList<String> elements = new ArrayList<>();
		String location;
		if(checkTokens(6, parameters)) {
			selectedHouse = parameters.split(",")[0];
			name = parameters.split(",")[1];
			categoryList = fromStringToArrayList(parameters.split(",")[2]);
			bool = parameters.split(",")[3];
			elements = fromStringToArrayList(parameters.split(",")[4]);
			location = parameters.split(",")[5];
			
			for(String cat : categoryList) {
				if(verifyNonNumericSensorCategory(cat)) {
					categories = categories + "_"+ cat;
				}else return false;
			}
			
			if(verifyHousingUnit(selectedHouse) && verifyRoom(selectedHouse,location) && verifySensor(selectedHouse, name + categories) && isBoolean(bool)) {
				if(bool.equalsIgnoreCase("true")) {
					//stanze
					for(String room : elements) {
						if(!verifyRoom(selectedHouse, room)) return false;
					}
					inputHandler.createNonNumericSensor(selectedHouse, name, categoryList, true , elements, location);
					return true;
					
				}else {
					//artefatti
					for(String art : elements) {
						if(!verifyArtifact(selectedHouse, art)) return false;
					}
					inputHandler.createNonNumericSensor(selectedHouse, name, categoryList, false , elements, location);
					return true;
				}
			}
			
		}
		return false;
	}
	
	//non_numeric_sensor_category:name,abbreviation,constructor,domain1;domain2,detectableInfo
	private boolean importNonNumericSensorCategory(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = name,abbreviation,constructor,domain1;domain2,detectableInfo
		String name;
		String abbreviation;
		String constructor;
		ArrayList<String> domains = new ArrayList<>();
		String info;
		if(checkTokens(5, parameters)) {
			name = parameters.split(",")[0];
			abbreviation = parameters.split(",")[1];
			constructor = parameters.split(",")[2];
			domains = fromStringToArrayList(parameters.split(",")[3]);
			info = parameters.split(",")[4];
			
			if(verifyNonNumericSensorCategory(name)) {
				inputHandler.createNonNumericSensorCategory(name, abbreviation, constructor, domains, info);
				return true;
			}
		}
		return false;
	}
	
	//numeric_sensor:selectedHouse, name, cate1;cate2, true/false, element1;element2, location)
	private boolean importNumericSensor(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = selectedHouse,name,cate1;cate2,true/false,element1;element2,location
		String selectedHouse;
		String name;
		String categories = "";
		ArrayList<String> categoryList = new ArrayList<>();
		String bool;
		ArrayList<String> elements = new ArrayList<>();
		String location;
		if(checkTokens(6, parameters)) {
			selectedHouse = parameters.split(",")[0];
			name = parameters.split(",")[1];
			categoryList = fromStringToArrayList(parameters.split(",")[2]);
			bool = parameters.split(",")[3];
			elements = fromStringToArrayList(parameters.split(",")[4]);
			location = parameters.split(",")[5];
			
			for(String cat : categoryList) {
				if(verifyNumericSensorCategory(cat)) {
					categories = categories + "_"+ cat;
				}else return false;
			}
			
			if(verifyHousingUnit(selectedHouse) && verifyRoom(selectedHouse,location) && verifySensor(selectedHouse, name + categories) && isBoolean(bool)) {
				if(bool.equalsIgnoreCase("true")) {
					//stanze
					for(String room : elements) {
						if(!verifyRoom(selectedHouse, room)) return false;
					}
					inputHandler.createNumericSensor(selectedHouse, name, categoryList, true , elements, location);
					return true;
					
				}else {
					//artefatti
					for(String art : elements) {
						if(!verifyArtifact(selectedHouse, art)) return false;
					}
					inputHandler.createNumericSensor(selectedHouse, name, categoryList, false , elements, location);
					return true;
				}
			}
			
		}
		return false;
	}
	
	//numeric_sensor_category:name, abbr,constru, domain, detectableInfo [domain deve essere del tipo -> "1#10#gradi" ]
	private boolean importNumericSensorCategory(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = name,abbreviation,constructor,domain,detectableInfo
		String name;
		String abbreviation;
		String constructor;
		String domain;
		String info;
		if(checkTokens(5, parameters)) {
			name = parameters.split(",")[0];
			abbreviation = parameters.split(",")[1];
			constructor = parameters.split(",")[2];
			domain = parameters.split(",")[3]; //domain = 1#10#gradi
			info = parameters.split(",")[4];
			
			int num = 0;
			for(int i = 0; i < domain.length(); i++) {
				if(domain.charAt(i) == '#') num++;
			}
			
			double min;
			double max;
			String unit_measurements;
			if(num == 2) {
				String s_min = domain.split("#")[0];
				String s_max = domain.split("#")[1];
				unit_measurements = domain.split("#")[2];
				if(s_min.matches(DOUBLE_REGEX) && s_max.matches(DOUBLE_REGEX) 
						&& unit_measurements.length() > 0){
					min = Double.parseDouble(s_min);
					max = Double.parseDouble(s_max);
				}else return false;
				
				if(min > max) return false;
				
				//min + " -to- " + max (unit_measurement);
				domain = min + " -to- " + max + " (" + unit_measurements + ")";
			}else return false;
			
			if(verifyNumericSensorCategory(name)) {
				inputHandler.createNumericSensorCategory(name, abbreviation, constructor, domain, info);
				return true;
			}
		}
		return false;
	}
	
	//room:selectedHouse,name,descr,temp,umidita,pressione,vento,pres_pers
	private boolean importRoom(String importedText) {
		String parameters = importedText.split(":")[1];
		String selectedHouse;
		String name;
		String descr;
		String temp;
		String umidita;
		String pressione;
		String vento;
		String bool_pres_pers;
		
		if(checkTokens(8, parameters)) {
			selectedHouse = parameters.split(",")[0];
			name = parameters.split(",")[1];
			descr = parameters.split(",")[2];
			temp = parameters.split(",")[3];
			umidita = parameters.split(",")[4];
			pressione = parameters.split(",")[5];
			vento = parameters.split(",")[6];
			bool_pres_pers = parameters.split(",")[7];
			
			if(verifyHousingUnit(selectedHouse) && verifyRoom(selectedHouse, name)
					&& temp.matches(DOUBLE_REGEX) && umidita.matches(DOUBLE_REGEX) 
						&& pressione.matches(DOUBLE_REGEX) && temp.matches(DOUBLE_REGEX) 
							&& vento.matches(DOUBLE_REGEX) && isBoolean(bool_pres_pers)) {
				double d_temp = Double.parseDouble(temp);
				double d_umidita = Double.parseDouble(umidita);
				double d_pressione = Double.parseDouble(pressione);
				double d_vento = Double.parseDouble(vento);
				boolean presenza = Boolean.parseBoolean(bool_pres_pers);
				String presenza_persone;
				if(presenza) presenza_persone = "presenza di persone";
				else presenza_persone = "assenza di persone";
				
				inputHandler.createRoom(selectedHouse, name, descr, d_temp, d_umidita, d_pressione, d_vento, presenza_persone);
				return true;
			}
		}
		return false;
		
	}
	/*
	 *
		Controlla nomi 
	Controlla consistenza tra array sensor, attuatore e antString consString
	Rule r = new Rule(dataHandler.getHousingUnit(selectedHouse), name, antString, consString, sensors, act, state);
	Chiamare metodo dataHandler.getHousingUnit(selectedHouse).addRule(r)
	 */
	private boolean importRule(String importedText) {
		String parameters = importedText.split(":")[1]; //parameters = selectedHouse, name, antString, consString, true/false
		String selectedHouse;
		String rule_name;
		String antString;
		String consString;
		String state;
		//rule:selectedHouse, name, s1_sensori_termici.temperatura>10 && s2_sensore di colore.colore == "verde" || time == 10.00, a1_categoria := mantenimentoTemperatura(19), true
		if(checkTokens(5, parameters)) {
			selectedHouse = parameters.split(",")[0];
			rule_name = parameters.split(",")[1];
			antString = parameters.split(",")[2];
			consString = parameters.split(",")[3];
			state = parameters.split(",")[4];
			boolean rule_state;
			ArrayList<String> sensors = new ArrayList<>();
			String actuator;

			
			if(verifyHousingUnit(selectedHouse) && verifyRule(selectedHouse, rule_name)) {
				//procedo a controllare antString, consString e costruire ArrayList<String> sensors, e String actuator
				String toElaborate = antString;
				
				while(toElaborate.contains("&&") || toElaborate.contains("||")) {
					if(!toElaborate.startsWith("&&") && !toElaborate.startsWith("||")) {
						if(!verifySyntaxRuleCondition(selectedHouse, toElaborate.split("[\\&\\&|\\|\\|]")[0])) return false;
						if(!verifySyntaxRuleCondition(selectedHouse, toElaborate.split("[\\&\\&|\\|\\|]")[1])) return false;
						toElaborate = toElaborate.split("[\\&\\&|\\|\\|]")[1];
					}	
				}
				
				//sintassi condizioni tutto apposto
				//devo riempire sensor
				toElaborate = antString;
				sensors = getSensorFromAntString(toElaborate);
				
				//controllo consString
				if(!consString.contains(":=")) return false;
				
				//a1_categoria := mantenimentoTemperatura(19)
				String test_actuator = consString.split(":=")[0];
				String operating_mode = consString.split(":=")[1];
				
				if(operating_mode.contains("(")) {
					operating_mode = operating_mode.split("(")[0];
				}
				
				if(verifyActuator(selectedHouse, test_actuator)) {
					actuator = test_actuator;
				}else return false;
				
				if(!OperatingModesHandler.hasOperatingMode(operating_mode)) return false;
				
				if(isBoolean(state)) {
				 rule_state = Boolean.parseBoolean(state);
				}else return false;
				
				//creo Rule
				Rule r = new Rule(dataHandler.getHousingUnit(selectedHouse), rule_name, antString, consString, sensors, actuator, rule_state);
				dataHandler.getHousingUnit(selectedHouse).addRule(r);
				return true;
			}
		

		}
		return false;
	}
	
	private boolean checkTokens(int num_tokens, String parameters) {
		int comma = 0;
		int num_elements = 0;
		for(int i = 0; i < parameters.length(); i++) {
			if(parameters.charAt(i) == ',') comma ++;
			if(i>0 && parameters.charAt(i-1) == ',' && parameters.charAt(i) == ',') return false;
		}
		comma++;
		num_elements = comma;
		if(num_tokens == num_elements) {
			if(parameters.lastIndexOf(',') == parameters.length()) return false;
			return true;
		}
		else return false;
	}
	
	private ArrayList<String> fromStringToArrayList(String elements){
		ArrayList<String> result = new ArrayList<>();
		if(elements.contains(";")) {
			do {
				result.add(elements.split(";")[0]);
				elements = elements.split(";")[1];
			}while(elements.contains(";"));
		}else {
			//element contiene un solo elemento
			result.add(elements);
		}
		return result;
	}
	
	private boolean isBoolean(String bool) {
		if(bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("false")) return true;
		return false;
	}
	//metodo verifica nome casa
	private boolean verifyHousingUnit(String house) {
		boolean flag = true;
		house = house.toLowerCase();
		for(String h : dataHandler.getHouseList()) {
			if(house.equalsIgnoreCase(h)) flag = false;
		}
		return flag;
	}
	
	//metodo verifica non num sens cat
	private boolean verifyNonNumericSensorCategory(String category) {
		boolean flag = true;
		category = category.toLowerCase();
		for(String cat : dataHandler.getSensorCategoryList()) {
			SensorCategory sens_cat = dataHandler.getSensorCategory(cat);
			if(!sens_cat.getIsNumeric()) {
				if(category.equalsIgnoreCase(cat)) flag = false;
			}
		}
		return flag;
	}
	
	//metodo verifica num sens cat
	private boolean verifyNumericSensorCategory(String category) {
		boolean flag = true;
		category = category.toLowerCase();
		for(String cat : dataHandler.getSensorCategoryList()) {
			SensorCategory sens_cat = dataHandler.getSensorCategory(cat);
			if(sens_cat.getIsNumeric()) {
				if(category.equalsIgnoreCase(cat)) flag = false;
			}
		}
		return flag;
	}
	
	private boolean verifyActuatorCategory(String category) {
		boolean flag = true;
		category = category.toLowerCase();
		for(String cat : dataHandler.getActuatorCategoryList()) {
			if(category.equalsIgnoreCase(cat)) flag = false;
		}
		
		return flag;
	}
	
	//metodo verifica room
	private boolean verifyRoom(String selectedHouse, String room) {
		boolean flag = true;
		room = room.toLowerCase();
		if(verifyHousingUnit(selectedHouse)) {
			for(String r : dataHandler.getRoomList(selectedHouse)) {
				if(room.equalsIgnoreCase(r)) flag = false;
			}
		}else return false;
		
		return flag;
	}
	
	private boolean verifyActuator(String selectedHouse, String name) {
		boolean flag = true; 
		name = name.toLowerCase();
		if(verifyHousingUnit(selectedHouse)) {
			for(String r : dataHandler.getRoomList(selectedHouse)) {
				for(String act : dataHandler.getActuatorNames(selectedHouse, r)) {
					if(name.equalsIgnoreCase(act)) flag = false;
				}
			}
		}else return false;
		
		
		return flag;
	}
	
	private boolean verifyArtifact(String selectedHouse, String name) {
		boolean flag = true;
		name = name.toLowerCase();
		if(verifyHousingUnit(selectedHouse)) {
			for(String r : dataHandler.getRoomList(selectedHouse)) {
				for(String art : dataHandler.getArtifactNames(selectedHouse, r)) {
					if(name.equalsIgnoreCase(art)) flag = false;
				}
			}
		}else return false;
		
		return flag;
	}
	
	private boolean verifySensor(String selectedHouse, String name) {
		boolean flag = true;
		name = name.toLowerCase();
		if(verifyHousingUnit(selectedHouse)) {
			for(String r : dataHandler.getRoomList(selectedHouse)) {
				for(String sens : dataHandler.getSensorNames(selectedHouse, r)) {
					if(name.equalsIgnoreCase(sens)) flag = false;
				}
			}
		}else return false;
		
		return flag;
	}
	
	private boolean verifyOperatingMode(String operating_mode) {
		boolean flag;
		if(dataHandler.hasOperatingMode(operating_mode)) flag = true;
		else flag = false;
		return flag;
	}
	
	private boolean verifyRule(String selectedHouse, String rule) {
		boolean flag = true;
		rule = rule.toLowerCase();
		if(verifyHousingUnit(selectedHouse)) {
			if(dataHandler.getHousingUnit(selectedHouse).hasRule(rule)) flag = false;
		}else return false;
		return flag;
	}
	
	private boolean verifySyntaxRuleCondition(String selectedHouse, String condition) {
		//controlli preliminari (contiente operatore, valore, time, punto...)
		String test;
		try {
			if(condition.contains("time")) {
				test = condition.split("<|>|>=|<=|==|!=")[0];
				test = condition.split("<|>|>=|<=|==|!=")[1];
			}else {
				test = condition.split("\\.")[0];
				test = condition.split("\\.")[1].split("<|>|>=|<=|==|!=")[0];
				test = condition.split("\\.")[1].split("<|>|>=|<=|==|!=")[1];
			}
		}catch(Exception ex){
			return false;
		}
		
		
		if(!condition.contains("time")) {
			
			String sensor = condition.split("\\.")[0]; //s1_sensori_termici
			String info = condition.split("\\.")[1].split("<|>|>=|<=|==|!=")[0];//temperatura
			String value = condition.split("\\.")[1].split("<|>|>=|<=|==|!=")[1];//10
	
			if(verifySensor(selectedHouse, sensor)) {
				
				SensorCategory cat = dataHandler.getSensorCategoryByInfo(info);
		
				boolean sensorCategoryHasInfo = false;
				for(String category : dataHandler.getCategoriesOfASensor(selectedHouse, sensor)){
					if(category.equalsIgnoreCase(cat.getName())) sensorCategoryHasInfo = true;
				}
				if(!sensorCategoryHasInfo) return false;
		
				if(cat.getIsNumeric() && !value.matches(DOUBLE_REGEX)) return false; //se la categoria è numerica ma il valore non è double fine
				if(!cat.getIsNumeric() && value.matches(DOUBLE_REGEX)) return false; //se la categoria è non num ma il valore è double fine (cambiare con String regex)
				}else return false;
				
			}else {
				//toElaborate.split("[\\&\\&|\\|\\|]")[0]; = time > 10
				String time = condition.split("<|>|>=|<=|==|!=")[1]; //10.00
				if(time.contains(".")) {
					int hour;
					int minute;
					try{
						hour = Integer.parseInt(time.split("\\.")[0]);
						minute = Integer.parseInt(time.split("\\.")[1]);
						}catch(Exception ex) {
							hour = -1;
							minute = -1;
						}
					if(hour < 0 || hour > 23 || minute < 0 || minute > 59) return false;
				}
			
		}
		//se va tutto bene arriviamo qui
		return true;
	}
	
	private ArrayList<String> getSensorFromAntString(String antString) {
		ArrayList<String> sensors = new ArrayList<>();
		//sens1_ciao.temperatura > 10 && time < 10 || s1.colore == verde
		while(antString.contains("&&") || antString.contains("||")) {
			String condition = antString.split("[\\&\\&|\\|\\|]")[0];
			if(!condition.contains("time")) {
				String sensor = condition.split("\\.")[0];
				sensors.add(sensor);
			}
			antString = antString.split("[\\&\\&|\\|\\|]")[1];
		}
		if(!antString.contains("time")) sensors.add(antString.split("\\.")[0]);
		
		return sensors;
	}
}
