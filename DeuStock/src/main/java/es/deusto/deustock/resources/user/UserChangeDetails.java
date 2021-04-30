package es.deusto.deustock.resources.user;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Erik B. Terres
 */
@Path("users/details/change")
public class UserChangeDetails {

    private UserDAO userDAO;

    public UserChangeDetails(){
        userDAO = UserDAO.getInstance();
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeDetails(UserDTO userDTO) throws SQLException {
        User user = userDAO.getUser(userDTO.getUsername());

        if(user==null){
            return Response.status(401).build();
        }

        user.updateInfo(userDTO);
        userDAO.updateUser(user);

        return Response.status(200).build();
    }
}
