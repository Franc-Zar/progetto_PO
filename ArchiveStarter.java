
package progetto.demoSpringBootApp.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class ArchiveStarter {

	private final String nomeFile="src/main/resources/historical.txt";
	
	public JSONArray putArchive() {
		
		try {
		
			BufferedReader reader= new BufferedReader(new FileReader(nomeFile));
			String archive="";
			
			try {
			
				String app="";
				
				while((app=reader.readLine())!=null) archive+=app;
			
			} catch(IOException e) {System.out.println("ERROR: I/O error while reading file");}
			
			//JSONObject obj = new JSONObject(archive);
			return new JSONArray(archive);
		
		} catch (FileNotFoundException e) {System.out.println("ERROR: FILE not found");;}
		
		return new JSONArray("[{\"file\":\"nontrovato\"}]");
	}
}