package es.deusto.deustock.services.investment.stock;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.log.DeuLogger;

import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData.Interval;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.stock.exceptions.InvalidStockQueryDataException;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import org.ejml.data.DEigenpair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StockService {

    private final List<String> smallList = Arrays.asList("INTC","TSLA") ;
    private final List<String> bigList = Arrays.asList("INTC","TSLA","ETH","BB") ;

    private final Logger logger = LoggerFactory.getLogger(StockService.class);

    private StockDAO stockDAO;

    StockDataAPIGateway gateway;


    public StockService(){
        gateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
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
            Interval interval = StockQueryData.Interval.valueOf(intervalString);

            DeuStock returnStock = gateway.getStockData(
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

        switch(size){
            case "small" -> stockList = smallList;
            case "big" ->  stockList =  bigList;
            default ->  throw new IllegalArgumentException("Invalid list name");
        };

        for(String stockName : stockList){
            try{
                if(!stockDAO.has(stockName)){
                    stockDAO.store(new DeuStock(stockName));
                }
            }catch (SQLException e){
                logger.error("Error saving info of {}", stockName);
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
