package it.unibs.ing.domohouse.view;

import java.util.stream.Collectors;

import it.unibs.ing.domohouse.model.components.elements.HousingUnit;
import it.unibs.ing.domohouse.model.components.elements.Manageable;

public class HousingUnitRenderer implements ManageableRenderer {

	private ManageableRenderer next;

	public HousingUnitRenderer(ManageableRenderer next) {
		this.next = next;
	}

	@Override
	public String render(Manageable m) {
		if (m instanceof HousingUnit) {
			HousingUnit house = (HousingUnit) m;
			String result = String.format(
					ViewStrings.HOUSING_UNIT + ViewStrings.LINE_SEPARATOR + ViewStrings.NAME + "%s"
							+ ViewStrings.LINE_SEPARATOR + ViewStrings.DESCRIPTION + "%s" + ViewStrings.LINE_SEPARATOR
							+ ViewStrings.HOUSING_UNIT_TYPE + "%s" + ViewStrings.LINE_SEPARATOR
							+ ViewStrings.ROOMS_AVAILABLE + ViewStrings.LINE_SEPARATOR + "%s",
					house.getName(), house.getDescr(), house.getType(),
					house.getRoomList().stream().collect(Collectors.joining(ViewStrings.LINE_SEPARATOR)));
			return result;
		}
		return next.render(m);
	}
}
