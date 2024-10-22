package es.deusto.deustock.data;

import java.util.ArrayList;
import java.util.List;

import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import yahoofinance.histquotes.HistoricalQuote;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;

/**
 * Stock class.
 *
 * @author Erik B. Terres
 */
@PersistenceCapable(detachable = "true")
public class DeuStock {

    @NotPersistent
    double price;
    String fullName;
    @Unique
    String acronym;
    String description;

    /** Stock price history */
    @NotPersistent
    List<HistoricalQuote> history = new ArrayList<>();


    public  DeuStock(){
        // Don't erase. Compulsory for JSON
    }
    public DeuStock(String acronym){
        setAcronym(acronym);
    }

    public DeuStock(StockQueryData data){
        setAcronym(data.getAcronym());
    }


    public double getPrice() {
        return price;
    }
    public DeuStock setPrice(double price) {
        this.price = price;
        return this;
    }
    public String getFullName() { return fullName; }
    public DeuStock setFullName(String fullName) {
        this.fullName = fullName; return this;
    }
    public String getAcronym() { return acronym; }
    public DeuStock setAcronym(String acronym) {
        this.acronym = acronym; return this;
    }
    public String getDescription() { return description; }
    public DeuStock setDescription(String description) {
        this.description = description; return this;
    }
    public List<HistoricalQuote> getHistory() { return history; }
    public DeuStock setHistory(List<HistoricalQuote> history) {
        this.history = history; return this;
    }

    @Override
    public String toString() {
        return "DeuStock{" +
                "price=" + price +
                ", acronym='" + acronym + '\'' +
                '}';
    }
}