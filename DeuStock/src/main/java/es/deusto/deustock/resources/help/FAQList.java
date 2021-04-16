package es.deusto.deustock.resources.help;

import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.util.file.DSJSONUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;


/**
 * @author Erik B. Terres
 */
@Path("help/faq/list")
public class FAQList {

    private final String path ="data/faq_list.json";

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getFAQList() {
        try {
            return DSJSONUtils.readFile(path);
        } catch (IOException | ParseException e) {
        	DeuLogger.logger.error("Could not get FAQList.");
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
