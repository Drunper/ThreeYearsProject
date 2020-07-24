package it.unibs.ing.domohouse.view;

import java.util.stream.Collectors;

import it.unibs.ing.domohouse.model.components.elements.Actuator;
import it.unibs.ing.domohouse.model.components.elements.Manageable;

public class ActuatorRenderer implements ManageableRenderer {

	private ManageableRenderer next;

	public ActuatorRenderer(ManageableRenderer next) {
		this.next = next;
	}

	@Override
	public String render(Manageable m) {
		if (m instanceof Actuator) {
			Actuator actuator = (Actuator) m;
			String controlledElements = actuator.getControlledObjectSet().stream()
					.collect(Collectors.joining(ViewStrings.LINE_SEPARATOR));
			String result = String.format(ViewStrings.ACTUATOR + ViewStrings.LINE_SEPARATOR + ViewStrings.NAME + "%s"
					+ ViewStrings.LINE_SEPARATOR + ViewStrings.CATEGORY + "%s" + ViewStrings.LINE_SEPARATOR
					+ ViewStrings.CONTROLLED_ELEMENTS + ViewStrings.LINE_SEPARATOR + "%s" + ViewStrings.LINE_SEPARATOR
					+ ViewStrings.OPERATING_MODE + "%s" + ViewStrings.LINE_SEPARATOR + ViewStrings.STATUS + "%s",
					actuator.getName(), actuator.getCategoryName(), controlledElements,
					actuator.getOperatingMode(), actuator.getState());
			return result;
		}
		return next.render(m);
	}

}
