package progetto.demoSpringBootApp.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;

public class ArchiveStarter {

	private final String nomeFile="src/main/resources/storico.txt";
	public JSONArray putArchive() {
		try {
			BufferedReader reader= new BufferedReader(new FileReader(nomeFile));
			String text="";
			try {
				String app="";
				while((app=reader.readLine())!=null)text+=app;
			} catch(IOException ex) {ex.printStackTrace();}
			return new JSONArray(text);
		} catch (FileNotFoundException e) {e.printStackTrace();}
		return new JSONArray("[{\"file\":\"nontrovato\"}]");
	}
}
