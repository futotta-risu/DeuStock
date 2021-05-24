package es.deusto.deustock.services.investment.stock.exceptions;

/**
 * Thrown when Invalid Stock Data detected.
 *
 * @author Erik B. Terres
 */
public class InvalidStockQueryDataException extends StockException{
    public InvalidStockQueryDataException(String message){
        super(message);
    }
}
