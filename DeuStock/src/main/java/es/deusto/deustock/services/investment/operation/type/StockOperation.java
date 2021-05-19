package es.deusto.deustock.services.investment.operation.type;

/**
 * Stock operation functions.
 *
 * @author Erik B. Terres
 */
public interface StockOperation {
    double getOpenPrice();
    double getClosePrice(double actualStockPrice);

    OperationType getType();
}
