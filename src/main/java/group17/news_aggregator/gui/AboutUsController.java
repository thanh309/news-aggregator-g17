package group17.news_aggregator.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutUsController {

    private Stage stageAu;
    private Scene sceneAu;
    @FXML
    private Button homeButton;

    public AboutUsController(Stage stageAu, Scene sceneAu) {
        this.stageAu = stageAu;
        this.sceneAu = sceneAu;
    }


    @FXML
    void returnHome(ActionEvent event) {
        if (stageAu == null) {
            stageAu = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader loaderAu = new FXMLLoader(getClass().getResource("start-view.fxml"));
        StartController startController = new StartController(stageAu, sceneAu);

        try {
            Parent aboutScene = loaderAu.load();
            stageAu.setScene(new Scene(aboutScene));
            stageAu.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}