package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class StartController {

    private Stage stageStart;
    private Scene sceneStart;

    public StartController() {
    }
    public StartController(Stage stage, Scene scene){
        this.stageStart = stage;
        this.sceneStart = scene;
    }

    @FXML
    void startnow(ActionEvent event) {
        if (stageStart == null) {
            stageStart = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        HelloController helloController = new HelloController(stageStart, sceneStart);
        FXMLLoader loadMain = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        loadMain.setController(helloController);

        try {
            Parent visitScene = loadMain.load();

            helloController = loadMain.getController();
            stageStart.setScene(new Scene(visitScene));
            stageStart.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void aboutus(ActionEvent event){
        if (stageStart == null) {
            stageStart = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        HelloController helloController = new HelloController(stageStart, sceneStart);
        FXMLLoader loadMain = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        loadMain.setController(helloController);

        try {
            Parent visitScene = loadMain.load();

            helloController = loadMain.getController();
            stageStart.setScene(new Scene(visitScene));
            stageStart.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
