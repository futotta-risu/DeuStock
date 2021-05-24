package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ResetException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;


import static javafx.application.Platform.runLater;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

@NotThreadSafe
@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
public class UserDetailControllerTest extends ApplicationTest {

    private Button editProfileButton;
    private Button resetWalletButton;
    private Button accountDeleteButton;

    private Label usernameLabel;
    private TextArea descriptionText;

    private UserDetailController controller;

    private MainController mockMainController;
    private DeustockGateway mockGateway;

    @BeforeAll
    public static void setupSpec() throws Exception {
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
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserDetailView.fxml"));
        System.out.println(loader);
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        editProfileButton = controller.editProfileButton;
        resetWalletButton = controller.resetWalletButton;
        accountDeleteButton = controller.accountDeleteButton;

        usernameLabel = controller.usernameLabel;
        descriptionText = controller.descriptionLabel;
        stage.setScene(scene);
        stage.show();

        this.mockGateway = mock(DeustockGateway.class);
    }

    @BeforeEach
    void setUp(){
        this.mockGateway = mock(DeustockGateway.class);
        this.mockMainController = mock(MainController.class);
    }

    @Test
    void testOpenOnUser() {
        // Given
        User user = new User();
        user.setUsername("Test");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.getUser(anyString())).thenReturn(user);
        controller.setDeustockGateway(mockGateway);

        // When
        runLater( () -> {
            controller.setParams(params);
            //Then
            assertEquals(usernameLabel.getText(), "Test");
            assertEquals(descriptionText.getText(), "");
        } );
    }

    @Test
    void testOpenOnNullUser() {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.getUser(anyString())).thenReturn(null);
        controller.setDeustockGateway(mockGateway);

        // When & Then
        assertDoesNotThrow( () -> controller.setParams(params)) ;
    }

    @Test
    void testDeleteUser() {
        // Given
        User user = new User();
        user.setUsername("username");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.getUser(anyString())).thenReturn(user);
        when(mockGateway.deleteUser(any())).thenReturn(true);
        doNothing().when(mockMainController).setUser(any());
        doNothing().when(mockMainController).loadAndChangeScene(anyString());

        controller.setMainController(mockMainController);
        controller.setDeustockGateway(mockGateway);
        runLater( () -> controller.setParams(params));;

        // When & Then
        assertDoesNotThrow( () -> controller.deleteUser() );
    }

    @Test
    void testDeleteUserOnNonExistentUser() {
        // Given
        User user = new User();
        user.setUsername("username");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.getUser(anyString())).thenReturn(user);
        when(mockGateway.deleteUser(any())).thenReturn(false);

        doNothing().when(mockMainController).setUser(any());
        doNothing().when(mockMainController).loadAndChangeScene(anyString());

        controller.setMainController(mockMainController);
        controller.setDeustockGateway(mockGateway);
        runLater( () -> controller.setParams(params));

        // When & Then
        assertDoesNotThrow( () -> controller.deleteUser() );
    }

    @Test
    void testResetWalletSuccesfully() throws ResetException {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        //Por alguna razon con anyString no funciona
        when(mockGateway.resetHoldings(any())).thenReturn(true);
        controller.setDeustockGateway(mockGateway);
        controller.setParams(params);

        // When & Then
        assertDoesNotThrow( () -> controller.resetAccountWallet() );
    }

    @Test
    void testResetWalletNotSuccesfull() throws ResetException {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.resetHoldings(anyString())).thenReturn(false);
        controller.setDeustockGateway(mockGateway);
        controller.setParams(params);

        // When & Then
        assertThrows( ResetException.class, () ->  controller.resetAccountWallet() );
    }

    //TODO Revisar los FXRobot
    @Test
    void testEditProfileButton(FxRobot robot){
        //Given
        controller.setMainController(mockMainController);
        doNothing().when(mockMainController).loadAndChangePaneWithParams(any(), any());

        //When & Then
        assertDoesNotThrow( () -> clickOn(editProfileButton) );
        //Then

    }

    @Test
    void testResetWalletButton(FxRobot robot){
        //Given
        when(mockGateway.resetHoldings(any())).thenReturn(true);
        controller.setDeustockGateway(mockGateway);

        //When & Then
        assertDoesNotThrow( () -> clickOn(editProfileButton) );
    }

    @Test
    void testResetWalletButtonReturnException(FxRobot robot){
        //Given
        when(mockGateway.resetHoldings(anyString())).thenReturn(false);
        controller.setDeustockGateway(mockGateway);

        //When & Then
        //assertThrows(ResetException.class, () -> robot.clickOn(editProfileButton));
    }

}
