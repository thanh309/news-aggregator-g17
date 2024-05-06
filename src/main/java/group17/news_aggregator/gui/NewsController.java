package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class NewsController {

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

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return "Unknown";
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

}

