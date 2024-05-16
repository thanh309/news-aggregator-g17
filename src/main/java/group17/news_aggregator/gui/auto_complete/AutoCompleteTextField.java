package group17.news_aggregator.gui.auto_complete;

import group17.news_aggregator.auto_complete.AutoComplete;
import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AutoCompleteTextField extends TextField implements Initializable {
    private static final int maxEntries = 5;
    protected static List<News> newsList = new CSVConverter().fromCSV("src/main/resources/data/database.csv");
    private final List<String> wordDict = new ArrayList<>();
    private final ContextMenu entriesPopup = new ContextMenu();
    protected String fieldType;
    private List<String> res;
    private AutoComplete autoComplete;

    public AutoCompleteTextField() {
        try {
            FXMLLoader l = new FXMLLoader(getClass().getResource("ac.fxml"));
            l.setController(this);
            l.setRoot(this);
            l.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populatePopup(List<String> res) {
        List<CustomMenuItem> menuItems = new ArrayList<>();
        int count = Math.min(res.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = res.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(actionEvent -> {
                setText(result);
                entriesPopup.hide();
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switch (fieldType) {
            case "author":
                for (News news : newsList) {
                    wordDict.add(news.getAuthor());
                }
                break;
            case "tag":
                for (News news : newsList) {
                    wordDict.addAll(news.getTags());
                }
                break;
            case "category":
                for (News news : newsList) {
                    wordDict.add(news.getCategory());
                }
                break;
        }

        autoComplete = new AutoComplete(wordDict);
        autoComplete.createTrie();

        textProperty().addListener(event -> {
            if (getText().isEmpty()) {
                entriesPopup.hide();
            } else {
                res = autoComplete.getSuggestion(getText());
                if (!res.isEmpty()) {
                    populatePopup(res);
                    if (!entriesPopup.isShowing()) {
                        entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    entriesPopup.hide();
                }
            }
        });

        focusedProperty().addListener((observableValue, aBoolean, aBoolean2) -> entriesPopup.hide());
    }
}
