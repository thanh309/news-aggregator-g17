package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebController {
    @FXML
    private AnchorPane WebScene;
    @FXML
    private ImageView homeImage;
    @FXML
    private WebView webView;
    private WebEngine engine;

    public Stage stage;
    public Scene firstScene;

    public WebController(Stage stage, Scene scene) {
        this.stage = stage;
        this.firstScene = scene;
    }

    public void initialize() {
//        Image home = new Image("house.png");
//        homeImage.setImage(home);
        homeImage.setOnMouseClicked(backHome ->{
            stage.hide();
        });


    }

    public void showVisitScene(LineComponent lineComponent) {
        engine = webView.getEngine();
        engine.load(lineComponent.getLink());
    }


}