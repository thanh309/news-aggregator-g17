package group17.news_aggregator.search_engine;

import group17.news_aggregator.news.News;

import java.util.*;

public class SearchEngine {

    private BM25 searchCore;

    public List<List<String>> corpusTokenizer(List<News> newsList) {
        List<List<String>> resultList = new ArrayList<>();
        for (News news : newsList) {
            String row = news.getTitle() + " " + news.getContent();
            resultList.add(Arrays.asList(FileHandling.removePunctuations(row).split("\\s+")));
        }
        return resultList;
    }

    public void initialize(List<News> newsList) {
        List<List<String>> tokenizedCorpus = corpusTokenizer(newsList);
        searchCore = new BM25(tokenizedCorpus);
    }

    public List<Integer> searchFromFile(List<Integer> toSortList, String query, int maxNumberOfResults) {
        List<String> tokenizedQuery = new ArrayList<>(Arrays.asList(FileHandling.removePunctuations(query).split("\\s+")));
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

    public void sortByTitle(List<Integer> ids, List<News> references) {
        Map<Integer, String> map = new HashMap<>();
        for (Integer id : ids) {
            map.put(id, references.get(id).getTitle());
        }

        List<Map.Entry<Integer, String>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        List<Integer> res = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : list) {
            res.add(entry.getKey());
        }

        for (int i = 0; i < ids.size(); i++) {
            ids.set(i, res.get(i));
        }
    }

    public void sortByCreationDate(List<Integer> ids, List<News> references) {
        Map<Integer, Long> map = new HashMap<>();

        for (Integer id : ids) {
            map.put(id, references.get(id).getCreationDate());
        }

        List<Map.Entry<Integer, Long>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        List<Integer> res = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : list) {
            res.add(entry.getKey());
        }

        for (int i = 0; i < ids.size(); i++) {
            ids.set(i, res.get(i));
        }

    }
}