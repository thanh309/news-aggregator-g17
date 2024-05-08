package group17.news_aggregator.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class WebApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("JavaFX WebView Example");


        String url = "https://medium.com/@rihot_gusron/traveling-salesman-problem-in-lingo-adbf55da3467";

        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        // specific to this fucking site; you probably won't need it
        doc.select("div[class=\"elementor elementor-421319 elementor-location-header\"]").remove();

        String header = doc.head().html();

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


        webEngine.loadContent(header + content);
        VBox vBox = new VBox(webView);
        Scene scene = new Scene(vBox, 1080, 720);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
