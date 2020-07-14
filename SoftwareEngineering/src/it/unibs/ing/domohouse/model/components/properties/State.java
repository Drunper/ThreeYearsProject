package it.unibs.ing.domohouse.model.components.properties;

import java.io.Serializable;

import it.unibs.ing.domohouse.model.components.elements.Stateable;

public interface State extends Serializable {

	void trigger(Stateable context);
	String toString();
	boolean isActive();
}
