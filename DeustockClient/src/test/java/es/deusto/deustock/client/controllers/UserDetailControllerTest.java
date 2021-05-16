package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
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

import static javafx.event.Event.*;
import static javafx.scene.input.MouseEvent.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
public class UserDetailControllerTest{

    private Button editProfileButton;
    private Button resetWalletButton;
    private Button accountDeleteButton;

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
        resetWalletButton = controller.resetWalletButton;
        accountDeleteButton = controller.accountDeleteButton;
        stage.setScene(scene);
        stage.show();
    }



    @Test
    void testOpenOnUser() {
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
    void testOpenOnNonNullUser() {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        DeustockGateway gateway = mock(DeustockGateway.class);
        when(gateway.getUser(anyString())).thenReturn(null);
        controller.setDeustockGateway(gateway);

        // When
        Platform.runLater( () -> controller.setParams(params) );

    }

    @Test
    void testDeleteUser() {
        // Given
        User user = new User();
        user.setUsername("username");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        DeustockGateway gateway = mock(DeustockGateway.class);
        MainController mockMainController = mock(MainController.class);

        when(gateway.getUser(anyString())).thenReturn(user);
        when(gateway.deleteUser(any())).thenReturn(true);

        doNothing().when(mockMainController).setUser(any());
        doNothing().when(mockMainController).loadAndChangeScene(anyString());

        controller.setMainController(mockMainController);
        controller.setDeustockGateway(gateway);
        // When
        Platform.runLater( () -> {
            controller.setParams(params);
        } );

        //Then
        controller.deleteUser();


    }

    @Test
    void testDeleteUserOnNonExistentUser() {
        // Given
        User user = new User();
        user.setUsername("username");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        DeustockGateway gateway = mock(DeustockGateway.class);
        MainController mockMainController = mock(MainController.class);

        when(gateway.getUser(anyString())).thenReturn(user);
        when(gateway.deleteUser(any())).thenReturn(false);

        doNothing().when(mockMainController).setUser(any());
        doNothing().when(mockMainController).loadAndChangeScene(anyString());

        controller.setMainController(mockMainController);
        controller.setDeustockGateway(gateway);

        Platform.runLater( () -> {
            controller.setParams(params);
        } );

        // When
        controller.deleteUser();
    }

    @Test
    void testResetWalletSuccesfully() throws Exception {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        DeustockGateway gateway = mock(DeustockGateway.class);
        //Por alguna razon con anyString no funciona
        when(gateway.resetHoldings(any())).thenReturn(true);
        controller.setDeustockGateway(gateway);

        Platform.runLater( () -> {
            controller.setParams(params);
        } );

        // When
        controller.resetAccountWallet();
    }
    @Test
    void testResetWalletNotSuccesfull() throws Exception {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        DeustockGateway gateway = mock(DeustockGateway.class);
        when(gateway.resetHoldings(anyString())).thenReturn(false);
        controller.setDeustockGateway(gateway);

        Platform.runLater( () -> {
            controller.setParams(params);
        } );

        // When & Then
        assertThrows( Exception.class, () ->  controller.resetAccountWallet() );
    }

    //TODO Revisar los FXRobot
    @Test
    void testEditProfileButton(FxRobot robot){
        //Given
        MainController mockMainController = mock(MainController.class);
        controller.setMainController(mockMainController);
        doNothing().when(mockMainController).loadAndChangePaneWithParams(any(), any());
        //When
        robot.clickOn(editProfileButton);
        //Then
    }

    @Test
    void testResetWalletButton(FxRobot robot){
        //Given
        DeustockGateway gateway = mock(DeustockGateway.class);
        when(gateway.resetHoldings(any())).thenReturn(true);
        controller.setDeustockGateway(gateway);

        //When
        robot.clickOn(editProfileButton);
        //Then
    }

    @Test
    void testResetWalletButtonReturnException(FxRobot robot){
        //Given
        DeustockGateway gateway = mock(DeustockGateway.class);
        when(gateway.resetHoldings(any())).thenReturn(false);
        controller.setDeustockGateway(gateway);

        //When & Then
        assertThrows(Exception.class, () -> robot.clickOn(editProfileButton));
    }





}
