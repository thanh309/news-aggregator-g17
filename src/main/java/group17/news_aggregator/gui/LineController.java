package group17.news_aggregator.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class LineController {
    @FXML
    private Button previewContent;
    @FXML
    private Button author;
//    @FXML
//    private HBox TagBox;
    @FXML
    private FlowPane flowp;

    @FXML
    private HBox date_type;

    @FXML
    private Button title;

    @FXML
    private Label label;
    private Stage stage;
    private Scene firstScene;
    public LineController(Stage stage, Scene firstScene) {
        this.stage = stage;
        this.firstScene = firstScene;
    }

    public void attachValue(LineComponent lineComponent, Stage stage){
        label.setText(lineComponent.getCreationDate()+"\\/"+lineComponent.getType());
        title.setText(lineComponent.getTitle());
        author.setText(lineComponent.getAuthor());

        String[] LTags = lineComponent.getTags().split("\\|");

        previewContent.setOnAction(watchContent ->{
            FXMLLoader loadcontent = new FXMLLoader(getClass().getResource("../../../../../resources/content-view.fxml"));
            ContentController contentController = new ContentController(stage, firstScene);
            loadcontent.setController(contentController);


            try {
                Parent visitContent = loadcontent.load();

                contentController = loadcontent.getController();
                contentController.displayContent(lineComponent);

                stage.setScene(new Scene(visitContent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        title.setOnAction(visitSite -> {
            FXMLLoader loadweb = new FXMLLoader(getClass().getResource("../../../../../resources/webview.fxml"));
            WebController webController = new WebController(stage, firstScene); // Truyền Stage vào LineController
            loadweb.setController(webController);


            try {
                Parent visitScene = loadweb.load();

                webController = loadweb.getController();
                webController.showVisitScene(lineComponent);

                stage.setScene(new Scene(visitScene));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if (LTags.length == 0) {
//            TagBox.setVisible(false);


        } else {

            flowp.setVisible(true);
            for (String T: LTags) {
                Button buttonTag = new Button();
                buttonTag.setPrefWidth(130);
                buttonTag.setPrefHeight(25);
                buttonTag.setAlignment(Pos.TOP_LEFT);
                String capitalizedText = T.substring(0, 1).toUpperCase() + T.substring(1);
                buttonTag.setText(capitalizedText);
                buttonTag.setStyle("-fx-background-color: white; ");
                flowp.getChildren().add(buttonTag);

            }
        }
    }

    private class ContentController {
    }
}

