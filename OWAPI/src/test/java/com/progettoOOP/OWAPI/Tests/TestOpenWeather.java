package com.progettoOOP.OWAPI.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.progettoOOP.OWAPI.model.AbstractCityData;
import com.progettoOOP.OWAPI.service.OpenWeather;

@SpringBootTest
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
				
	}

}
