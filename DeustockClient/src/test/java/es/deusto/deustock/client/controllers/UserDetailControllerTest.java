package es.deusto.deustock.client.controllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.loadui.testfx.GuiTest;

public class UserDetailControllerTest extends GuiTest {
    @Override
    protected Parent getRootNode() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../../../../main/resources/views/UserDetailView.fxml"));
            return parent;
        } catch (Exception ex) {
        }
        return parent;
    }

}
