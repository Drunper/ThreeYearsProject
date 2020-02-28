package it.unibs.ing.domohouse.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogWriter {
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	public void write(String toWrite) {
		
	  Date date = new Date();
	  BufferedWriter bw = null;
      FileWriter fw = null;

      try {
    	  
          fw = new FileWriter(Strings.LOG_PATH + Strings.LOG_NAME_FILE, true);
          bw = new BufferedWriter(fw);
          bw.write("[" + dateFormat.format(date) + "] "+ toWrite);

      } catch (IOException e) {
    	  
    	  e.printStackTrace();
    	  
      } finally {
    	  
          try {
        	  
              if (bw != null)	bw.close();
              if (fw != null)	fw.close();
              
          } catch (IOException ex) {
        	  
              ex.printStackTrace();
          }
      }
	}

}


