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
	private String antString;
	private String consString;


	private boolean state;
	
	private Map<String, Operator> numericOpMap = new HashMap<String, Operator>();
	private Map<String, StringOperator> nonNumericOpMap = new HashMap<String, StringOperator>();
	
	/*
	 * invariante name != null, antString !=null, consString != null, numericOpMap != null, nonNumericOpMap != null
	 */
	public Rule(HousingUnit housingUnit, String name, String antString, String consString, boolean state) {
		this.name = name;
		this.antString = antString;
		this.consString = consString;
		this.state = state;
		this.housingUnit = housingUnit;
		fillMap();
	}
	
	/*
	 * Metodi pubblici
	 */
	public void checkRule() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		if(state && getAntecedente()) actuateConseguente();
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
		return "[if]   " + antString + "   \n\t\t\t[then]   " + consString;
	}
	
	/*
	 * Metodi privati
	 */
	private boolean getAntecedente() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		return getAntValue(this.antString);
	}
	
	private void actuateConseguente() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		consElaboration(this.consString);
	}
	
	//i1_igrometro.umiditaRelativa > 30 
	private boolean getAntValue(String antString) {
		assert antString != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		boolean res;
		
		if(antString.contains("&&") || antString.contains("||")) {
			res = getCostValue(antString.split("[\\&\\&|\\|\\|]")[0]);
		antString = antString.replace(antString.split("[\\&\\&|\\|\\|]")[0], "");
		if(antString.startsWith("&&")) { 
			antString = antString.replace("&&", "");
			return res && getAntValue(antString);
		}else {
			antString = antString.replace("||", "");
			return res || getAntValue(antString);
			}
		}else {
			return getCostValue(antString);
		}
	}
	
	//i1_igrometro.umiditaRelativa > 30 
	//v1_videocamera.presenza == "presenza di persone"
	private boolean getCostValue(String s) {
		assert s != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		if(housingUnit.getSensor(s.split("\\.")[0]).isNumeric()) {		
			String value1;
			String info;
			String op = "";
			String value2;
		
			value1 = s.split("<|>|>=|<=|==|!=")[0];
			double num1 = getValue(value1);
		
			value2 = s.split("<|>|>=|<=|==|!=")[1];
			double num2 = getValue(value2);
		
			if(s.contains(">=")) op = ">=";
			else if(s.contains("<=")) op = "<=";
			else if(s.contains(">")) op = ">";
			else if(s.contains("<")) op = "<";
			else if(s.contains("==")) op = "==";
			else if(s.contains("!=")) op = "!=";
			
			return numericOpMap.get(op).compare(num1, num2);
		}else {
			//v1_videocamera.presenza == "presenza di persone"
			String sensor;
			String info;
			String op ="";
			String value;
			
			sensor = s.split("\\.")[0];
			s = s.split("\\.")[1]; //presenza == presenza di persone
			info = s.split("==|!=")[0];
			
			if(s.contains("==")) {
				op = "==";
				value = s.split("==")[1];
			}
			else {
				op = "!=";
				value = s.split("!=")[1];
			}
			
			String temp = housingUnit.getNonNumericSensorValue(sensor, info);
			
			return nonNumericOpMap.get(op).compare(temp, value);
		}
	}
	
	//i1_igrometro.umiditàRelativa ,  30
	private double getValue(String toElaborate) {
		assert toElaborate != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		String sensor;
		String info;
		if(toElaborate.matches("^[-+]?\\d+(\\.{0,1}(\\d+?))?$")) {      //deve prendere Double non integer
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
	private void consElaboration(String toElaborate) {
		assert toElaborate != null;
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		toElaborate.replace(" ", ""); //elimino gli spazi
		String act;
		String cat;
		String modOp;
		String param;
		double paramValue;
		
		act = toElaborate.split("_")[0];
		toElaborate = toElaborate.split("_")[1];
		cat = toElaborate.split(":=")[0];
		toElaborate = toElaborate.split(":=")[1]; 
		modOp = toElaborate; //da verificare che sia parametrica o no

		ArrayList<Double> paramDouble = new ArrayList<>(); //array double da passare per azionare op Mod
		ArrayList<String> paramString = new ArrayList<>(); //array string da passare per azionare op Mod
		
		//si assume che una modOp parametrica sia nella forma "mantenimentoTemperatura(param)"
		if(modOp.contains("(")) { //se paramatrica
			modOp = toElaborate.split("\\(")[0]; //modOp = mantenimentoTemperatura
			toElaborate = toElaborate.split("\\(")[1]; //toElaborate = param)
			param = toElaborate.split("\\)")[0]; //toElaborate = param
			
			if(!param.contains(",")) { //se è mono parametrica
				//param può essere una stringa oppure un double
				if(param.matches("^[-+]?\\d+(\\.{0,1}(\\d+?))?$")) { //se fa match con double regex allora è double
				paramValue = Double.parseDouble(param);
				paramDouble.add(paramValue);
				housingUnit.getActuator(act + "_" + cat).setParametricOperatingMode(modOp, paramDouble);
				}else {//altrimenti è una stringa e possiamo tenere param	
				paramString.add(param);
				housingUnit.getActuator(act + "_" + cat).setParametricOperatingMode(modOp, paramString);
				}
			}else {
				if(param.split(",")[0].matches("^[-+]?\\d+(\\.{0,1}(\\d+?))?$")) { //se sono parametri Double
					do { //12,31,42
					paramDouble.add(Double.parseDouble(param.split(",")[0]));
					param = param.split(",")[1];
					
					}while(param.contains(","));
					
					paramDouble.add(Double.parseDouble(param)); //aggiungo ultimo valore che rimarrebe fuori
					
					housingUnit.getActuator(act + "_" + cat).setParametricOperatingMode(modOp, paramDouble);
					
				}else {//sono parametri String
					do {
					paramString.add(param.split(",")[0]);
					param = param.split(",")[1];
					
					}while(param.contains(","));
					
					paramString.add(param); //aggiungo ultimo valore che rimarrebe fuori
					
					housingUnit.getActuator(act + "_" + cat).setParametricOperatingMode(modOp, paramString);
					
				}
			}
		}else {//se non è parametrica
			housingUnit.getActuator(act + "_" + cat).setNonParametricOperatingMode(modOp);	
		}	
	}

	private void fillMap() {
		assert ruleInvariant() : "Invariante della classe non soddisfatto";
		
		numericOpMap.put(">", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a > b;
            }
        });
        numericOpMap.put("<", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a < b;
            }
        });
        numericOpMap.put("==", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a == b;
            }
        });
        numericOpMap.put(">=", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a >= b;
            }
        });
        numericOpMap.put("<=", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a <= b;
            }
        });
        numericOpMap.put("!=", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a != b;
            }
        });
        nonNumericOpMap.put("==", new StringOperator() {
            @Override public boolean compare(String a, String b) {
                return a.equalsIgnoreCase(b);
            }
        });
        nonNumericOpMap.put("!=", new StringOperator() {
            @Override public boolean compare(String a, String b) {
                return !a.equalsIgnoreCase(b);
            }
        });
        
        
        assert numericOpMap.size() > 0 && nonNumericOpMap.size() > 0;
        assert ruleInvariant() : "Invariante della classe non soddisfatto";
	}
	
	private boolean ruleInvariant() {
		boolean checkName = name != null;
		boolean checkAntString = antString != null;
		boolean checkConsString = consString != null;
		boolean checkMaps = numericOpMap != null && nonNumericOpMap != null;
		
		if(checkName && checkAntString && checkConsString && checkMaps) return true;
		return false;
	}

}
