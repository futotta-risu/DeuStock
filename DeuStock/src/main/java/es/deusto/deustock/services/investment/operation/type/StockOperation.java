package es.deusto.deustock.services.investment.operation.type;

public interface StockOperation {
    double getOpenPrice();
    double getClosePrice(double actualStockPrice);

    OperationType getType();
}
