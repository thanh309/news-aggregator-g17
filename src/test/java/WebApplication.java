import group17.news_aggregator.gui.utils.WebFormatter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

public class WebApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("JavaFX WebView Example");


        String url = "https://readmedium.com/surprise-bitcoin-is-back-above-50-000-f943a2fa9de1";

        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        String header = doc.head().html();

        WebFormatter.format(doc);

        String content = doc.html();

//        var x = doc.select("div[data-elementor-type=\"single-post\"]").first().cssSelector();
//        System.out.println(x);
//        var x = doc.select("style");
//
//        for (Element element : x) {
//            System.out.println(element.toString().replaceAll("<.+?>", ""));
//        }



        WebView webView = new WebView();
        webView.setPrefSize(50000, 50000);
        WebEngine webEngine = webView.getEngine();


//        if (url.split("/")[2].equals("readmedium.com")) {
//            webEngine.setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("css/medium-reader.css")).toString());
//        }


        webEngine.loadContent(header + content);
        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 1080, 720);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
