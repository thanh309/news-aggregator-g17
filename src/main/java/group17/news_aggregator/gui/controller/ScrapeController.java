package group17.news_aggregator.gui.controller;

import group17.news_aggregator.scraper.ScraperEngine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ScrapeController {
    private Stage stageScr;

    private Scene sceneScr;

    @FXML
    private CheckBox checkCryptonews;

    @FXML
    private CheckBox checkCryptopolitan;

    @FXML
    private CheckBox checkCryptoslate;

    @FXML
    private CheckBox checkMedium;

    @FXML
    private TextField namefile;

    @FXML
    private TextField numberPageCryptonews;

    @FXML
    private TextField numberPageCryptopolitan;

    @FXML
    private TextField numberPageCryptoslate;

    @FXML
    private Button scrapeButton;

    public ScrapeController() {
    }

    public ScrapeController(Stage stageScr, Scene sceneScr) {
        this.stageScr = stageScr;
        this.sceneScr = sceneScr;
    }

    @FXML
    void returnHome(ActionEvent event) {
        if (stageScr == null) {
            stageScr = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader loaderAu = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/home-view.fxml"));

        try {
            Parent aboutScene = loaderAu.load();
            stageScr.setScene(new Scene(aboutScene));
            stageScr.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void aboutUs(ActionEvent event) {
        if (stageScr == null) {
            stageScr = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/about-us.fxml"));

        try {
            Parent aboutScene = searchLoader.load();
            stageScr.setScene(new Scene(aboutScene));
            stageScr.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void discover(ActionEvent event) {
        if (stageScr == null) {
            stageScr = (Stage) ((Node) event.getSource()).getScene().getWindow();
        }

        FXMLLoader searchLoader = new FXMLLoader(getClass().getResource("/group17/news_aggregator/gui/fxml/discover-view.fxml"));

        try {
            Parent aboutScene = searchLoader.load();
            stageScr.setScene(new Scene(aboutScene));
            stageScr.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void scrapeNow(ActionEvent event) throws InterruptedException {
        ScraperEngine scraperEngine = new ScraperEngine();
        if (checkCryptonews.isSelected()) {
            scraperEngine.setScrapeCryptonews(true);
            try {
                int pageCount = Integer.parseInt(numberPageCryptonews.getText());
                scraperEngine.setNbPagesCryptonews(pageCount);

            } catch (NumberFormatException e) {
                // Handle the case where the text cannot be parsed as an integer
                numberPageCryptonews.clear();
            }
        }

        if (checkCryptopolitan.isSelected()) {
            scraperEngine.setScrapeCryptopolitan(true);
            try {
                int pageCount = Integer.parseInt(numberPageCryptopolitan.getText());
                scraperEngine.setNbPagesCryptopolitan(pageCount);
            } catch (NumberFormatException e) {
                // Handle the case where the text cannot be parsed as an integer
                numberPageCryptopolitan.clear();
            }
        }

        if (checkCryptoslate.isSelected()) {
            scraperEngine.setScrapeCryptoSlate(true);
            try {
                int pageCount = Integer.parseInt(numberPageCryptoslate.getText());
                scraperEngine.setNbPagesCryptoSlate(pageCount);
            } catch (NumberFormatException e) {
                // Handle the case where the text cannot be parsed as an integer
                numberPageCryptoslate.clear();
            }
        }

        if (checkMedium.isSelected()) {
            scraperEngine.setScrapeMedium(true);
        }

        scraperEngine.setFileName(namefile.getText());

        scraperEngine.scrape();

        scrapeButton.setText("Done");
    }
}