package es.deusto.deustock.services.auth.exceptions;

/**
 * Exception thrown on the login process
 */
public class LoginException extends AuthException{
    public LoginException(String message){
        super(message);
    }
}
