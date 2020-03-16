package it.unibs.ing.domohouse.util;

import java.io.FileInputStream;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileLoader {
	
	public DataHandler getDataHandler() {
		return safeReadDataHandler();
	}
	
	private DataHandler safeReadDataHandler() {		
		String filePath = Strings.DATA_HANDLER_PATH + Strings.DATA_HANDLER_NAME_FILE;
				
		File f = new File(filePath);
		if(f.isFile() && f.canRead()) {
			try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(f))) {
				return (DataHandler) objectIn.readObject();
			}
			catch(IOException ex) {
				System.out.println(Strings.ERROR_LOAD_FILE);
				ex.printStackTrace();
			}
			catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	public void runFileFromName(String fileName) {
		 try
         {
             File f = new File(fileName);
             if (f.exists()) 
             {
                 if (Desktop.isDesktopSupported()) 
                 {
                 Desktop.getDesktop().open(f);
                 } 
                 else
                 {
                 System.out.println(Strings.ERROR_HELP_FILE);
                 }           
             }         
             else
             {
            	 System.out.println(Strings.ERROR_HELP_FILE);
             }       
         }
         catch(Exception e)
         {
         	e.printStackTrace();
         }
	}
}
