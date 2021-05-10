package es.deusto.deustock.resources.socialnetwork;

import es.deusto.deustock.dataminer.features.SentimentExtractor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Erik B. Terres & Lander San Millan
 */
@Tag("server-resource")
class TwitterSentimentTest {
	
	private SentimentExtractor mockExtractor;
	
    @BeforeEach
    public void setUp() {
    	mockExtractor = mock(SentimentExtractor.class);
    }

    public void setMocksToResource(TwitterSentimentResource twitterSentimentResource){
    	twitterSentimentResource.setSentimentExtractor(mockExtractor);
    }

    @Test
    void testTwitterSentimentReturnsCorrectOutput() throws InterruptedException {

        SentimentExtractor extractor = mock(SentimentExtractor.class);
        when(extractor.getSentimentTendency(anyString())).thenReturn(3.0);

        TwitterSentimentResource resource = new TwitterSentimentResource(extractor);
        Response response = resource.getSentiment("Test");
        double result = (double) response.getEntity();
        assertEquals(3, result, 0.001);
    }
    
    @Test
    @DisplayName("Test get holdings list returns Illegal Argument Exception")
    void testTwitterGetSentimentReturnsInterruptedException() throws InterruptedException{
    	//Given
    	TwitterSentimentResource twitterSentimentResource = new TwitterSentimentResource();
        when(mockExtractor.getSentimentTendency(anyString())).thenThrow(new InterruptedException());
        setMocksToResource(twitterSentimentResource);

        //When       
          	
        //Then
        try {
        	twitterSentimentResource.getSentiment("Test");
		} catch (Exception e) {
			assertEquals(InterruptedException.class, e.getClass());
		}
    }
}
