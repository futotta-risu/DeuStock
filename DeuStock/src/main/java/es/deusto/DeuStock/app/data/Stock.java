package es.deusto.DeuStock.app.data;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Unique;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

@PersistenceCapable
public class Stock implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotPersistent
	BigDecimal price;
	
	String fullName;
	@Unique
	String acronym;
	String description;
	
	//Price per hours last 24h
	@NotPersistent
	List<HistoricalQuote> ppdLastWeek = new ArrayList<HistoricalQuote>();
	
	

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
	public List<HistoricalQuote> getppdLastWeek() {
		return ppdLastWeek;
	}
	public void setPphLast24h(List<HistoricalQuote> ppdLastWeek) {
		this.ppdLastWeek = ppdLastWeek;
	}
	
	
	
	public Stock(String acronym, String description) {
		this.acronym = acronym;
		this.description = description;

		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.WEEK_OF_YEAR, -1); // from 1 WEEK ago
		try {
			yahoofinance.Stock stock = YahooFinance.get(acronym, from, to, Interval.DAILY);
			this.price = stock.getQuote().getPrice();
			this.fullName = stock.getName();
			this.ppdLastWeek = stock.getHistory();
		} catch (IOException e) {
			System.out.println("Error AL CONSTRUIR STOCK");
			e.printStackTrace();
		}

	}
	
	public Stock() {
		this.price = BigDecimal.ZERO;
		this.fullName = "NULL";
		this.acronym = "NULL";
		this.description = "NULL";
		this.ppdLastWeek = null;
	}
	@Override
	public String toString() {
		return "Stock [price=" + price + ", fullName=" + fullName + ", acronym=" + acronym + ", description="
				+ description + ", ppdLastWeek=" + ppdLastWeek + "]";
	}
	
	public void refreshPrices() throws IOException{	
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.WEEK_OF_YEAR, -1); // from 1 WEEK ago
		yahoofinance.Stock stock = YahooFinance.get(this.acronym, from, to, Interval.DAILY);
		this.price = stock.getQuote().getPrice();
		this.ppdLastWeek = stock.getHistory();
	}
	
}