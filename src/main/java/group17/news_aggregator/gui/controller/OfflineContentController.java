package group17.news_aggregator.gui.controller;

import group17.news_aggregator.news.News;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;


public class OfflineContentController {
    private Stage stageOffline;

    private Scene sceneOffline;

    private int currentIndex;

    private List<News> newsList;

    private ImageView copyLink = new ImageView();

    @FXML
    private Pane Navigator;

    @FXML
    private TextField httpField;

    @FXML
    private ImageView lostConnection;

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

    @FXML
    private HBox headerPage;


    public OfflineContentController(Stage stage, Scene scene, List<News> newsList) {
        this.stageOffline = stage;
        this.sceneOffline = scene;
        this.newsList = newsList;
    }

    public void initialize() {
        Image copyImage = new Image("group17/news_aggregator/gui/image/copy.png");
        Image copyDoneImage = new Image("group17/news_aggregator/gui/image/copyDone.png");
        copyLink.setImage(copyImage);
        copyLink.setPickOnBounds(true);

        nextImage.setOnMouseClicked(nextOffline -> {
            if (currentIndex < newsList.size() - 1) {
                currentIndex += 1;
                copyLink.setImage(copyImage);
                showOfflineContent(newsList.get(currentIndex));
            }
        });

        prevImage.setOnMouseClicked(prevOffline -> {
            if (currentIndex > 0) {
                currentIndex -= 1;
                copyLink.setImage(copyImage);
                showOfflineContent(newsList.get(currentIndex));
            }
        });

        copyLink.setOnMouseClicked(copied -> {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent linkCopied = new ClipboardContent();
            linkCopied.putString(httpField.getText());
            clipboard.setContent(linkCopied);
            copyLink.setImage(copyDoneImage);
        });

        copyLink.setFitHeight(40);
        headerPage.getChildren().add(copyLink);
    }

    public void showOfflineContent(News news) {
        httpField.setEditable(true);
        httpField.setText(news.getLink());
        httpField.setEditable(false);

        warnText.setText("Error: no internet connection, no access available");
        currentIndex = newsList.indexOf(news);
        List<String> stringList = news.getContent();

        txt.setText("");
        for (String str : stringList) {
            txt.setText(txt.getText() + str + "\n\n");
        }
    }
}