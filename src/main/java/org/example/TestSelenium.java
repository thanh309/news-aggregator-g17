package org.example;

import org.jsoup.Jsoup;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestSelenium {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


        driver.get("https://a16zcrypto.com/posts/?format=article");


        for (int i = 1; i <= 5; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            System.out.println("dads");
            try {
                Thread.sleep(4000); // Wait for the page to load
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        String htmlSource = driver.getPageSource();
        System.out.println(Jsoup.parse(htmlSource));

        driver.quit();
    }
}
