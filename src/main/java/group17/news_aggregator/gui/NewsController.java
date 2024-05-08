package group17.news_aggregator.gui;

import group17.news_aggregator.news.News;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NewsController {

    private Stage stage;
    private Scene scene;


    @FXML
    Button author;

    @FXML
    private FlowPane flowp;

    @FXML
    private HBox news;

    @FXML
    Button title;

    @FXML
    Label datetype;

    public NewsController() {
    }

    public NewsController(Stage stage, Scene firstScene) {
        this.stage = stage;
        this.scene = firstScene;
    }

    // Method to update FlowPane with tags
    public void createTags(List<String> tags) {
        for (String tag : tags) {
            Button buttonTag = new Button();
            buttonTag.setPrefWidth(130);
            buttonTag.setPrefHeight(20);
            buttonTag.setText(capitalize(tag));
            buttonTag.setStyle("-fx-background-color: white;");
            buttonTag.getClass().getResource("css/style.css");
            buttonTag.getStyleClass().add("round-layout");
            buttonTag.setCursor(Cursor.HAND);
            flowp.getChildren().add(buttonTag);
        }
    }

    public void attachValue (News news, Stage stage){

        this.title.setText(news.getTitle());
        this.author.setText(news.getAuthor());
        this.datetype.setText(news.getCreationDate() + " \\ " + news.getType());
        this.createTags(news.getTags());
        // set prevContent here

        this.title.setOnAction(visitSite -> {
            FXMLLoader loadweb = new FXMLLoader(getClass().getResource("show-web.fxml"));
            ShowWebController showWebController = new ShowWebController(stage, scene);
            loadweb.setController(showWebController);

            try {
                Parent visitScene = loadweb.load();

                showWebController = loadweb.getController();
                showWebController.showVisitScene(news);
                stage.setScene(new Scene(visitScene));
                stage.show();
            } catch (IOException e) {
//                e.printStackTrace();
                FXMLLoader loadOfflineContent = new FXMLLoader(getClass().getResource("offline-content.fxml"));
                OfflineContentController offlineContentController = new OfflineContentController(stage, scene);
                loadOfflineContent.setController(offlineContentController);

                try {
                    Parent visitOfflineContent = loadOfflineContent.load();

                    offlineContentController = loadOfflineContent.getController();
                    offlineContentController.showOfflineContent(news);
                    stage.setScene(new Scene(visitOfflineContent));
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }

        });

    }


    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return "Unknown";
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

}

