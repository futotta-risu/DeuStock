package es.deusto.deustock.resources.help;

import es.deusto.deustock.util.file.DSJSONUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.ws.rs.core.Response;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

/**
 * @author Erik B. Terres
 */
class FAQListTest {

    @Test
    void testFAQReturns200()  {
        // Given
        FAQList faqList = new FAQList();

        // When
        Response response = faqList.getFAQList();

        // Then
        assertEquals(200, response.getStatus());
    }

    @Test
    void testFAQReturnsQuestions()  {
        // Given
        FAQList faqList = new FAQList();

        // When
        Response response = faqList.getFAQList();
        JSONObject faq = new JSONObject((String) response.getEntity());

        // Then
        assertTrue(faq.getJSONArray("questions").length() > 0);
    }


    @Test
    void testFAQThrowsErrorOnWrongPath() {
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
