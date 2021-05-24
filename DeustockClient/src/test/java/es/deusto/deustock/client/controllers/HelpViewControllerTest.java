package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.help.FAQQuestion;
import es.deusto.deustock.client.gateways.DeustockGateway;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import junit.framework.TestCase;
import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

@NotThreadSafe
@ExtendWith(ApplicationExtension.class)
class HelpViewControllerTest extends ApplicationTest {

    private HelpViewController helpViewController;
    private DeustockGateway gateway;
    private VBox questionList;

    @BeforeEach
    public void setupSpec() throws TimeoutException {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HelpView.fxml"));

        Parent root = loader.load();
        helpViewController = loader.getController();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();

        questionList = helpViewController.questionList;


    }

    @BeforeEach
    void setUp(){
        this.gateway = mock(DeustockGateway.class);
    }

    @Test
    void testGetQuestionListHasSameNumberOfQuestions(){
        //Given
        List<FAQQuestion> questionList2 = new ArrayList<>();
        FAQQuestion faq1 = new FAQQuestion("q1","a1");
        FAQQuestion faq2 = new FAQQuestion("q2","a2");
        questionList2.add(faq1);
        questionList2.add(faq2);

        //When
        when(gateway.getFAQList()).thenReturn(questionList2);
        helpViewController.setDeustockGateway(gateway);

        //Then
        assertEquals(gateway.getFAQList().size(), 2);
    }

    @Test
    void testGetListIsEmpty(){
        //Given
        List<FAQQuestion> questionList2 = new ArrayList<>();

        //When
        when(gateway.getFAQList()).thenReturn(questionList2);
        helpViewController.setDeustockGateway(gateway);

        //Then
        assertEquals(gateway.getFAQList().size(), 0);
    }
}