package es.deusto.deustock.dataminer.gateway.socialnetworks;

import java.util.Date;


public class SocialNetworkQueryData {

    private String searchQuery;
    private Date dateFrom = new Date(2021,1,1);
    private int nMessages  = 20;

    public SocialNetworkQueryData() {}

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

    public void setNMessages(int nMessages) {
        this.nMessages = Math.min(100,nMessages);
    }
}
