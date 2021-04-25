package es.deusto.deustock.simulation.investment;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.simulation.investment.operations.Operation;
import es.deusto.deustock.simulation.investment.operations.OperationType;
import es.deusto.deustock.simulation.investment.operations.LongOperation;
import es.deusto.deustock.simulation.investment.operations.StockOperation;

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
            case SHORT -> new LongOperation(stock, amount);
        };

    }

}
