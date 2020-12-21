package com.progettoOOP.OWAPI.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.model.CityData;
import com.progettoOOP.OWAPI.model.CityDataStats;
import com.progettoOOP.OWAPI.model.CityDataStatsAll;
import com.progettoOOP.OWAPI.model.MaxVarianceCity;
import com.progettoOOP.OWAPI.service.OpenWeather;

class TestOpenWeather {

	@BeforeEach
	void setUp() throws Exception{}

	@AfterEach
	void tearDown() throws Exception {}
	
	
	@Test
	@DisplayName("Test funzionamento eccezioni")
	void exceptionTest() {
		IllegalArgumentException e;
		
		e=assertThrows(
				IllegalArgumentException.class,
				()->{OpenWeather.APIcall(0, 0, 1, 1, "null");}
				);
		assertTrue(e.getMessage().contains("Invalid type string"));
		
		e=assertThrows(
				IllegalArgumentException.class,
				()->{OpenWeather.APIcall(91, 0, 1, 1, "cloud");}
				);
		assertTrue(e.getMessage().contains("Invalid latitude"));
		
		e=assertThrows(
				IllegalArgumentException.class,
				()->{OpenWeather.APIcall(0, 181, 1, 1, "null");}
				);
		assertTrue(e.getMessage().contains("Invalid longitude"));
		
		e=assertThrows(
				IllegalArgumentException.class,
				()->{OpenWeather.APIcall(0, 0, 1, 0, "null");}
				);
		assertTrue(e.getMessage().contains("Invalid cities' number"));
	}

	
	@Test
	@DisplayName("Test sul counter delle citta'")
	void cntTest() {
		ArrayList<AbstractCityData> a;

		a=OpenWeather.APIcall(42.12, 14.71, 0, 3, "actual");
		assertNotNull(a);
		assertEquals(a.size(),3);
		
		a=OpenWeather.APIcall(42.12, 14.71, 2, 2, "cloud");
		assertNotNull(a);
		assertEquals(a.size(),3);
		
		a=OpenWeather.APIcall(42.12, 14.71, 2, 2, "all");
		assertNotNull(a);
		assertEquals(a.size(),2);	
	}
	

	@Test
	@DisplayName("Test sul periodo di tempo richiesto")
	void periodTest1() {
		ArrayList<AbstractCityData> a;
		
		a=OpenWeather.APIcall(42.12, 14.71, 0, 1, "actual");
		assertTrue(a.get(0) instanceof CityData);
	}

	@Test
	@DisplayName("Test sul periodo di tempo richiesto")
	void periodTest2() {
		ArrayList<AbstractCityData> a;
		a=OpenWeather.APIcall(42.12, 14.71, 1, 1, "cloud");
		assertTrue(a.get(0) instanceof CityDataStats);
		assertTrue(a.get(1) instanceof MaxVarianceCity);
	}
	
	@Test
	@DisplayName("Test sul periodo di tempo richiesto")
	void periodTest3() {
		ArrayList<AbstractCityData> a;
		
		a=OpenWeather.APIcall(42.12, 14.71, 1, 1, "pressure");
		assertTrue(a.get(0) instanceof CityDataStats);
		assertTrue(a.get(1) instanceof MaxVarianceCity);
	}

	@Test
	@DisplayName("Test sul periodo di tempo richiesto")
	void periodTest4() {
		ArrayList<AbstractCityData> a;
				
		a=OpenWeather.APIcall(42.12, 14.71, 1, 1, "all");
		assertTrue(a.get(0) instanceof CityDataStatsAll);	
	}

}

