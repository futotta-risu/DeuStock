package es.deusto.deustock.dataminer.gateway.socialnetworks.exceptions;

/**
 * Exception to secure the gateway preparation
 */
public class NoGatewayTypeException extends Exception{
    public NoGatewayTypeException(String message){
        super(message);
    }
}
