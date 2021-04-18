package es.deusto.deustock.client.data;

import yahoofinance.histquotes.HistoricalQuote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erik B. Terres
 */
public class Stock {

    BigDecimal price;
    String fullName;
    String acronym;
    String description;
    //Price per hours last 24h
    List<HistoricalQuote> history = new ArrayList<>();

    
    public Stock(String acronym, BigDecimal price){
        this.acronym =acronym;
        this.price = price;
    }

    
    public BigDecimal getPrice() { return price; }
    public Stock setPrice(BigDecimal price) {
        this.price = price; return this;
    }
    public String getFullName() { return fullName; }
    public Stock setFullName(String fullName) {
        this.fullName = fullName; return this;
    }
    public String getAcronym() { return acronym; }
    public Stock setAcronym(String acronym) {
        this.acronym = acronym; return this;
    }
    public String getDescription() { return description; }
    public Stock setDescription(String description) {
        this.description = description; return this;
    }
    public List<HistoricalQuote> getHistory() { return history; }
    public Stock setHistory(List<HistoricalQuote> history) {
        this.history = history; return this;
    }
}
