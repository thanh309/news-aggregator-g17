package group17.news_aggregator.gui;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class HelloApplication extends Application {
    private Parent root;
    private Scene firstScene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Group 17 OOP");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));

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