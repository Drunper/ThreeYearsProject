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
	private boolean antecedente;
	private String antString;

	private ArrayList<String> actList;
	private String consString;
	private ArrayList<String> modOp;

	private boolean state;
	
	Map<String, Operator> opMap = new HashMap<String, Operator>();
	Map<String, booleanOperator> booleanOpMap = new HashMap<String, booleanOperator>();
	
	public Rule(HousingUnit housingUnit, String name, String antString, String consString, boolean state) {
		this.name = name;
		this.antString = antString;
		this.consString = consString;
		this.state = state;
		this.housingUnit = housingUnit;
		fillMap();
		//metodo che costruisce gli arrayList
	}
	
	
	public boolean getAntecedente() {
		return getAntValue(this.antString);
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
			return res && getAntValue(antString);
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
		
		if(s.contains(">")) op = ">";
		else if(s.contains("<")) op = "<";
		else if(s.contains(">=")) op = ">=";
		else if(s.contains("<=")) op = "<=";
		else if(s.contains("==")) op = "==";
		else if(s.contains("!=")) op = "!=";
		
		return opMap.get(op).compare( num1, num2);
	}
	
	
	private void consElaboration(String toElaborate) {
		
	}
	
	
	//i1_igrometro.umiditàRelativa ,  30
	private double getValue(String toElaborate) {
		String sensor;
		String info;
		if(toElaborate.matches("[0-9]")) {
			return Double.parseDouble(toElaborate);
		}else {	
			sensor = toElaborate.split(".")[0];
			info = toElaborate.split(".")[1];	
			return housingUnit.getSensorValue(sensor, info);
					
		}
		
	}
	//checkAntecedente
	
	//aziona modOp

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
        booleanOpMap.put("&&", new booleanOperator() {
            @Override public boolean compare(boolean a, boolean b) {
                return a && b;
            }
        });
        booleanOpMap.put("||", new booleanOperator() {
            @Override public boolean compare(boolean a, boolean b) {
                return a || b;
            }
        });
	}

}
