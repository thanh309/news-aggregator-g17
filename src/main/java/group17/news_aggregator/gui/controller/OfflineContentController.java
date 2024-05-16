package group17.news_aggregator.gui.controller;

import group17.news_aggregator.news.News;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;


public class OfflineContentController {

    private Stage stageOffline;
    private Scene sceneOffline;

    @FXML
    private Region spacer;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView lostConnection;

    @FXML
    private Pane naviPane;

    @FXML
    private ImageView nextImage;

    @FXML
    private ImageView prevImage;

    @FXML
    private ScrollPane textPane;

    @FXML
    private Text txt;

    @FXML
    private Text warnText;

    public OfflineContentController(Stage stage, Scene scene) {
        this.stageOffline = stage;
        this.sceneOffline = scene;
    }

    public void showOfflineContent(News news) {
        warnText.setText("Error: no internet connection, no access available");

        List<String> stringList = news.getContent();

        for (String str : stringList) {
            txt.setText(txt.getText() + str + "\n\n");
        }
    }
}
