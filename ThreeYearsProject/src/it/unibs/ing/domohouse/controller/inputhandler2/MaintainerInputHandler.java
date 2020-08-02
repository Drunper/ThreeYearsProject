package it.unibs.ing.domohouse.controller.inputhandler2;

import it.unibs.ing.domohouse.model.util.DataFacade;

public class MaintainerInputHandler {

	private DataFacade dataFacade;
	
	public MaintainerInputHandler(DataFacade dataFacade) {
		this.dataFacade = dataFacade;
	}
	
	public boolean insertUser(String user) {
		if (dataFacade.hasUser(user))
			return false;
		else
			dataFacade.addUser(user);
		return true;
	}
}
