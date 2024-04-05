package group17.news_aggregator.scraper;

import group17.news_aggregator.news.Article;
import group17.news_aggregator.news.CryptopolitanArticle;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CryptopolitanScraper extends Scraper {

    public static final int MAX_PAGE = 1000;

    @Override
    public ArrayList<Article> scrapeArticleUrls() {

        var resultList = new ArrayList<Article>();

        try {
            for (int i = 1; i <= MAX_PAGE; i++) {
                String url = String.format("https://www.cryptopolitan.com/news/page/%d/", i);
                Document document = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                        .get();
                Elements links = document
                        .body()
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


        // get tags and category
        Elements tags = document
                .body()
                .select("div[class=\"elementor-element elementor-element-58c72d8 elementor-align-left elementor-widget elementor-widget-breadcrumbs\"]")
                .select("a[href]");
        tags.removeFirst();
        article.setCategory(tags.removeFirst().text());

        List<String> tagsList = tags.eachText();

        Elements tags2 = document
                .head()
                .select("meta[property=\"article:tag\"]");
        for (Element element : tags2) {
            tagsList.add(element.attr("content"));
        }
        article.setTags(tagsList);


        // get author
        String author = document
                .head()
                .select("meta[name=\"twitter:data1\"]")
                .attr("content");
        article.setAuthor(author);


        // get summary
        try {
            String summary = document
                    .body()
                    .select("div[class=\"elementor-element elementor-element-bcc212e elementor-widget elementor-widget-shortcode\"]")
                    .select("ul")
                    .eachText().getFirst();
            article.setSummary(summary);
        } catch (NoSuchElementException ignored) {}



        // get title
        String title = document
                .body()
                .select("div[class=\"elementor-element elementor-element-b01a881 elementor-widget__width-inherit elementor-widget elementor-widget-theme-post-title elementor-page-title elementor-widget-heading\"]")
                .text();
        article.setTitle(title);


        // get content
        List<String> content = document
                .select("div[class=\"elementor-element elementor-element-e3418d0 cp-post-content elementor-widget elementor-widget-theme-post-content\"]")
                .select("div[class=\"elementor-widget-container\"]")
                .getFirst()
                .children()
                .eachText();
        article.setContent(content);


        // get creation date
        String creationDate = document
                .head()
                .select("meta[property=\"og:updated_time\"]")
                .attr("content");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        LocalDateTime time = LocalDateTime.parse(creationDate, dateTimeFormatter);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd 'at' HH:mm");
        article.setCreationDate(time.format(customFormatter));


        return article;
    }

    public static void main(String[] args) {
        CryptopolitanScraper scraper = new CryptopolitanScraper();
        ArrayList<Article> list = scraper.scrapeArticleUrls();
        System.out.println(list);
    }
}
