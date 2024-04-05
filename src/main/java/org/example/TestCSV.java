package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestCSV {

    private static String getAuthor(Document document) {
        Elements elements = document.select("meta[name=\"twitter:data1\"]");
        if (elements.isEmpty()) return "";
        return elements.getFirst().attr("content");
    }

    private static String getSource(Document document) {
        Elements elements = document.select("meta[property=\"og:site_name\"]");
        if (elements.isEmpty()) return "";
        return elements.getFirst().attr("content");
    }

//    private static String
    public static void main(String[] args) throws Exception {
        String url = "https://www.cryptopolitan.com/how-behavioral-investing-investment-choices/";
        Document document = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();
        System.out.println(document.select("meta"));
        Elements a = new Elements();
        a.isEmpty();
        System.out.println(getAuthor(document));
        System.out.println(getSource(document));
    }
}
