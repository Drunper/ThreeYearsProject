package it.unibs.ing.domohouse.view;

import java.util.stream.Collectors;

import it.unibs.ing.domohouse.model.components.elements.Manageable;
import it.unibs.ing.domohouse.model.components.properties.SensorCategory;

public class SensorCategoryRenderer implements ManageableRenderer {

	private ManageableRenderer next;

	public SensorCategoryRenderer(ManageableRenderer next) {
		this.next = next;
	}

	@Override
	public String render(Manageable m) {
		if (m instanceof SensorCategory) {
			SensorCategory senscat = (SensorCategory) m;
			String infos = senscat
					.getDetectableInfoList().stream().map(s -> ViewStrings.INFO_NAME + s + ViewStrings.SPACE
							+ ViewStrings.INFO_DOMAIN + senscat.getDomainString(s))
					.collect(Collectors.joining(ViewStrings.LINE_SEPARATOR));
			String result = String.format(
					ViewStrings.SENSOR_CATEGORY + ViewStrings.LINE_SEPARATOR + ViewStrings.NAME + "%s"
							+ ViewStrings.LINE_SEPARATOR + ViewStrings.ABBREVATION + "%s" + ViewStrings.LINE_SEPARATOR
							+ ViewStrings.MANUFACTURER + "%s" + ViewStrings.LINE_SEPARATOR
							+ ViewStrings.DETECTABLE_INFOS + ViewStrings.LINE_SEPARATOR + "%s",
					senscat.getName(), senscat.getAbbreviation(), senscat.getManufacturer(), infos);
			return result;
		}
		return next.render(m);
	}

}
