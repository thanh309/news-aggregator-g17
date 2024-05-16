package group17.news_aggregator.gui;

import group17.news_aggregator.news.News;
import group17.news_aggregator.csv_converter.CSVConverter;
import javasearchengine.core.searchengine.SearchEngine;

import java.util.List;

public class DataLoader {
    private static DataLoader instance;
    private List<News> cachedNews;
    private SearchEngine searchEngine;

    private final String dataPath = "src/main/resources/data/database.csv";

    private DataLoader() {
        loadNews();
        initializeSearchEngine();
    }

    public static DataLoader getInstance() {
        if (instance == null) {
            instance = new DataLoader();
        }
        return instance;
    }

    public List<News> getNews() {
        return cachedNews;
    }

    private void loadNews() {
        CSVConverter csvConverter = new CSVConverter();
        cachedNews = csvConverter.fromCSV(dataPath);
    }

    private void initializeSearchEngine() {
        searchEngine = new SearchEngine();
        searchEngine.initialize(cachedNews);
    }

    public SearchEngine getSearchEngine() {
        return searchEngine;
    }
}
