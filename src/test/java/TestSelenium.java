import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestSelenium {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/libs/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


        driver.get("https://www.cryptopolitan.com/news/");


        for (int i = 1; i <= 10; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            System.out.println("scrolling" + i);
            try {
                Thread.sleep(2000); // Wait for the page to load
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        String htmlSource = driver.getPageSource();
        Document doc = Jsoup.parse(htmlSource);
        Elements links = doc.select("a[href~=^/posts/article]");
        int ans = 0;
        for (Element link : links) {
            ans++;
            String href = link.attr("href");
            System.out.println(href);
        }

        System.out.println(ans);

        driver.quit();
    }
}
