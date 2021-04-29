package es.deusto.deustock.simulation.investment.operations;

public interface StockOperation {
    double getOpenPrice();
    double getClosePrice();

    OperationType getType();
}
