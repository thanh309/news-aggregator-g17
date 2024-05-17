package group17.news_aggregator.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    private Stage stageStart;

    private Scene sceneStart;

    @FXML
    private Button aboutUs;

    public HomeController() {
    }

    public HomeController(Stage stage, Scene scene) {
        this.stageStart = stage;
        this.sceneStart = scene;
    }

    @FXML
    void aboutUs(ActionEvent event) {
        if (stageStart == null) {
            stageStart = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/about-us.fxml"));

        try {
            Parent aboutScene = loader.load();
            stageStart.setScene(new Scene(aboutScene));
            stageStart.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void discover(ActionEvent event) {
        if (stageStart == null) {
            stageStart = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/discover-view.fxml"));

        try {
            Parent aboutScene = searchLoader.load();
            stageStart.setScene(new Scene(aboutScene));
            stageStart.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}