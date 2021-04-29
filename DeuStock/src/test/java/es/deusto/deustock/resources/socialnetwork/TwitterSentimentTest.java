package es.deusto.deustock.resources.socialnetwork;

import es.deusto.deustock.dataminer.features.SentimentExtractor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Erik B. Terres
 */
@Tag("server-resource")
public class TwitterSentimentTest {

    @Test
    public void testTwitterSentimentReturnsCorrectOutput() throws InterruptedException {

        SentimentExtractor extractor = mock(SentimentExtractor.class);
        when(extractor.getSentimentTendency(anyString())).thenReturn(3.0);

        TwitterSentimentResource resource = new TwitterSentimentResource(extractor);
        Response response = resource.getSentiment("Test");
        double result = (double) response.getEntity();
        assertEquals(3, result, 0.001);
    }
}
