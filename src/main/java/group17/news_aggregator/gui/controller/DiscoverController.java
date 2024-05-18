package group17.news_aggregator.gui.controller;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.gui.utils.DataLoader;
import group17.news_aggregator.gui.utils.auto_complete_field.AuthorTextField;
import group17.news_aggregator.gui.utils.auto_complete_field.CategoryTextField;
import group17.news_aggregator.gui.utils.auto_complete_field.TagTextField;
import group17.news_aggregator.news.News;
import group17.news_aggregator.search_engine.Query;
import group17.news_aggregator.search_engine.SearchEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class DiscoverController {
    private final CSVConverter csvConverter = new CSVConverter();

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private Stage stage;

    private Scene mainScene;

    private Stage newStage = new Stage();

    private int startIndex = 0;

    private int endIndex = 20;

    private int currentPage = 0;

    private List<News> originalNewsList;

    private List<News> newsList;

    @FXML
    private VBox vboxAuthor;

    @FXML
    private TextField filterText;

    @FXML
    private ScrollPane scrollableID;

    @FXML
    private VBox vboxContainer;

    @FXML
    private ImageView next20;

    @FXML
    private ImageView prev20;

    @FXML
    private Label totalPages;

    @FXML
    private Button searchButton;

    @FXML
    private TextField endDateField;

    @FXML
    private TextField startDateField;

    @FXML
    private TextField toPage;

    @FXML
    private Text errorFormatText;
    @FXML
    private VBox vboxCategory;

    @FXML
    private VBox vobxTag;

    private TextField authorComplete;
    private TextField cateComplete;
    private TextField tagComplete;

    @FXML
    private Button noneSort;

    @FXML
    private Button titleSort;

    @FXML
    private Button cdSort;

    public DiscoverController() {
    }


    public DiscoverController(Stage stage, Scene mainScene) {
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

            query = new Query(textQuery, authorComplete.getText(), cateComplete.getText(), tagComplete.getText(), startDateMillis, endDateMillis);

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


        searchEngine.filterIndices(res, query, originalNewsList);
        newsList.clear();
        for (Integer i : res) {
            newsList.add(originalNewsList.get(i));
        }

        List<Integer> originalIds = new ArrayList<>();
        for (Integer id : res) {
            originalIds.add(Integer.valueOf(id));
        }

        next20.setOnMouseClicked(increase20 -> {
            if (endIndex + 20 <= newsList.size()) {
                startIndex += 20;
                endIndex += 20;
                currentPage++;
                displayNews(startIndex, endIndex, newsList);
            }
        });

        prev20.setOnMouseClicked(decrease20 -> {
            if (startIndex >= 20) {
                startIndex -= 20;
                endIndex -= 20;
                currentPage--;
                displayNews(startIndex, endIndex, newsList);
            }
        });

        toPage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String pageNumberText = toPage.getText();
                if (!pageNumberText.isEmpty()) {
                    int pageNumber = Integer.parseInt(pageNumberText);
                    if (pageNumber >= 1 && pageNumber <= totalNewPage) {
                        currentPage = pageNumber - 1;
                        startIndex = currentPage * 20;
                        endIndex = startIndex + 20;
                        displayNews(startIndex, endIndex, newsList);
                    }
                }
            }
        });

        startIndex = 0;
        endIndex = 20;
        currentPage = 0;
        toPage.setText("1");

        displayNews(startIndex, endIndex, newsList);

        titleSort.setOnMouseClicked(titleSort -> {
            searchEngine.sortByTitle(res, originalNewsList);
            updateSortedList(newsList, res);
        });

        cdSort.setOnMouseClicked(cdSort -> {
            searchEngine.sortByCreationDate(res, originalNewsList);
            updateSortedList(newsList, res);
        });

        noneSort.setOnMouseClicked(noneSort -> {
            updateSortedList(newsList, originalIds);
        });
    }

    public void initialize() {

        authorComplete = new AuthorTextField();
        authorComplete.setPrefHeight(30);
        authorComplete.setPromptText("Author");
        vboxAuthor.getChildren().add(authorComplete);

        cateComplete = new CategoryTextField();
        cateComplete.setPrefHeight(30);
        cateComplete.setPromptText("Category");
        vboxCategory.getChildren().add(cateComplete);

        tagComplete = new TagTextField();
        tagComplete.setPrefHeight(30);
        tagComplete.setPromptText("Tag");
        vobxTag.getChildren().add(tagComplete);


        originalNewsList = DataLoader.getInstance().getNews();
        SearchEngine searchEngine = DataLoader.getInstance().getSearchEngine();

        int size = originalNewsList.size();
        int totalPage = (int) Math.ceil((double) originalNewsList.size() / 20);

//        List<Integer> ids = new ArrayList<>();
//        for (int i = 0; i < size; i ++){
//            ids.add(i);
//        }
        List<Integer> ids = new ArrayList<>(IntStream.rangeClosed(0, size-1).boxed().toList());
        List<Integer> originalIds = new ArrayList<>();
        newsList = new ArrayList<>();

        for (Integer id : ids) {
            originalIds.add(Integer.valueOf(id));
            newsList.add(originalNewsList.get(id));
        }

        searchButton.setOnMouseClicked(mouseEvent -> search_handle(originalNewsList, newsList, searchEngine, originalIds));
        filterText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                search_handle(originalNewsList, newsList, searchEngine, originalIds);
            }
        });

        startDateField.setOnMouseClicked(mouseEvent -> errorFormatText.setVisible(false));
        endDateField.setOnMouseClicked(mouseEvent -> errorFormatText.setVisible(false));

        next20.setOnMouseClicked(increase20 -> {
            if (endIndex <= originalNewsList.size()) {
                startIndex += 20;
                endIndex += 20;
                currentPage++;
                displayNews(startIndex, endIndex, originalNewsList);
            }
        });

        prev20.setOnMouseClicked(decrease20 -> {
            if (startIndex >= 20) {
                startIndex -= 20;
                endIndex -= 20;
                currentPage--;
                displayNews(startIndex, endIndex, originalNewsList);
            }
        });


        toPage.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String pageNumberText = toPage.getText();
                int pageNumber = Integer.parseInt(pageNumberText);
                if (pageNumber >= 1 && pageNumber <= totalPage) {
                    currentPage = pageNumber - 1;
                    startIndex = currentPage * 20;
                    endIndex = startIndex + 20;
                    displayNews(startIndex, endIndex, originalNewsList);
                }
            }
        });

        displayNews(startIndex, endIndex, originalNewsList);

        titleSort.setOnMouseClicked(titleSort -> {
            searchEngine.sortByTitle(ids, originalNewsList);
            updateSortedList(newsList, ids);

        });

        cdSort.setOnMouseClicked(cdSort -> {
            searchEngine.sortByCreationDate(ids, originalNewsList);
            updateSortedList(newsList, ids);
        });

        noneSort.setOnMouseClicked(noneSort -> {
            updateSortedList(newsList, originalIds);
        });
    }

    private void updateSortedList(List<News> aNewsList, List<Integer> sortedIds) {
        aNewsList.clear();
        for (int i = 0; i < sortedIds.size(); i++) {
            aNewsList.add(originalNewsList.get(sortedIds.get(i)));
        }

        startIndex = 0;
        endIndex = 20;
        currentPage = 0;
        toPage.setText("1");
        displayNews(startIndex, endIndex, aNewsList);
    }

    private void displayNews(int startIndex, int endIndex, List<News> newsList) {
        vboxContainer.getChildren().clear();
        int size = newsList.size();
        endIndex = Math.min(endIndex, size);

        if (startIndex < size) {
            List<? extends News> subList = newsList.subList(startIndex, endIndex);
            for (News news : subList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/news-component.fxml"));
                    NewsController newsController = new NewsController(newStage, mainScene, newsList);

                    loader.setController(newsController);
                    HBox newsComponent = loader.load();

                    newsController.attachValue(news, stage, newsList);
                    newsController = loader.getController();
                    newsController.attachValue(news, newStage, newsList);

                    vboxContainer.getChildren().add(newsComponent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        toPage.setText(String.valueOf(currentPage + 1));
        prev20.setDisable(currentPage <= 0);
        next20.setDisable(endIndex >= size);
        int total = (int) Math.ceil((double) size / 20);
        totalPages.setText("/  " + total);
    }

    @FXML
    void aboutUs(ActionEvent event) {
        if (stage == null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/about-us.fxml"));

        try {
            Parent aboutScene = loader.load();
            stage.setScene(new Scene(aboutScene));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void home(MouseEvent event) {
        if (stage == null) {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/home-view.fxml"));

        try {
            Parent mainScene = loader.load();
            stage.setScene(new Scene(mainScene));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
