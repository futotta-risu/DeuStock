package es.deusto.deustock.dataminer.gateway.stocks;


import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author Erik B. Terres
 */
public class StockQueryData {

    public enum Interval {
        DAILY, WEEKLY, MONTHLY
    };

    private final int DEFAULT_INTERVAL_SIZE = 25;

    private String acronym;
    private Calendar from, to;
    private Interval interval  = Interval.DAILY;
    private boolean withHistoric = false;
    private int intervalSize = DEFAULT_INTERVAL_SIZE;



    private StockQueryData() {}

    public StockQueryData(String acronym) {
        setAcronym(acronym);
        setInterval(Interval.DAILY);
    }

    public StockQueryData(String acronym, Interval interval) {
        setAcronym(acronym);
        setInterval(interval);
    }

    public String getAcronym() {
        return acronym;
    }

    private StockQueryData setAcronym(String acronym) {
        if(acronym.isBlank())
            throw new IllegalArgumentException("Acronym cannot be empty");

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

    private StockQueryData setInterval(Interval interval) {
        Objects.requireNonNull(interval);

        this.interval = interval;
        setDateRangeByInterval();

        return this;
    }

    public StockQueryData setIntervalSize(int size){
        this.intervalSize = size;
        setDateRangeByInterval();
        return this;
    }


    private void setDateRangeByInterval() {
        this.from = Calendar.getInstance();
        this.to = Calendar.getInstance();
        switch (this.interval) {
            case DAILY -> from.add(Calendar.DAY_OF_YEAR, -intervalSize);
            case WEEKLY -> from.add(Calendar.WEEK_OF_YEAR, -intervalSize);
            case MONTHLY -> from.add(Calendar.MONTH, -intervalSize);
        }
    }

    public boolean isWithHistoric() {
        return withHistoric;
    }

    public StockQueryData setWithHistoric(boolean withHistoric) {
        this.withHistoric = withHistoric;
        return this;
    }
}
