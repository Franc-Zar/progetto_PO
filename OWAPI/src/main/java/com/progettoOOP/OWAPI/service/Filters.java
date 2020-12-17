package com.progettoOOP.OWAPI.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.model.CityDataStats;
import com.progettoOOP.OWAPI.model.CityDataStatsAll;


/** @Author Francesco Zaritto
 * 
 * Classe di visibilità di default, i cui metodi eseguono operazioni riguardanti il calcolo delle statistiche
 */
 class Filters {
	
	 /** Classe protected che prende come parametri un JSONArray contenente i dati di archivio (data), un ArrayList che ospiterà i dati
	 * filtrati, dopo il ccalcolo, in funzione delle richieste dell'utente (filteredData), il periodo sul quale eseguire le statistiche (period) e una Stringa
	 * contenente il nome del parametro sul quale la funzione andrà a calcolare media e varianza (param1)
	 */
	protected static void calculateStats(JSONArray data, ArrayList<AbstractCityData> filteredData,int period,String param1) {
		
		JSONArray stats;
		JSONObject obj;
	    String name;	
	    double lat,lon;
	    double param1Average,param1Variance;
		
		
		for(int i=0;i<data.length();i++) {
			   param1Average=0;
			   param1Variance=0;
			   obj = data.getJSONObject(i);
			   name = obj.getString("name");
			   lon = obj.getDouble("lon");
			   lat = obj.getDouble("lat");
			   stats = obj.getJSONArray("data"); 
			   
			   for(int j=0;j<stats.length(); j++) {
				   param1Average += stats.getJSONObject(j).getInt(param1);	                       
				   param1Variance += Math.pow( stats.getJSONObject(j).getInt(param1), 2 );
	        }
			   param1Average/=period;
			   param1Variance = param1Variance/period - Math.pow(param1Average, 2); 
			   filteredData.add(new CityDataStats(lat,lon,name,param1Average,param1Variance));
		   }
		}
		
		
	/** Overloading del precedente metodo: esegue il calcolo di media e varianza su due parametri richiesti dall'utente (param1,param2)
	*/
protected static void calculateStats(JSONArray data, ArrayList<AbstractCityData> filteredData,int period,String param1,String param2) {
	
	JSONArray stats;
    JSONObject obj;
	String name;
	double lon,lat;
	double param1Average,param1Variance,param2Average,param2Variance;
	
	for(int i=0;i<data.length();i++) {
		   param1Average=0;
		   param1Variance=0; 
		   param2Average=0;
		   param2Variance=0;
		  
		   obj = data.getJSONObject(i);
		   name = obj.getString("name");
		   lon = obj.getDouble("lon");
		   lat = obj.getDouble("lat");
		   stats = obj.getJSONArray("data"); 
		   
		   for(int j=0;j<stats.length(); j++) {
			   param1Average += stats.getJSONObject(j).getInt(param1);	                       
			   param1Variance += Math.pow( stats.getJSONObject(j).getInt(param1), 2 );
			   param2Average += stats.getJSONObject(j).getInt(param2);	                       
			   param2Variance += Math.pow( stats.getJSONObject(j).getInt(param2), 2 );
        }
		   param1Average/= period;
		   param1Variance = param1Variance/period - Math.pow(param1Average, 2); 
		   
		   param2Average/= period;
		   param2Variance = param2Variance/period - Math.pow(param2Average, 2);
		   
		   filteredData.add(new CityDataStatsAll(lat,lon,name,param1Average,param1Variance,param2Average,param2Variance));
	   }
		
     }
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


