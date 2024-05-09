package group17.news_aggregator.scraper;

import group17.news_aggregator.exception.EmptyContentException;
import group17.news_aggregator.news.Article;
import group17.news_aggregator.news.News;

import java.io.IOException;
import java.util.List;

import static group17.news_aggregator.scraper.ScraperConstants.MAX_RETRIES;

public abstract class ArticleScraper implements IScraper {

    @Override
    public Article scrapeFromURL(String url) {
        Article article = new Article();
        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try {
                getInfoFromURL(url, article);
                return article;
            } catch (EmptyContentException emptyContentException) {
                System.out.println(emptyContentException.getMessage());
                return null;
            } catch (IOException e) {
                retryCount++;
                System.out.printf("Error parsing URL: %s. Retrying (%d/%d)...\n", url, retryCount, MAX_RETRIES);
            }

        }
        return null;
    }

    @Override
    public abstract List<Article> scrapeAll() throws InterruptedException;

    @Override
    public abstract void getInfoFromURL(String url, News news) throws IOException;

}
