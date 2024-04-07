package group17.news_aggregator.scraper2;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.Article;
import group17.news_aggregator.news.News;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        String outputPath = "src/resources/output_Cryptopolitan_Test2.csv";

        CryptopolitanScraper scraper = new CryptopolitanScraper();
        CSVConverter converter = new CSVConverter();
        List<Article> articleList = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        // get urls list from file
        File URLFromTxt = new File("src/resources/cryptopolitan_urls.txt");
        Scanner myReader = new Scanner(URLFromTxt);
        while (myReader.hasNextLine()) {
            String url = myReader.nextLine();
            urls.add(url);
        }
        myReader.close();

        // scrape urls to an article list
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (String url : urls) {
            executorService.execute(
                    () -> articleList.add(scraper.scrapeFromURL(url))
            );
        }
        executorService.shutdown();
        boolean ignored = executorService.awaitTermination(300, TimeUnit.SECONDS);

        // test adding to csv file
        converter.toCSV(articleList, outputPath, true);

        // test getting articles from csv file
        List<Article> articlesOut = (List<Article>) converter.fromCSV(outputPath);
        System.out.println("Size: " + articlesOut.size());
        System.out.println("Done");
    }
}
