package com.progettoOOP.OWAPI.Tests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
* @author Luigi Smargiassi
*/

@RunWith(JUnitPlatform.class)
@SelectClasses({ArchiveTest.class,UtilitiesTest.class,TestCityData.class,TestOpenWeather.class})
public class AllTests {
	
}
