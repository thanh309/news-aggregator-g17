package group17.news_aggregator.scraper.article;

import group17.news_aggregator.exception.EmptyContentException;
import group17.news_aggregator.news.Article;
import group17.news_aggregator.news.News;
import group17.news_aggregator.scraper.design.ArticleScraper;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static group17.news_aggregator.scraper.ScraperConstants.MAX_NEWS_PER_SITE;
import static group17.news_aggregator.scraper.ScraperConstants.MAX_RETRIES;

public class CryptopolitanScraper extends ArticleScraper {
    private final int MAX_PAGE;

    public CryptopolitanScraper() {
        MAX_PAGE = MAX_NEWS_PER_SITE / 40;
    }

    public CryptopolitanScraper(int maxPage) {
        MAX_PAGE = maxPage;
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.cryptopolitan.com/ubisofts-shooter-xdefiant-postponed-2/";
        CryptopolitanScraper scraper = new CryptopolitanScraper();
        News news = new News();
        scraper.getInfoFromURL(url, news);
        System.out.println("done");
    }

    @Override
    public List<Article> scrapeAll() throws InterruptedException {
        List<Article> resultList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(50);

        pageLoop:
        for (int i = 1; i <= MAX_PAGE; i++) {
            int retryCount = 0;
            boolean success = false;

            while (retryCount < MAX_RETRIES && !success) {
                try {
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
                        executorService.execute(
                                () -> {
                                    String articleUrl = link.attr("abs:href");
                                    Article article = scrapeFromURL(articleUrl);
                                    resultList.add(article);
                                }
                        );
                    }

                    System.out.printf("Page %d scraped. ", i);
                    System.out.println("Number of links: " + links.size());

                    success = true;

                } catch (HttpStatusException httpStatusException) {
                    int statusCode = httpStatusException.getStatusCode();
                    if (statusCode == 404) {
                        break pageLoop;
                    } else {
                        retryCount++;
                        System.out.printf("Error fetching page %d. Retrying (%d/%d)...\n", i, retryCount, MAX_RETRIES);
                    }
                } catch (IOException e) {
                    retryCount++;
                    System.out.printf("Error fetching page %d. Retrying (%d/%d)...\n", i, retryCount, MAX_RETRIES);
                }
            }

        }

        System.out.println("-----------------");
        System.out.println("Finished scraping");
        System.out.println("-----------------");

        executorService.shutdown();
        boolean ignored = executorService.awaitTermination(300, TimeUnit.SECONDS);
        return resultList;
    }

    @Override
    public void getInfoFromURL(String url, News news) throws IOException {
        news.setLink(url);
        news.setWebsiteSource("Cryptopolitan");

        Document document = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        /* get tags and category */
        try {
            Elements tags = document
                    .body()
                    .select("div[class=\"elementor-element elementor-element-58c72d8 elementor-align-left elementor-widget elementor-widget-breadcrumbs\"]")
                    .select("a[href]");
            tags.removeFirst();
            news.setCategory(tags.removeFirst().text());

            List<String> tagsList = tags.eachText();

            Elements tags2 = document
                    .head()
                    .select("meta[property=\"article:tag\"]");
            for (Element element : tags2) {
                tagsList.add(element.attr("content"));
            }
            news.setTags(tagsList);
        } catch (NoSuchElementException ignored) {
        }

        /* get author */
        String author = document
                .head()
                .select("meta[name=\"twitter:data1\"]")
                .attr("content");
        news.setAuthor(author);


        /* get summary */
        try {
            String summary = document
                    .body()
                    .select("div[class=\"elementor-element elementor-element-bcc212e elementor-widget elementor-widget-shortcode\"]")
                    .select("ul")
                    .eachText().getFirst();
            news.setSummary(summary);
        } catch (NoSuchElementException ignored) {
        }


        /* get title */
        String title = document
                .body()
                .select("div[class=\"elementor-element elementor-element-b01a881 elementor-widget__width-inherit elementor-widget elementor-widget-theme-post-title elementor-page-title elementor-widget-heading\"]")
                .text();
        news.setTitle(title);

        /* get content */
        try {
            List<String> content = document
                    .select("div[class=\"elementor-element elementor-element-e3418d0 cp-post-content elementor-widget elementor-widget-theme-post-content\"]")
                    .select("div[class=\"elementor-widget-container\"]")
                    .getFirst()
                    .children()
                    .eachText();
            news.setContent(content);
        } catch (NoSuchElementException e) {
            throw new EmptyContentException(news.getLink());
        }

        /* get creation date */
        String datetime = document
                .head()
                .select("meta[property=\"og:updated_time\"]")
                .attr("content");

        if (datetime.isBlank()) {
            news.setCreationDate(0);
        } else {
            news.setCreationDate(Instant.parse(datetime).toEpochMilli());
        }
    }
}
