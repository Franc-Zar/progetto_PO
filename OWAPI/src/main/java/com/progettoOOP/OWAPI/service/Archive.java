package com.progettoOOP.OWAPI.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

/*
@Author Francesco Zaritto
@Author Luigi Smargiassi

classe per eseguire operazioni di raccolta dati dall'archivio degli storici delle città
*/
@Service
public class Archive {

	private final String path="src/main/resources/";
	private final String nomeFileElenco="elencoCitta.txt";
	private final double limiteCerchio=0.3;
	private final double passo=0.01;		
	private final double limite= limiteCerchio/passo;
	
	
	
	/* metodo public che prende come parametri latitudine (lat) e longitudine (lon) della città scelta, 
	 * numero di città (cnt), periodo di calcolo delle statistiche (period); restituisce una lista contenente
	 * i file di archivio delle città cercate, filtrati nel contenuto tramite "period", utili al successivo
	 * calcolo delle statistiche di media e varianza
	 */
	public List<Object> archiveCall(double lat, double lon, int cnt, int period) {
			if(period<1) period = 1;
	
			ArrayList<String> fileIDs = getFileIDs(lat,lon,cnt);
			JSONArray a=new JSONArray();
			for(int i=0; i<fileIDs.size();i++)
				try {
					a.put(new JSONObject(getCityArchive(fileIDs.get(i), period)));
				} catch (Exception e) {e.printStackTrace();}
			return  a.toList();
	}

	
	/* metodo private che prende come parametri latitudine (lat) e longitudine (lon) della città scelta,
	 * numero città (cnt); cerca il numero "cnt" di città limitrofe definendo una serie di circonferenze, 
	 * di centro la città selezionata tramite coordinate, di raggio sempre maggiore e restituisce una 
	 * ArrayList di stringhe contenente i fileIDs delle città trovate.
	 */
	private ArrayList<String> getFileIDs(double lat,double lon,int cnt){
		String json=getFileContent(path+nomeFileElenco);
		ArrayList<String> cities= new ArrayList<String>();
		
		try {
			JSONArray elenco = new JSONArray(json);	
			double latCitta,lonCitta;
			int prese=0;
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
		}catch (JSONException e) {e.printStackTrace();}
		
		return cities;
	}
	
	
	/* metodo private che prende come parametri il fileID e il periodo di calcolo delle statistiche (period) 
	 * e restituisce una stringa contenente la parte dell'archivio della città interessata corrispondente
	 * al periodo richiesto 
	 * 
	 */
	private String getCityArchive (String fileID,int period) {
		String json=getFileContent(path+fileID+".txt");
		JSONObject o= new JSONObject(json);	
		JSONArray jarr=o.getJSONArray("data");
		
		if(period<jarr.length()) {
			while(period<jarr.length()) jarr.remove(period);
			o.remove("data");
			o.put("data", jarr);
		}
		return o.toString();
	}
	
	/* metodo private che prende come parametro la stringa corrispondente al nome del file (fileName) 
	 * e restituisce, come stringa, il contenuto del file stesso
	 * 
	 */
	private String getFileContent(String fileName) {
		String content="";
		try {
			BufferedReader reader=new BufferedReader(new FileReader(fileName));
			try {
				String line="";
				while((line=reader.readLine())!=null) content+=line;
			}catch (IOException e) {e.printStackTrace();}
		}catch (FileNotFoundException e){e.printStackTrace();}
		return content;
	}

}
