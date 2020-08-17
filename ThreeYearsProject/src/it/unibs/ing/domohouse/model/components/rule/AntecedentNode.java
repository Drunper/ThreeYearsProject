package it.unibs.ing.domohouse.model.components.rule;

public interface AntecedentNode {

	boolean getConditionValue(String time);
	void setLeftNode(AntecedentNode left) throws Exception;
	void setRightNode(AntecedentNode right) throws Exception;
}
