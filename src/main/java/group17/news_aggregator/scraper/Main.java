package group17.news_aggregator.scraper;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.Article;
import group17.news_aggregator.news.News;
import group17.news_aggregator.scraper.article.CryptoSlateScraper;
import group17.news_aggregator.scraper.article.CryptonewsScraper;
import group17.news_aggregator.scraper.article.CryptopolitanScraper;
import group17.news_aggregator.scraper.blog.MediumScraper;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String outputPath = "src/main/resources/data/morepage.csv";

        List<News> list = new ArrayList<>();

//        CryptopolitanScraper scraper0 = new CryptopolitanScraper();
//        list.addAll(scraper0.scrapeAll());
//
//        CryptonewsScraper scraper1 = new CryptonewsScraper();
//        list.addAll(scraper1.scrapeAll());
//
//        CryptoSlateScraper scraper2 = new CryptoSlateScraper();
//        list.addAll(scraper2.scrapeAll());

//        MediumScraper scraper3 = new MediumScraper();
//        list.addAll(scraper3.scrapeAll());

        /*
        --------------------------------------

        CHECK OVERWRITE BOOLEAN BEFORE RUNNING

        --------------------------------------
         */

        CSVConverter converter = new CSVConverter();
//        List<News> list = converter.fromCSV(outputPath);
//        System.out.println(list.size());

        MediumScraper scraper3 = new MediumScraper();
        list.addAll(scraper3.scrapeAll());
        System.out.println(list.size());
//
        converter.toCSV(list, outputPath, true);
    }
}
