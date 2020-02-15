package it.unibs.ing.domohouse.util;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileLoader {
	
	public DataHandler getDataHandler() {
		if(safeReadDataHandler() == null)
			return null;
		else
			return safeReadDataHandler();
	}
	
	private DataHandler safeReadDataHandler() {
		String filePath = Strings.DATA_HANDLER_PATH;
		File f = new File(filePath);
		if(f.isFile() && f.canRead()) {
			try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(f)))
			{
				return (DataHandler) objectIn.readObject();
			}
			catch(IOException | ClassNotFoundException ex) 
			{
				ex.printStackTrace();
			} 
		}
		return null;
	}
}
