package it.unibs.ing.domohouse.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unibs.ing.domohouse.interfaces.Operator;
import it.unibs.ing.domohouse.interfaces.StringOperator;


public class Rule implements Serializable{
	
	private static final long serialVersionUID = -3835080225181052479L;
	private HousingUnit housingUnit;
	private String name;
	private String antecedentRuleString; //stringa antecedente regola
	private String consequentRuleString; //stringa conseguente regola
	private String startConsequentString;
	
	//dispositivi coinvolti nella regola
	private ArrayList<String> involvedSensorsInRule;
	private String involvedActuatorInRule;


	private boolean state;
	private boolean hasStartConsequent;//contiene la key word "start"
	
	private Map<String, Operator> numericOperatingModeMap = new HashMap<String, Operator>();
	private Map<String, StringOperator> nonNumericOperatingModeMap = new HashMap<String, StringOperator>();
	
	/*
	 * invariante name != null, antString !=null, consequentRuleString != null, numericOperatingModeMap != null, nonNumericOperatingModeMap != null
	 */
	public Rule(HousingUnit housingUnit, String name, String antecedentRuleString, String consequentRuleString, ArrayList<String> involvedSensorsInRule, String involvedActuatorInRule, boolean ruleState) {
		this.name = name;
		this.antecedentRuleString = antecedentRuleString;
		this.consequentRuleString = consequentRuleString;
		this.state = ruleState;
		this.housingUnit = housingUnit;
		this.involvedSensorsInRule = involvedSensorsInRule;
		this.involvedActuatorInRule = involvedActuatorInRule;
		this.hasStartConsequent = false;
		fillMap();
	}
	
