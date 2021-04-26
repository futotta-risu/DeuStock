package es.deusto.deustock.client.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockTest {

    @Test
    public void testSetPrice() {
        Stock stock = new Stock("AMZN", 100);
        stock.setPrice(200);
        assertEquals(200, stock.getPrice(), 0.02);
    }
}