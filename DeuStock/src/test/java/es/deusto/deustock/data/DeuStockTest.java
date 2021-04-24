package es.deusto.deustock.data;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import yahoofinance.histquotes.HistoricalQuote;

public class DeuStockTest {

	@Test
	public void testPrice() {
		DeuStock deustock = new DeuStock();
		BigDecimal testDecimal = new BigDecimal(50);
		BigDecimal expectedDecimal = new BigDecimal(50);
		
		deustock.setPrice(testDecimal);
		
		assertEquals("getPrice and setPrice doesn't work", expectedDecimal, deustock.getPrice());
	}
	
	@Test
	public void testFullName() {
		DeuStock deustock = new DeuStock();
		String testString = "Aritz Zugazaga";
		String expectedString = "Aritz Zugazaga";
		
		deustock.setFullName(testString);
		
		assertEquals("getFullName and setFullName doesn't work", expectedString, deustock.getFullName());
	}
	
	@Test
	public void testAcronym() {
		DeuStock deustock = new DeuStock();
		String testString = "AZ";
		String expectedString = "AZ";
		
		deustock.setAcronym(testString);
		
		assertEquals("getAcronym and setAcronym doesn't work", expectedString, deustock.getAcronym());
	}

	@Test
	public void testDescription() {
		DeuStock deustock = new DeuStock();
		String testString = "Hola me gusta la CocaCola";
		String expectedString = "Hola me gusta la CocaCola";
		
		deustock.setDescription(testString);
		
		assertEquals("getDescription and setDescription doesn't work", expectedString, deustock.getDescription());
	}
	
	@Test
	public void testHistory() {
		DeuStock deustock = new DeuStock();
		List<HistoricalQuote> testList = new ArrayList<HistoricalQuote>();
		List<HistoricalQuote> expectedList = new ArrayList<HistoricalQuote>();
		HistoricalQuote HQ = new HistoricalQuote();
		
		HQ.setSymbol("BTC");
		testList.add(HQ);
		expectedList.add(HQ);
		deustock.setHistory(testList);
		
		assertEquals("getHistory and setHistory doesn't work", expectedList, deustock.getHistory());
	}
}
