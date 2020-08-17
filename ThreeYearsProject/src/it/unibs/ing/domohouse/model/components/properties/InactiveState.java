package it.unibs.ing.domohouse.model.components.properties;

import it.unibs.ing.domohouse.model.components.elements.Stateable;
import it.unibs.ing.domohouse.model.ModelStrings;

public class InactiveState implements State {

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
