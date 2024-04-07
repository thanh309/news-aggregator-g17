package group17.news_aggregator.scraper2;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.Article;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String outputPath = "src/resources/output_Cryptopolitan2.csv";

        CryptopolitanScraper scraper = new CryptopolitanScraper();
        scraper.setMaxPage(10);
        List<Article> articleList = scraper.scrapeAll();

        CSVConverter converter = new CSVConverter();
        converter.toCSV(articleList, outputPath, true);
    }
}
