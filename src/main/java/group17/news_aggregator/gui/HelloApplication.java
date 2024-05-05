package group17.news_aggregator.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Text textEmail = new Text("Email");
        Text textPassword = new Text("Password");

        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();

        Button submitButton = new Button("Submit");
        Button clearButton = new Button("Clear");


        GridPane gridPane = new GridPane();
        gridPane.setMinSize(400, 200);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(textEmail, 0, 0);
        gridPane.add(emailField, 1, 0);
        gridPane.add(textPassword, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(submitButton, 0, 2);
        gridPane.add(clearButton, 1, 2);

        //Styling nodes
        submitButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");
        clearButton.setStyle("-fx-background-color: darkslateblue; -fx-text-fill: white;");

        textPassword.setStyle("-fx-font: normal bold 20px 'serif' ");
        textEmail.setStyle("-fx-font: normal bold 20px 'serif' ");
        gridPane.setStyle("-fx-background-color: BEIGE;");


        //Creating a scene object
        Scene scene = new Scene(gridPane);

        submitButton.setOnMouseClicked(event -> System.out.println("Submit button clicked"));
        //Setting title to the Stage
        primaryStage.setTitle("CSS Example");

        //Adding scene to the stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }
}