package es.deusto.deustock.services.investment.operation;

import es.deusto.deustock.services.investment.operation.type.*;

public class OperationFactory {

    private static OperationFactory instance;

    private OperationFactory(){}

    public static OperationFactory getInstance(){
        if(instance == null){
            instance = new OperationFactory();
        }
        return instance;
    }

    public Operation create(OperationType operation, double stockOpenPrice, double amount){
        return switch (operation) {
            case LONG -> new LongOperation(stockOpenPrice, amount);
            case SHORT -> new ShortOperation(stockOpenPrice, amount);
        };

    }

}
