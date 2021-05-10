package es.deusto.deustock.services.investment.stock.exceptions;

public class InvalidStockQueryDataException extends StockException{
    public InvalidStockQueryDataException(String message){
        super(message);
    }
}
