package com.progettoOOP.OWAPI.service;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.progettoOOP.OWAPI.model.RequestMonitoringClass;
import com.progettoOOP.OWAPI.util.FileUtilities;

/**
@author Francesco Zaritto
@author Luigi Smargiassi

classe per eseguire operazioni di raccolta dati dall'archivio degli storici delle città
*/
@Service
public class Archive {

	private final String path="src/main/resources/";
	private final String nomeFileElenco="elencoCitta.txt";
	private final double limiteCerchio=0.3;
	private final double passo=0.01;		
	private final double limite= limiteCerchio/passo;
	
	
	
	/** metodo public che prende come parametri latitudine (lat) e longitudine (lon) della città scelta, 
	 * numero di città (cnt), periodo di calcolo delle statistiche (period); restituisce una lista contenente
	 * i file di archivio delle città cercate, filtrati nel contenuto tramite "period", utili al successivo
	 * calcolo delle statistiche di media e varianza
	 * 
	 * @param lat
         * @param lon
         * @param cnt
         * @param period
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

	public ArrayList<String> cityListCall() {
		
		ArrayList<String> fileIDsToCities = new ArrayList<String>();
		String thisFileID;
		String cityListContent;
		int citiesNumber;
		String cityArchiveContent;
		JSONArray searchingIDs;
		JSONObject searchingNames;
		
		cityListContent = FileUtilities.getFileContent(path+nomeFileElenco);
	    searchingIDs = new JSONArray(cityListContent);
		
		for(int i=0; i<searchingIDs.length(); i++) fileIDsToCities.add(searchingIDs.getJSONObject(i).getString("fileID"));
		
		citiesNumber = fileIDsToCities.size();
		
		for(int i=0; i<citiesNumber;i++) {
			
			thisFileID = fileIDsToCities.get(i);
			
			cityArchiveContent = FileUtilities.getFileContent(path+thisFileID+".txt");
		    
			searchingNames = new JSONObject(cityArchiveContent);
			
			fileIDsToCities.set(i,searchingNames.getString("name"));	
		}
		
		return fileIDsToCities;
	}
	
	
	
	/** metodo public che prende come parametri latitudine (lat), longitudine (lon) e nome (name) della città. Effettua verifiche sull'idoneità
	 * della città richiesta, segnalando all'utente eventuali problemi o il successo dell'operazione richiesta
	 * 
	 * @param lat
	 * @param lon
	 * @param name
	 * 
	 * @return oggetto RequestMonitoringClass contenente nel campo "response" informazioni riguardanti l'esito dell'operazione richiesta dall' utente
	 */
	public RequestMonitoringClass addNewCity(double lat, double lon, String name) {
	
		String newData;
		
		if(lon<-180.0||lon>180.0 || lat<-90.0||lat>90.0) 
		
		newData = FileUtilities.getSiteContent("http://localhost:8080/actual?lat="+lat+"&lon="+lon+"&cnt=1");
		
		else return new RequestMonitoringClass(lat, lon, name, "some error occurred: invalid coordinates");
			
		if(cityExists(lat, lon, name, newData) == true) {
			
			String oldElenco = FileUtilities.getFileContent(path+nomeFileElenco);
	
		                     if(!monitored(lat, lon, oldElenco)) {
			
			String newFileID = setNewArchiveName(oldElenco);
			String newCity = ",{\"lon\":"+ lon + ",\"lat\":" + lat + ",\"fileID\":" + "\""+ newFileID + "\"" + "}]";
			String newElenco;
			boolean response = true;
			
			newElenco = oldElenco.substring(0,oldElenco.length()-1) + newCity;
	      
			FileUtilities.overWrite(path + nomeFileElenco, newElenco);
			
			File newCityArchive = new File(path+ newFileID + ".txt");
				
			try {
					response = newCityArchive.createNewFile();
				} catch (IOException e) {e.printStackTrace();}
				
				if(response==true) {
			
			FileUtilities.overWrite(newCityArchive.getPath(), getFirstData(lat, lon, name, newData));
		
			return new RequestMonitoringClass(lat, lon, name, "started monitoring this city");
		
		} else return new RequestMonitoringClass(lat, lon, name, "some error occurred: archive for this city not created");
		
		                 } else return new RequestMonitoringClass(lat, lon, name, "some error occurred: city already monitored");
		
		                       } else return new RequestMonitoringClass(lat, lon, name, "some error occurred: this city doesn't exist");
	     
		
		
	}
	
