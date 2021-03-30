package es.deusto.deustock.resources.user;

import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Erik B. Terres
 */
@Path("user/{query}")
public class UserDetail {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUsername(@PathParam("query") String username) {
        // User user = DataManager.getUser(username)
        // if user == null: throw error

        JSONObject user = new JSONObject();
        user.put("username","testuser");
        user.put("fullName","Erik T");
        user.put("birthDate","21/02/1999");
        user.put("aboutMe","soy yo");
        user.put("name","testname");
        user.put("description","description test");
        user.put("country","Spain");
        return user;
    }
}