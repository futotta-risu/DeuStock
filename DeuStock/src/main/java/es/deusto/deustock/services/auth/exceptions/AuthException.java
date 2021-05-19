package es.deusto.deustock.services.auth.exceptions;

/**
 * Base error for authentication service
 *
 * @author Erik B. Terres
 */
public abstract class AuthException extends IllegalStateException{
    protected AuthException(String message){
        super(message);
    }
}
