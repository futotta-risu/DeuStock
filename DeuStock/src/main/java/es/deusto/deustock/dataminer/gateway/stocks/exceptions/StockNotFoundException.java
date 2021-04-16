package es.deusto.deustock.dataminer.gateway.stocks.exceptions;

import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;

/**
 * @author Erik B. Terres
 */
public class StockNotFoundException  extends Exception{
    public StockNotFoundException(StockQueryData stockQuery){
        super("Could not find stock " + stockQuery.getAcronym() + ".");
    }
}
