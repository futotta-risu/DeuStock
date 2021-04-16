package es.deusto.deustock.client.data;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

public class StockTest {

    @Test
    public void testSetPrice() {
        Stock stock = new Stock("AMZN", new BigDecimal(100));
        stock.setPrice(new BigDecimal(200));
        assertTrue(new BigDecimal(200).equals(stock.getPrice()));
    }
}