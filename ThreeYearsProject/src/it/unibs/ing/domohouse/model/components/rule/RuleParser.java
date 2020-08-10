package it.unibs.ing.domohouse.model.components.rule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import it.unibs.ing.domohouse.model.ModelStrings;
import it.unibs.ing.domohouse.model.components.elements.Actuator;
import it.unibs.ing.domohouse.model.components.elements.Sensor;

public class RuleParser implements Serializable {

	private static final long serialVersionUID = -3437535853622849347L;
	private static final String DRELOP_REGEX = ">|<|<=|>=|!=";
	private static final String SRELOP_REGEX = "==";
	private static final String DOUBLE_REGEX = "^[-+]?\\d+(\\.{0,1}(\\d+?))?$";
	private static final String STRING_REGEX = "^(?=\\s*\\S).*$";
	private static final String SENS_VALUE_REGEX = "^[^_]+_[^_]+\\.[^_]+$";
	private static final String EOL = "";
	
	private Map<String, Operator> doubleOperatorsMap = new HashMap<>();
	private StringOperator strOperator = new StringOperator() {

		private static final long serialVersionUID = 2506064165581591427L;

		@Override
		public boolean compare(String a, String b) {
			return a.equalsIgnoreCase(b);
		}

		@Override
		public String toString() {
			return "==";
		}
	};

	private String lookahead;
	private Stack<String> tokenStack;

	public RuleParser() {
		fillMap();
		this.tokenStack = new Stack<>();
	}

	private void setStringToParse(String toParse) {
		String[] tokenArray = toParse.split(" ");
		for (int i = tokenArray.length - 1; i >= 0; i--)
			tokenStack.push(tokenArray[i]);
		lookahead = tokenStack.pop();
	}

	private void advance() {
		try {
			lookahead = tokenStack.pop();
		}
		catch (EmptyStackException e) {
			lookahead = EOL;
		}
	}

	private void matchKeyword(String keyword) throws Exception {
		if (lookahead.equals(keyword))
			advance();
		else
			throw new Exception("Error expected " + keyword + ", got " + lookahead);
	}

	private void matchRegex(String regex) throws Exception {
		if (lookahead.matches(regex))
			advance();
		else
			throw new Exception("Error " + lookahead + " does not match regex: " + regex);
	}

	public AntecedentNode parseAnt(String antString, Set<Sensor> involvedSensors) throws Exception {
		setStringToParse(antString);
		AntecedentNode tree = null;
		AntecedentNode left;
		if (lookahead.equals("true")) {
			tree = new AntecedentNode() {

				private static final long serialVersionUID = 4301569602512457678L;

				@Override
				public boolean getConditionValue(String time) {
					return true;
				}

				@Override
				public void setLeftNode(AntecedentNode left) throws Exception {
					throw new Exception("Unexpected operation");
				}

				@Override
				public void setRightNode(AntecedentNode right) throws Exception {
					throw new Exception("Unexpected operation");
				}
			};
		}
		else if (lookahead.equals("time")) {
			left = time();
			if (lookahead.equals("&&") || lookahead.equals("||")) {
				tree = logop();
				advance(); // legge il nuovo carattere di lookahead
				tree.setLeftNode(left);
				tree.setRightNode(logicNode(involvedSensors));
			}
			else if (lookahead.equals("")) {
				tree = left;
			}
			else
				throw new Exception("Error unexpected token");
		}
		else if (lookahead.matches(SENS_VALUE_REGEX)) { // lookahead == sensore_categoria.informazione
			tree = logicNode(involvedSensors);
		}
		return tree;
	}

	private AntecedentNode time() throws Exception {
		matchKeyword("time");
		String operator = lookahead;
		matchRegex(DRELOP_REGEX);
		String time = lookahead;
		matchRegex(DOUBLE_REGEX);
		return new TemporalNode(Double.parseDouble(time), doubleOperatorsMap.get(operator));
	}

	private AntecedentNode logop() throws Exception {
		if (lookahead.equals("&&"))
			return new AndNode();
		else if (lookahead.equals("||"))
			return new OrNode();
		else
			throw new Exception("Error unexpected token");
	}

	private AntecedentNode logicNode(Set<Sensor> involvedSensors) throws Exception {
		AntecedentNode left;
		AntecedentNode logicNode;
		AntecedentNode right;
		left = condNode(involvedSensors);
		if (lookahead.equals("&&") || lookahead.equals("||")) {
			logicNode = logop();
			advance();
			logicNode.setLeftNode(left);
			right = logicNode(involvedSensors);
			logicNode.setRightNode(right);
			return logicNode;
		}
		else if (lookahead.equals(""))
			return left;
		else
			throw new Exception("Error unexpected token");
	}

