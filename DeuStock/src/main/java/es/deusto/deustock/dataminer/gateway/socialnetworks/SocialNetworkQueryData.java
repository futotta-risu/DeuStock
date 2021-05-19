package es.deusto.deustock.dataminer.gateway.socialnetworks;

import java.util.Calendar;
import java.util.Date;

/**
 * Query for the {@link es.deusto.deustock.data.SocialNetworkMessage} retrieval
 *
 * @author Erik B. Terres
 */
public class SocialNetworkQueryData {

    private String searchQuery;
    private Date dateFrom = new Date(2021, Calendar.FEBRUARY,1);
    private int nMessages  = 20;

    public SocialNetworkQueryData() {}

    public SocialNetworkQueryData(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public SocialNetworkQueryData(String searchQuery, Date dateFrom, int nMessages) {
        this.searchQuery = searchQuery;
        this.dateFrom = dateFrom;
        this.nMessages = nMessages;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public int getNMessages() {
        return nMessages;
    }

    /**
     * Sets the number of messages the Search will return
     * @param nMessages Integer between 1 to 100. If a value outside is passed,
     *                 it will be approximated to the closest value in the range
     */
    public void setNMessages(int nMessages) {
        if(nMessages>100){
            this.nMessages = 100;
        }else {
            this.nMessages = Math.max(nMessages, 1);
        }
    }

}
