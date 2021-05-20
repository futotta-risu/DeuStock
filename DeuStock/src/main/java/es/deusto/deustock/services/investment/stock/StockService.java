package es.deusto.deustock.services.investment.stock;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;

import es.deusto.deustock.services.investment.stock.exceptions.InvalidStockQueryDataException;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;


import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stock service with all stock methods.
 *
 * @author Erik B. Terres
 */
public class StockService {

    private final List<String> smallList = Arrays.asList("INTC","TSLA") ;
    private final List<String> bigList = Arrays.asList("INTC","TSLA","ETH","BB") ;

    private final Logger logger = Logger.getLogger(StockService.class);

    private StockDAO stockDAO;

    StockDataAPIGateway gateway;


    public StockService(){
        gateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YAHOO_FINANCE);
        stockDAO = StockDAO.getInstance();
    }

    public void setStockDataAPIGateway(StockDataAPIGateway gateway){
        this.gateway = gateway;
    }
    public void setStockDAO(StockDAO stockDAO){
        this.stockDAO = stockDAO;
    }

    public DeuStock getStockDetailData(String symbol, String intervalString, boolean withHistoric) throws StockException {
        if( symbol.isBlank() || intervalString.isBlank() ){
            throw new InvalidStockQueryDataException("Invalid symbol");
        }
        DeuStock stock;
        try {
            if(!stockDAO.has(symbol)){
                stockDAO.store(new DeuStock(symbol));
            }
            stock = stockDAO.get(symbol);
        } catch (SQLException sqlException) {
            throw new InvalidStockQueryDataException("Invalid symbol");
        }
        try {
            var interval = StockQueryData.Interval.valueOf(intervalString);

            var returnStock = gateway.getStockData(
                    new StockQueryData(symbol,interval).setWithHistoric(withHistoric)
            );

            stock.setHistory(returnStock.getHistory());
            stock.setPrice(returnStock.getPrice());
            return stock;

        } catch (IllegalArgumentException e){
            throw new InvalidStockQueryDataException("Invalid interval");
        } catch (StockNotFoundException e) {
            throw new InvalidStockQueryDataException("Stock not found.");
        }

    }

    public DeuStock getStockDetailData(String symbol, String intervalString) throws StockException {
        return getStockDetailData(symbol, intervalString, true);
    }

    public DeuStock getStockWithPrice(String symbol) throws StockException {
        return getStockDetailData(symbol, "DAILY", false);
    }


    public List<DeuStock> getStockListData(String size){

        List<String> stockList;

        stockList = switch(size){
            case "small" -> smallList;
            case "big" ->  bigList;
            default ->  throw new IllegalArgumentException("Invalid list name");
        };

        for(String stockName : stockList){
            try{
                if(!stockDAO.has(stockName)){
                    stockDAO.store(new DeuStock(stockName));
                }
            }catch (SQLException e){
                logger.error(String.format("Error saving info of %s", stockName));
            }
        }

        return new ArrayList<>(
                gateway.getStocksData(stockList).values()
        );
    }

    public List<DeuStock> getStocksWithPrice(List<DeuStock> stocks){
        List<String> stockNames = stocks.stream().map(DeuStock::getAcronym).collect(Collectors.toList());

        return new ArrayList<>(
                gateway.getStocksData(stockNames).values()
        );
    }
}
