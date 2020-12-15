package com.progettoOOP.OWAPI.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.model.CityData;
import com.progettoOOP.OWAPI.model.CityDataStats;

public class OpenWeather {
	
	private final String APIkey="bd0426afbb623d69f91f5c3f70b6613d";

	public Vector<AbstractCityData> APIcall(double lat,double lon,int period,int cnt,String type){

		if(cnt<1||cnt>20) throw new IllegalArgumentException("ERROR: Invalid cities' number");
		if(lon<-180.0||lon>180.0) throw new IllegalArgumentException("ERROR: Invalid longitude");
		if(lat<-90.0||lat>90.0) throw new IllegalArgumentException("ERROR: Invalid latitude");
		
		Vector<AbstractCityData> filteredData = new Vector<AbstractCityData>();
		
		String site;
		if(period==0) 
            site="http://api.openweathermap.org/data/2.5/find?lat="+lat
            +"&lon="+lon+"&cnt="+cnt+"&units=metric&appid="+APIkey;
		else site="http://localhost:8080/archive/"+period+"?lat="+lat+"&lon="+lon+"&cnt="+cnt;
		
		String json=getFileContent(site);
		JSONArray data;
		JSONObject obj;

		if(type.equals("actual")) {
			obj = new JSONObject(json);
			data = obj.getJSONArray("list");
			parseData(data, filteredData);
		}
		else if(type.equals("pressure") || type.equals("cloud")) {	
			data = new JSONArray(json);
			calculateStats(data,filteredData,type,period);
			Collections.sort((List<AbstractCityData>)filteredData);
		}
		else throw new IllegalArgumentException("ERROR: Invalid type string");
		
		return filteredData;
	}
	
	
	private void calculateStats(JSONArray data, Vector<AbstractCityData> filteredData, String type, int period) {
		String name;
		double lon,lat;
		   
	   JSONArray stats;
	   JSONObject o;
	   double average=0,variance=0;
		   
	   for(int i=0;i<data.length();i++) {
		   average=0;
		   variance=0;
		   o = data.getJSONObject(i);
		   name = o.getString("name");
		   lon = o.getDouble("lon");
		   lat = o.getDouble("lat");
		   stats = o.getJSONArray("data"); 
		   
		   for(int j=0;j<stats.length(); j++) {
			   average += stats.getJSONObject(j).getInt(type);	                       
			   variance += Math.pow( stats.getJSONObject(j).getInt(type), 2 );
           }
		   average/=period;
		   variance = variance/period - Math.pow(average, 2); 
		   filteredData.add(new CityDataStats(lat,lon,name,average,variance));
	   }
	}


	private void parseData(JSONArray data, Vector<AbstractCityData> filteredData) {
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


	//maaaa un package di util?
	public String getFileContent(String site) {
		HttpURLConnection connection = null;
		try {
			URL url=new URL(site);
        	connection = (HttpURLConnection) url.openConnection();
        	connection.setConnectTimeout(5000);
			connection.connect();
			connection.setReadTimeout(10000);
		}catch(MalformedURLException e) { e.printStackTrace(); }
		catch(IOException a) { a.printStackTrace(); }
		
		String json="";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));	
			String appoggio;
			while((appoggio=reader.readLine())!=null)
				json+=appoggio;
			reader.close();
		} catch (IOException e) {e.printStackTrace();}
		return json;
	}


}