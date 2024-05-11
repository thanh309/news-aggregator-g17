package group17.news_aggregator.scraper.blog;

import group17.news_aggregator.exception.EmptyContentException;
import group17.news_aggregator.news.Blog;
import group17.news_aggregator.news.News;
import group17.news_aggregator.scraper.design.BlogScraper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MediumScraper extends BlogScraper {

    public MediumScraper() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/libs/chromedriver.exe");
    }

    public static void main(String[] args) throws InterruptedException {
        MediumScraper scraper = new MediumScraper();
        List<Blog> x = scraper.scrapeAll();
        System.out.println("done");

    }

    public void addURLsWithCategory(List<String> URLList, String fromURL, String category) {
        WebDriver driver = new ChromeDriver();
        driver.get(fromURL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 1; i <= 100; i++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String htmlSource = driver.getPageSource();
        driver.quit();

        Document doc = Jsoup.parse(htmlSource);
        Elements links = doc.select("div[role=\"link\"]");

        String[] x = category.split("-");
        StringBuilder formattedCategory = new StringBuilder();
        for (String s : x) {
            formattedCategory.append(s.substring(0, 1).toUpperCase()).append(s.substring(1)).append(" ");
        }

        for (Element link : links) {
            URLList.add(link.attr("data-href") + "|" + formattedCategory.toString().trim());
        }
    }

    @Override
    public List<Blog> scrapeAll() throws InterruptedException {

        List<Blog> resultList = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(50);
        final String[] mediumCategories =
                {"bitcoin", "cryptocurrency", "decentralized-finance", "ethereum", "nft", "web3"};
        List<String> urls = new ArrayList<>();


        for (String mediumCategory : mediumCategories) {
            executorService.execute(
                    () -> addURLsWithCategory(urls, "https://medium.com/tag/%s/recommended".formatted(mediumCategory), mediumCategory)
            );
        }

        executorService.shutdown();
        boolean ignored = executorService.awaitTermination(300, TimeUnit.SECONDS);

        executorService = Executors.newFixedThreadPool(50);
        for (String url : urls) {
            executorService.execute(
                    () -> {
                        String[] urlAndCategory = url.split("\\|");
                        Blog blog = scrapeFromURL(urlAndCategory[0]);
                        blog.setCategory(urlAndCategory[1]);
                        resultList.add(blog);
                    }
            );
        }

        executorService.shutdown();
        boolean ignore = executorService.awaitTermination(300, TimeUnit.SECONDS);

        System.out.println("-----------------");
        System.out.println("Finished scraping");
        System.out.println("-----------------");

        return resultList;
    }

    @Override
    public void getInfoFromURL(String url, News news) throws IOException {
        String[] temp = url.split("/");
        String readUrl = "https://readmedium.com/" + temp[temp.length - 1];

        news.setLink(readUrl);
        news.setWebsiteSource("Medium");
        news.setCategory("None");

        Document readDoc = Jsoup
                .connect(readUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        readDoc.select("div[class=\"link-block\"]").remove();

        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        // get tags
        try {
            Elements tags = Objects.requireNonNull(
                    readDoc
                            .body()
                            .select("div[class=\" text-sm font-bold mt-12 space-y-[4px]\"]")
                            .first()
            ).children();
            List<String> tagsList = tags.eachText();
            news.setTags(tagsList);
        } catch (NoSuchElementException | NullPointerException ignored) {
        }

        //get author
        String author = doc
                .head()
                .select("meta[name=\"author\"]")
                .attr("content");
        if (author.isEmpty()) {
            author = "Guest";
        }
        news.setAuthor(author);

        //get summary (description)
        try {
            String summary = readDoc
                    .select("div[class=\"prose break-words dark:prose-invert prose-p:leading-relaxed " +
                            "prose-pre:p-0 text-[var(--theme-text)]\"]")
                    .getFirst()
                    .children()
                    .get(1)
                    .text();
            news.setSummary(summary);
        } catch (IndexOutOfBoundsException ignored) {
        }

        // get title
        String title = doc
                .head()
                .select("meta[property=\"og:title\"]")
                .attr("content");
        news.setTitle(title);

        // get content
        try {
            List<String> content = readDoc
                    .select("article")
                    .getFirst()
                    .children()
                    .eachText();
            news.setContent(content);
        } catch (NoSuchElementException e) {
            throw new EmptyContentException(news.getLink());
        }

        // get creation date
        String datetime = doc
                .head()
                .select("meta[property=\"article:published_time\"]")
                .attr("content");

        if (datetime.isBlank()) {
            news.setCreationDate(0);
        } else {
            news.setCreationDate(Instant.parse(datetime).toEpochMilli());
        }
    }

}
