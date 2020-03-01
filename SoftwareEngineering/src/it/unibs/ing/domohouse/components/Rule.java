package it.unibs.ing.domohouse.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unibs.ing.domohouse.util.DataHandler;

interface Operator{
	boolean compare(double a, double b);
}

interface booleanOperator{
	boolean compare(boolean a, boolean b);
}


public class Rule {
	
	private HousingUnit housingUnit;
	private String name;
	private String antString;
	private String consString;


	private boolean state;
	
	private Map<String, Operator> opMap = new HashMap<String, Operator>();
	//Map<String, booleanOperator> booleanOpMap = new HashMap<String, booleanOperator>();
	
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
		if(getAntecedente()) actuateConseguente();
	}
	
	public void setState(boolean state) {
		this.state = state;
	}
	
	public boolean isActive() {
		return this.state;
	}
	
	/*
	 * Metodi privati
	 */
	private boolean getAntecedente() {
		return getAntValue(this.antString);
	}
	
	private void actuateConseguente() {
		consElaboration(this.consString);
	}
	
	private boolean getAntValue(String antString) {
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
	
	private boolean getCostValue(String s) {
		String value1;
		String info;
		String op = "";
		String value2;
		
		value1 = s.split(".")[0];
		double num1 = getValue(value1);
		
		value2 = s.split("<|>|>=|<=|==|!=")[1];
		double num2 = getValue(value2);
		
		if(s.contains(">=")) op = ">=";
		else if(s.contains("<=")) op = "<=";
		else if(s.contains(">")) op = ">";
		else if(s.contains("<")) op = "<";
		else if(s.contains("==")) op = "==";
		else if(s.contains("!=")) op = "!=";

		
		return opMap.get(op).compare( num1, num2);
	}
	
	//i1_igrometro.umiditàRelativa ,  30
	private double getValue(String toElaborate) {
		String sensor;
		String info;
		if(toElaborate.matches("^[-+]?\\\\d+(\\\\.{0,1}(\\\\d+?))?$")) {      //deve prendere Double non integer
			return Double.parseDouble(toElaborate);
		}else {	
			sensor = toElaborate.split(".")[0];
			info = toElaborate.split(".")[1];	
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
		
		//si assume che una modOp parametrica sia nella forma "mantenimentoTemperatura(param)"
		if(modOp.contains("(")) { //se paramatrica
			modOp = toElaborate.split("(")[0]; //modOp = mantenimentoTemperatura
			toElaborate = toElaborate.split("(")[1]; //toElaborate = param)
			param = toElaborate.split(")")[0]; //toElaborate = param
			
			//param può essere una stringa oppure un double
			if(param.matches("^[-+]?\\\\d+(\\\\.{0,1}(\\\\d+?))?$")) { //se fa match con double regex allora è double
				paramValue = Double.parseDouble(param);
				housingUnit.getActuator(act).setParametricOperatingMode(modOp, paramValue);
			}else {//altrimenti è una stringa e possiamo tenere param	
				housingUnit.getActuator(act).setParametricOperatingMode(modOp, param);
			}
		}else {//se non è parametrica
			housingUnit.getActuator(act).setNonParametricOperatingMode(modOp);	
		}	
	}

	private void fillMap() {
		opMap.put(">", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a > b;
            }
        });
        opMap.put("<", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a < b;
            }
        });
        opMap.put("==", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a == b;
            }
        });
        opMap.put(">=", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a >= b;
            }
        });
        opMap.put("<=", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a <= b;
            }
        });
        opMap.put("!=", new Operator() {
            @Override public boolean compare(double a, double b) {
                return a != b;
            }
        });
 
	}

}
