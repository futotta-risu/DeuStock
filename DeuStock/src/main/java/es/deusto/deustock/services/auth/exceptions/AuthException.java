package es.deusto.deustock.services.auth.exceptions;

/**
 * Base error for authentication service
 */
public abstract class AuthException extends IllegalStateException{
    public AuthException(String message){
        super(message);
    }
}
