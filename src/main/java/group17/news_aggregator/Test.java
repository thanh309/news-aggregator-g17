package group17.news_aggregator;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.CryptopolitanArticle;
import group17.news_aggregator.scraper.CryptopolitanScraper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {

        String filePath = "src/resources/output_Cryptopolitan_Test.csv";
        CryptopolitanScraper scraper = new CryptopolitanScraper();


        List<String> urls = new ArrayList<>();
        File myObj = new File("src/resources/cryptopolitan_urls.txt");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String url = myReader.nextLine();
            urls.add(url);
        }
        myReader.close();

        List<CryptopolitanArticle> articleList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (String url : urls) {
            executorService.execute(
                    () -> articleList.add(scraper.scrapeArticle(url))
            );
        }
        executorService.shutdown();
        boolean ignored = executorService.awaitTermination(300, TimeUnit.SECONDS);

        CSVConverter converter = new CSVConverter();
        converter.toCSV(articleList, filePath, true);
        System.out.println("Done");
    }
}
