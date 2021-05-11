package es.deusto.deustock.services.investment.operation.type;

/**
 * @author Erik B. Terres
 */
public class ShortOperation extends Operation{

    public ShortOperation(double stockOpenPrice, double amount) {
        super(stockOpenPrice, amount);
    }

    @Override
    public double getOpenPrice() {
        return 0;
    }

    @Override
    public double getClosePrice(double actualStockPrice) {
        return 0;
    }

    @Override
    public OperationType getType() {
        return OperationType.SHORT;
    }
}
