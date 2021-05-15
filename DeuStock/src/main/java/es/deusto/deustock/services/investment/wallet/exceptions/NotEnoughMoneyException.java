package es.deusto.deustock.services.investment.wallet.exceptions;

public class NotEnoughMoneyException extends WalletException{
    public NotEnoughMoneyException(String message){
        super(message);
    }
}
