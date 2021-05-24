package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.Main;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

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

    private final Logger logger = Logger.getLogger(MainController.class);
    private static MainController instance = null;

    private Stage stage;
    private Scene scene;
    private BorderPane genericPane;

    private String user;
    private String token;

    private final int MIN_HEIGHT = 600;
    private final int MIN_WIDTH  = 1000;

    private MainController(){}

    public static MainController getInstance(){
        if(instance == null) instance = new MainController();
        return instance;
    }

    public void setDefaultStageParams(){
        stage.setTitle("DeuStock Client");

        // TODO Look up for real screen size.
        stage.setWidth(MIN_WIDTH);
        stage.setHeight(MIN_HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
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
            logger.error("Could not load " + path + " fxml file.");
            e.printStackTrace();
            logger.info("Closing system due to error.");
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

    /**
     * Loads the Scene from a FXML file and then the current center Pane to the new loaded Pane.
     *
     * @param path FXML file path
     *
     * @see  #loadPane(String, HashMap)
     * @see  #changeScene(Scene)
     */
    public void loadAndChangeScene(String path) {
        changeScene(new Scene(loadPane(path,null)));
        setDefaultStageParams();
    }

    /**
     * Loads the Pane from a FXML file and then the current center Pane to the new loaded Pane.
     * @param path FXML file path
     * @param params collects all the received objects with their respective key in a HashMap
     *
     * @see #changePane(Pane)
     * @see #loadPane(String, HashMap)
     */
    public void loadAndChangePaneWithParams(String path, HashMap<String,Object> params) {
        changePane(loadPane(path,params));
    }

    /**
     * Loads the Scene from a FXML file and then the current center Pane to the new loaded Pane.
     * @param path FXML file path
     * @param params collects all the received objects with their respective key in a HashMap
     *
     * @see #changeScene(Scene)
     * @see #loadPane(String, HashMap)
     */
    public void loadAndChangeSceneWithParams(String path, HashMap<String,Object> params) {
        changeScene(new Scene(loadPane(path,params)));
    }

    /**
     * Setter method for the Stage
     * @param stage receives an Stage and adds it to the variable Stage of the class and shows it.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.show();
    }

    /**
     * Method initializes the generic stage setting the scenes and panes
     * @param user receives the user for setting the username
     * @param token receives the String parameter token
     */
    public void initGenericStage(String user, String token){
        setUser(user);
        this.token = token;
        this.genericPane = new BorderPane();

        HashMap<String, Object> params = new HashMap<>();
        params.put("username", user);

        this.genericPane.setBottom(loadPane(ViewPaths.ControlButtonViewPath, params));
        this.genericPane.setCenter(new Pane());
        this.scene = new Scene(this.genericPane);
        this.stage.setScene(this.scene);
        this.stage.setMaximized(true);
    }

    /**
     * Getter method for User
     * @return returns the User as a String
     */
    public String getUser() {
        return user;
    }

    /**
     * Setter method for User
     * @param user receives the User as a String and sets it
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Getter method for Token
     * @return returns the Token as a String
     */
    public String getToken(){
        return token;
    }


    public Scene getScene() {
        return scene;
    }
}
