package es.deusto.deustock.resources.help;

import es.deusto.deustock.util.file.DSJSONUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

/**
 * @author Erik B. Terres
 */
public class FAQListTest {

    @Test
    public void testFAQReturns200()  {
        // Given
        FAQList faqList = new FAQList();

        // When
        Response response = faqList.getFAQList();

        // Then
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testFAQReturnsQuestions()  {
        // Given
        FAQList faqList = new FAQList();

        // When
        Response response = faqList.getFAQList();
        JSONObject faq = new JSONObject((String) response.getEntity());

        // Then
        assertTrue(faq.getJSONArray("questions").length() > 0);
    }


    @Test
    public void testFAQThrowsErrorOnWrongPath() {
        try (MockedStatic<DSJSONUtils> jsonMock= mockStatic(DSJSONUtils.class)) {
            // Given
            FAQList faqList = new FAQList();

            jsonMock.when(() -> DSJSONUtils.readFile(anyString())).thenThrow(
                    new IOException("Could not parse file")
            );
            // When
            Response response = faqList.getFAQList();

            // Then
            assertEquals(401, response.getStatus());
        }
    }
}
