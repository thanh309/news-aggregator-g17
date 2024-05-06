package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;

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

    private final CSVConverter csvConverter = new CSVConverter();
    private int startIndex = 0;
    private int endIndex = 20;

    public void initialize() {
        displayNews(startIndex, endIndex);
    }

    @FXML
    void next20(MouseEvent event) {
        startIndex -= 20;
        endIndex -= 20;
        displayNews(startIndex, endIndex);
    }

    @FXML
    void prev20(MouseEvent event) {
        if(startIndex >= 0){
            startIndex += 20;
            endIndex += 20;
            displayNews(startIndex, endIndex);
        }
    }

    private void displayNews(int startIndex, int endIndex) {
        vboxcont.getChildren().clear();
        List<? extends News> newsList = csvConverter.fromCSV("\"D:\\kybon\\OOP-NewAggregator\\Project\\output_Cryptopolitan.csv\"");
        for (int i = startIndex; i < Math.min(endIndex, newsList.size()); i++) {
            News news = newsList.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("news-component.fxml"));
                HBox newsComponent = loader.load();
                NewsComponentController newsComponentController = loader.getController();

                newsComponentController.title.setText(news.getTitle());
                newsComponentController.author.setText(news.getAuthor());
                newsComponentController.datetype.setText(news.getCreationDate() + "\\" + news.getType());
                newsComponentController.createTags(news.getTags());

                vboxcont.getChildren().add(newsComponent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
