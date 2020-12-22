package com.progettoOOP.OWAPI.service;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.progettoOOP.OWAPI.util.FileUtilities;
import com.progettoOOP.OWAPI.model.AbstractCityData;

/**classe public i cui metodi stabiliscono la connessione all'API o all'Archivio e che, dopo la manipolazione dei
 * dati, li restituisce all'utente nel formato dell'applicazione, in funzione delle richieste effettuate
 *
 *@author Francesco Zaritto
 *  @author Luigi Smargiassi 
 */
public class OpenWeather {
	
	private final static String APIkey=FileUtilities.getFileContent("src/main/resources/APIkey.txt");

/** metodo public che prende in input le coordinate geografiche (lat,lon), il periodo entro il quale definire
 * i dati (period=0 nel caso di informazioni attuali), il numero totale di città da analizzare (cnt = 1 default)
 * e una Stringa contenente il tipo di richiesta (type = "actual" nel caso di informazioni attuali, "type" = 
 * pressure/cloud nel caso di richieste di statistiche). Restituisce un ArrayList contenente le città cercate
 * e le relative informazioni desiderate
 * 
 * @throws IllegalArgumentException
 * 
 * @param lat
 * @param lon
 * @param period
 * @param cnt
 * @param type
 * 
 * 
 * @return informazioni cercate sulle città (sia attuali che storiche)
 */
	public static ArrayList<AbstractCityData> APIcall(double lat,double lon,int period,int cnt,String type) throws IllegalArgumentException{

		if(cnt<1||cnt>50) throw new IllegalArgumentException("ERROR: Invalid cities' number");
		if(lon<-180.0||lon>180.0) throw new IllegalArgumentException("ERROR: Invalid longitude");
		if(lat<-90.0||lat>90.0) throw new IllegalArgumentException("ERROR: Invalid latitude");
		
		ArrayList<AbstractCityData> filteredData = new ArrayList<AbstractCityData>();
		
		String site;
		if(period==0) 
            site="http://api.openweathermap.org/data/2.5/find?lat="+lat
            +"&lon="+lon+"&cnt="+cnt+"&units=metric&appid="+APIkey;
		else site="http://localhost:8080/archive/"+period+"?lat="+lat+"&lon="+lon+"&cnt="+cnt;
		
		String json=FileUtilities.getSiteContent(site);
		JSONArray data;
		JSONObject obj;

		if(type.equals("actual")) {
			obj = new JSONObject(json);
			data = obj.getJSONArray("list");
			Filters.filterData(data, filteredData);
		}
		else if(type.equals("pressure") || type.equals("cloud")) {	
			data = new JSONArray(json);
			getStats(data,filteredData,type,period);
			Arrays.asList(filteredData);
			Collections.sort(filteredData);
			Filters.findMaxVariance(filteredData);
			
		}
		else if(type.equals("all")) {
			
			data = new JSONArray(json);
			getStats(data, filteredData, type, period);
			
		}
		else throw new IllegalArgumentException("ERROR: Invalid type string");
		
		return filteredData;
	}
	
/** metodo private che prende come parametri un JSONArray contenente i dati ottenuti dalla chiamata all'
* archivio (data), un ArrayList (filteredData), una stringa contenente il tipo di parametro sul quale
* effettuare le statistiche (type = pressure/cloud) e il periodo (period) sul quale calcolare tali statistiche
* (media e varianza) 
* 
* @param data
* @param filteredData
* @param type
* @param period
*/
	private static void getStats(JSONArray data, ArrayList<AbstractCityData> filteredData, String type, int period) {
		
	if(type.equals("all")) Filters.calculateStats(data, filteredData, period, "cloud","pressure");
		
	   else Filters.calculateStats(data, filteredData, period, type);
	   
	 }
}
