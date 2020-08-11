package it.unibs.ing.domohouse.model.components.rule;

public class OrNode implements AntecedentNode {

	private AntecedentNode left;
	private AntecedentNode right;

	@Override
	public boolean getConditionValue(String time) {
		return left.getConditionValue(time) || right.getConditionValue(time);
	}

	public String toString() {
		return left.toString() + " || " + right.toString();
	}

	@Override
	public void setLeftNode(AntecedentNode left) throws Exception {
		this.left = left;
	}

	@Override
	public void setRightNode(AntecedentNode right) throws Exception {
		this.right = right;
	}
}
