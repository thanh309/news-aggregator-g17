package group17.news_aggregator.gui.controller;

import group17.news_aggregator.gui.utils.DataLoader;
import group17.news_aggregator.news.News;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NewsController {
    private Scene scene;

    private Stage stage;

    private List<News> newsList;

    private Image cryptopolitanImage = DataLoader.getInstance().getCryptopolitanImage();

    private Image cryptoslateImage = DataLoader.getInstance().getCryptoslateImage();

    private Image cryptonewsImage = DataLoader.getInstance().getCryptonewsImage();

    private Image mediumImage = DataLoader.getInstance().getMediumImage();

    @FXML
    private ImageView iconImage;

    @FXML
    Button author;

    @FXML
    Button title;

    @FXML
    TextFlow datetype;

    @FXML
    private FlowPane flowPane;

    @FXML
    private HBox news;

    public NewsController() {
    }

    public NewsController(Stage stage, Scene mainScene, List<News> newsList) {
        this.stage = stage;
        this.scene = mainScene;
        this.newsList = newsList;
    }

    public void createTags(List<String> tags, int lim) {
        flowPane.getChildren().clear();
        int count = 0;

        for (String tag : tags) {
            count += 1;
            Button buttonTag = new Button();
            buttonTag.setPrefWidth(130);
            buttonTag.setPrefHeight(20);
            buttonTag.setText(capitalize(tag));
//            buttonTag.getClass().getResource("css/news.css");
            buttonTag.getStyleClass().add("round-layout-tag");
            buttonTag.getStyleClass().add("transparent");
            buttonTag.setCursor(Cursor.HAND);
            flowPane.getChildren().add(buttonTag);
            if (count >= lim) {
                break;
            }
        }
    }

    public void attachValue(News news, Stage stage, List<News> newsList) {
        switch (news.getWebsiteSource()) {
            case "Cryptopolitan" -> iconImage.setImage(cryptopolitanImage);
            case "Cryptoslate" -> iconImage.setImage(cryptoslateImage);
            case "Cryptonews" -> iconImage.setImage(cryptonewsImage);
            case "Medium" -> iconImage.setImage(mediumImage);
        }
        this.title.setText(news.getTitle());
        this.author.setText(news.getAuthor());
        this.datetype.getChildren().clear();
        Text newText = new Text(news.getCreationDateStr() + " | " + news.getType());
        newText.setStyle("-fx-font-size: 15px;");
        this.datetype.getChildren().add(newText);
        this.createTags(news.getTags(), 10);

        this.title.setOnAction(visitSite -> {
            FXMLLoader loadweb = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/show-web.fxml"));
            ShowWebController showWebController = new ShowWebController(stage, scene, newsList);
            loadweb.setController(showWebController);

            try {
                Parent visitScene = loadweb.load();
                showWebController = loadweb.getController();
                showWebController.showVisitScene(news);
                stage.setScene(new Scene(visitScene));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                FXMLLoader loadOfflineContent = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/offline-content.fxml"));
                OfflineContentController offlineContentController = new OfflineContentController(stage, scene, newsList);

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