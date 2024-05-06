package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
    private ImageView search_img;

    @FXML
    private Button tagsSearch;

    @FXML
    private Button titleSearch;

    @FXML
    private VBox vbox_2;

    @FXML
    private VBox vboxcont;

    private final CSVConverter csvConverter = new CSVConverter();

    public void initialize() {
        List<? extends News> newsList = csvConverter.fromCSV("D:\\kybon\\OOP-NewAggregator\\Project\\output_Cryptopolitan_Test.csv");
        for (News news : newsList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("news-component.fxml"));
                HBox newsComponent = loader.load();
                NewsComponentController newsComponentController = loader.getController();

                // Populate the news component with data from the current News object
                newsComponentController.title.setText(news.getTitle());
                newsComponentController.author.setText(news.getAuthor());
                newsComponentController.datetype.setText(news.getCreationDate() + "\\" + news.getType());
                newsComponentController.createTags(news.getTags());

                // Add the news component to the main container
                vboxcont.getChildren().add(newsComponent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