	/*
	 * Metodi pubblici
	 */
	public void checkRule() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		if(state && getAntecedent()) actuateConsequent();
	}
	
	public void setState(boolean state) {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		this.state = state;
	}
	
	public boolean isActive() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		return this.state;
	}
	
	public String getName() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		return this.name;
	}
	
	public String getCompleteRule() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		String result;
		if(hasStartConsequent) {
			result = "[if]   " + antecedentRuleString + "   \n\t\t\t[then]   " + consequentRuleString +", "+ startConsequentString;
		}else result = "[if]   " + antecedentRuleString + "   \n\t\t\t[then]   " + consequentRuleString;
		
		return result;
	}
	
	public void actuateConsequent() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		consequentMode(this.consequentRuleString);
	}
	
	public String getAntecedentString() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		return this.antecedentRuleString;
	}
	
	public String getConsequentString() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		return this.consequentRuleString;
	}
	
	public String [] getInvolvedSensors() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		String [] result = new String[involvedSensorsInRule.size()];
		
		for(int i = 0; i < involvedSensorsInRule.size(); i++) {
			result[i] = involvedSensorsInRule.get(i);
		}
		
		assert result != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		return result; 
	}
	
	public String getInvolvedActuator() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		return this.involvedActuatorInRule;
	}
	
	/*
	 * Metodi privati
	 */
	private boolean getAntecedent() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		return getAntecedentValue(this.antecedentRuleString);
	}
	

	
	//i1_igrometro.umiditaRelativa > 30
	//antecedente è costituito da condizioni
	private boolean getAntecedentValue(String antString) {
		assert antString != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		boolean res;
		
		if(antString.contains("&&") || antString.contains("||")) {
			res = getConditionBooleanValue(antString.split("[\\&\\&|\\|\\|]")[0]);
		antString = antString.replace(antString.split("[\\&\\&|\\|\\|]")[0], "");
		if(antString.startsWith("&&")) { 
			antString = antString.replace("&&", "");
			return res && getAntecedentValue(antString);
		}else {
			antString = antString.replace("||", "");
			return res || getAntecedentValue(antString);
			}
		}else {
			return getConditionBooleanValue(antString);
		}
	}
	
	//i1_igrometro.umiditaRelativa > 30 
	//v1_videocamera.presenza == "presenza di persone"
	//time < 6.00
	private boolean getConditionBooleanValue(String condition) {
		assert condition != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		if(!condition.contains("time")) {
		if(housingUnit.getSensor(condition.split("\\.")[0]).isNumeric()) {		
			String value1;
			String op = "";
			String value2;
		
			value1 = condition.split("<|>|>=|<=|==|!=")[0];
			double num1 = getValue(value1);
		
			value2 = condition.split("<|>|>=|<=|==|!=")[1];
			double num2 = getValue(value2);
		
			if(condition.contains(">=")) op = ">=";
			else if(condition.contains("<=")) op = "<=";
			else if(condition.contains(">")) op = ">";
			else if(condition.contains("<")) op = "<";
			else if(condition.contains("==")) op = "==";
			else if(condition.contains("!=")) op = "!=";
			
			return numericOperatingModeMap.get(op).compare(num1, num2);
		}else {
			//v1_videocamera.presenza == "presenza di persone"
			String sensor;
			String info;
			String op ="";
			String value;
			
			sensor = condition.split("\\.")[0];
			condition = condition.split("\\.")[1]; //presenza == presenza di persone
			info = condition.split("==|!=")[0];
			
			if(condition.contains("==")) {
				op = "==";
				value = condition.split("==")[1];
			}
			else {
				op = "!=";
				value = condition.split("!=")[1];
			}
			
			String temp = housingUnit.getNonNumericSensorValue(sensor, info);
			
			return nonNumericOperatingModeMap.get(op).compare(temp, value);
			}
		}else {
			double currentTime = Double.parseDouble(Clock.getCurrentTime());
			double value = Double.parseDouble(condition.split("<|>|>=|<=|==|!=")[1]);
			
			String op = "";
			if(condition.contains(">=")) op = ">=";
			else if(condition.contains("<=")) op = "<=";
			else if(condition.contains(">")) op = ">";
			else if(condition.contains("<")) op = "<";
			else if(condition.contains("==")) op = "==";
			else if(condition.contains("!=")) op = "!=";
		
			return numericOperatingModeMap.get(op).compare(currentTime, value);
		}
	}
	
	//i1_igrometro.umiditàRelativa ,  30
	private double getValue(String toElaborate) {
		assert toElaborate != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		String sensor;
		String info;
		if(toElaborate.matches("^[-+]?\\d+(\\.{0,1}(\\d+?))?$")) {    //deve prendere Double non integer
			return Double.parseDouble(toElaborate);
		}else {	
			sensor = toElaborate.split("\\.")[0];
			info = toElaborate.split("\\.")[1];	
			return housingUnit.getSensorValue(sensor, info);			
		}
	}
	
	/*
	 * 	b1_attCancelloBattente := apertura
	 *	b1 -> nome attuatore
	 *	attCancelloBattente -> categoria
	 *	apertura -> mod operativa da assegnare
	 *
	 *  I requisiti non specificano l'elaborazione di due o più conseguenti, dunque per ora ne assumiamo uno
	 */
	
	//b1_attCancelloBattente := apertura, start := 10
	private void consequentMode(String toElaborate) {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		if(toElaborate.contains("start")) {
			this.consequentRuleString = toElaborate.split(",")[0];
			this.hasStartConsequent = true;
			toElaborate = toElaborate.split(",")[1];
			startConsequentString = toElaborate;
			double time = Double.parseDouble(toElaborate.split(":=")[1]);
			housingUnit.addQueuedRule(this, time);
		}
		else consequentElaboration(toElaborate);
		
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
	}
	
	private void consequentElaboration(String toElaborate) {
		assert toElaborate != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		toElaborate.replace(" ", ""); //elimino gli spazi
		String actuator;
		String category;
		String operatingMode;
		String parameter;
		double parameterValue;
		System.out.println(toElaborate);
		actuator = toElaborate.split("_")[0];
		toElaborate = toElaborate.split("_")[1];
		category = toElaborate.split(":=")[0];
		toElaborate = toElaborate.split(":=")[1]; 
		operatingMode = toElaborate; //da verificare che sia parametrica o no
		
		if(housingUnit.containsQueuedRule(this)){
			//se l'attuatore su cui stiamo lavorando viene azionato e era presente nella coda di attuatori
			//da azionare in futuro, allora viene rimosso
			housingUnit.removeQueuedRule(this);
		}
		
		ArrayList<Double> paramDouble = new ArrayList<>(); //array double da passare per azionare op Mod
		ArrayList<String> paramString = new ArrayList<>(); //array string da passare per azionare op Mod
		
		//si assume che una modOp parametrica sia nella forma "mantenimentoTemperatura(param)"
		if(operatingMode.contains("(")) { //se paramatrica
			operatingMode = toElaborate.split("\\(")[0]; //modOp = mantenimentoTemperatura
			toElaborate = toElaborate.split("\\(")[1]; //toElaborate = param)
			parameter = toElaborate.split("\\)")[0]; //toElaborate = param
			
			if(!parameter.contains(",")) { //se è mono parametrica
				//param può essere una stringa oppure un double
				if(parameter.matches("^[-+]?\\d+(\\.{0,1}(\\d+?))?$")) { //se fa match con double regex allora è double
				parameterValue = Double.parseDouble(parameter);
				paramDouble.add(parameterValue);
				housingUnit.getActuator(actuator + "_" + category).setParametricOperatingMode(operatingMode, paramDouble);
				}else {//altrimenti è una stringa e possiamo tenere param	
				paramString.add(parameter);
				housingUnit.getActuator(actuator + "_" + category).setParametricOperatingMode(operatingMode, paramString);
				}
			}else {
				if(parameter.split(",")[0].matches("^[-+]?\\d+(\\.{0,1}(\\d+?))?$")) { //se sono parametri Double
					do { //12,31,42
					paramDouble.add(Double.parseDouble(parameter.split(",")[0]));
					parameter = parameter.split(",")[1];
					
					}while(parameter.contains(","));
					
					paramDouble.add(Double.parseDouble(parameter)); //aggiungo ultimo valore che rimarrebe fuori
					
					housingUnit.getActuator(actuator + "_" + category).setParametricOperatingMode(operatingMode, paramDouble);
					
				}else {//sono parametri String
					do {
					paramString.add(parameter.split(",")[0]);
					parameter = parameter.split(",")[1];
					
					}while(parameter.contains(","));
					
					paramString.add(parameter); //aggiungo ultimo valore che rimarrebe fuori
					
					housingUnit.getActuator(actuator + "_" + category).setParametricOperatingMode(operatingMode, paramString);
					
				}
			}
		}else {//se non è parametrica
			housingUnit.getActuator(actuator + "_" + category).setNonParametricOperatingMode(operatingMode);	
		}	
	}

	private void fillMap() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		numericOperatingModeMap.put(">", new Operator() {
			private static final long serialVersionUID = -5250726407104602586L;
			@Override public boolean compare(double a, double b) {
                return a > b;
            }
        });
        numericOperatingModeMap.put("<", new Operator() {
			private static final long serialVersionUID = 9045164402111710193L;
			@Override public boolean compare(double a, double b) {
                return a < b;
            }
        });
        numericOperatingModeMap.put("==", new Operator() {
			private static final long serialVersionUID = -3971340992796446174L;
			@Override public boolean compare(double a, double b) {
                return a == b;
            }
        });
        numericOperatingModeMap.put(">=", new Operator() {
        	private static final long serialVersionUID = -5488945513794521410L;
			@Override public boolean compare(double a, double b) {
                return a >= b;
            }
        });
        numericOperatingModeMap.put("<=", new Operator() {
			private static final long serialVersionUID = 6812440109872863492L;
			@Override public boolean compare(double a, double b) {
                return a <= b;
            }
        });
        numericOperatingModeMap.put("!=", new Operator() {
			private static final long serialVersionUID = -3807829785337361890L;
			@Override public boolean compare(double a, double b) {
                return a != b;
            }
        });
        nonNumericOperatingModeMap.put("==", new StringOperator() {
			private static final long serialVersionUID = -2287384876050549224L;
			@Override public boolean compare(String a, String b) {
                return a.equalsIgnoreCase(b);
            }
        });
        nonNumericOperatingModeMap.put("!=", new StringOperator() {
			private static final long serialVersionUID = 5161859393054128838L;
			@Override public boolean compare(String a, String b) {
                return !a.equalsIgnoreCase(b);
            }
        });
        
        
        assert numericOperatingModeMap.size() > 0 && nonNumericOperatingModeMap.size() > 0;
        assert ruleInvariant() : "Invariante della classe non soddisfatto";
	}
	
	private boolean ruleInvariant() {
		boolean checkName = name != null;
		boolean checkAntString = antecedentRuleString != null;
		boolean checkConsString = consequentRuleString != null;
		boolean checkMaps = numericOperatingModeMap != null && nonNumericOperatingModeMap != null;
		
		if(checkName && checkAntString && checkConsString && checkMaps) return true;
		return false;
	}

}
