package es.deusto.deustock.client.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

@ExtendWith(ApplicationExtension.class)
public class UserDetailControllerTest{

    private Button editProfileButton;
    private UserDetailController controller;

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


    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void testEditProfileButton(FxRobot robot) {
        // when:
        robot.clickOn(editProfileButton);
        //Then
        Assertions.assertThat(robot.lookup("#editProfileButton").queryButton()).hasText("Cambiar Detalles");
    }

}
