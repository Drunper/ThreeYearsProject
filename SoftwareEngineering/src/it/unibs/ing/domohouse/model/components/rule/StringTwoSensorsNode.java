package it.unibs.ing.domohouse.model.components.rule;

import it.unibs.ing.domohouse.model.components.elements.Sensor;

public class StringTwoSensorsNode implements AntecedentNode {

	private static final long serialVersionUID = -8580715572742901086L;
	private Sensor sens1;
	private Sensor sens2;
	private String info1;
	private String info2;
	private StringOperator relop;

	public StringTwoSensorsNode(Sensor sens1, String info1, Sensor sens2, String info2, StringOperator relop) {
		super();
		this.sens1 = sens1;
		this.sens2 = sens2;
		this.info1 = info1;
		this.info2 = info2;
		this.relop = relop;
	}

	@Override
	public boolean getConditionValue(String time) {
		return relop.compare(sens1.getMeasurementOf(info1), sens2.getMeasurementOf(info2));
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
