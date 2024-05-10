package javasearchengine.core.searchengine;

import group17.news_aggregator.news.News;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchEngine {

    private BM25 searchCore;

    public List<List<String>> corpusTokenizer(List<News> newsList) {
        List<List<String>> resultList = new ArrayList<>();
        for (News news : newsList) {
            String row = news.getTitle() + " " + news.getContent();
            resultList.add(Arrays.asList(FileHandling.removePuncts(row).split("\\s+")));
        }
        return resultList;
    }

    public void initialize(List<News> newsList) {
        List<List<String>> tokenizedCorpus = corpusTokenizer(newsList);
        searchCore = new BM25(tokenizedCorpus);
    }

    public List<Integer> searchFromFile(List<Integer> toSortList, String query, int maxNumberOfResults) {
        List<String> tokenizedQuery = new ArrayList<>(Arrays.asList(FileHandling.removePuncts(query).split("\\s+")));
        return searchCore.getTopNIndex(tokenizedQuery, toSortList, maxNumberOfResults);
    }
}