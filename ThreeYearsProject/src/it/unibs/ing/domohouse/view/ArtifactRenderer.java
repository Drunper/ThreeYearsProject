package it.unibs.ing.domohouse.view;

import it.unibs.ing.domohouse.model.components.elements.Artifact;
import it.unibs.ing.domohouse.model.components.elements.Manageable;

public class ArtifactRenderer implements ManageableRenderer {

	private ManageableRenderer next;

	public ArtifactRenderer(ManageableRenderer next) {
		this.next = next;
	}

	@Override
	public String render(Manageable m) {
		if (m instanceof Artifact) {
			Artifact artifact = (Artifact) m;
			String result = String.format(
					ViewStrings.ARTIFACT + ViewStrings.LINE_SEPARATOR + ViewStrings.NAME + "%s"
							+ ViewStrings.LINE_SEPARATOR + ViewStrings.DESCRIPTION + "%s",
					artifact.getName(), artifact.getDescr());
			return result;
		}
		return next.render(m);
	}

}
