package es.deusto.deustock.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;

@PersistenceCapable
public class DeuStock {

    @NotPersistent
    BigDecimal price;
    String fullName;
    @Unique
    String acronym;
    String description;

    //Price per hours last 24h
    @NotPersistent
    List<HistoricalQuote> history = new ArrayList<>();

    public DeuStock(StockDataQueryData data){
        setAcronym(data.getAcronym());
    }


    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getAcronym() {
        return acronym;
    }
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<HistoricalQuote> getHistory() {
        return history;
    }
    public void setHistory(List<HistoricalQuote> history) {
        this.history = history;
    }

}