package es.deusto.deustock.data;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import yahoofinance.histquotes.HistoricalQuote;

public class DeuStockTest {
	
	@Test
	public void testConstructor1() {
		DeuStock deustock = new DeuStock("AZ");
		assertTrue("Constructor1 doesn't work", deustock.getAcronym() == "AZ");
	}
	
	@Test
	public void testConstructor2() {
		StockQueryData sqd = new StockQueryData("AZ");
		DeuStock deustock = new DeuStock(sqd);
		assertTrue("Constructor2 doesn't work", deustock.getAcronym() == "AZ");
	}

	@Test
	public void testGetPrice() {
		DeuStock deustock = new DeuStock();
		BigDecimal decimal = new BigDecimal(50);
		deustock.setPrice(decimal);
		assertTrue("getPrice doesn't work", deustock.getPrice() == decimal);
	}
	
	@Test
	public void testSetPrice() {
		DeuStock deustock = new DeuStock();
		BigDecimal decimal = new BigDecimal(50);
		deustock.setPrice(decimal);
		assertTrue("setPrice doesn't work", deustock.getPrice() == decimal);
	}
	
	@Test
	public void testGetFullName() {
		DeuStock deustock = new DeuStock();
		deustock.setFullName("Aritz Zugazaga");
		assertTrue("getFullName doesn't work", deustock.getFullName() == "Aritz Zugazaga");
	}
	
	@Test
	public void testSetFullName() {
		DeuStock deustock = new DeuStock();
		deustock.setFullName("Aritz Zugazaga");
		assertTrue("setFullName doesn't work", deustock.getFullName() == "Aritz Zugazaga");
	}
	
	@Test
	public void testGetAcronym() {
		DeuStock deustock = new DeuStock("AZ");
		assertTrue("getAcronym doesn't work", deustock.getAcronym() == "AZ");
	}
	
	@Test
	public void testSetAcronym() {
		DeuStock deustock = new DeuStock("AZ");
		deustock.setAcronym("LS");
		assertTrue("setAcronym doesn't work", deustock.getAcronym() == "LS");
	}

	@Test
	public void testGetDescription() {
		DeuStock deustock = new DeuStock();
		deustock.setDescription("Hola me gusta la CocaCola");
		assertTrue("getDescription doesn't work", deustock.getDescription() == "Hola me gusta la CocaCola");
	}
	
	@Test
	public void testSetDescription() {
		DeuStock deustock = new DeuStock();
		deustock.setDescription("Hola me gusta la CocaCola");
		assertTrue("setDescription doesn't work", deustock.getDescription() == "Hola me gusta la CocaCola");
	}
	
	@Test
	public void testGetHistory() {
		DeuStock deustock = new DeuStock();
		List<HistoricalQuote> list = new ArrayList<HistoricalQuote>();
		HistoricalQuote HQ = new HistoricalQuote();
		HQ.setSymbol("BTC");
		list.add(HQ);
		deustock.setHistory(list);
		assertTrue("getHistory doesn't work", deustock.getHistory() == list);
	}
	
	@Test
	public void testSetHistory() {
		DeuStock deustock = new DeuStock();
		List<HistoricalQuote> list = new ArrayList<HistoricalQuote>();
		HistoricalQuote HQ = new HistoricalQuote();
		HQ.setSymbol("BTC");
		list.add(HQ);
		deustock.setHistory(list);
		assertTrue("setHistory doesn't work", deustock.getHistory() == list);
	}
	
	@Test
	public void testToString() {
		DeuStock deustock = new DeuStock("AZ");
		BigDecimal decimal = new BigDecimal(50);
		deustock.setPrice(decimal);
		assertTrue("toString doesn't work", deustock.toString() == "DeuStock{" +
                												   "price=" + deustock.getPrice() +
                												   ", acronym='" + deustock.getAcronym() + '\'' +
                												   '}');
	}
}
