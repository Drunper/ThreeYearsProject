package it.unibs.ing.domohouse.model.components.properties;

import it.unibs.ing.domohouse.model.components.elements.Stateable;
import java.io.Serializable;
import it.unibs.ing.domohouse.model.ModelStrings;

public class InactiveState implements State, Serializable {

	private static final long serialVersionUID = -798647398663197975L;

	@Override
	public void trigger(Stateable context) {
		context.setState(new ActiveState());
	}

	public String toString() {
		return ModelStrings.OFF;
	}

	@Override
	public boolean isActive() {
		return false;
	}
}
