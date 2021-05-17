package es.deusto.deustock.client.controllers.exceptions;

public class EmptyFieldsException extends Exception{
    public EmptyFieldsException(String message){
        super(message);
    }
}
