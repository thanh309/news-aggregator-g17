package group17.news_aggregator.gui;

import group17.news_aggregator.news.News;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ShowWebController {
    private Stage stageWeb;
    private Scene sceneWeb;
    @FXML
    private Pane Navigator;


    @FXML
    private WebView webView;
    private WebEngine engine;

    @FXML
    private AnchorPane webscene;


    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView nextImage;

    @FXML
    private ImageView prevImage;

    public ShowWebController(Stage stage, Scene scene) {
        this.stageWeb = stage;
        this.sceneWeb = scene;
    }
    public void initialize() {
        homeImage.setOnMouseClicked(backHome ->{
            stageWeb.hide();
        });
    }
    public void showVisitScene(News news) {
        engine = webView.getEngine();
        engine.load(news.getLink());
    }

}
