package es.deusto.deustock.services.investment.operations;

public interface StockOperation {
    double getOpenPrice();
    double getClosePrice();

    OperationType getType();
}
