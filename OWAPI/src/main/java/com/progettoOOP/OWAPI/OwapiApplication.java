package com.progettoOOP.OWAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.progettoOOP.OWAPI.model.RequestBodyClass;
import com.progettoOOP.OWAPI.util.FileUtilities;

import ch.qos.logback.classic.Logger;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;


/**
 * 
 * @author Francesco Zaritto
 *
 */
@SpringBootApplication
public class OwapiApplication {
     
	
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(OwapiApplication.class);
	
	
	/** ArrayList contenente le coordinate delle città dell'archivio
	 */
	@SuppressWarnings("serial")
	private static final ArrayList<RequestBodyClass> CityList = new ArrayList<RequestBodyClass>() {
		 
	{
		try{
			JSONArray jarr = new JSONArray(FileUtilities.getFileContent("src/main/resources/ElencoCitta.txt"));
			for(int i=0; i<jarr.length();i++) {
				JSONObject o=jarr.getJSONObject(i);
				add(new RequestBodyClass(o.getDouble("lat"),o.getDouble("lon"),1));
			}
		}catch (JSONException e) {e.printStackTrace();}
	}};
											
			
	/** ArrayList contenente i fileIDs delle città 
	 */
@SuppressWarnings("serial")
private static final ArrayList<String> fileIDs = new ArrayList<String> () {
{
	try{
		JSONArray jarr=new JSONArray(FileUtilities.getFileContent("src/main/resources/ElencoCitta.txt"));
		for(int i=0; i<jarr.length();i++) {
			JSONObject o=jarr.getJSONObject(i);
			add(o.getString("fileID"));
		}
	}catch (JSONException e) {e.printStackTrace();}
}};

	
/** Main dell'applicazione generato da Spring boot  
*/
	public static void main(String[] args) {
		SpringApplication.run(OwapiApplication.class, args);
	}



/** metodo void che esegue ogni due ore l'update dell'archivio dati storici delle città
 * 	
 * @throws IndexOufOfBoundException
 */
@Scheduled(cron = "0 0 */2 * * ?")
void updateArchive() {
	
	
	JSONArray dataArray;
	JSONObject actualData;
	int cloud,pressure;
	String thisFileID;
	String site, actualInfo;
	String newArchive="";
	RequestBodyClass singleCity;
	
	
	for(int i=0; i<CityList.size(); i++) {
		
		 thisFileID = fileIDs.get(i);
		 singleCity = CityList.get(i);
		 newArchive ="";
	
		site = "http://localhost:8080/actual?lat="+singleCity.getLat()+"&lon="+singleCity.getLon();
	   
		
		dataArray = new JSONArray(FileUtilities.getSiteContent(site));
		actualData = dataArray.getJSONObject(0);
		
		
		
	     cloud = actualData.getInt("cloud");
	     pressure = actualData.getInt("pressure");
	     
	     actualInfo = ",{\"cloud\":" + cloud + ", \"pressure\":"+pressure+"}]}";
			 
	       newArchive = FileUtilities.getFileContent("src/main/resources/" + thisFileID + ".txt");
	       
	       try {
	       newArchive = newArchive.substring(0,newArchive.length()-2) + actualInfo; 
	       } catch(IndexOutOfBoundsException e) {e.printStackTrace();}
		      FileUtilities.overWrite("src/main/resources/" + thisFileID + ".txt", newArchive);
			

	} LOGGER.info("Archive updated successfully");
		
}


@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {}


}
