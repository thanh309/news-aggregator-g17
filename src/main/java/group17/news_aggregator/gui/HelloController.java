package group17.news_aggregator.gui;

import group17.news_aggregator.search_engine.Query;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javasearchengine.core.searchengine.SearchEngine;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class HelloController {


    @FXML
    private TextField filterText;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField cateTextField;

    @FXML
    private TextField tagTextField;

    @FXML
    private ScrollPane scollableid;


    @FXML
    private VBox vboxcont;

    @FXML
    private Button next20;

    @FXML
    private Button prev20;

    @FXML
    private Pagination pagination;
    @FXML
    private Button search_but;

    @FXML
    private TextField endDateField;
    @FXML
    private TextField startDateField;

    @FXML
    private TextField toPage;

    @FXML
    private Text errorFormatText;
    private Stage stage;
    private Scene mainScene;

    private Stage newStage = new Stage();

    private final CSVConverter csvConverter = new CSVConverter();

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private int startIndex = 0;
    private int endIndex = 20;

    public HelloController() {
    }

    public HelloController(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
    }

    public void search_handle(List<News> originalNewsList, List<News> newsList, SearchEngine searchEngine, List<Integer> ids) {
        String textQuery = filterText.getText();

        String startDate = startDateField.getText().trim() + " 00:00:00";
        String endDate = endDateField.getText().trim() + " 00:00:00";

        int totalNewPage = (int) Math.ceil((double) newsList.size() / 20);

        Query query;
        try {
            long startDateMillis;
            if (Objects.equals(startDateField.getText(), "")) {
                startDateMillis = 0;
            } else {
                startDateMillis = LocalDateTime.parse(startDate, dateTimeFormatter).atZone(ZoneId.systemDefault())
                        .toInstant().toEpochMilli();
            }

            long endDateMillis;
            if (Objects.equals(endDateField.getText(), "")) {
                endDateMillis = Long.MAX_VALUE;
            } else {
                endDateMillis = LocalDateTime.parse(endDate, dateTimeFormatter).atZone(ZoneId.systemDefault())
                        .toInstant().toEpochMilli();
            }

            query = new Query(textQuery, authorTextField.getText(), cateTextField.getText(), tagTextField.getText(), startDateMillis, endDateMillis);

        } catch (DateTimeParseException dte) {
            errorFormatText.setVisible(true);
            return;
        }

        List<Integer> res;

        if (!textQuery.isBlank()) {
            res = searchEngine.searchFromFile(ids, query.getSearchQuery(), 100000);
        } else {
            res = new ArrayList<>(ids);
        }

//        System.out.println(res);
        searchEngine.filterIndices(res, query, originalNewsList);
//        System.out.println(res);
        newsList.clear();
//        System.out.println(res);
        for (Integer i : res) {
            newsList.add(originalNewsList.get(i));
        }

        pagination.setPageCount(totalNewPage);
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * 20;
            int toIndex = Math.min(fromIndex + 20, newsList.size());
            displayNews(fromIndex, toIndex, newsList);
            return new VBox();
        });

        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            int fromIndex = newIndex.intValue() * 20;
            int toIndex = Math.min(fromIndex + 20, newsList.size());
            displayNews(fromIndex, toIndex, newsList);
        });

        toPage.setOnAction(event -> {
            int pageNumber = Integer.parseInt(toPage.getText());
            if (pageNumber >= 1 && pageNumber <= totalNewPage) {
                pagination.setCurrentPageIndex(pageNumber - 1);
            }
        });


        startIndex = 0;
        endIndex = 20;

        displayNews(startIndex, endIndex, newsList);
    }


    public void initialize() {
        CSVConverter csvConverter = new CSVConverter();
        List<News> originalNewsList = csvConverter.fromCSV("src/main/resources/data/database.csv");

        int size = originalNewsList.size();
        int totalPage = (int) Math.ceil((double) originalNewsList.size() / 20);
        // search
        SearchEngine searchEngine = new SearchEngine();
        searchEngine.initialize(originalNewsList);
        List<Integer> ids = IntStream.rangeClosed(0, size - 1).boxed().toList();

        List<News> newsList = new ArrayList<>();


        search_but.setOnMouseClicked(mouseEvent -> search_handle(originalNewsList, newsList, searchEngine, ids));
        filterText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search_handle(originalNewsList, newsList, searchEngine, ids);
            }
        });
        startDateField.setOnMouseClicked(mouseEvent -> errorFormatText.setVisible(false));
        endDateField.setOnMouseClicked(mouseEvent -> errorFormatText.setVisible(false));

        pagination.setPageCount(totalPage);
        pagination.setPageFactory(pageIndex -> {
            int fromIndex = pageIndex * 20;
            int toIndex = Math.min(fromIndex + 20, originalNewsList.size());
            displayNews(fromIndex, toIndex, originalNewsList);
            return new VBox();
        });

        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            int fromIndex = newIndex.intValue() * 20;
            int toIndex = Math.min(fromIndex + 20, originalNewsList.size());
            displayNews(fromIndex, toIndex, originalNewsList);
        });

        toPage.setOnAction(event -> {
            int pageNumber = Integer.parseInt(toPage.getText());
            if (pageNumber >= 1 && pageNumber <= totalPage) {
                pagination.setCurrentPageIndex(pageNumber - 1);
            }
        });

        displayNews(startIndex, endIndex, originalNewsList);

    }


    private void displayNews(int startIndex, int endIndex, List<News> newsList) {
        vboxcont.getChildren().clear();
        int size = newsList.size();
        if (startIndex < size && endIndex <= size) {
            List<? extends News> subList = newsList.subList(startIndex, endIndex);
            for (News news : subList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("news-component.fxml"));
                    NewsController newsController = new NewsController(newStage, mainScene);
                    loader.setController(newsController);

                    HBox newsComponent = loader.load();


                    newsController.attachValue(news, stage);
                    newsController = loader.getController();
                    newsController.attachValue(news, newStage);

                    vboxcont.getChildren().add(newsComponent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
