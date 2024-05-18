package group17.news_aggregator.gui.utils;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.gui.utils.auto_complete_field.AuthorTextField;
import group17.news_aggregator.gui.utils.auto_complete_field.CategoryTextField;
import group17.news_aggregator.gui.utils.auto_complete_field.TagTextField;
import group17.news_aggregator.news.News;
import group17.news_aggregator.search_engine.SearchEngine;

import java.util.List;

public class DataLoader {
    private static DataLoader instance;

    private final String dataPath = "src/main/resources/data/database.csv";

    private List<News> cachedNews;

    private SearchEngine searchEngine;

    private AuthorTextField authorTextField;

    private TagTextField tagTextField;

    private CategoryTextField categoryTextField;

    public CategoryTextField getCategoryTextField() {
        return categoryTextField;
    }

    public TagTextField getTagTextField() {
        return tagTextField;
    }

    public AuthorTextField getAuthorTextField() {
        return authorTextField;
    }

    private DataLoader() {
        loadNews();
        initializeSearchEngine();
        initializeAutocompleteFields();
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

    private void initializeAutocompleteFields() {
        authorTextField = new AuthorTextField();
        tagTextField = new TagTextField();
        categoryTextField = new CategoryTextField();

        authorTextField.setPrefHeight(30);
        authorTextField.setPromptText("Author");

        categoryTextField.setPrefHeight(30);
        categoryTextField.setPromptText("Category");

        tagTextField.setPrefHeight(30);
        tagTextField.setPromptText("Tag");
    }
}