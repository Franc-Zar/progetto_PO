
package progetto.demoSpringBootApp.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Vector;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

private final String pathElenco="";
private final double limiteCerchio=2.5;
private final double passo=0.01;


public class ArchiveStarter {

	//private final String nomeFile="src/main/resources/historical.txt";
	
	public String putArchive(double lat, double lon, int cnt, int period) {
		
		
		
		
	
	}



private Vector<String> citiesInCircle(double lat, double lon, int cnt) {
	String archive="";
	try {
		BufferedReader reader= new BufferedReader(new FileReader(pathElenco));
	
		try {
			String app="";		
			while((app=reader.readLine())!=null) archive+=app;
		} catch(IOException e) {System.out.println("ERROR: I/O error while reading file");}
  
	} catch(FileNotFoundException e) {System.out.println("ERROR: file not found");}

	JSONArray elenco= new JSONArray(archive);
	Vector<String> cities=null;
	double latCitta,lonCitta;
	int prese=0;
	double limite= limiteCerchio/passo;
	for(int n=0; n<limite; n++) {
		for(int i=0; i<elenco.length(); i++) {
			JSONObject o=elenco.getJSONObject(i);
			latCitta=o.getDouble("lat");
			lonCitta=o.getDouble("lon");
			
			if((latCitta>=lat-n*passo)&&(latCitta<=lat+n*passo))
				if((lonCitta>=lon-n*passo)&&(lonCitta<=lon+n*passo)) {
					cities.add(o.getString("fileID"));
					elenco.remove(i);
					prese++;
					if(prese==cnt) return cities;
				}
		}
	}	
	return cities;
}

	
private String getCityArchive (String fileId, int period) {
	
 
	String archive="";
	try {
		
		BufferedReader reader= new BufferedReader(new FileReader("src/main/resources/"+fileId));
		
		try {
		    int count=0;
			String line="";
			
			while((line=reader.readLine())!=null && count<period+2) {
				archive+=line;
				
				if(line==null && count<period+2) throw new ArchiveException("ERROR: Archive has no stats available "
						+ "for requested period");
			}
		
			
		} catch(IOException e) {System.out.println("ERROR: I/O error while reading file");}
	
	  
} catch(FileNotFoundException e) {System.out.println("ERROR: file not found");}
	
	 return archive;
	

}




}
