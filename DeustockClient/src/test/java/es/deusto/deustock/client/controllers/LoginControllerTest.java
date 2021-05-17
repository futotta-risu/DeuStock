package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
public class LoginControllerTest {

    private LoginController controller;

    private MainController mockMainController;
    private DeustockGateway mockGateway;

    private Button loginButton;

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

    @Start
    private void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPaths.LoginViewPath));

        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        this.loginButton = controller.loginButton;

        this.mockGateway = mock(DeustockGateway.class);
        this.mockMainController = mock(MainController.class);
    }

    @Test
    void testLoginButtonShowsEmptyFields(FxRobot robot) throws ForbiddenException {
        // Given

        when(mockGateway.login(anyString(), anyString())).thenReturn("Test Token");
        controller.setGateway(mockGateway);

        // When
        robot.clickOn(loginButton);

        // Then
        Assertions.assertThat(controller.loginErrorLabel).hasText("Campos vacios");
    }

    @Test
    void testLoginButtonShowsInvalidDataOnForbiddenException(FxRobot robot) throws ForbiddenException {
        // Given

        when(mockGateway.login(anyString(), anyString())).thenThrow(new ForbiddenException("Exception"));
        controller.setGateway(mockGateway);

        controller.passwordTxt.setText("TestPass");
        controller.usernameTxt.setText("TestUsername");

        // When
        robot.clickOn(loginButton);

        // Then
        Assertions.assertThat(controller.loginErrorLabel).hasText("Datos Incorrectos");
    }

    @Test
    void testLoginButtonChangesScene(FxRobot robot) throws ForbiddenException {
        // Given

        when(mockGateway.login(anyString(), anyString())).thenReturn("Test Token");
        doNothing().when(mockMainController).initGenericStage(anyString(),anyString());
        doNothing().when(mockMainController).loadAndChangePane(anyString());
        controller.setGateway(mockGateway);
        controller.setMainController(mockMainController);

        controller.passwordTxt.setText("TestPass");
        controller.usernameTxt.setText("TestUsername");

        // When
        robot.clickOn(loginButton);

        // Then
        verify(mockMainController, times(1)).loadAndChangePane(anyString());
    }

    @Test
    void testRegisterButtonChangesScene(FxRobot robot){
        // Given

        doNothing().when(mockMainController).loadAndChangeScene(anyString());
        controller.setGateway(mockGateway);
        controller.setMainController(mockMainController);

        // When
        robot.clickOn(controller.registerBtn);

        // Then
        verify(mockMainController, times(1)).loadAndChangeScene(anyString());
    }

}