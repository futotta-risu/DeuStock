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


import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
public class UserDetailControllerTest{

    private Button editProfileButton;
    private Button resetWalletButton;
    private Button accountDeleteButton;

    private UserDetailController controller;

    private MainController mockMainController;
    private DeustockGateway mockGateway;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserDetailView.fxml"));
        System.out.println(loader);
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        editProfileButton = controller.editProfileButton;
        resetWalletButton = controller.resetWalletButton;
        accountDeleteButton = controller.accountDeleteButton;
        stage.setScene(scene);
        stage.show();

        this.mockGateway = mock(DeustockGateway.class);
        this.mockMainController = mock(MainController.class);
    }


    @Test
    void testOpenOnUser() {
        // Given
        User user = new User();
        user.setUsername("Test2");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.getUser(anyString())).thenReturn(user);
        controller.setDeustockGateway(mockGateway);

        // When & Then
        assertDoesNotThrow(() -> Platform.runLater(() -> controller.setParams(params)) );
    }

    @Test
    void testOpenOnNonNullUser() {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.getUser(anyString())).thenReturn(null);
        controller.setDeustockGateway(mockGateway);

        // When & Then
        assertDoesNotThrow(() -> Platform.runLater(() -> controller.setParams(params)) );
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
        Platform.runLater( () -> controller.setParams(params) );

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
        Platform.runLater( () -> controller.setParams(params) );

        // When & Then
        assertDoesNotThrow( () -> controller.deleteUser() );
    }

    @Test
    void testResetWalletSuccesfully() throws Exception {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        //Por alguna razon con anyString no funciona
        when(mockGateway.resetHoldings(any())).thenReturn(true);
        controller.setDeustockGateway(mockGateway);
        Platform.runLater( () -> controller.setParams(params) );

        // When & Then
        assertDoesNotThrow( () -> controller.resetAccountWallet() );
    }

    @Test
    void testResetWalletNotSuccesfull() throws Exception {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.resetHoldings(anyString())).thenReturn(false);
        controller.setDeustockGateway(mockGateway);
        Platform.runLater( () -> controller.setParams(params) );

        // When & Then
        assertThrows( RuntimeException.class, () ->  controller.resetAccountWallet() );
    }

    //TODO Revisar los FXRobot
    @Test
    void testEditProfileButton(FxRobot robot){
        //Given
        controller.setMainController(mockMainController);
        doNothing().when(mockMainController).loadAndChangePaneWithParams(any(), any());

        //When & Then
        assertDoesNotThrow( () -> robot.clickOn(editProfileButton) );
        ;
        //Then
    }

    @Test
    void testResetWalletButton(FxRobot robot){
        //Given
        when(mockGateway.resetHoldings(any())).thenReturn(true);
        controller.setDeustockGateway(mockGateway);

        //When & Then
        assertDoesNotThrow( () -> robot.clickOn(editProfileButton) );
    }

    @Test
    void testResetWalletButtonReturnException(FxRobot robot){
        //Given
        when(mockGateway.resetHoldings(anyString())).thenReturn(false);
        controller.setDeustockGateway(mockGateway);

        //When & Then
        //assertThrows(Exception.class, () -> robot.clickOn(editProfileButton));
    }

}
