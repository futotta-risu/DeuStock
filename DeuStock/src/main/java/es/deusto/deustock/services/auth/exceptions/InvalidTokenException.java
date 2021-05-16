package es.deusto.deustock.services.auth.exceptions;

public class InvalidTokenException extends AuthException {
    public InvalidTokenException(String message){
        super(message);
    }
}
