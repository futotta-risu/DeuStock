package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
public class UserDetailControllerTest{

    private Button editProfileButton;
    private UserDetailController controller;

    @BeforeClass
    public static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }
    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */
    @Start
    private void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserDetailView.fxml"));
        System.out.println(loader);
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        editProfileButton = controller.editProfileButton;
        stage.setScene(scene);
        stage.show();
    }



    @Test
    void testOpenOnUser(FxRobot robot) {
        // Given
        User user = new User();
        user.setUsername("Test2");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        DeustockGateway gateway = mock(DeustockGateway.class);
        when(gateway.getUser(anyString())).thenReturn(user);
        controller.setDeustockGateway(gateway);
        // When


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.setParams(params);
            }
        });

    }

    @Test
    void testOpenOnNonNullUser(FxRobot robot) {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        DeustockGateway gateway = mock(DeustockGateway.class);
        when(gateway.getUser(anyString())).thenReturn(null);
        controller.setDeustockGateway(gateway);
        // When


        Platform.runLater( () -> controller.setParams(params) );
    }


}
