package es.deusto.deustock.data;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import org.junit.jupiter.api.function.ThrowingSupplier;
import yahoofinance.histquotes.HistoricalQuote;

public class DeuStockTest {
	
	@Test
	public void testConstructor0() {
		DeuStock deustock = new DeuStock();
		assertDoesNotThrow((ThrowingSupplier<DeuStock>) DeuStock::new);
		assertNotNull(deustock);
	}
	
	@Test
	public void testConstructor1() {
		DeuStock deustock = new DeuStock("AZ");
		assertEquals("AZ", deustock.getAcronym());
	}
	
	@Test
	public void testConstructor2() {
		StockQueryData sqd = new StockQueryData("AZ");
		DeuStock deustock = new DeuStock(sqd);
		assertEquals("AZ", deustock.getAcronym());
	}

	@Test
	public void testGetPrice() {
		DeuStock deustock = new DeuStock();
		deustock.setPrice(50);
		assertEquals(50, deustock.getPrice(), 0.02);
	}
	
	@Test
	public void testSetPrice() {
		DeuStock deustock = new DeuStock();
		deustock.setPrice(50);
		assertEquals(50, deustock.getPrice(), 0.02);
	}
	
	@Test
	public void testGetFullName() {
		DeuStock deustock = new DeuStock();
		deustock.setFullName("Aritz Zugazaga");
		assertEquals("Aritz Zugazaga", deustock.getFullName());
	}
	
	@Test
	public void testSetFullName() {
		DeuStock deustock = new DeuStock();
		deustock.setFullName("Aritz Zugazaga");
		assertEquals("Aritz Zugazaga", deustock.getFullName());
	}
	
	@Test
	public void testGetAcronym() {
		DeuStock deustock = new DeuStock("AZ");
		assertEquals("AZ", deustock.getAcronym());
	}
	
	@Test
	public void testSetAcronym() {
		DeuStock deustock = new DeuStock("AZ");
		deustock.setAcronym("LS");
		assertEquals("LS", deustock.getAcronym());
	}

	@Test
	public void testGetDescription() {
		DeuStock deustock = new DeuStock();
		deustock.setDescription("Hola me gusta la CocaCola");
		assertEquals("Hola me gusta la CocaCola", deustock.getDescription());
	}
	
	@Test
	public void testSetDescription() {
		DeuStock deustock = new DeuStock();
		deustock.setDescription("Hola me gusta la CocaCola");
		assertEquals("Hola me gusta la CocaCola", deustock.getDescription());
	}
	
	@Test
	public void testGetHistory() {
		DeuStock deustock = new DeuStock();
		List<HistoricalQuote> list = new ArrayList<>();
		HistoricalQuote HQ = new HistoricalQuote();
		HQ.setSymbol("BTC");
		list.add(HQ);
		deustock.setHistory(list);
		assertSame(list, deustock.getHistory());
	}
	
	@Test
	public void testSetHistory() {
		DeuStock deustock = new DeuStock();
		List<HistoricalQuote> list = new ArrayList<>();
		HistoricalQuote HQ = new HistoricalQuote();
		HQ.setSymbol("BTC");
		list.add(HQ);
		deustock.setHistory(list);
		assertSame(list, deustock.getHistory());
	}
	
	@Test
	public void testToString() {
		DeuStock deustock = new DeuStock("AZ");
		deustock.setPrice(50);
		String actuals = deustock.toString();
		String expected = "DeuStock{price=50.0, acronym='AZ'}";
		assertEquals(expected, actuals);
	}
}