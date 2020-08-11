package it.unibs.ing.domohouse.model.components.properties;

import it.unibs.ing.domohouse.model.components.elements.Stateable;

public interface State  {

	void trigger(Stateable context);
	String toString();
	boolean isActive();
}
