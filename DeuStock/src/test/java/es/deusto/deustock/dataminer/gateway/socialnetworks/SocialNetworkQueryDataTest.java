package es.deusto.deustock.dataminer.gateway.socialnetworks;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SocialNetworkQueryDataTest {

    @Test
    void testConstructorWithQuery(){
        SocialNetworkQueryData data = new SocialNetworkQueryData("Test Data");
        assertEquals("Test Data", data.getSearchQuery());
    }

    @Test
    void testConstructorWithAllParameters(){
        SocialNetworkQueryData data = new SocialNetworkQueryData("Test Data", new Date(2019, Calendar.MARCH,3),15);
        assertEquals("Test Data", data.getSearchQuery());
        assertEquals(new Date(2019, Calendar.MARCH,3), data.getDateFrom());
        assertEquals(15, data.getNMessages());
    }


    @Test
    void setDateFrom() {
        SocialNetworkQueryData data = new SocialNetworkQueryData();
        data.setDateFrom(new Date(2022, Calendar.MAY,5));
        assertEquals(new Date(2022, Calendar.MAY,5), data.getDateFrom());
    }

    @Test
    void setNMessages() {
        SocialNetworkQueryData data = new SocialNetworkQueryData();
        data.setNMessages(10);
        assertEquals(10, data.getNMessages());
    }

    @Test
    void testSetNMessagesLessThanZero() {
        SocialNetworkQueryData data = new SocialNetworkQueryData();
        data.setNMessages(-2);
        assertEquals(1, data.getNMessages());
    }

    @Test
    void testSetNMessagesGreaterThan100() {
        SocialNetworkQueryData data = new SocialNetworkQueryData();
        data.setNMessages(200);
        assertEquals(100, data.getNMessages());

    }
}