package es.deusto.deustock.data.dto.stocks;

import es.deusto.deustock.services.investment.operations.OperationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erik B. Terres
 */
public class StockHistoryDTOTest {

    @Test
    @DisplayName("Test Constructor does not return null")
    void testConstructorDoesNotReturnNull(){
        StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        assertNotNull(stockHistoryDTO);
    }


    @Test
    @DisplayName("Test Constructor does not throw error")
    void testConstructorDoesNotThrowError(){
        assertDoesNotThrow(StockHistoryDTO::new);
    }
    @Test
    @DisplayName("Test setID works")
    void testSetterID() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();

        //when
        stockHistoryDTO.setId(5);

        //then
        final Field field = stockHistoryDTO.getClass().getDeclaredField("id");
        field.setAccessible(true);

        assertEquals(field.get(stockHistoryDTO), (long) 5);
    }

    @Test
    @DisplayName("Test getID works")
    void testGetterId() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        final Field field = stockHistoryDTO.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(stockHistoryDTO, 2);

        //when
        final long result = stockHistoryDTO.getId();

        //then
        assertEquals( result,  2);
    }

    @Test
    @DisplayName("Test setSymbol works")
    void testSetterSymbol() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();

        //when
        stockHistoryDTO.setSymbol("BB");

        //then
        final Field field = stockHistoryDTO.getClass().getDeclaredField("symbol");
        field.setAccessible(true);

        assertEquals(field.get(stockHistoryDTO), "BB");
    }

    @Test
    @DisplayName("Test getSymbol works")
    void testGetterSymbol() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        final Field field = stockHistoryDTO.getClass().getDeclaredField("symbol");
        field.setAccessible(true);
        field.set(stockHistoryDTO, "AMZN");

        //when
        final String result = stockHistoryDTO.getSymbol();

        //then
        assertEquals( result, "AMZN");
    }

    @Test
    @DisplayName("Test setOpenPrice works")
    void testSetterOpenPrice() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();

        //when
        stockHistoryDTO.setOpenPrice(44.3);

        //then
        final Field field = stockHistoryDTO.getClass().getDeclaredField("openPrice");
        field.setAccessible(true);

        assertEquals(field.get(stockHistoryDTO), 44.3);
    }

    @Test
    @DisplayName("Test getOpenPrice works")
    void testGetterOpenPrice() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        final Field field = stockHistoryDTO.getClass().getDeclaredField("openPrice");
        field.setAccessible(true);
        field.set(stockHistoryDTO, 44.6);

        //when
        final double result = stockHistoryDTO.getOpenPrice();

        //then
        assertEquals( result, 44.6);
    }

    @Test
    @DisplayName("Test setActualPrice works")
    void testSetterActualPrice() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();

        //when
        stockHistoryDTO.setActualPrice(44.3);

        //then
        final Field field = stockHistoryDTO.getClass().getDeclaredField("actualPrice");
        field.setAccessible(true);

        assertEquals(field.get(stockHistoryDTO), 44.3);
    }

    @Test
    @DisplayName("Test getActualPrice works")
    void testGetterActualPrice() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        final Field field = stockHistoryDTO.getClass().getDeclaredField("actualPrice");
        field.setAccessible(true);
        field.set(stockHistoryDTO, 44.6);

        //when
        final double result = stockHistoryDTO.getActualPrice();

        //then
        assertEquals( result, 44.6);
    }

    @Test
    @DisplayName("Test setAmount works")
    void testSetterAmount() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();

        //when
        stockHistoryDTO.setAmount(44.3);

        //then
        final Field field = stockHistoryDTO.getClass().getDeclaredField("amount");
        field.setAccessible(true);

        assertEquals(field.get(stockHistoryDTO), 44.3);
    }

    @Test
    @DisplayName("Test getAmount works")
    void testGetterAmount() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        final Field field = stockHistoryDTO.getClass().getDeclaredField("amount");
        field.setAccessible(true);
        field.set(stockHistoryDTO, 44.6);

        //when
        final double result = stockHistoryDTO.getAmount();

        //then
        assertEquals( result, 44.6);
    }

    @Test
    @DisplayName("Test setOpenValue works")
    void testSetterOpenValue() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();

        //when
        stockHistoryDTO.setOpenValue(44.3);

        //then
        final Field field = stockHistoryDTO.getClass().getDeclaredField("openValue");
        field.setAccessible(true);

        assertEquals(field.get(stockHistoryDTO), 44.3);
    }

    @Test
    @DisplayName("Test getOpenValue works")
    void testGetterOpenValue() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        final Field field = stockHistoryDTO.getClass().getDeclaredField("openValue");
        field.setAccessible(true);
        field.set(stockHistoryDTO, 44.6);

        //when
        final double result = stockHistoryDTO.getOpenValue();

        //then
        assertEquals( result, 44.6);
    }

    @Test
    @DisplayName("Test setActualValue works")
    void testSetterActualValue() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();

        //when
        stockHistoryDTO.setActualValue(44.3);

        //then
        final Field field = stockHistoryDTO.getClass().getDeclaredField("actualValue");
        field.setAccessible(true);

        assertEquals(field.get(stockHistoryDTO), 44.3);
    }

    @Test
    @DisplayName("Test getActualValue works")
    void testGetterActualValue() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        final Field field = stockHistoryDTO.getClass().getDeclaredField("actualValue");
        field.setAccessible(true);
        field.set(stockHistoryDTO, 44.6);

        //when
        final double result = stockHistoryDTO.getActualValue();

        //then
        assertEquals( result, 44.6);
    }

    @Test
    @DisplayName("Test setOperation works")
    void testSetterOperation() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();

        //when
        stockHistoryDTO.setOperation(OperationType.LONG);

        //then
        final Field field = stockHistoryDTO.getClass().getDeclaredField("operation");
        field.setAccessible(true);

        assertEquals(field.get(stockHistoryDTO), OperationType.LONG);
    }

    @Test
    @DisplayName("Test getOperation works")
    void testGetterOperation() throws NoSuchFieldException, IllegalAccessException {
        //given
        final StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        final Field field = stockHistoryDTO.getClass().getDeclaredField("operation");
        field.setAccessible(true);
        field.set(stockHistoryDTO, OperationType.SHORT);

        //when
        final OperationType result = stockHistoryDTO.getOperation();

        //then
        assertEquals( result, OperationType.SHORT);
    }
}
