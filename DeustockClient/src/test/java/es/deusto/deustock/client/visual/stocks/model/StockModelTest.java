package es.deusto.deustock.client.visual.stocks.model;

import junit.framework.TestCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class StockModelTest{

    StockModel stockModel;

    @BeforeEach
    public void setUp(){
        stockModel = new StockModel("23", "12.2", "6.2");
    }

    @Test
    public void testConstructor(){
        assertEquals("23", stockModel.getPrice());
        assertEquals("12.2", stockModel.getMedia());
        assertEquals("6.2", stockModel.getDesviacionEstandar());
        assertNotNull(stockModel);

    }

    @Test
    public void getPrice() { assertEquals("23", stockModel.getPrice()); }

    @Test
    public void getMedia() { assertEquals("12.2", stockModel.getMedia()); }

    @Test
    public void getDesviacionEstandar() { assertEquals("6.2", stockModel.getDesviacionEstandar()); }
}