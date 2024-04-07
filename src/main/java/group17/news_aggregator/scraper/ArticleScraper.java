package group17.news_aggregator.scraper;

import group17.news_aggregator.exception.EmptyContentException;
import group17.news_aggregator.news.Article;
import group17.news_aggregator.news.News;

import java.io.IOException;
import java.util.List;

public abstract class ArticleScraper implements Scraper {

    private int maxPage = 1000;

    @Override
    public Article scrapeFromURL(String url) {
        Article article = new Article();
        try {
            getInfoFromURL(url, article);
            return article;
        } catch (EmptyContentException emptyContentException) {
            System.out.println(emptyContentException.getMessage());
        } catch (IOException e) {
            System.out.println("Error parsing URL: " + url);
        }
        return null;
    }

    @Override
    public abstract List<Article> scrapeAll() throws InterruptedException;

    @Override
    public abstract void getInfoFromURL(String url, News news) throws IOException;

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }
}
