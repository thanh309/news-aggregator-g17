package group17.news_aggregator.scraper;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.CryptopolitanArticle;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String outputPath = "src/resources/output_Cryptopolitan.csv";

        CryptopolitanScraper scraper = new CryptopolitanScraper();
        List<CryptopolitanArticle> articleList = scraper.scrapeArticleList();

        CSVConverter converter = new CSVConverter();
        converter.toCSV(articleList, outputPath, true);
    }
}
