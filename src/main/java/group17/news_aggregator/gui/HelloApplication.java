package group17.news_aggregator.gui;

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
    public void start(Stage stage) throws IOException {
        stage.setTitle("Group 17 OOP");
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        HelloController helloController = new HelloController(stage, firstScene);
        fxmlLoader.setController(helloController);
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);

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