package it.unibs.ing.domohouse.model.components.elements;

import it.unibs.ing.domohouse.model.components.properties.State;

public interface Stateable {

	State getState();
	void setState(State state);
}