	/** metodo private che prende come parametri latitudine (lat) e longitudine (lon) della città scelta,
	 * numero città (cnt); cerca il numero "cnt" di città limitrofe definendo una serie di circonferenze, 
	 * di centro la città selezionata tramite coordinate, di raggio sempre maggiore e restituisce una 
	 * ArrayList di stringhe contenente i fileIDs delle città trovate.
	 * 
	 * @param lat
         * @param lon
         * @param cnt
         * 
     * @return fileIDs delle città
	 */
	private ArrayList<String> getFileIDs(double lat,double lon,int cnt){
		String json=FileUtilities.getFileContent(path+nomeFileElenco);
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
		} catch (JSONException e) {e.printStackTrace();}
		
		return cities;
	}
	
	
	/** metodo private che prende come parametri il fileID e il periodo di calcolo delle statistiche (period) 
	 * e restituisce una stringa contenente la parte dell'archivio della città interessata corrispondente
	 * al periodo richiesto 
	 * 
	 * @param fileID
	 * @param period
	 * 
	 * @return porzione di archivio di dimensioni "period"
	 * 
	 */
	private String getCityArchive (String fileID,int period) {
		String json=FileUtilities.getFileContent(path+fileID+".txt");
		JSONObject o= new JSONObject(json);	
		JSONArray jarr=o.getJSONArray("data");
		
		if(period<jarr.length()) {
			while(period<jarr.length()) jarr.remove(period);
			o.remove("data");
			o.put("data", jarr);
		}
		return o.toString();
	}
	
	
	
	/** metodo private che prende come parametri latitudine (lat), longitudine (lon) e nome (name) della città, una stringa contenente i dati
	 * attuali (data). Il metodo restituisce "true" se le coordinate fornite corrispondono alla città il cui nome corrisponde a quello fornito
	 * come parametro (name); "false" altrimenti. 
	 *  
	 * @param lat
	 * @param lon
	 * @param name
	 * @param data
	 * 
	 * @return boolean 
	 */
	private boolean cityExists (double lat, double lon, String name, String data) {
	
		JSONArray analize = new JSONArray(data);
		
		if(name.equals(analize.getJSONObject(0).getString("name"))) return true;
		else return false;
		
	}
	
	
	/** metodo private che prende come parametri latitudine (lat), longitudine (lon) e una stringa contenente i dati attuali (data).
	 * Il metodo restituisce "true" se la richiesta dell'utente è effettuata per una città già monitorata;
	 * "false" altrimenti.
	 * 
	 * @param lat
	 * @param lon
	 * @param data
	 * 
	 * @return boolean
	 */
	private boolean monitored (double lat, double lon, String data) {
		
	JSONArray analize = new JSONArray(data);
	BigDecimal gaveLon = new BigDecimal(lon).setScale(2,RoundingMode.HALF_UP);
	BigDecimal gaveLat = new BigDecimal(lat).setScale(2,RoundingMode.HALF_UP);
	BigDecimal thisLon;
	BigDecimal thisLat;
	
	for(int i=0; i<analize.length();i++) {
		
		 thisLon = new BigDecimal(analize.getJSONObject(i).getDouble("lon")).setScale(2,RoundingMode.HALF_UP);
		 thisLat = new BigDecimal(analize.getJSONObject(i).getDouble("lat")).setScale(2,RoundingMode.HALF_UP);
	
		if(gaveLon.compareTo(thisLon) == 0 && gaveLat.compareTo(thisLat) == 0) return true; 
	}
	
	return false;
			
	}
	
	
	/** metodo private che prende come parametri latitudine (lat), longitudine (lon) e nome (name) della città, una stringa contenente i dati
	 *  attuali (data). Restituisce una Stringa contenente info attuali da inserire nell'archivio appena creato della nuova città aggiunta 
	 *  all'elenco delle monitorate 
	 * 
	 * @param lat
	 * @param lon
	 * @param name
	 * @param data
	 * 
	 * @return stringa rappresentante un JSONObject contenente info attuali al momento della richiesta dell'utente per la città da monitorare
	 */
	private String getFirstData (double lat, double lon, String name, String data) {
		
		
		JSONArray takeActual = new JSONArray(data);
		
		double clouds = takeActual.getJSONObject(0).getDouble("cloud");
		double pressure = takeActual.getJSONObject(0).getDouble("pressure");
		
		return "{\"name\":"+"\""+ name + "\"" +", \"lon\":" + lon 
		
		+ ", \"lat\":" + lat +", \"data\": [{\"cloud\":" + clouds + ", \"pressure\":" + pressure + "}]}";
	
	}
	
	/** metodo private che restituisce una stringa contenente il nome dell'archivio che verrà creato per ospitare i dati storici della città
	 * appena inserita nell'elenco delle monitorate
	 * 
	 * @param data
	 * @return nome del file di archivio
	 */
	private String setNewArchiveName(String data) {
		
		JSONArray jarr = new JSONArray(data);
		
			Integer newFileID = Integer.parseInt(jarr.getJSONObject(jarr.length()-1).getString("fileID"));
		  
			return (++newFileID).toString();
				
	}
	
	
}
