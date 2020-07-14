package it.unibs.ing.domohouse.model.components.properties;

import it.unibs.ing.domohouse.model.components.elements.Stateable;

import java.io.Serializable;

import it.unibs.ing.domohouse.model.ModelStrings;

public class ActiveState implements State, Serializable {

	private static final long serialVersionUID = 5568254303159696169L;

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
