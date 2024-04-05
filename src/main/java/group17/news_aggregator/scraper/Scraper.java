package group17.news_aggregator.scraper;

import group17.news_aggregator.news.Article;

import java.io.IOException;
import java.util.List;

public abstract class Scraper {

    public abstract List<Article> scrapeArticleUrls();

    public abstract Article getArticleInfoFromUrl(String url, Article article) throws IOException;


}
