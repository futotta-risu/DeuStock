package es.deusto.deustock.dataminer.gateway.stocks;

import es.deusto.deustock.dataminer.gateway.stocks.gateways.YahooFinanceGateway;

/**
 * Factory for the SocialNetworkGateway.
 *
 *  @author Erik B. Terres
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
        return switch (type) {
            case YahooFinance -> YahooFinanceGateway.getInstance();
        };
    }


}
