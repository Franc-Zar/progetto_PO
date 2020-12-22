package com.progettoOOP.OWAPI.model;


/** Classe public che rappresenta il tipo di dato, utilizzato all'interno dell'applicazione, per eseguire
 *  le richieste in POST al fine di aggiungere/rimuovere città dall'elenco di monitoraggio
 *  
 * 
 * @author Francesco Zaritto
 *
 */
public class RequestMonitoringClass extends AbstractCityData {

	private String response;

	public RequestMonitoringClass(double lat, double lon, String name, String response) {
		super(lat, lon, name);
		this.response = response;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}


}
