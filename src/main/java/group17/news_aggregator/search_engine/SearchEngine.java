package group17.news_aggregator.search_engine;

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

    public void filterIndices(List<Integer> ids, Query query, List<News> references) {
        ids.removeIf(id ->
                !references.get(id).getAuthor().toLowerCase().contains(query.getAuthor())
                        | !references.get(id).getCategory().toLowerCase().contains(query.getCategory())
                        | !String.join(" ", references.get(id).getTags()).toLowerCase().contains(query.getTag())
                        | references.get(id).getCreationDate() > query.getEndDateTime()
                        | references.get(id).getCreationDate() < query.getStartDateTime());
    }
}