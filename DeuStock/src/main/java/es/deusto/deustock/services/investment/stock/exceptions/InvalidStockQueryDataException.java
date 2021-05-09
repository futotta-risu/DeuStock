package es.deusto.deustock.services.investment.stock.exceptions;

public class InvalidStockQueryDataException extends Exception{
    public InvalidStockQueryDataException(String message){
        super(message);
    }
}
