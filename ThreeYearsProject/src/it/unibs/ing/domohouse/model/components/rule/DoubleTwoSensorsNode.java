package it.unibs.ing.domohouse.model.components.rule;

import it.unibs.ing.domohouse.model.components.elements.Sensor;

public class DoubleTwoSensorsNode implements AntecedentNode {

	private static final long serialVersionUID = -6806961283278044099L;
	private Sensor sens1;
	private Sensor sens2;
	private String info1;
	private String info2;
	private Operator relop;

	public DoubleTwoSensorsNode(Sensor sens1, String info1, Sensor sens2, String info2, Operator relop) {
		this.sens1 = sens1;
		this.sens2 = sens2;
		this.relop = relop;
		this.info1 = info1;
		this.info2 = info2;
	}

	@Override
	public boolean getConditionValue(String time) {
		return relop.compare(Double.parseDouble(sens1.getMeasurementOf(info1)),
				Double.parseDouble(sens2.getMeasurementOf(info2)));
	}

	public String toString() {
		return sens1.getName() + "." + info1 + " " + relop.toString() + " " + sens2.getName() + "." + info2;
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
