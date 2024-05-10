package javasearchengine.searchengine;


import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import javasearchengine.core.searchengine.SearchEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TestSearchFromFile {
    public static void main(String[] args) {

        CSVConverter csvConverter = new CSVConverter();
        List<News> newsList = csvConverter.fromCSV("src/main/resources/data/database.csv");

        SearchEngine searchEngine = new SearchEngine();
        searchEngine.initialize(newsList);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Query:");
        String query = scanner.next();
        scanner.close();

        List<Integer> ids = IntStream.rangeClosed(0, newsList.size() - 1).boxed().toList();
        List<Integer> res = searchEngine.searchFromFile(ids, query, 20);

        // test
        for (Integer i : res) {
            System.out.println(newsList.get(i).getTitle());
        }

    }
}
