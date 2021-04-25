package es.deusto.deustock.client.simulation.investment.operations;


public interface StockOperation {
    double getOpenPrice();
    double getClosePrice(double actualPrice);

    OperationType getType();
}
