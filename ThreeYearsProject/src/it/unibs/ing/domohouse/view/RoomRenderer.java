package it.unibs.ing.domohouse.view;

import java.util.stream.Collectors;

import it.unibs.ing.domohouse.model.components.elements.Manageable;
import it.unibs.ing.domohouse.model.components.elements.Room;

public class RoomRenderer implements ManageableRenderer {

	private ManageableRenderer next;

	public RoomRenderer(ManageableRenderer next) {
		this.next = next;
	}

	@Override
	public String render(Manageable m) {
		if (m instanceof Room) {
			Room room = (Room) m;
			String sensors = room.getSensorsNames().stream().map(s -> s + " - Sensore")
					.collect(Collectors.joining(ViewStrings.LINE_SEPARATOR));
			String actuators = room.getActuatorsNames().stream().map(s -> s + " - Attuatore")
					.collect(Collectors.joining(ViewStrings.LINE_SEPARATOR));
			String artifacts = room.getArtifactsNames().stream().map(s -> s + " - Artefatto")
					.collect(Collectors.joining(ViewStrings.LINE_SEPARATOR));

			String result = String.format(
					ViewStrings.ROOM + ViewStrings.LINE_SEPARATOR + ViewStrings.NAME + "%s" + ViewStrings.LINE_SEPARATOR
							+ ViewStrings.DESCRIPTION + "%s" + ViewStrings.LINE_SEPARATOR
							+ ViewStrings.ELEMENTS_AVAILABLE + ViewStrings.LINE_SEPARATOR + "%s"
							+ ViewStrings.LINE_SEPARATOR + "%s" + ViewStrings.LINE_SEPARATOR + "%s",
					room.getName(), room.getDescr(), sensors, actuators, artifacts);
			return result;
		}
		return next.render(m);
	}

}
