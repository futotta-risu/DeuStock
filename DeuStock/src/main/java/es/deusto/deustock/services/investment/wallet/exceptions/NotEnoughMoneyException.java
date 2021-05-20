package es.deusto.deustock.services.investment.wallet.exceptions;

/**
 * Thrown when not enough money on operation detected
 *
 * @author Erik B. Terres
 */
public class NotEnoughMoneyException extends WalletException{
    public NotEnoughMoneyException(String message){
        super(message);
    }
}
