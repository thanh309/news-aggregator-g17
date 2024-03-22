package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class TestJsoup {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup
                .connect("https://phet-dev.colorado.edu/html/build-an-atom/0.0.0-3/simple-text-only-test-page.html")
                .get();
        System.out.println(document.body());
    }
}