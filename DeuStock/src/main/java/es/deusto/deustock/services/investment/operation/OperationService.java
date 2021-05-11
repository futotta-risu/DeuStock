package es.deusto.deustock.services.investment.operation;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.operation.type.Operation;
import es.deusto.deustock.services.investment.operation.type.OperationType;

import java.sql.SQLException;

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
        return operationFactory.create(operationType, openPrice, amount).getOpenPrice();
    }

    public double getClosePrice(OperationType operationType, double openPrice, double closePrice, double amount) {
        return operationFactory.create(operationType, openPrice, amount).getClosePrice(closePrice);
    }

}
