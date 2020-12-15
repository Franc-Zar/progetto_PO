package com.progettoOOP.OWAPI.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class Archive {

	private final String path="src/main/resources/";
	private final String nomeFileElenco="elencoCitta.txt";
	private final double limiteCerchio=2.5;
	private final double passo=0.01;		
	private final double limite= limiteCerchio/passo;
	
	public /*List<Object>*/List<Object> archiveCall(double lat, double lon, int cnt, int period) {
			if(period<1) period = 1;
			/*String json="[";
			
			for(int i=0; i<fileIDs.size();i++) {
				try {
					json+=getCityArchive(fileIDs.get(i),period);
					if(i<fileIDs.size()-1) json+=",";
					if(i==fileIDs.size()-1) json+="]";
				}catch (Exception e) {e.printStackTrace();}
			}
			System.out.println(json);
			return json;
*/
			Vector<String> fileIDs = getFileIDs(lat,lon,cnt);
			JSONArray a=new JSONArray();
			for(int i=0; i<fileIDs.size();i++)
				try {
					a.put(new JSONObject(getCityArchive2(fileIDs.get(i), period)));
				} catch (Exception e) {e.printStackTrace();}
			return  a.toList();
	}

	private Vector<String> getFileIDs(double lat,double lon,int cnt){
		String json=getFileContent(path+nomeFileElenco);
		Vector<String> cities= new Vector<String>();
		
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
	

	private String getCityArchive (String fileId, int period) throws /*Archive*/Exception {
		String archive="";
		try {			
			BufferedReader reader= new BufferedReader(new FileReader("src/main/resources/"+fileId+".txt"));			
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
			if(count<period+2) throw new Exception("ERROR: Archive has no stats available for requested period");
			} catch(IOException e) {e.printStackTrace();}
		} catch(FileNotFoundException e) {e.printStackTrace();}
		 return archive;		
	}
	
	private String getCityArchive2 (String fileID,int period) {
		String json=getFileContent(path+fileID+".txt");
		JSONObject o= new JSONObject(json);	
		JSONArray jarr=o.getJSONArray("data");
		
		if(period<jarr.length()) {
			for(int i=period;i<jarr.length();i++)
				jarr.remove(i);
			o.put("data", jarr);
		}
		return o.toString();
	}
	
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
