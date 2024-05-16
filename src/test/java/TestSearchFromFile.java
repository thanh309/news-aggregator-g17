import group17.news_aggregator.csv_converter.CSVConverter;
import group17.news_aggregator.news.News;
import group17.news_aggregator.search_engine.Query;
import group17.news_aggregator.search_engine.SearchEngine;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class TestSearchFromFile {
    public static void main(String[] args) {

        CSVConverter csvConverter = new CSVConverter();
        List<News> newsList = csvConverter.fromCSV("src/main/resources/data/database.csv");

        SearchEngine searchEngine = new SearchEngine();
        searchEngine.initialize(newsList);
        List<Integer> ids = IntStream.rangeClosed(0, newsList.size() - 1).boxed().toList();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Query:");
        String textQuery = scanner.next();
        scanner.close();

        Query query = new Query(textQuery, "lacton", "", "");

        List<Integer> res = searchEngine.searchFromFile(ids, query.getSearchQuery(), 20);

        searchEngine.filterIndices(res, query, newsList);

        System.out.println(res);

        // test
        for (Integer i : res) {
            System.out.println(newsList.get(i).getTitle() + "| " + newsList.get(i).getAuthor() + "| " + newsList.get(i).getCategory() + "| " + newsList.get(i).getTags());
        }



        // test
        System.out.println();
        System.out.println("after filter");
        System.out.println();

        for (Integer i : res) {
            System.out.println(newsList.get(i).getTitle() + "| " + newsList.get(i).getAuthor() + "| " + newsList.get(i).getCategory() + "| " + newsList.get(i).getTags());
        }

        // this is the list of result indices
        System.out.println(res);

    }
}
