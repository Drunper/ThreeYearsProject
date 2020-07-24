package it.unibs.ing.domohouse.view;

import java.util.stream.Collectors;

import it.unibs.ing.domohouse.model.components.elements.Manageable;
import it.unibs.ing.domohouse.model.components.elements.Sensor;

public class SensorRenderer implements ManageableRenderer {

	private ManageableRenderer next;

	public SensorRenderer(ManageableRenderer next) {
		this.next = next;
	}

	@Override
	public String render(Manageable m) {
		if (m instanceof Sensor) {
			Sensor sensor = (Sensor) m;
			String measuredElements = sensor.getMeasuredObjectSet().stream()
					.collect(Collectors.joining(ViewStrings.LINE_SEPARATOR));
			String values = sensor.getMeasurements().stream().map(s -> ViewStrings.LAST_MEASURED_VALUE + s)
					.collect(Collectors.joining(ViewStrings.LINE_SEPARATOR));
			String result = String.format(ViewStrings.SENSOR + ViewStrings.LINE_SEPARATOR + ViewStrings.NAME + "%s"
					+ ViewStrings.LINE_SEPARATOR + ViewStrings.CATEGORY + "%s" + ViewStrings.LINE_SEPARATOR
					+ ViewStrings.MEASURED_ELEMENTS + ViewStrings.LINE_SEPARATOR + "%s" + ViewStrings.LINE_SEPARATOR
					+ "%s" + ViewStrings.LINE_SEPARATOR + ViewStrings.STATUS + "%s", sensor.getName(),
					sensor.getCategory(), measuredElements, values, sensor.getState());
			return result;
		}
		return next.render(m);
	}

}
