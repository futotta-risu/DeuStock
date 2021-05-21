package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
public class ChangeUserDetailControllerTest extends ApplicationTest {

    private ChangeUserDetailController controller;
    private Button changeButton;
    private Button cancelButton;

    private Label usernameLabel;
    private TextField fullNameTxt;
    private DatePicker birthDatePicker;
    private TextArea aboutMeTxt;
    private ChoiceBox<String> countryChoice;
    private Dialog<String> dialog;

    private DeustockGateway mockGateway;
    private MainController mockMainController;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ChangeUserDetailView.fxml"));

        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        changeButton = controller.changeBtn;
        cancelButton = controller.cancelBtn;

        usernameLabel = controller.usernameLabel;
        fullNameTxt = controller.fullNameTxt;
        birthDatePicker = controller.birthDatePicker;
        aboutMeTxt = controller.aboutMeTxt;
        countryChoice = controller.countryChoice;
        dialog = controller.getDialog();

        stage.setScene(scene);
        stage.show();
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
        Platform.runLater( () -> {
            controller.setParams(params);
            Assertions.assertThat(controller.usernameLabel).hasText("Test");
            Assertions.assertThat(controller.aboutMeTxt).hasText("");
        });
        // Then


    }

    @Test
    void testUpdateUserDetails() {

        //Given
        User user = new User();
        user.setUsername("Test");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.getUser(anyString())).thenReturn(user);
        controller.setDeustockGateway(mockGateway);
        controller.setMainController(mockMainController);
        when(mockGateway.updateUser(any(), anyString())).thenReturn(true);
        doNothing().when(mockMainController).loadAndChangePane(anyString());

        fullNameTxt.setText("test");
        fullNameTxt.setText("test");

        Platform.runLater(() -> controller.setParams(params) );
        // When & Then
        assertDoesNotThrow(()-> clickOn(changeButton));


    }

    @Test
    void testUpdateEmptyUserDetails() {
        //Given
        User user = new User();
        user.setUsername("Test");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        when(mockGateway.getUser(anyString())).thenReturn(user);
        when(mockGateway.updateUser(any(), anyString())).thenReturn(true);
        doNothing().when(mockMainController).loadAndChangePane(anyString());

        controller.setDeustockGateway(mockGateway);
        controller.setMainController(mockMainController);

        Platform.runLater(() -> controller.setParams(params) );

        // When & Then
        assertDoesNotThrow(()-> clickOn(changeButton));



    }


}