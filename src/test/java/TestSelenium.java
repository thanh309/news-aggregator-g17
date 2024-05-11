import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class TestSelenium {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/libs/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://medium.com/tag/web3/recommended");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 1; i <= 10; i++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        List<String> urls = new ArrayList<>();

        String htmlSource = driver.getPageSource();
        driver.quit();

        Document doc = Jsoup.parse(htmlSource);
        Elements links = doc.select("div[role=\"link\"]");
        for (Element link : links) {
            urls.add(link.attr("data-href"));
        }


        System.out.println(urls);
        System.out.println("done");


    }
}
