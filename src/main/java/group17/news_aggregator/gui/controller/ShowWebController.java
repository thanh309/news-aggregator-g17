package group17.news_aggregator.gui.controller;

import group17.news_aggregator.gui.utils.WebFormatter;
import group17.news_aggregator.news.News;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ShowWebController {
    private Stage stageWeb;

    private Scene sceneWeb;

    private List<News> newsList;

    private int currentIndex;

    private WebEngine engine;

//    private ImageView copyLink = new ImageView();

    private ImageView copyLink = new ImageView();


    @FXML
    private Pane Navigator;

    @FXML
    private HBox headerPage;

    @FXML
    private TextField httpField;

    @FXML
    private ImageView nextImage;

    @FXML
    private ImageView prevImage;

    @FXML
    private WebView webView;

    @FXML
    private AnchorPane webscene;



    public ShowWebController(Stage stage, Scene scene, List<News> newsList) {
        this.stageWeb = stage;
        this.sceneWeb = scene;
        this.newsList = newsList;
    }

    public void initialize() {
        Image copyImage = new Image("group17/news_aggregator/gui/image/copy.png");
        Image copyDoneImage = new Image("group17/news_aggregator/gui/image/copyDone.png");
        copyLink.setImage(copyImage);
        copyLink.setPickOnBounds(true);

//
        Stop[] stops = new Stop[] { new Stop(0, Color.LIGHTCYAN), new Stop(1, Color.rgb(97, 225, 237))};
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        Navigator.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundFill(linearGradient, null, null)));
        nextImage.setOnMouseClicked(nextOn -> {
            if (currentIndex < newsList.size() - 1) {
                currentIndex += 1;
                try {
                    copyLink.setImage(copyImage);
                    showVisitScene(newsList.get(currentIndex));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        prevImage.setOnMouseClicked(prevOn -> {
            if (currentIndex > 0) {
                currentIndex -= 1;
                try {
                    copyLink.setImage(copyImage);
                    showVisitScene(newsList.get(currentIndex));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        copyLink.setOnMouseClicked(copied ->{
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent linkCopied = new ClipboardContent();
            linkCopied.putString(httpField.getText());
            clipboard.setContent(linkCopied);
            copyLink.setImage(copyDoneImage);
        });
        copyLink.setFitHeight(40);
        headerPage.getChildren().add(copyLink);
    }

    public void showVisitScene(News news) throws IOException {
        httpField.setEditable(true);
        httpField.setText(news.getLink());
        httpField.setEditable(false);
        currentIndex = newsList.indexOf(news);
        String url = news.getLink();
        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        WebFormatter.format(doc);
        String header = doc.head().html();
        String content = doc.html();
        engine = webView.getEngine();

        if (news.getWebsiteSource().equals("Medium")) {
            engine.setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/group17/news_aggregator/gui/css/medium-reader.css")).toString());
        }

        engine.loadContent(header + content);
    }
}