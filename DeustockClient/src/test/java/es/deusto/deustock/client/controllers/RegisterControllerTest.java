package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

@NotThreadSafe
@ExtendWith(ApplicationExtension.class)
public class RegisterControllerTest extends ApplicationTest{

    private RegisterController controller;

    private MainController mockMainController;
    private DeustockGateway mockGateway;

    @BeforeAll
    public static void setupSpec() throws TimeoutException {
        System.setProperty("testfx.robot", "glass");
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/auth/RegisterView.fxml"));

        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setUp(){
        this.mockGateway = mock(DeustockGateway.class);
        this.mockMainController = mock(MainController.class);
    }

    @Test
    void testInitializeSetsCellsToEmpty(){
        Label errorLabel = controller.registerErrorLabel;

        TextField usernameLabel = controller.usernameTxt;
        TextField passwordLabel = controller.passwordTxt;
        TextField fullNameTextField = controller.fullNameTxt;
        TextArea aboutMeTxt = controller.aboutMeTxt;

        Assertions.assertThat(errorLabel).hasText("");
        Assertions.assertThat(usernameLabel).hasText("");
        Assertions.assertThat(passwordLabel).hasText("");
        Assertions.assertThat(fullNameTextField).hasText("");
        Assertions.assertThat(aboutMeTxt).hasText("");
    }

    @Test
    void testCancelButtonChangesScene(FxRobot robot){
        // Given
        doNothing().when(mockMainController).loadAndChangeScene(anyString());
        controller.setGateway(mockGateway);
        controller.setMainController(mockMainController);

        // When
        robot.clickOn(controller.cancelBtn);
        WaitForAsyncUtils.waitForFxEvents();
        // Then
        verify(mockMainController, times(1)).loadAndChangeScene(anyString());
    }

    @Test
    void testRegisterButtonChangesScene(FxRobot robot) {
        // Given

        when(mockGateway.register(anyString(), anyString(),anyString(),anyString(),anyString())).thenReturn(true);
        doNothing().when(mockMainController).initGenericStage(anyString(),anyString());
        doNothing().when(mockMainController).loadAndChangePane(anyString());
        controller.setGateway(mockGateway);
        controller.setMainController(mockMainController);

        TextField usernameLabel = controller.usernameTxt;
        TextField passwordLabel = controller.passwordTxt;
        TextField fullNameTextField = controller.fullNameTxt;
        TextArea aboutMeTxt = controller.aboutMeTxt;

        passwordLabel.setText("TestPass");
        usernameLabel.setText("TestUsername");
        fullNameTextField.setText("TestFullName");
        aboutMeTxt.setText("TestAboutMe");

        // When
        robot.clickOn(controller.registerBtn);

        // Then
        verify(mockMainController, times(1)).loadAndChangeScene(anyString());
    }

    @Test
    void testRegisterShowsErrorLabelOnEmptyData(FxRobot robot) {
        // Given

        when(mockGateway.register(anyString(), anyString(),anyString(),anyString(),anyString())).thenReturn(true);
        doNothing().when(mockMainController).initGenericStage(anyString(),anyString());
        doNothing().when(mockMainController).loadAndChangePane(anyString());
        controller.setGateway(mockGateway);
        controller.setMainController(mockMainController);

        Label errorLabel = controller.registerErrorLabel;

        // When
        robot.clickOn(controller.registerBtn);
        // Then
        Assertions.assertThat(errorLabel).hasText("Casillas Vacias detectadas");
    }

    @Test
    void testRegisterShowErrorOnGatewayError(FxRobot robot){
        // Given

        when(mockGateway.register(anyString(), anyString(),anyString(),anyString(),anyString())).thenReturn(false);
        doNothing().when(mockMainController).initGenericStage(anyString(),anyString());
        doNothing().when(mockMainController).loadAndChangePane(anyString());
        controller.setGateway(mockGateway);
        controller.setMainController(mockMainController);

        TextField usernameLabel = controller.usernameTxt;
        TextField passwordLabel = controller.passwordTxt;
        TextField fullNameTextField = controller.fullNameTxt;
        TextArea aboutMeTxt = controller.aboutMeTxt;

        passwordLabel.setText("TestPass");
        usernameLabel.setText("TestUsername");
        fullNameTextField.setText("TestFullName");
        aboutMeTxt.setText("TestAboutMe");

        Label errorLabel = controller.registerErrorLabel;

        // When
        robot.clickOn(controller.registerBtn);
        // Then
        Assertions.assertThat(errorLabel).hasText("Registro invalido");
    }
}

















