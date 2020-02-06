package it.unibs.ing.domohouse.util;

import java.io.FileOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;

public class FileSaver {

	private static final String dataHandlerPath = "C:\\test\\data";

	/*
	 * Salvare solo il DataHandler che contiene tutto. Salvare altre cose in maniera separata diventa complicato.
	 * La fase di caricamento diventerebbe estremamente complicata, ed è ciò che dovremo fare (più o meno) nella versione 5.
	 */
	public void writeDataHandlerToFile(DataHandler data) {
		String filePath = dataHandlerPath+"\\dataHandler.dat";
		try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filePath))){ 			
			objectOut.writeObject(data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
		
	public void createDirs() {
		File file = new File(dataHandlerPath);
		file.mkdirs();
	}
}
