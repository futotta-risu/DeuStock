package es.deusto.deustock.dataminer.gateway.socialnetworks;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SocialNetworkQueryDataTest {

    @Test
    void testConstructorWithQuery(){
        SocialNetworkQueryData data = new SocialNetworkQueryData("Test Data");
        assertEquals(data.getSearchQuery(),"Test Data");
    }

    @Test
    void testConstructorWithAllParameters(){
        SocialNetworkQueryData data = new SocialNetworkQueryData("Test Data", new Date(2019,2,3),15);
        assertEquals(data.getSearchQuery(),"Test Data");
        assertEquals(data.getDateFrom(),new Date(2019,2,3));
        assertEquals(data.getNMessages(),15);
    }


    @Test
    void setDateFrom() {
        SocialNetworkQueryData data = new SocialNetworkQueryData();
        data.setDateFrom(new Date(2022,4,5));
        assertEquals(data.getDateFrom(),new Date(2022,4,5));
    }

    @Test
    void setNMessages() {
        SocialNetworkQueryData data = new SocialNetworkQueryData();
        data.setNMessages(10);
        assertEquals(data.getNMessages(),10);
    }

    @Test
    void testSetNMessagesLessThanZero() {
        SocialNetworkQueryData data = new SocialNetworkQueryData();
        data.setNMessages(-2);
        assertEquals(data.getNMessages(),1);
    }

    @Test
    void testSetNMessagesGreaterThan100() {
        SocialNetworkQueryData data = new SocialNetworkQueryData();
        data.setNMessages(200);
        assertEquals(data.getNMessages(),100);

    }
}