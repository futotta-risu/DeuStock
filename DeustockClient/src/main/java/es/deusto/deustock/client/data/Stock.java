package es.deusto.deustock.client.data;

import yahoofinance.histquotes.HistoricalQuote;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Erik B. Terres
 */
public class Stock {

    double price;
    String fullName;
    String acronym;
    String description;

    List<HistoricalQuote> history = new ArrayList<>();


    // Compulsory for JSON conversion. Don't delete.
    public Stock(){}

    public Stock(String acronym, double price){
        this.acronym =acronym;
        this.price = price;
    }



    public double getPrice() { return price; }
    public Stock setPrice(double price) {
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
