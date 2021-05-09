package es.deusto.deustock.services.investment.stock;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.log.DeuLogger;

import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData.Interval;
import es.deusto.deustock.services.investment.stock.exceptions.InvalidStockQueryDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockService {

    private final List<String> smallList = Arrays.asList("INTC","TSLA") ;
    private final List<String> bigList = Arrays.asList("INTC","TSLA","ETH","BB") ;

    private final Logger logger = LoggerFactory.getLogger(StockService.class);

    StockDataAPIGateway gateway;


    public StockService(){
        gateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
    }

    public void setStockDataAPIGateway(StockDataAPIGateway gateway){
        this.gateway = gateway;
    }

    public DeuStock getStockDetailData(String symbol, String intervalString) throws InvalidStockQueryDataException {
        if( symbol.isBlank() || intervalString.isBlank() ){
            throw new InvalidStockQueryDataException("Invalid symbol");
        }
        try {
            Interval interval = StockQueryData.Interval.valueOf(intervalString);

            return gateway.getStockData(
                    new StockQueryData(symbol,interval).setWithHistoric(true)
            );

        } catch (IllegalArgumentException e){
            throw new InvalidStockQueryDataException("Invalid interval");
        } catch (StockNotFoundException e) {
            throw new InvalidStockQueryDataException("Stock not found.");
        }
    }


    public List<DeuStock> getStockListData(String size){

        List<String> stockList;

        switch(size){
            case "small" -> stockList = smallList;
            case "big" ->  stockList =  bigList;
            default ->  throw new IllegalArgumentException("Invalid list name");
        };


        return new ArrayList<>(
                gateway.getStocksData(stockList).values()
        );
    }
}
