import group17.news_aggregator.news.News;
import group17.news_aggregator.search_engine.SearchEngine;

import java.util.ArrayList;
import java.util.List;

public class TestSortFeatures {
    public static void main(String[] args) {
        SearchEngine searchEngine = new SearchEngine();
        List<News> listNews = new ArrayList<>();
        News news1= new News();
        news1.setTitle("Introduction to AI");
        news1.setCreationDate(100);

        News news2 = new News();
        news2.setTitle("Apple watch");
        news2.setCreationDate(100);

        News news3 = new News();
        news3.setTitle("The new laptop");
        news3.setCreationDate(100);

        News news4 = new News();
        news4.setTitle("The new laptop");
        news4.setCreationDate(100);

        News news5 = new News();
        news5.setTitle("Machine Learning");
        news5.setCreationDate(100);

        listNews.add(news1);
        listNews.add(news2);
        listNews.add(news3);
        listNews.add(news4);
        listNews.add(news5);

        List<Integer> ids = new ArrayList<>();
        ids.add(0);
        ids.add(1);
        ids.add(2);
        ids.add(3);
        ids.add(4);


        searchEngine.sortByTitle(ids, listNews);
        System.out.println(ids);

        searchEngine.sortByCreationDate(ids, listNews);
        System.out.println(ids);

        searchEngine.sortByTitle(ids, listNews);
        System.out.println(ids);
        searchEngine.sortByTitle(ids, listNews);
        System.out.println(ids);
        searchEngine.sortByTitle(ids, listNews);
        System.out.println(ids);
        searchEngine.sortByTitle(ids, listNews);
        System.out.println(ids);
        searchEngine.sortByTitle(ids, listNews);
        System.out.println(ids);
        searchEngine.sortByTitle(ids, listNews);
        System.out.println(ids);
        searchEngine.sortByCreationDate(ids, listNews);
        System.out.println(ids);
        searchEngine.sortByCreationDate(ids, listNews);
        System.out.println(ids);
        searchEngine.sortByCreationDate(ids, listNews);
        System.out.println(ids);

    }

}
