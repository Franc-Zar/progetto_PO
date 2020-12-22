package com.progettoOOP.OWAPI.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * classe di utilities per la lettura
 *
 * @author Francesco Zaritto
 */
public class FileUtilities {
	
	
	/** metodo public che prende come parametro l'url di una pagina web, apre la connessione e restituisce 
	 *  il contenuto della pagina sotto formato di Stringa (formattata come JSON) 
	 *  
	 *  @param site
	 *  
	 *  @return contenuto della pagina web
	 */
		public static String getSiteContent(String site) {
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
				String line;
				while((line=reader.readLine())!=null)
					json+=line;
				reader.close();
			} catch (IOException e) {e.printStackTrace();}
			return json;
		}
	
	
	/** Metodo public che prende come parametro il path di un file (filePath) e restituisce il contenuto del
	 * file come stringa
	 * 
	 * @param filePath
	 * 
	 * @return contenuto del file
	 * 
	 */
	public static String getFileContent(String filePath)  {

		String content="";
		
		BufferedReader reader = null;
		
		try {
			
			 reader = new BufferedReader(new FileReader(filePath));
			
			try {
			    
				String line="";
				while((line=reader.readLine())!=null) content+=line;
				reader.close();
				
			} catch(IOException e) {e.printStackTrace();;}
			
			
		} catch(FileNotFoundException e) {e.printStackTrace();}
		
		return content;

}
	
	/**
	 *  Metodo public static che prende come parametri il path del file (filePath) e il nuovo contenuto da 
	 *  sovrascrivere nel file (newContent)
	 *  
	 *  @param filePath
	 *  @param newContent
	 *  
	 */
	public static void overWrite(String filePath,String newContent) {
	
	try {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,false));
	
		writer.write(newContent);
		
		writer.close();
		
	
	} catch (IOException e) {e.printStackTrace();}
	
}
}
