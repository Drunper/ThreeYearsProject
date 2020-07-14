package it.unibs.ing.domohouse.model.components.rule;

import java.io.Serializable;

//interfaccia per la classe RulesWorker
public interface StringOperator extends Serializable {
	boolean compare(String a, String b);
}
