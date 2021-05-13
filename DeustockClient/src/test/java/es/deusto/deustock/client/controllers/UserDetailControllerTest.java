package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;


public class UserDetailControllerTest extends FxRobot{

    @Test
    public void testProgress() throws Exception {
        // Setup JavaFX for testing.
        Stage stage = FxToolkit.registerPrimaryStage();
        Application app = FxToolkit.setupApplication(Main.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserDetailView.fxml"));
        System.out.println(loader);
        Parent root = loader.load();
    }

}