package group17.news_aggregator.scraper;

import group17.news_aggregator.news.Article;
import group17.news_aggregator.news.CryptopolitanArticle;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class CryptopolitanScraper extends Scraper {

    public static final int MAX_PAGE = 1000;

    @Override
    public ArrayList<Article> scrapeArticleUrls() {

        var resultList = new ArrayList<Article>();

        try {
            for (int i = 1; i < MAX_PAGE; i++) {
                String url = String.format("https://www.cryptopolitan.com/news/page/%d/", i);
                Document document = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                        .get();
                Elements links = document
                        .select("h3[class=\"elementor-heading-title elementor-size-default\"]")
                        .select("a[href]");
                for (Element link : links) {
                    Article article = new CryptopolitanArticle();
                    resultList.add(getArticleInfoFromUrl(link.attr("abs:href"), article));
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


    @Override
    public Article getArticleInfoFromUrl(String url, Article article) throws IOException {
        article.setLink(url);

        Document document = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        //get tags
        Elements tags = document
                .select("div[class=\"elementor-element elementor-element-58c72d8 elementor-align-left elementor-widget elementor-widget-breadcrumbs\"]")
                .select("a[href]");
        tags.removeFirst();
        article.setTags(tags.eachText());

        // get author
        String author = document
                .head()
                .select("meta[name=\"twitter:data1\"]")
                .attr("content");
        article.setAuthor(author);


        return article;
    }
}
