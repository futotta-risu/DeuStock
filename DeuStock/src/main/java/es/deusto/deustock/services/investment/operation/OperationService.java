package es.deusto.deustock.services.investment.operation;

import es.deusto.deustock.services.investment.operation.type.OperationType;


/**
 * Class containing all operation services.
 *
 * @author Erik B. Terres
 */
public class OperationService {

    OperationFactory operationFactory;

    public OperationService(){
        operationFactory = OperationFactory.getInstance();
    }

    public void setOperationFactory(OperationFactory factory){
        this.operationFactory = factory;
    }


    public double getOpenPrice(OperationType operationType, double openPrice, double amount) {
        return operationFactory.create(operationType, openPrice, amount).getOpenPrice();
    }

    public double getClosePrice(OperationType operationType, double openPrice, double closePrice, double amount) {
        return operationFactory.create(operationType, openPrice, amount).getClosePrice(closePrice);
    }

}