	private AntecedentNode condNode(Set<Sensor> involvedSensors) throws Exception {
		String sensorValue = lookahead;
		matchRegex(SENS_VALUE_REGEX);
		Sensor sens = getSensorFromList(sensorValue.split("\\.")[ModelStrings.FIRST_TOKEN], involvedSensors);
		String info = sensorValue.split("\\.")[ModelStrings.SECOND_TOKEN];
		String operator = lookahead;
		if (lookahead.matches(SRELOP_REGEX)) {
			advance();
			String secondOperand = lookahead;
			if (lookahead.matches(STRING_REGEX)) {
				advance();
				boolean remain = true;
				do {
					if (lookahead.equals("&&") || lookahead.equals("||") || lookahead.equals(EOL))
						remain = false;
					else {
						secondOperand = secondOperand + ModelStrings.SPACE + lookahead;
						advance();
					}
				}
				while (remain);
				return new StringOneSensorNode(sens, info, secondOperand, strOperator); // strOperator = operatore '=='
			}
			else if (lookahead.matches(SENS_VALUE_REGEX)) {
				advance();
				return new StringTwoSensorsNode(sens, info,
						getSensorFromList(secondOperand.split("\\.")[ModelStrings.FIRST_TOKEN], involvedSensors),
						secondOperand.split("\\.")[ModelStrings.SECOND_TOKEN], strOperator);
			}
			else if (lookahead.matches(DOUBLE_REGEX)) {
				advance();
				return new DoubleOneSensorNode(sens, info, Double.parseDouble(secondOperand),
						doubleOperatorsMap.get(operator));
			}
			else if (lookahead.matches(SENS_VALUE_REGEX)) {
				advance();
				return new DoubleTwoSensorsNode(sens, info,
						getSensorFromList(secondOperand.split("\\.")[ModelStrings.FIRST_TOKEN], involvedSensors),
						secondOperand.split("\\.")[ModelStrings.SECOND_TOKEN], doubleOperatorsMap.get(operator));
			}
			else
				throw new Exception("Error unexpected token");
		}
		else if (lookahead.matches(DRELOP_REGEX)) {
			advance();
			String secondOperand = lookahead;
			if (lookahead.matches(DOUBLE_REGEX)) {
				advance();
				return new DoubleOneSensorNode(sens, info, Double.parseDouble(secondOperand),
						doubleOperatorsMap.get(operator));
			}
			else if (lookahead.matches(SENS_VALUE_REGEX)) {
				advance();
				return new DoubleTwoSensorsNode(sens, info,
						getSensorFromList(secondOperand.split("\\.")[ModelStrings.FIRST_TOKEN], involvedSensors),
						secondOperand.split("\\.")[ModelStrings.SECOND_TOKEN], doubleOperatorsMap.get(operator));
			}
			else
				throw new Exception("Error unexpected token");
		}
		else
			throw new Exception("Error unexpected token");
	}

	public double getTime(String consString) {
		String start = consString.split(", ")[ModelStrings.FIRST_TOKEN];
		return Double.parseDouble(start.split(":=")[ModelStrings.SECOND_TOKEN]);
	}

	public Action parseCons(String consString, Set<Actuator> involvedActuators) {
		String[] tokenArray = consString.split(", ");
		int start = consString.contains("start") ? ModelStrings.SECOND_TOKEN : ModelStrings.FIRST_TOKEN;

		Action firstAction = parseAction(tokenArray[start], involvedActuators);
		Action previous = firstAction;
		for (int i = start + 1; i < tokenArray.length; i++) {
			Action next = parseAction(tokenArray[i], involvedActuators);
			previous.setNextAction(next);
			previous = next;
		}
		return firstAction;
	}

	private Action parseAction(String actionString, Set<Actuator> involvedActuators) {
		String[] tokens = actionString.split(" := ");
		Actuator act = getActuatorFromList(tokens[ModelStrings.FIRST_TOKEN], involvedActuators);
		String modop;
		if (tokens[ModelStrings.SECOND_TOKEN].contains("(")) {
			modop = tokens[ModelStrings.SECOND_TOKEN].split("\\(")[ModelStrings.FIRST_TOKEN];
			String params = tokens[ModelStrings.SECOND_TOKEN].split("\\(|\\)")[ModelStrings.SECOND_TOKEN];
			return new Action(act, modop, new ArrayList<String>(Arrays.asList(params)));
		}
		else
			return new Action(act, tokens[ModelStrings.SECOND_TOKEN], new ArrayList<String>());
	}

	private Sensor getSensorFromList(String sensorName, Set<Sensor> sensors) {
		for(Sensor sensor : sensors) {
			if(sensor.getName().equalsIgnoreCase(sensorName)) {
				return sensor;
			}
		}
		return null;
	}
	
	private Actuator getActuatorFromList(String actuatorName, Set<Actuator> actuators) {
		for(Actuator sensor : actuators) {
			if(sensor.getName().equalsIgnoreCase(actuatorName)) {
				return sensor;
			}
		}
		return null;
	}
	
	private void fillMap() {
		doubleOperatorsMap.put(">", new Operator() {

			private static final long serialVersionUID = 9067942704960379655L;

			@Override
			public boolean compare(double a, double b) {
				return a > b;
			}

			@Override
			public String toString() {
				return ">";
			}
		});
		doubleOperatorsMap.put("<", new Operator() {

			private static final long serialVersionUID = 6159795260740347252L;

			@Override
			public boolean compare(double a, double b) {
				return a < b;
			}

			@Override
			public String toString() {
				return "<";
			}
		});
		doubleOperatorsMap.put("==", new Operator() {

			private static final long serialVersionUID = -5084554106505110737L;

			@Override
			public boolean compare(double a, double b) {
				return a == b;
			}

			@Override
			public String toString() {
				return "==";
			}
		});
		doubleOperatorsMap.put(">=", new Operator() {

			private static final long serialVersionUID = 20334773074397736L;

			@Override
			public boolean compare(double a, double b) {
				return a >= b;
			}

			@Override
			public String toString() {
				return ">=";
			}
		});
		doubleOperatorsMap.put("<=", new Operator() {

			private static final long serialVersionUID = 6842298864404739131L;

			@Override
			public boolean compare(double a, double b) {
				return a <= b;
			}

			@Override
			public String toString() {
				return "<=";
			}
		});
		doubleOperatorsMap.put("!=", new Operator() {

			private static final long serialVersionUID = -6787163850193407555L;

			@Override
			public boolean compare(double a, double b) {
				return a != b;
			}

			@Override
			public String toString() {
				return "!=";
			}
		});

	}
}
