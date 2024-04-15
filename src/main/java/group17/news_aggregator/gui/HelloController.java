package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileReader;
import java.net.URL;
import com.opencsv.CSVReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

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

    private String column = "";

    private Stage stage;
    private Scene firstScene;
    private Stage newStage = new Stage();

    public HelloController(Stage stage, Scene firstScene) {
        this.stage = stage;
        this.firstScene = firstScene;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        authorSearch.setOnAction(event -> {
            column = "AUTHOR";
        });

        titleSearch.setOnAction(event -> {
            column ="TITLE";
        });

        tagsSearch.setOnAction(event -> {
            column ="TAGS";
        });
        filterText.textProperty().addListener((obs, oldValue, newValue) -> {
            filterData(newValue, column);
        });

        filterData("","");
    }

    @FXML
    private void filterData(String filterValue ,String column) {
        try {
            List<LineComponent> data = readFromCSV("output_Cryptopolitan.csv",filterValue, column);

            vboxcont.getChildren().clear();
            for (LineComponent lineComponent : data) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../../../resources/Line.fxml"));
                LineController lineController = new LineController(newStage, firstScene);
                fxmlLoader.setController(lineController);

                HBox lineBox = fxmlLoader.load();
                lineController = fxmlLoader.getController();
                lineController.attachValue(lineComponent,newStage);

                vboxcont.getChildren().add(lineBox);
            }
            scollableid.setContent(vboxcont);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<LineComponent> readFromCSV(String filename, String filterValue, String column) throws Exception {
        List<LineComponent> lst = new ArrayList<>();
        int recCount = 0;

        try (FileReader fileReader = new FileReader(filename);
             CSVReader csvReader = new CSVReader(fileReader)) {

            String[] headers = csvReader.readNext(); // Assuming the first row contains headers

            String[] values;
            while ((values = csvReader.readNext()) != null && recCount <= 20) {
                if (matchesFilter(values, filterValue, column)) {
                    LineComponent lineComponent = new LineComponent(
                            values[0], values[1], values[2], values[3], values[4],
                            values[5], values[6], values[7], values[8], values[9]
                    );
                    lst.add(lineComponent);
                    recCount ++;
                }
            }
        }
        return lst;
    }

    private boolean matchesFilter(String[] values, String filterValue, String column) {
        if (column.isEmpty() || column.equals("TAGS")) {
            return values[6].toLowerCase().contains(filterValue.toLowerCase());
        } else if (column.equals("AUTHOR")) {
            return values[0].toLowerCase().contains(filterValue.toLowerCase());
        } else if (column.equals("TITLE")) {
            return values[7].toLowerCase().contains(filterValue.toLowerCase());
        }
        return false;
    }

}

