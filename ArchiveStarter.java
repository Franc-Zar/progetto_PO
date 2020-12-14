
package progetto.demoSpringBootApp.service;

import java.io.BufferedReader;

import progetto.demoSpringBootApp.customExceptions.ArchiveException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

public class ArchiveStarter {
	
	public String putArchive(double lat, double lon, int cnt, int period) {
		
		if(period<1) period = 1;
		
		Vector<String> circle = new Vector<String>();
		String archive= "[";
	
		circle = citiesInCircle(lat,lon,cnt);
		
		for(int i=0; i<circle.size();i++)
			try {
				
				archive+=getCityArchive(circle.get(i), period);
				
				if(i<circle.size()-1) archive+= ",";
				
				if(i==circle.size()-1) archive+="]";
				
			} catch (ArchiveException e) {System.out.println(e);}
		
		
		return archive;		
	}



private Vector<String> citiesInCircle(double lat, double lon, int cnt) {
	String archive="";
	try {
		BufferedReader reader= new BufferedReader(new FileReader("src/main/resources/CityList"));
	
		try {
			String app="";		
			while((app=reader.readLine())!=null) archive+=app;
		} catch(IOException e) {System.out.println("ERROR: I/O error while reading file");}
  
	} catch(FileNotFoundException e) {System.out.println("ERROR: file not found");}

	JSONArray elenco = new JSONArray(archive);
	Vector<String> cities= new Vector<String>();
	double latCitta,lonCitta;
	int prese=0;
	double limiteCerchio=2.5;
	double passo=0.1;		
	double limite= limiteCerchio/passo;
	for(int n=0; n<limite; n++) {
		for(int i=0; i<elenco.length(); i++) {
			JSONObject o=elenco.getJSONObject(i);
			latCitta=o.getDouble("lat");
			lonCitta=o.getDouble("lon");
			
			if((latCitta>=lat-n*passo)&&(latCitta<=lat+n*passo))
				if((lonCitta>=lon-n*passo)&&(lonCitta<=lon+n*passo)) {
					cities.add(o.getString("fileId"));
					elenco.remove(i);
					prese++;
					if(prese==cnt) return cities;
				}
		}
	}	
	return cities;
}
	


private String getCityArchive (String fileId, int period) throws ArchiveException {
	
 
	String archive="";
	try {
		
		BufferedReader reader= new BufferedReader(new FileReader("src/main/resources/"+fileId));
		
		try {
		    int count=0;
			String line="";
			
			while((line=reader.readLine())!=null && count<period+2) {
				archive+=line;
				
				if(count==period+1) { 
					
					archive = archive.substring(0, archive.length()-1);
					
					archive+= "]}";
				}
				
				count++;
		}
		
			reader.close();
			
		if(count<period+2) throw new ArchiveException("ERROR: Archive has no stats available "
						+ "for requested period");
			
		} catch(IOException e) {System.out.println("ERROR: I/O error while reading file");}
	
	  
} catch(FileNotFoundException e) {System.out.println("ERROR: file not found");}
	
	 return archive;
	

}




}
