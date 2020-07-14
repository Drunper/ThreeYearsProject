package it.unibs.ing.domohouse.model.components.rule;

import java.io.Serializable;
import java.util.List;

import it.unibs.ing.domohouse.model.components.elements.Actuator;

public class Action implements Serializable {

	private static final long serialVersionUID = 8652717414506860502L;
	private Actuator actuator;
	private String operatingMode;
	private List<String> parameters;
	private Action next;

	public Action(Actuator actuator, String operatingMode, List<String> parameters) {
		this.actuator = actuator;
		this.operatingMode = operatingMode;
		this.parameters = parameters;
	}

	public void setNextAction(Action next) {
		this.next = next;
	}

	public void action() {
		actuator.setOperatingMode(operatingMode, parameters);
		if (next != null)
			next.action();
	}

	public String toString() {
		String text = actuator.getName() + " := " + operatingMode;
		if (!parameters.isEmpty())
			text = text + "(" + String.join(",", parameters) + ")";
		if (next != null)
			text = text + ", " + next.toString();
		return text;
	}
}
