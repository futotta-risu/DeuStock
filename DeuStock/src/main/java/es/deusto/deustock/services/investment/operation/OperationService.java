package es.deusto.deustock.services.investment.operation;

import es.deusto.deustock.services.investment.operation.type.OperationType;

/**
 *
 * Service for operations
 *
 * @author Erik B. Terres
 */
public class OperationService {

    OperationFactory operationFactory;

    public OperationService(){ }

    public void setOperationFactory(OperationFactory factory){
        this.operationFactory = factory;
    }


    public double getOpenPrice(OperationType operationType, double openPrice, double amount) {
        System.out.println("Entrando en GETOPENPRICE");
        return operationFactory.create(operationType, openPrice, amount).getOpenPrice();
    }

    public double getClosePrice(OperationType operationType, double openPrice, double closePrice, double amount) {
        return operationFactory.create(operationType, openPrice, amount).getClosePrice(closePrice);
    }

}
