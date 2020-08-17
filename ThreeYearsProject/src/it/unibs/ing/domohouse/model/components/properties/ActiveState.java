package it.unibs.ing.domohouse.model.components.properties;

import it.unibs.ing.domohouse.model.components.elements.Stateable;

import it.unibs.ing.domohouse.model.ModelStrings;

public class ActiveState implements State {

	@Override
	public void trigger(Stateable context) {
		context.setState(new InactiveState());
	}

	public String toString() {
		return ModelStrings.ON;
	}

	@Override
	public boolean isActive() {
		return true;
	}
}
