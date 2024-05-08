package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController {

    @FXML
    private Button authorSearch;

    @FXML
    private TextField filterText;

    @FXML
    private ScrollPane scollableid;

    @FXML
    private Button tagsSearch;

    @FXML
    private Button titleSearch;

    @FXML
    private VBox vbox_2;

    @FXML
    private VBox vboxcont;

    @FXML
    private Button next20;

    @FXML
    private Button prev20;

    private Stage stage;
    private Scene firstScene;

    private Stage newStage = new Stage();

    private final CSVConverter csvConverter = new CSVConverter();
    private int startIndex = 0;
    private int endIndex = 20;

    public HelloController() {
    }
    public HelloController(Stage stage, Scene firstScene) {
        this.stage = stage;
        this.firstScene = firstScene;
    }

    private List<? extends News> newsList = csvConverter.fromCSV("src/main/java/group17/news_aggregator/csv_converter/output_Cryptopolitan_Test.csv");

    public void initialize() {
        displayNews(startIndex, endIndex);


        int sizeList = newsList.size();
        next20.setOnMouseClicked(increase20 -> {
            if (endIndex + 20 <= sizeList) {
                this.startIndex += 20;
                this.endIndex += 20;
                displayNews(startIndex, endIndex);
            }
        });
        prev20.setOnMouseClicked(decrease20 -> {
            if (startIndex >= 20) {
                this.startIndex -= 20;
                this.endIndex -= 20;
                displayNews(startIndex, endIndex);
            }
        });
    }



    private void displayNews(int startIndex, int endIndex) {
        vboxcont.getChildren().clear();


        int size = newsList.size();
        if (startIndex < size && endIndex <= size) {
            List<? extends News> subList = newsList.subList(startIndex, endIndex);
            for (News news : subList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("news-component.fxml"));
                    NewsController newsController = new NewsController(newStage, firstScene);
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
