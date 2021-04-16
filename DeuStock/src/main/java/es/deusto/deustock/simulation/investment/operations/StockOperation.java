package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.Wallet;

public interface StockOperation {
    static void operate(Wallet wallet, DeuStock stock);
}
