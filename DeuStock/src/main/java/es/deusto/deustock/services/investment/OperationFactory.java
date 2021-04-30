package es.deusto.deustock.services.investment;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.services.investment.operations.*;

public class OperationFactory {

    private static OperationFactory instance;

    private OperationFactory(){}

    public static OperationFactory getInstance(){
        if(instance == null){
            instance = new OperationFactory();
        }
        return instance;
    }

    public Operation create(OperationType operation, DeuStock stock, double amount){
        return switch (operation) {
            case LONG -> new LongOperation(stock, amount);
            case SHORT -> new ShortOperation(stock, amount);
        };

    }

}
