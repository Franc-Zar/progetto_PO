package com.progettoOOP.OWAPI.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.model.CityData;
import com.progettoOOP.OWAPI.model.CityDataStats;
import com.progettoOOP.OWAPI.model.CityDataStatsAll;
import com.progettoOOP.OWAPI.model.MaxVarianceCity;


/** 
 * classe di visibilità di default contenete metodi protected statici che eseguono calcolo delle statistiche sui 
 * dati passati come parametri 
 *
 * @author Francesco Zaritto
 */
 class Filters {
	
	 
/** metodo protected static che prende come parametro un JSONArray contenente i dati ottenuti dalla chiamata all'API 
* (data), un ArrayList (filterdData), il periodo di calcolo statistiche (giorni) e una stringa indicante il tipo di
* parametro sul quale eseguire le statistiche (param1). Il metodo calcola media e varianza sui dati forniti per il 
* "param1" scelto
* 
* @param data
* @param filteredData
* @param period
* @param param1
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
		
		
/** Overloading di calculateStats: esegue le stesse operazioni con due parametri (param1,param2)
* 
* @param data
* @param filteredData
* @param period
* @param param1
* @param param2
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
	
/** metodo protected static che prende come parametro un JSONArray contenente i dati ottenuti dalla chiamata all'API 
* (data) e un ArrayList (filterdData). Il metodo popola "filteredData" con le città cercate e i 
* corrispondenti valori di pressione e nuvolosità, filtrati dalla totalità di informazioni fornite dall'API
* 
* @param data
* @param filteredData 
*/
		protected static void filterData(JSONArray data, ArrayList<AbstractCityData> filteredData) {
			String name;
			double lon,lat;
			int pressure,clouds;
			
			for(int i=0; i<data.length();i++) {	
				name = data.getJSONObject(i).getString("name");
				lon = data.getJSONObject(i).getJSONObject("coord").getDouble("lon");
				lat = data.getJSONObject(i).getJSONObject("coord").getDouble("lat");
				pressure = data.getJSONObject(i).getJSONObject("main").getInt("pressure");
				clouds = data.getJSONObject(i).getJSONObject("clouds").getInt("all");
				filteredData.add(new CityData(lat,lon,name,clouds,pressure));
			}	
		}
		
	
	
 /** metodo private che prende come parametro una ArrayList di dati ordinati rispetto alla media di 
  * nuvolosità/pressione e la restituisce dopo aver aggiunto, come ultimo elemento, la città che presenta
  * varianza massima di nuvolosità/pressione
  * 
  * @param filteredData
  */
 	protected static void findMaxVariance(ArrayList<AbstractCityData> filteredData) {
 		
 		double maxVariance = ((CityDataStats) filteredData.get(0)).getVariance();
         double lat = ((CityDataStats) filteredData.get(0)).getLat();
 	    double lon = ((CityDataStats) filteredData.get(0)).getLon();
 	    double newVariance;
 	      
         String name = ((CityDataStats) filteredData.get(0)).getName();
 		
 		for(int i=0; i<filteredData.size(); i++) {
 			     
 			newVariance = ((CityDataStats) filteredData.get(i)).getVariance();
 			
 			            if(maxVariance < newVariance) {
 			            	
 			            	     maxVariance = newVariance;
 			            	     name = ((CityDataStats) filteredData.get(i)).getName();
 			            	     lat = ((CityDataStats) filteredData.get(i)).getLat();
 			            	     lon = ((CityDataStats) filteredData.get(i)).getLon();
 			          } }
 	
 		filteredData.add(new MaxVarianceCity(lat,lon,name,maxVariance));
 		
 		}           	                          
 			
 	}
