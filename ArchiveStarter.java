
package progetto.demoSpringBootApp.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;
import java.io.IOException;

public class ArchiveStarter {

	private final String nomeFile="src/main/resources/historical.txt";
	
	public String putArchive(double lat, double lon, int cnt, int period) {
		
		
		
		
	
	}



private Vector<String> citiesInCircle(double lat, double lon, int cnt) {
	
	
	
	
	
	
	return
}


private String getCityArchive (String fileId, int period) {
	
	
	
	
	
	
}


private String readFile (String nomeFile) {
	String archive="";
	try {
		
		BufferedReader reader= new BufferedReader(new FileReader(nomeFile));
		
		try {
		
			String app="";
			
			while((app=reader.readLine())!=null) archive+=app;
		
		} catch(IOException e) {System.out.println("ERROR: I/O error while reading file");}
	
	  
} catch(FileNotFoundException e) {System.out.println("ERROR: file not found");}
	
	 return archive;
	
}

}
