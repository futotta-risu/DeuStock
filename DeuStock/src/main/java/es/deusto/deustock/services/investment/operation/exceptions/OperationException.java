package es.deusto.deustock.services.investment.operation.exceptions;

/**
 * Thrown when operation fails.
 *
 * @author Erik B. Terres
 */
public class OperationException extends Exception{
    public OperationException(String message){
        super(message);
    }
}
