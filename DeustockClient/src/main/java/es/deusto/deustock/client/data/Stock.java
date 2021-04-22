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

    
    public Stock() {}
    
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
    
	public double calcularMediaPrecio() {
		double result = 0;
		for (HistoricalQuote hq : this.history) {
			result += Double.parseDouble(hq.getClose().toString());
		}
		result = result / this.history.size();
		return result;
	}
	
	public double calcularSD(){
        double result = 0.0, standardDeviation = 0.0;
        
        for(HistoricalQuote hq : this.history) {
            result += Double.parseDouble(hq.getClose().toString());
        }
        double mean = result/this.history.size();
        for(HistoricalQuote hq : this.history) {
            standardDeviation += Math.pow(Double.parseDouble(hq.getClose().toString()) - mean, 2);
        }

        return Math.sqrt(standardDeviation/this.history.size());
    }
	
}
