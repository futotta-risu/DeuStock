package es.deusto.deustock.services.auth.exceptions;

/**
 * Exception thrown on the login process.
 *
 * @author Erik B. Terres
 */
public class LoginException extends AuthException{
    public LoginException(String message){
        super(message);
    }
}
