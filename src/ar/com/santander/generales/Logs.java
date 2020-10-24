package ar.com.santander.generales;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.output.TeeOutputStream;

public class Logs {

	  static DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"); 
      static Date date = new Date();/*LOG*/
      static PrintStream fileOut;
      
	public static void CrearLog() throws IOException {
      
        new File("logs\\").mkdirs(); 
        
        fileOut = new PrintStream("Logs/logs_" + dateFormat.format(date).toString() + ".txt");
        
//        String path = "logs"; 
//        Process process = Runtime.getRuntime().exec("cmd.exe /C attrib +h " + path);  
        
        PrintStream fileOut = new PrintStream("Logs/logs_" + dateFormat.format(date).toString() + ".txt");
        System.setOut(fileOut); 
        System.setErr(fileOut);
        
    }
	
	
	
	public static void print(String mensaje) {
		
		 System.setOut(fileOut); 
	        System.setErr(fileOut);
		
		System.out.println(mensaje);
		
	        System.setOut(fileOut); 
	        System.setErr(fileOut);
	        
	        System.out.println(mensaje);
		
		
	}
	
	public static void generateLog() {

          File f = new File("Logs/logs_" + dateFormat.format(date).toString() + ".txt");
          if(!f.exists())
          {
            try {
                       f.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
          }

          try {
        	    FileOutputStream fos = new FileOutputStream(f);
        	    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        	        try {
        	            fos.flush();
        	        }
        	        catch (Throwable t) {
        	            // Ignore
        	        }
        	    }, "Shutdown hook Thread flushing " + f));
        	    //we will want to print in standard "System.out" and in "file"
        	    TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
        	    PrintStream ps = new PrintStream(myOut, true); //true - auto-flush after println
        	    System.setOut(ps);
        	} catch (Exception e) {
        	    e.printStackTrace();
        	}

    }
	
}
