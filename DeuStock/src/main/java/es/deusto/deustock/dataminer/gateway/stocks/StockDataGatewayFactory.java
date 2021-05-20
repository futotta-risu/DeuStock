package es.deusto.deustock.dataminer.gateway.stocks;

import es.deusto.deustock.dataminer.gateway.stocks.gateways.YahooFinanceGateway;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Factory for the {@link StockDataAPIGateway}.
 *
 * @author Erik B. Terres
 */
public class StockDataGatewayFactory {

    private static StockDataGatewayFactory instance = null;

    private StockDataGatewayFactory(){}

    public static StockDataGatewayFactory getInstance(){
        if(instance == null)
            instance = new StockDataGatewayFactory();

        return instance;
    }

    public StockDataAPIGateway create(StockDataGatewayEnum type){
        Objects.requireNonNull(type);
        return switch (type) {
            case YAHOO_FINANCE -> YahooFinanceGateway.getInstance();
        };
    }


}
