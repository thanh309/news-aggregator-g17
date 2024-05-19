package group17.news_aggregator.scraper;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import group17.news_aggregator.scraper.article.CryptonewsScraper;
import group17.news_aggregator.scraper.article.CryptoSlateScraper;
import group17.news_aggregator.scraper.article.CryptopolitanScraper;
import group17.news_aggregator.scraper.blog.MediumScraper;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String outputPath = "src/main/resources/data/database.csv";

        List<News> list = new ArrayList<>();

        CryptonewsScraper scraper0 = new CryptonewsScraper();
        list.addAll(scraper0.scrapeAll());

        CryptoSlateScraper scraper1 = new CryptoSlateScraper();
        list.addAll(scraper1.scrapeAll());

        MediumScraper scraper2 = new MediumScraper();
        list.addAll(scraper2.scrapeAll());

        CryptopolitanScraper scraper3 = new CryptopolitanScraper();
        list.addAll(scraper3.scrapeAll());

        /*
        --------------------------------------

        CHECK OVERWRITE BOOLEAN BEFORE RUNNING

        --------------------------------------
         */

        CSVConverter converter = new CSVConverter();
        System.out.println(list.size());
        converter.toCSV(list, outputPath, true);
    }
}
