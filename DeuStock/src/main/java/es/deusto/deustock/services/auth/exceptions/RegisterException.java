package es.deusto.deustock.services.auth.exceptions;

/**
 * Exception thrown on the register process
 */
public class RegisterException extends AuthException{
    public RegisterException(String message){
        super(message);
    }
}
