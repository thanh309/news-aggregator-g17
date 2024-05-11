package group17.news_aggregator.scraper.design;

import group17.news_aggregator.news.News;

import java.io.IOException;
import java.util.List;

public interface IScraper {
    News scrapeFromURL(String url);

    List<? extends News> scrapeAll() throws InterruptedException;

    void getInfoFromURL(String url, News news) throws IOException;

}
