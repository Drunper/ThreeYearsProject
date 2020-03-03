package it.unibs.ing.domohouse.interfaces;

import java.io.Serializable;

//interfaccia per la classe Rule
public interface StringOperator extends Serializable {
	boolean compare(String a, String b);
}
