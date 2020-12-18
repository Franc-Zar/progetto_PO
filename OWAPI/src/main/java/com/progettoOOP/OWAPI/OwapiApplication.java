package com.progettoOOP.OWAPI;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.progettoOOP.OWAPI.model.RequestBodyClass;
import com.progettoOOP.OWAPI.util.FileUtilities;

import ch.qos.logback.classic.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**Main dell'applicazione generato da Spring boot  
*/

@SpringBootApplication
public class OwapiApplication {
     
	
	
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(OwapiApplication.class);
	
	@SuppressWarnings("serial")
	private static final ArrayList<RequestBodyClass> CityList = new ArrayList<RequestBodyClass>() {
		 
	{
			 add(new RequestBodyClass(42.88,13.91,1));
					add(new RequestBodyClass(42.93,13.89,1));
							add(new RequestBodyClass(42.87,13.87,1));
									add(new RequestBodyClass(42.12,14.71,1));
											add(new RequestBodyClass(42.09,14.73,1));
											}};
											
			
	
@SuppressWarnings("serial")
private static final ArrayList<Integer> fileIDs = new ArrayList<Integer> () {


{
		
	for(int i=0; i<CityList.size();i++) add(i+1);
		
	}};

	
	
	public static void main(String[] args) {
		SpringApplication.run(OwapiApplication.class, args);
	}



@Scheduled(cron = "0 0 */2 * * ?")	
void updateArchive() {
	
	
	JSONArray dataArray;
	JSONObject actualData;
	int cloud,pressure,thisFileID;
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
	       
	       newArchive = newArchive.substring(0,newArchive.length()-2) + actualInfo; 
		     
		      FileUtilities.overWrite("src/main/resources/" + thisFileID + ".txt", newArchive);
			

	} LOGGER.info("Archive updated successfully");
	
		
}


@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {}


}
