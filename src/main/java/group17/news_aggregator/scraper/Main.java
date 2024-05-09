package group17.news_aggregator.scraper;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.Article;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String outputPath = "src/main/resources/data/database.csv";

        CryptopolitanScraper scraper0 = new CryptopolitanScraper();
        List<Article> list = scraper0.scrapeAll();

        CryptonewsScraper scraper1 = new CryptonewsScraper();
        list.addAll(scraper1.scrapeAll());

        CryptoSlateScraper scraper2 = new CryptoSlateScraper();
        list.addAll(scraper2.scrapeAll());

        /*
        --------------------------------------

        CHECK OVERWRITE BOOLEAN BEFORE RUNNING

        --------------------------------------
         */

        CSVConverter converter = new CSVConverter();
        converter.toCSV(list, outputPath, true);
    }
}
