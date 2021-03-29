package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.log.DeuLogger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    private MainController(){}

    public static MainController getInstance(){
        if(instance == null) instance = new MainController();
        return instance;
    }

    /**
     * Loads the scene from a fxml file and returns it.
     * If the file is not loaded correctly, the JavaFX framework will be shutdown.
     *
     * @param path FXML file path
     * @return Returns the FXML scene
     *
     */
    public Scene loadScene(String path, HashMap<String, Object> params) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));

        VBox node = null;
        try {
            node = loader.load();
            if(params != null){
                DSGenericController controller = loader.getController();
                controller.setParams(params);
            }

        } catch (IOException e) {
            DeuLogger.logger.error("Could not load " + path + " fxml file.");
            DeuLogger.logger.info("Closing system due to error.");
        }

        // In case of not loaded VBox, exit application
        if(node != null) return new Scene(node);
        else Platform.exit();

        // Empty return statement in case of Platform.exit to avoid further error
        // with instances like return null
        return new Scene(new VBox());
    }

    public void changeScene(Scene scene){
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the Scene from a FXML file and then changes the current Scene to the new laoded Scene.
     *
     * @param path FXML file path
     *
     * @see  #loadScene(String, HashMap)
     * @see  #changeScene(Scene)
     */
    public void loadAndChangeScene(String path) {
        changeScene(loadScene(path,null));
    }

    public void loadAndChangeSceneWithParams(String path, HashMap<String,Object> params) {
        changeScene(loadScene(path,params));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.show();
    }
}
