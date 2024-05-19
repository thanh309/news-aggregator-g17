package group17.news_aggregator.gui.utils;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.exception.RequestException;
import group17.news_aggregator.gui.controller.PricePredictionController;
import group17.news_aggregator.gui.utils.auto_complete_field.AuthorTextField;
import group17.news_aggregator.gui.utils.auto_complete_field.CategoryTextField;
import group17.news_aggregator.gui.utils.auto_complete_field.TagTextField;
import group17.news_aggregator.news.News;
import group17.news_aggregator.search_engine.SearchEngine;
import javafx.scene.image.Image;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class DataLoader {
    private static DataLoader instance;

    private final String dataPath = "src/main/resources/data/database.csv";

    private List<News> cachedNews;

    private List<List<String>> cachedPricePredictions;

    private SearchEngine searchEngine;

    private AuthorTextField authorTextField;

    private TagTextField tagTextField;

    private CategoryTextField categoryTextField;

    private Image cryptopolitanImage;

    private Image cryptoslateImage;

    private Image cryptonewsImage;

    private Image mediumImage;

    public Image getMediumImage() {
        return mediumImage;
    }

    public Image getCryptonewsImage() {
        return cryptonewsImage;
    }

    public Image getCryptoslateImage() {
        return cryptoslateImage;
    }

    public Image getCryptopolitanImage() {
        return cryptopolitanImage;
    }

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
        loadPricePredictions();
        initializeAutocompleteFields();
        initializeImage();
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

    public List<List<String>> getPricePredictions() {
        return cachedPricePredictions;
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

    private void loadPricePredictions() {
        PricePredictionController pricePredictionController = new PricePredictionController();
        try {
            cachedPricePredictions = pricePredictionController.getFormattedResponse();
        } catch (IOException | InterruptedException | RequestException | JSONException e) {
            e.printStackTrace();
            cachedPricePredictions = List.of();
        }
    }

    private void initializeImage() {
        mediumImage = new Image("group17/news_aggregator/gui/image/website_icons/medium.png");
        cryptonewsImage = new Image("group17/news_aggregator/gui/image/website_icons/cryptonews.png");
        cryptoslateImage = new Image("group17/news_aggregator/gui/image/website_icons/cryptoslate.png");
        cryptopolitanImage = new Image("group17/news_aggregator/gui/image/website_icons/cryptopolitan.png");
    }
}