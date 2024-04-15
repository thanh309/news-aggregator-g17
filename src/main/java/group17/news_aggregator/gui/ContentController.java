package group17.news_aggregator.gui;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ContentController implements Initializable {
    @FXML
    private ScrollPane scrPane;

    @FXML
    private ImageView homeImage2;

    @FXML
    private Text txt;
    private Stage stage;
    private Scene scene;

    public ContentController(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
    }

    public void displayContent(LineComponent lineComponent){
        String content = lineComponent.getContent();
        content = content.replace("|", "\n");
        txt.setText(content);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeImage2.setOnMouseClicked(backHome ->{
            stage.hide();
        });
    }
}
