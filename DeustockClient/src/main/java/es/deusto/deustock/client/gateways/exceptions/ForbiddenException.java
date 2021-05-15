package es.deusto.deustock.client.gateways.exceptions;

public class ForbiddenException extends Exception{
    public ForbiddenException(String message){
        super(message);
    }
}
