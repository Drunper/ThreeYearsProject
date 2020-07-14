package it.unibs.ing.domohouse.model.components.rule;

import java.io.Serializable;

//interfaccia per la classe RulesWorker
public interface Operator extends Serializable {
	boolean compare(double a, double b);
}
