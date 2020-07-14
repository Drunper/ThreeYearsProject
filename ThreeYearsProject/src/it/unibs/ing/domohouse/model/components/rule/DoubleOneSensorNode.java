package it.unibs.ing.domohouse.model.components.rule;

import it.unibs.ing.domohouse.model.components.elements.Sensor;

public class DoubleOneSensorNode implements AntecedentNode {

	private static final long serialVersionUID = -6057436113897731711L;
	private Sensor sens;
	private String info;
	private double value;
	private Operator relop;

	public DoubleOneSensorNode(Sensor sens, String info, double value, Operator relop) {
		this.sens = sens;
		this.info = info;
		this.value = value;
		this.relop = relop;
	}

	@Override
	public boolean getConditionValue(String time) {
		return relop.compare(Double.parseDouble(sens.getMeasurementOf(info)), value);
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
