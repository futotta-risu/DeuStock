package es.deusto.deustock.resources.user;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.report.UserReport;
import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;

@Path("reports/user")
public class UserReportResource {

    @GET
    @Produces("application/pdf")
    @Path("/{username}")
    public Response createUserReport(@PathParam("username") String username) throws IOException, SQLException {

        UserDTO user;
        try {
            user = new UserService().getUserByUsername(username);
            System.out.println(user.toString());
        } catch ( UserException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if(user== null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        System.out.println("1");
        var report = new UserReport(new User(user.getUsername(), user.getPassword()));
        System.out.println("2");

        var reportFile = report.generate();
        System.out.println("3");

        Response.ResponseBuilder response = Response.ok(reportFile);
        response.header("Content-Disposition","attachment; filename=" + username + "_report.pdf");


        return response.build();
    }


}
