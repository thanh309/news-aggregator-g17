package group17.news_aggregator.gui.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    private Parent root;

    private Scene firstScene;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Group 17 OOP");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/home-view.fxml"));

        try {
            root = fxmlLoader.load();
            firstScene = new Scene(root);
            stage.setScene(firstScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Wrong fxml");
        }
    }
}