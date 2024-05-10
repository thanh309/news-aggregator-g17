package group17.news_aggregator.gui;

import group17.news_aggregator.search_engine.Query;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import javafx.stage.Stage;
import javasearchengine.core.searchengine.SearchEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class HelloController {

    @FXML
    private CheckBox authorCheckBox;

    @FXML
    private TextField filterText;

    @FXML
    private ScrollPane scollableid;

    @FXML
    private CheckBox tagsCheckBox;

    @FXML
    private CheckBox titleCheckBox;
    @FXML
    private VBox vbox_2;

    @FXML
    private VBox vboxcont;

    @FXML
    private Button next20;

    @FXML
    private Button prev20;


    @FXML
    private ImageView search_img;

    private Stage stage;
    private Scene mainScene;

    private Stage newStage = new Stage();

    private final CSVConverter csvConverter = new CSVConverter();
    private int startIndex = 0;
    private int endIndex = 20;

    public HelloController() {
    }
    public HelloController(Stage stage, Scene mainScene) {
        this.stage = stage;
        this.mainScene = mainScene;
    }



    public void initialize() {
        CSVConverter csvConverter = new CSVConverter();
        List<News> originalNewsList = csvConverter.fromCSV("src/main/resources/data/database.csv");

        int size = originalNewsList.size();
        displayNews(startIndex, endIndex, originalNewsList);

        // search
        SearchEngine searchEngine = new SearchEngine();
        searchEngine.initialize(originalNewsList);
        List<Integer> ids = IntStream.rangeClosed(0, size - 1).boxed().toList();

        List<News> newsList = new ArrayList<>();


        search_img.setOnMouseClicked(search -> {
            String textQuery =  filterText.getText();
            if (Objects.equals(textQuery, "")){

                startIndex = 0;
                endIndex = 20;
                displayNews(startIndex, endIndex, originalNewsList);
            } else{

                next20.setOnMouseClicked(increase20 -> {

                    if (endIndex + 20 <= newsList.size()) {
                        this.startIndex += 20;
                        this.endIndex += 20;
                        displayNews(startIndex, endIndex, newsList);

                    }
                });
                prev20.setOnMouseClicked(decrease20 -> {
                    if (startIndex >= 20) {
                        this.startIndex -= 20;
                        this.endIndex -= 20;
                        displayNews(startIndex, endIndex, newsList);
                    }
                });
                Query query = new Query(textQuery, "", "", "");
                List<Integer> res = searchEngine.searchFromFile(ids, query.getSearchQuery(), 10000);
//                searchEngine.filterIndices(res, query, newsList);
                newsList.clear();
                System.out.println(res);
                for (Integer i: res){
                    newsList.add(originalNewsList.get(i));
                }
                startIndex = 0;
                endIndex = 20;
                displayNews(startIndex, endIndex, newsList);
            }
        });

        
        int sizeList = originalNewsList.size();
        next20.setOnMouseClicked(increase20 -> {
            if (endIndex + 20 <= sizeList) {
                this.startIndex += 20;
                this.endIndex += 20;
                displayNews(startIndex, endIndex, originalNewsList);
            }
        });
        prev20.setOnMouseClicked(decrease20 -> {
            if (startIndex >= 20) {
                this.startIndex -= 20;
                this.endIndex -= 20;
                displayNews(startIndex, endIndex, originalNewsList);
            }
        });

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
