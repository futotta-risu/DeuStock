package es.deusto.deustock.services.investment.wallet.exceptions;

/**
 * Thrown when no wallet found.
 *
 * @author Erik B. Terres
 */
public class WalletNotFoundException extends WalletException{
    public WalletNotFoundException(String message){
        super(message);
    }
}
