package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class StartController {

    private Stage stageStart;
    private Scene sceneStart;

    @FXML
    private Button aboutUs;

    public StartController() {
    }
    public StartController(Stage stage, Scene scene){
        this.stageStart = stage;
        this.sceneStart = scene;
    }

    @FXML
    void aboutus(ActionEvent event){
        if (stageStart == null) {
            stageStart = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("about-us.fxml"));

        try {
            Parent aboutScene = loader.load();
            stageStart.setScene(new Scene(aboutScene));
            stageStart.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void discover(ActionEvent event){
        if (stageStart == null) {
            stageStart = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

        try {
            Parent aboutScene = searchLoader.load();
            stageStart.setScene(new Scene(aboutScene));
            stageStart.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
