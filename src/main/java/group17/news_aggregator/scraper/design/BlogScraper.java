package group17.news_aggregator.scraper.design;

import group17.news_aggregator.exception.EmptyContentException;
import group17.news_aggregator.news.Blog;
import group17.news_aggregator.news.News;

import java.io.IOException;
import java.util.List;

import static group17.news_aggregator.scraper.ScraperConstants.MAX_RETRIES;

public abstract class BlogScraper implements IScraper {
    @Override
    public Blog scrapeFromURL(String url) {
        Blog blog = new Blog();
        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try {
                getInfoFromURL(url, blog);
                return blog;
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
    public abstract List<Blog> scrapeAll() throws InterruptedException;

    @Override
    public abstract void getInfoFromURL(String url, News news) throws IOException;
}