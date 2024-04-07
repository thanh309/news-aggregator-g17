import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TestScraper {

    public static List<String> getArticleLinks(String category) {

        var resultList = new ArrayList<String>();

        try {
            for (int i = 1; i < 1000; i++) {
                String url = String.format("https://www.cryptopolitan.com/news/%s/page/%d/", category, i);
                Document document = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                        .get();
                Elements links = document.select("h3[class=\"elementor-heading-title elementor-size-default\"]").select("a[href]");
                for (Element link : links) {
                    resultList.add(link.attr("abs:href"));
                }
                System.out.printf("Page %d scraped%n", i);
                System.out.println("Number of links: " + links.size());
            }

        } catch (HttpStatusException httpStatusException) {

            int status = httpStatusException.getStatusCode();
            if (status == 404) {
                System.out.println("Finished scraping");
            } else {
                System.out.println(httpStatusException.getMessage());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public static void main(String[] args) {
        var linkList = getArticleLinks("etfs");
        int i = 0;
        for (String link : linkList) {
            System.out.println(link);
            i++;
        }
        System.out.println(i);
    }
}

