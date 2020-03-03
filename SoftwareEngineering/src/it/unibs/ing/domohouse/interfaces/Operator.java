package it.unibs.ing.domohouse.interfaces;

import java.io.Serializable;

//interfaccia per la classe Rule
public interface Operator extends Serializable {
	boolean compare(double a, double b);
}
