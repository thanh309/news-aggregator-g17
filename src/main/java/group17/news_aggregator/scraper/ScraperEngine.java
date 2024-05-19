package group17.news_aggregator.scraper;

import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import group17.news_aggregator.scraper.article.CryptoSlateScraper;
import group17.news_aggregator.scraper.article.CryptonewsScraper;
import group17.news_aggregator.scraper.article.CryptopolitanScraper;
import group17.news_aggregator.scraper.blog.MediumScraper;

import java.util.ArrayList;
import java.util.List;

public class ScraperEngine {
    private boolean scrapeCryptonews = false;

    private boolean scrapeCryptopolitan = false;

    private boolean scrapeCryptoSlate = false;

    private boolean scrapeMedium = false;

    private int nbPagesCryptonews = 0;

    private int nbPagesCryptopolitan = 0;

    private int nbPagesCryptoSlate = 0;

    private String fileName = "test.csv";

    public void setScrapeCryptonews(boolean scrapeCryptonews) {
        this.scrapeCryptonews = scrapeCryptonews;
    }

    public void setScrapeCryptopolitan(boolean scrapeCryptopolitan) {
        this.scrapeCryptopolitan = scrapeCryptopolitan;
    }

    public void setScrapeCryptoSlate(boolean scrapeCryptoSlate) {
        this.scrapeCryptoSlate = scrapeCryptoSlate;
    }

    public void setScrapeMedium(boolean scrapeMedium) {
        this.scrapeMedium = scrapeMedium;
    }

    public void setNbPagesCryptonews(int nbPagesCryptonews) {
        this.nbPagesCryptonews = nbPagesCryptonews;
    }

    public void setNbPagesCryptopolitan(int nbPagesCryptopolitan) {
        this.nbPagesCryptopolitan = nbPagesCryptopolitan;
    }

    public void setNbPagesCryptoSlate(int nbPagesCryptoSlate) {
        this.nbPagesCryptoSlate = nbPagesCryptoSlate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void scrape() throws InterruptedException {
        String outputPath = "src/main/resources/data/" + fileName;
        List<News> list = new ArrayList<>();
        if (scrapeCryptonews) {
            CryptonewsScraper cryptonewsScraper = new CryptonewsScraper(nbPagesCryptonews);
            list.addAll(cryptonewsScraper.scrapeAll());
        }
        if (scrapeCryptoSlate) {
            CryptoSlateScraper cryptoSlateScraper = new CryptoSlateScraper(nbPagesCryptoSlate);
            list.addAll(cryptoSlateScraper.scrapeAll());
        }
        if (scrapeCryptopolitan) {
            CryptopolitanScraper cryptopolitanScraper = new CryptopolitanScraper(nbPagesCryptopolitan);
            list.addAll(cryptopolitanScraper.scrapeAll());
        }
        if (scrapeMedium) {
            MediumScraper mediumScraper = new MediumScraper();
            list.addAll(mediumScraper.scrapeAll());
        }

        CSVConverter converter = new CSVConverter();
        converter.toCSV(list, outputPath, true);
    }

    public static void main(String[] args) throws InterruptedException {
        // test
        ScraperEngine scraperEngine = new ScraperEngine();

        scraperEngine.scrapeCryptoSlate = true;
        scraperEngine.setNbPagesCryptoSlate(5);

        scraperEngine.setFileName("testFile.csv");

        scraperEngine.scrape();
    }
}