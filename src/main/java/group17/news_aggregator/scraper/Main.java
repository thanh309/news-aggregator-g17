package group17.news_aggregator.scraper;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.Article;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String outputPath = "src/main/resources/data/output_Cryptopolitan.csv";

        CryptopolitanScraper scraper = new CryptopolitanScraper();
        List<Article> articleList = scraper.scrapeAll();

        /*
        --------------------------------------

        CHECK OVERWRITE BOOLEAN BEFORE RUNNING

        --------------------------------------
         */

        CSVConverter converter = new CSVConverter();
        converter.toCSV(articleList, outputPath, true);
    }
}
