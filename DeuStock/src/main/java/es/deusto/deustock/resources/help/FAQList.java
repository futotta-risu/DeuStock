package es.deusto.deustock.resources.help;

import es.deusto.deustock.util.file.DSJSONUtils;
import org.json.simple.parser.ParseException;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


/**
 * @author Erik B. Terres
 */
@Path("help/faq/list")
public class FAQList {

    private static final Logger logger = Logger.getLogger(FAQList.class);

    private static final String PATH ="data/faq_list.json";


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFAQList() {
        logger.info("Accessing FAQ");
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(DSJSONUtils.readFile(PATH).toString())
                    .build();

        } catch (IOException | ParseException e) {
        	logger.error("Could not get FAQList due to problems reading file: " + PATH);
        }
        return Response.status(401).build();
    }
}
