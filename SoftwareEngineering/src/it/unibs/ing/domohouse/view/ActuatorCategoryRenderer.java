package it.unibs.ing.domohouse.view;

import java.util.stream.Collectors;

import it.unibs.ing.domohouse.model.components.elements.Manageable;
import it.unibs.ing.domohouse.model.components.properties.ActuatorCategory;

public class ActuatorCategoryRenderer implements ManageableRenderer {

	@Override
	public String render(Manageable m) {
		if (m instanceof ActuatorCategory) {
			ActuatorCategory actcat = (ActuatorCategory) m;
			String operatingModes = actcat.listOfOperatingModes().stream()
					.collect(Collectors.joining(ViewStrings.LINE_SEPARATOR));
			String result = String.format(ViewStrings.ACTUATOR_CATEGORY + ViewStrings.LINE_SEPARATOR + ViewStrings.NAME
					+ "%s" + ViewStrings.LINE_SEPARATOR + ViewStrings.ABBREVATION + "%s" + ViewStrings.LINE_SEPARATOR
					+ ViewStrings.MANUFACTURER + "%s" + ViewStrings.LINE_SEPARATOR + ViewStrings.DEFAULT_MODE + "%s"
					+ ViewStrings.LINE_SEPARATOR + ViewStrings.OPERATING_MODES + ViewStrings.LINE_SEPARATOR + "%s",
					actcat.getName(), actcat.getAbbreviation(), actcat.getManufacturer(), actcat.getDefaultMode(),
					operatingModes);
			return result;
		}
		return ViewStrings.UNKNOWN_MANAGEABLE;
	}

}
