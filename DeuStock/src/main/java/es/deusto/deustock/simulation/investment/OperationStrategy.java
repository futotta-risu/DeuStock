package es.deusto.deustock.simulation.investment;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.OperationType;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.simulation.investment.operations.LongOperation;

public class OperationStrategy {

    private  OperationStrategy(){}

    public static void operate(Wallet wallet, DeuStock stock, OperationType operation){
        switch (operation){
            case LONG:
                LongOperation.operate(wallet, stock);
                break;
        }

    }

}
