package com.progettoOOP.OWAPI.model;

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