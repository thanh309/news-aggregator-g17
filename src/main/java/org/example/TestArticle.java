package org.example;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class TestArticle {
    public static void main(String[] args) throws Exception {
        String url = "https://www.cryptopolitan.com/gsr-markets-license-institution-in-singapore/";
        Document document = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        //try to get tags
        Elements tags = document
                .select("div[class=\"elementor-element elementor-element-58c72d8 elementor-align-left elementor-widget elementor-widget-breadcrumbs\"]")
                .select("a[href]");
        tags.removeFirst();
        List<String> stringsTags = tags.eachText();

        //get author
        String author = document
                .head()
                .select("meta[name=\"twitter:data1\"]")
                .attr("content");
        System.out.println(author);


        Elements content = document
                .select("div[class=\"elementor-element elementor-element-e3418d0 cp-post-content elementor-widget elementor-widget-theme-post-content\"]")
                .select("div[class=\"elementor-widget-container\"]");
        if (content.size() != 1) throw new Exception("parse error");
        System.out.println(content.getFirst());


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
