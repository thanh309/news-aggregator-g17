package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class TestArticle {
    public static void main(String[] args) throws IOException {
        String url = "https://www.cryptopolitan.com/brics-massive-35b-economic-stimulus-package/";
        Document document = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        // try to get tags
        Elements tags = document
                .select("div[class=\"elementor-element elementor-element-58c72d8 elementor-align-left elementor-widget elementor-widget-breadcrumbs\"]")
                .select("a[href]");
        tags.removeFirst();
        System.out.println(tags.removeFirst().text());
        List<String> tagsList = tags.eachText();
        Elements tags2 = document
                .head()
                .select("meta[property=\"article:tag\"]");
        for (Element element : tags2) {
            tagsList.add(element.attr("content"));
        }
        System.out.println(tagsList);


        // get author
        String author = document
                .head()
                .select("meta[name=\"twitter:data1\"]")
                .attr("content");
//        System.out.println(author);

        // get summary
//        String summary = document
//                .select("div[class=\"elementor-element elementor-element-bcc212e elementor-widget elementor-widget-shortcode\"]")
//                .select("ul")
//                .eachText().getFirst();
//        System.out.println(summary);


        Elements content = document
                .select("div[class=\"elementor-element elementor-element-e3418d0 cp-post-content elementor-widget elementor-widget-theme-post-content\"]")
                .select("div[class=\"elementor-widget-container\"]");
//        System.out.println(content.getFirst().text());


        // get title
        String title = document
                .body()
                .select("div[class=\"elementor-element elementor-element-b01a881 elementor-widget__width-inherit elementor-widget elementor-widget-theme-post-title elementor-page-title elementor-widget-heading\"]")
                .text();

        // get creation date
//        String creationDate = document
//                .head()
//                .select("meta[property=\"og:updated_time\"]")
//                .attr("content");
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
//        LocalDateTime time = LocalDateTime.parse(creationDate, dateTimeFormatter);
//        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd 'at' HH:mm");
//        System.out.println(time.format(customFormatter));

        String creationTime = document
                .body()
                .select("li[class=\"elementor-icon-list-item elementor-repeater-item-189e642 elementor-inline-item\"]")
                .text();
        System.out.println(creationTime);




        JEditorPane editorPane = new JEditorPane("text/html", content.getFirst().html());
        editorPane.setEditable(false);


        JScrollPane scrollPane = new JScrollPane(editorPane);

        JFrame frame = new JFrame("HTML Viewer");
        frame.add(scrollPane);
        frame.setSize(1080, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
