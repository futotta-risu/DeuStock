package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;

/**
 * @author Erik B. Terres
 */
public class ShortOperation extends Operation{

    public ShortOperation(DeuStock stock, double amount) {
        super(stock, amount);
    }

    @Override
    public double getOpenPrice() {
        return 0;
    }

    @Override
    public double getClosePrice() {
        return 0;
    }

    @Override
    public OperationType getType() {
        return null;
    }
}
