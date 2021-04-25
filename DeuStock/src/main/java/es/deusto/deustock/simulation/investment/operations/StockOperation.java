package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;

public interface StockOperation {
    double getOpenPrice();
    double getClosePrice(double actualPrice);

    OperationType getType();
}
