package group17.news_aggregator.gui.controller;

import group17.news_aggregator.gui.utils.WebFormatter;
import group17.news_aggregator.news.News;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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

    @FXML
    private Pane Navigator;

    @FXML
    private WebView webView;

    @FXML
    private AnchorPane webscene;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView nextImage;

    @FXML
    private ImageView prevImage;

    public ShowWebController(Stage stage, Scene scene, List<News> newsList) {
        this.stageWeb = stage;
        this.sceneWeb = scene;
        this.newsList = newsList;
    }

    public void initialize() {
        homeImage.setOnMouseClicked(backHome -> {
            stageWeb.hide();
        });

        nextImage.setOnMouseClicked(nextOn -> {
            if (currentIndex < newsList.size() - 1) {
                currentIndex += 1;
                try {
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
                    showVisitScene(newsList.get(currentIndex));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showVisitScene(News news) throws IOException {
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