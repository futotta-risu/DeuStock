package es.deusto.deustock.services.auth.exceptions;

/**
 * Thrown when Invalid Token detected.
 *
 * @author Erik B. Terres
 */
public class InvalidTokenException extends AuthException {
    public InvalidTokenException(String message){
        super(message);
    }
}
