import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class TestJsoup {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup
                .connect("https://www.cryptopolitan.com/news/page/2/")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();
        System.out.println(document.body());
    }
}