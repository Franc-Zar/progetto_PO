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
*  classe per eseguire operazioni di raccolta dati dall'archivio degli storici delle città e per aggiungere/rimuovere città dall'elenco di monitoraggio (creando/eliminando i
*  corrispondenti archivi)
*  
*   @author Francesco Zaritto
*   @author Luigi Smargiassi
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
     * 
     * @return informazioni di archivio
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

	
	
	/** metodo public che restituisce all'utente i nomi delle città monitorate dall'applicazione (al momento della
	 * richiesta)
	 * 
	 * @return lista di città monitorate
	 */
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
	 * @return oggetto RequestMonitoringClass contenente nel campo "response" informazioni riguardanti l'esito 
	 * dell'operazione richiesta dall'utente di monitorare la città scelta
	 */
	public RequestMonitoringClass addNewCity(double lat, double lon, String name) {
		JSONArray newData;
		
		if(lon<-180.0||lon>180.0 || lat<-90.0||lat>90.0) 
			 return new RequestMonitoringClass(lat, lon, name, "some error occurred: invalid coordinates");
		else newData = new JSONArray(FileUtilities.getSiteContent("http://localhost:8080/actual?lat="+lat+"&lon="+lon+"&cnt=1"));
			
		if(cityExists(lat, lon, name, newData)) {
			JSONObject newCity = new JSONObject();
			JSONArray newElenco = new JSONArray(FileUtilities.getFileContent(path+nomeFileElenco));
			if(!monitored(lat, lon, newElenco)) {
				String newFileID = setNewArchiveName(newElenco);
				
				newCity.put("lon", lon);
				newCity.put("lat", lat);
				newCity.put("fileID", newFileID);
				newElenco.put(newCity);	
				
				File newCityArchive = new File(path + newFileID + ".txt");
				try {
					if(newCityArchive.createNewFile()) { 
						FileUtilities.overWrite(path + nomeFileElenco, newElenco.toString());
						FileUtilities.overWrite(newCityArchive.getPath(), getFirstData(lat,lon,name,newData).toString());
						return new RequestMonitoringClass(lat, lon, name, "started monitoring this city");
					}
					else return new RequestMonitoringClass(lat, lon, name, "some error occurred: failed to create Archive, city is not monitored");
				} catch (IOException e) {e.printStackTrace();}
				
			} else return new RequestMonitoringClass(lat, lon, name, "some error occurred: city already monitored");
			
		} else return new RequestMonitoringClass(lat, lon, name, "some error occurred: this city doesn't exist");
		
		return new RequestMonitoringClass(lat, lon, name, "some error occurred");
	} 
	     
	
	/** metodo public che prende come parametri latitudine (lat), longitudine (lon) e nome (name) della città. 
	 * Effettua verifiche sull'idoneitàdella città richiesta, segnalando all'utente eventuali problemi o il 
	 * successo dell'operazione richiesta di interrompere il monitoraggio della città scelta
	 * 
	 * @param lat
	 * @param lon
	 * @param name
	 * 
	 * @return oggetto RequestMonitoringClass contenente nel campo "response" informazioni riguardanti 
	 * l'esito dell'operazione richiesta dall'utente di interrompere il monitoraggio della città scelta
	 */
	public RequestMonitoringClass removeCity(double lat, double lon, String name) {
		if(lon<-180.0||lon>180.0 || lat<-90.0||lat>90.0) 
			 return new RequestMonitoringClass(lat, lon, name, "some error occurred: invalid coordinates");

		JSONArray newData = new JSONArray(FileUtilities.getSiteContent("http://localhost:8080/actual?lat="+lat+"&lon="+lon+"&cnt=1")); 
		
		if(cityExists(lat, lon, name, newData)) {
			int indexRemove=-1;
			String deleteFileID="empty";
			String newElenco;
			String oldElenco = FileUtilities.getFileContent(path+nomeFileElenco);
			JSONArray searchCity = new JSONArray(oldElenco);
			JSONObject thisCity;
		
			for(int i=0; i<searchCity.length();i++) {
				thisCity = searchCity.getJSONObject(i);
			 	
				if(lat == thisCity.getDouble("lat") && lon == thisCity.getDouble("lon")) {
					   deleteFileID = thisCity.getString("fileID");                      
					    indexRemove = i;	   
				}
			}	 
		
			if(deleteFileID.equals("empty") && indexRemove==-1) 
				return new RequestMonitoringClass(lat, lon, name, "some error occurred: this city is not monitored");
			else {
				File thisCityArchive = new File(path+deleteFileID+".txt");
				
				if(thisCityArchive.delete()) {
					searchCity.remove(indexRemove);
					newElenco = searchCity.toString();
					FileUtilities.overWrite(path+nomeFileElenco, newElenco);
					return new RequestMonitoringClass(lat, lon, name, "stopped monitoring this city");
				}
				else return new RequestMonitoringClass(lat, lon, name, "some error occurred: Archive not deleted, city still monitored");	
			}
		} else  return new RequestMonitoringClass(lat, lon, name, "some error occurred: this city doesn't exist");
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
	
	
	
	/** metodo private che prende come parametri latitudine (lat), longitudine (lon) e nome (name) della città, un JSONArray contenente i dati
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
	private boolean cityExists (double lat, double lon, String name, JSONArray analize) {
		
		if(name.equals(analize.getJSONObject(0).getString("name"))) return true;
		else return false;
		
	}
	
	
	/** metodo private che prende come parametri latitudine (lat), longitudine (lon) e un JSONArray contenente i dati attuali (data).
	 * Il metodo restituisce "true" se la richiesta dell'utente è effettuata per una città già monitorata;
	 * "false" altrimenti.
	 * 
	 * @param lat
	 * @param lon
	 * @param data
	 * 
	 * @return boolean
	 */
	private boolean monitored (double lat, double lon, JSONArray analize) {
		
	BigDecimal gaveLon = new BigDecimal(lon).setScale(2,RoundingMode.HALF_UP);
	BigDecimal gaveLat = new BigDecimal(lat).setScale(2,RoundingMode.HALF_UP);
	BigDecimal thisLon;
	BigDecimal thisLat;
	
	if(analize.isEmpty()) return false;
	
	for(int i=0; i<analize.length();i++) {
		
		 thisLon = new BigDecimal(analize.getJSONObject(i).getDouble("lon")).setScale(2,RoundingMode.HALF_UP);
		 thisLat = new BigDecimal(analize.getJSONObject(i).getDouble("lat")).setScale(2,RoundingMode.HALF_UP);
	
		if(gaveLon.compareTo(thisLon) == 0 && gaveLat.compareTo(thisLat) == 0) return true; 
	}
	
	return false;
			
	}
	
	
	/** metodo private che prende come parametri latitudine (lat), longitudine (lon) e nome (name) della città, un JSONArray contenente i dati
	 *  attuali (data). Restituisce una Stringa contenente info attuali da inserire nell'archivio appena creato della nuova città aggiunta 
	 *  all'elenco delle monitorate 
	 * 
	 * @param lat
	 * @param lon
	 * @param name
	 * @param data
	 * 
	 * @return  JSONObject contenente info attuali al momento della richiesta dell'utente per la città da monitorare
	 */
	private JSONObject getFirstData (double lat, double lon, String name, JSONArray newData) {
		
		JSONObject initializeArchive = new JSONObject();
		JSONArray firstData;
		String data;
		
		double cloud = newData.getJSONObject(0).getDouble("cloud");
		double pressure = newData.getJSONObject(0).getDouble("pressure");
		
		data = "[{\"cloud\":" + cloud + ",\"pressure\":" + pressure + "}]";
		
		firstData = new JSONArray(data);
		
		initializeArchive.put("name",name);
		initializeArchive.put("lon", lon);
		initializeArchive.put("lat", lat);
		initializeArchive.put("data", firstData);
		
		return initializeArchive;
		
	}
	
	/** metodo private che prende come parametro un JSONArray contenente l'elenco delle città monitorate e restituisce 
	 * una stringa contenente il nome dell'archivio che verrà creato per ospitare i dati storici della città
	 * appena inserita nell'elenco delle monitorate
	 * 
	 * @param data
	 * @return nome del file di archivio
	 */
	private String setNewArchiveName(JSONArray data) {		
		Integer newFileID;
		
		if(!data.isEmpty()) {
			newFileID = Integer.parseInt(data.getJSONObject(data.length()-1).getString("fileID"));
			return (++newFileID).toString();
		}
		else return "1";
	}

	
}
