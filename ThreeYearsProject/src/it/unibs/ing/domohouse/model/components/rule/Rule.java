package it.unibs.ing.domohouse.model.components.rule;

import java.io.Serializable;
import java.util.List;

import it.unibs.ing.domohouse.model.components.elements.Manageable;
import it.unibs.ing.domohouse.model.components.elements.Stateable;
import it.unibs.ing.domohouse.model.components.properties.State;
import it.unibs.ing.domohouse.model.db.persistent.PersistentObject;
import it.unibs.ing.domohouse.model.ModelStrings;

public class Rule implements Serializable, Stateable, Manageable {

	private static final long serialVersionUID = -3835080225181052479L;
	private String name;
	private AntecedentNode antecedentRoot;
	private Action firstAction;
	private double time;

	// dispositivi coinvolti nella regola
	private List<String> involvedSensors;
	private List<String> involvedActuators;

	private State state;
	private boolean hasStartConsequent;
	private PersistentObject saveable;

	/*
	 * invariante name != null, antString !=null, consequentRuleString != null,
	 * numericOperatingModeMap != null, nonNumericOperatingModeMap != null
	 */
	public Rule(String name, AntecedentNode antecedentRoot, Action firstAction, List<String> involvedSensors,
			List<String> involvedActuators, State ruleState) {
		this.name = name;
		this.state = ruleState;
		this.antecedentRoot = antecedentRoot;
		this.firstAction = firstAction;
		this.involvedSensors = involvedSensors;
		this.involvedActuators = involvedActuators;
		this.hasStartConsequent = false;
	}
	
	public void setSaveable(PersistentObject saveable) {
		this.saveable = saveable;
	}
	
	public void modify() {
		saveable.modify();
	}
	
	public void delete() {
		saveable.delete();
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
		hasStartConsequent = true;
	}

	public boolean hasStartConsequent() {
		return hasStartConsequent;
	}

	public void setState(State state) {
		assert ruleInvariant() : ModelStrings.WRONG_INVARIANT;
		this.state = state;
	}

	public State getState() {
		assert ruleInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.state;
	}

	public String getName() {
		assert ruleInvariant() : ModelStrings.WRONG_INVARIANT;
		return this.name;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public List<String> getInvolvedSensors() {
		assert ruleInvariant() : ModelStrings.WRONG_INVARIANT;

		return involvedSensors;
	}

	public List<String> getInvolvedActuators() {
		assert ruleInvariant() : ModelStrings.WRONG_INVARIANT;
		return involvedActuators;
	}

	public void trigger() {
		state.trigger(this);
	}

	public boolean getAntecedentValue(String actualTime) {
		return antecedentRoot.getConditionValue(actualTime);
	}

	public void doAction() {
		firstAction.action();
	}
	
	public String getAntecedentText() {
		return antecedentRoot.toString();
	}
	
	public String getConsequentText() {
		return firstAction.toString();
	}

	private boolean ruleInvariant() {
		return name != null;
	}
	
	public String toString() {
		String stateString = state.isActive() ? "attiva" : "disattiva";
		return "[if]" + antecedentRoot.toString() + "[then]" + firstAction.toString() + "\tStato: " + stateString;
	}
}
