package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.log.DeuLogger;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Main controller of the application. This controller will handle the stage and scene changes.
 *
 * Singleton class. Call the class through {@link #getInstance()}
 *
 * @author Erik B. Terres
 */
public class MainController {

    private static MainController instance = null;

    private Stage stage;
    private Scene scene;
    private BorderPane genericPane;

    private User user;

    private final int DEFAULT_HEIGHT = 600;
    private final int DEFAULT_WIDTH  = 800;

    private MainController(){}

    public static MainController getInstance(){
        if(instance == null) instance = new MainController();
        return instance;
    }

    /**
     * Loads the pane from a fxml file and returns it.
     * If the file is not loaded correctly, the JavaFX framework will be shutdown.
     *
     * @param path FXML file path
     * @return Returns the FXML scene
     *
     */
    public Pane loadPane(String path, HashMap<String, Object> params) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));

        Pane node = null;
        try {
            node = loader.load();
            if(params != null){
                DSGenericController controller = loader.getController();
                controller.setParams(params);
            }

        } catch (IOException e) {
            DeuLogger.logger.error("Could not load " + path + " fxml file.");
            e.printStackTrace();
            DeuLogger.logger.info("Closing system due to error.");
        }
        // In case of not loaded VBox, exit application
        if(node != null) return node;
        else Platform.exit();
        // Empty return statement in case of Platform.exit to avoid further error
        // with instances like return null
        return new VBox();
    }

    public void changePane(Pane pane){
        this.genericPane.setCenter(pane);
        this.stage.show();
    }

    public void changeScene(Scene scene){
        this.scene = scene;
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Loads the Pane from a FXML file and then changes the current center Pane to the new loaded Pane.
     *
     * @param path FXML file path
     *
     * @see  #loadPane(String, HashMap)
     * @see  #changePane(Pane)
     */
    public void loadAndChangePane(String path) {
        changePane(loadPane(path,null));
    }
    public void loadAndChangeScene(String path) {
        changeScene(new Scene(loadPane(path,null),DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }

    public void loadAndChangePaneWithParams(String path, HashMap<String,Object> params) {
        changePane(loadPane(path,params));
    }
    public void loadAndChangeSceneWithParams(String path, HashMap<String,Object> params) {
        changeScene(new Scene(loadPane(path,params)));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.show();
    }

    public void initGenericStage(String username){
        this.genericPane = new BorderPane();

        HashMap<String, Object> params = new HashMap<>();
        params.put("username", username);

        this.genericPane.setBottom(loadPane(ViewPaths.ControlButtonViewPath, params));
        this.genericPane.setCenter(new Pane());
        this.scene = new Scene(this.genericPane,DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.stage.setScene(this.scene);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
