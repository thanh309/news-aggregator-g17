package javasearchengine.core.searchengine;

import java.util.*;

public class BM25 {


    private static final double K1 = 1.5;

    private static final double B = 0.75;

    private static final double EPSILON = 0.25;

    private final int corpusSize; // here is the number of documents in corpus

    private double averageLength = 0; // here is the average length of all documents

    private final List<Map<String, Integer>> termFrequency = new ArrayList<>();

    private final Map<String, Double> inverseDocumentFrequency = new HashMap<>();

    private final List<Integer> listDocumentLength = new ArrayList<>();


    public BM25(List<List<String>> tokenizedCorpus) {
        corpusSize = tokenizedCorpus.size();
        Map<String, Integer> documentFrequency = initialize(tokenizedCorpus);
        calcIdf(documentFrequency);
    }


    public Map<String, Integer> initialize(List<List<String>> tokenizedCorpus) {
        Map<String, Integer> documentFrequency = new HashMap<>();
        long lengthOfDocs = 0;

        for (List<String> document : tokenizedCorpus) {
            listDocumentLength.add(document.size());
            lengthOfDocs += document.size();

            Map<String, Integer> frequencies = new HashMap<>();
            for (String word : document) {
                if (frequencies.containsKey(word)) {
                    frequencies.put(word, frequencies.get(word) + 1);
                } else {
                    frequencies.put(word, 1);
                }
            }

            termFrequency.add(frequencies);

            for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
                String word = entry.getKey();
                if (documentFrequency.containsKey(word)) {
                    documentFrequency.put(word, documentFrequency.get(word) + 1);
                } else {
                    documentFrequency.put(word, 1);
                }
            }
        }

        this.averageLength = (double) lengthOfDocs / corpusSize;

        return documentFrequency;
    }

    public void calcIdf(Map<String, Integer> documentFrequency) {
        double inverseDocumentFrequencySum = 0.0;
        List<String> negativeInverseDocumentFrequencies = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : documentFrequency.entrySet()) {
            double idf = Math.log(corpusSize - entry.getValue() + 0.5) - Math.log(entry.getValue() + 0.5);
            inverseDocumentFrequency.put(entry.getKey(), idf);
            inverseDocumentFrequencySum += idf;
            if (idf < 0) {
                negativeInverseDocumentFrequencies.add(entry.getKey());
            }
        }

        double averageInverseDocumentFrequency = inverseDocumentFrequencySum
                / inverseDocumentFrequency.size();

        double eps = EPSILON * averageInverseDocumentFrequency;

        for (String word : negativeInverseDocumentFrequencies) {
            inverseDocumentFrequency.put(word, eps);
        }
    }

    public List<Double> getScores(List<String> tokenizedQuery) {
        List<Double> scores = new ArrayList<>();
        for (int i = 0; i < corpusSize; i++) {
            scores.add(0.0);
        }

        for (String q : tokenizedQuery) {
            List<Double> q_freq = new ArrayList<>();
            for (Map<String, Integer> stringIntegerMap : termFrequency) {
                if (stringIntegerMap.containsKey(q)) {
                    q_freq.add((double) stringIntegerMap.get(q));
                } else {
                    q_freq.add(0.0);
                }
            }
            if (inverseDocumentFrequency.containsKey(q)) {
                for (int i = 0; i < q_freq.size(); i++) {
                    double x = inverseDocumentFrequency.get(q) * q_freq.get(i) * (K1 + 1) / (q_freq.get(i)
                            + K1 * (1 - B + B * listDocumentLength.get(i) / averageLength));
                    scores.set(i, x);
                }
            }

        }
        return scores;
    }

    public List<Integer> getTopNIndex(List<String> query, List<Integer> toSortList, int maxNumberOfResults) {
        assert corpusSize == toSortList.size() : "It never occurs!!";
        List<Double> scores = getScores(query);
        List<Integer> indices2 = new ArrayList<>();

        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > 0) {
                indices2.add(i);
            }
        }

        indices2.sort((o1, o2) -> {
            if (scores.get(o1) - scores.get(o2) == 0) return 0;
            else if (scores.get(o1) - scores.get(o2) < 0) return 1;
            return -1;
        });


        List<Integer> topN = new ArrayList<>();
        for (int i = 0; i < Math.min(maxNumberOfResults, indices2.size()); i++) {
            topN.add(toSortList.get(indices2.get(i)));
        }

        return topN;
    }

}
