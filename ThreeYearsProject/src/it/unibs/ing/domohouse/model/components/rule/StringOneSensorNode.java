package it.unibs.ing.domohouse.model.components.rule;

import it.unibs.ing.domohouse.model.components.elements.Sensor;

public class StringOneSensorNode implements AntecedentNode {

	private Sensor sens;
	private String info;
	private String value;
	private StringOperator relop;

	public StringOneSensorNode(Sensor sens, String info, String value, StringOperator relop) {
		this.sens = sens;
		this.info = info;
		this.value = value;
		this.relop = relop;
	}

	@Override
	public boolean getConditionValue(String time) {
		return relop.compare(sens.getMeasurementOf(info), value);
	}

	public String toString() {
		return sens.getName() + "." + info + " " + relop.toString() + " " + value;
	}

	@Override
	public void setLeftNode(AntecedentNode left) throws Exception {
		throw new Exception("Unexpected operation");
	}

	@Override
	public void setRightNode(AntecedentNode right) throws Exception {
		throw new Exception("Unexpected operation");
	}

}
