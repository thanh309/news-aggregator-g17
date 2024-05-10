package group17.news_aggregator.gui;

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
        homeImage.setOnMouseClicked(backHome -> {
            stageWeb.hide();
        });
    }

    public void showVisitScene(News news) throws IOException {
        String url = news.getLink();
//        String url = "https://cryptonews.com/news/top-crypto-gainers-today-on-dexscreener-felon-yield-boob.htm";
        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        WebFormatter.format(doc);

        String header = doc.head().html();

        String content = doc.html();

        engine = webView.getEngine();
        engine.loadContent(header + content);
//        engine = webView.getEngine();
//        engine.load(news.getLink());

    }
}
