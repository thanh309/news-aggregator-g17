package group17.news_aggregator.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public  class HelloApplication extends Application {
    private Stage window;
    private Parent root;
    private Scene firstScene;
    @Override
    public void start(Stage stage) throws IOException {

        window = stage;
        window.setTitle("Page đầu");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../../../resources/hello-view.fxml"));
        HelloController helloController = new HelloController(window,firstScene); // Truyền Stage vào HelloController
        loader.setController(helloController);

        try {
            root = loader.load();
            firstScene = new Scene(root);
            firstScene.getStylesheets().add("css/style.css");
            window.setScene(firstScene);
            window.show();
        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("Error loading FXML file:"+ e.getMessage());
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}