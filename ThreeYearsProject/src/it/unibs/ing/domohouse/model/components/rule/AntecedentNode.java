package it.unibs.ing.domohouse.model.components.rule;

import java.io.Serializable;

public interface AntecedentNode extends Serializable {

	boolean getConditionValue(String time);
	void setLeftNode(AntecedentNode left) throws Exception;
	void setRightNode(AntecedentNode right) throws Exception;
}
