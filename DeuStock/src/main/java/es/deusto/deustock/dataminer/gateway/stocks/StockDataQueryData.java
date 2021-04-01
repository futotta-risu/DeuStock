package es.deusto.deustock.dataminer.gateway.stocks;


import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Erik B. Terres
 */
public class StockDataQueryData {

    public enum Interval {DAILY, WEEKLY, YEARLY};

    private String acronym;
    private Calendar from, to;
    private Interval interval  = Interval.DAILY;

    public StockDataQueryData() {}

    public StockDataQueryData(String acronym, Interval interval) {
        this.acronym = acronym;
        this.interval = interval;
        setDateRangeByInterval();
    }

    public String getAcronym() {
        return acronym;
    }

    public StockDataQueryData setAcronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public Calendar getFrom() {
        return from;
    }


    public Calendar getTo() {
        return to;
    }


    public Interval getInterval() {
        return interval;
    }

    public StockDataQueryData setInterval(Interval interval) {
        this.interval = interval;
        setDateRangeByInterval();
        return this;
    }

    public void setDateRangeByInterval() {
        this.from = Calendar.getInstance();
        this.to = Calendar.getInstance();
        switch (this.interval) {
            case DAILY -> from.add(Calendar.DAY_OF_YEAR, -25);
            case WEEKLY -> from.add(Calendar.WEEK_OF_YEAR, -25);
            case YEARLY -> from.add(Calendar.YEAR, -25);
        }
    }
}
