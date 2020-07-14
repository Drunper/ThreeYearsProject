package it.unibs.ing.domohouse.model.components.rule;

public class TemporalNode implements AntecedentNode {

	private static final long serialVersionUID = -654991166853103438L;
	private double time;
	private Operator relop;

	public TemporalNode(double time, Operator relop) {
		this.time = time;
		this.relop = relop;
	}

	@Override
	public boolean getConditionValue(String actualTime) {
		return relop.compare(Double.parseDouble(actualTime), time);
	}

	public String toString() {
		return "time " + relop.toString() + " " + time;
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
