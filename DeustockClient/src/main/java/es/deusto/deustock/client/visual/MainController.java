package es.deusto.deustock.client.visual;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController {


    @FXML
    private Label sentimentLabel;

    @FXML
    private TextField twitterSearchField;

    // location and resources will be automatically injected by the FXML loader


    // Add a public no-args constructor
    public MainController(){}

    @FXML
    private void initialize(){}

    @FXML
    private void sentimentSearch(){
        String query = twitterSearchField.getText();

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        Response response = target.path("myapp").path("twitter")
                .path("sentiment").path(query).request(MediaType.TEXT_PLAIN).get();

        String result = response.readEntity(String.class);

        sentimentLabel.setText("Sentiment: " + result);
        System.out.println(result);
    }

}
