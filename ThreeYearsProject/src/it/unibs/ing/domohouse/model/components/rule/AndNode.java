package it.unibs.ing.domohouse.model.components.rule;

public class AndNode implements AntecedentNode {

	private static final long serialVersionUID = 8970144741697498639L;
	private AntecedentNode left;
	private AntecedentNode right;

	@Override
	public boolean getConditionValue(String time) {
		return left.getConditionValue(time) && right.getConditionValue(time);
	}

	public String toString() {
		return left.toString() + " && " + right.toString();
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
