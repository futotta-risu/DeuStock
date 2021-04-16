package es.deusto.deustock.dataminer.gateway.stocks;


import java.util.Calendar;

/**
 *
 * @author Erik B. Terres
 */
public class StockQueryData {

    public enum Interval {
        DAILY, WEEKLY, MONTHLY
    };

    private String acronym;
    private Calendar from, to;
    private Interval interval;

    private StockQueryData() {}


    public StockQueryData(String acronym, Interval interval) {
        this.acronym = acronym;
        this.interval = interval;
        setDateRangeByInterval();
    }

    public String getAcronym() {
        return acronym;
    }

    public StockQueryData setAcronym(String acronym) {
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

    public StockQueryData setInterval(Interval interval) {
        this.interval = interval;
        setDateRangeByInterval();
        return this;
    }

    private void setDateRangeByInterval() {
        this.from = Calendar.getInstance();
        this.to = Calendar.getInstance();
        switch (this.interval) {
            case DAILY -> from.add(Calendar.DAY_OF_YEAR, -3);
            case WEEKLY -> from.add(Calendar.WEEK_OF_YEAR, -3);
            case MONTHLY -> from.add(Calendar.MONTH, -3);
        }
    }
}
