package es.deusto.deustock.services.auth.exceptions;

/**
 * Exception thrown on the register process
 *
 * @author Erik B. Terres
 */
public class RegisterException extends AuthException{
    public RegisterException(String message){
        super(message);
    }
}
