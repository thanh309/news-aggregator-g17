package javasearchengine.core.searchengine;

import java.util.*;

public class BM25 {
    private int corpusSize; // here is the nubmber of documents in corpus
    private double averageLength; // here is the average length of all documents
    private List<Map<String, Integer>> termFrequency;
    private Map<String, Double> inverseDocumentFrequency;
    private List<Integer> listDocumentLength;

    public int getCorpusSize() {
        return corpusSize;
    }

    public void setCorpusSize(int corpusSize) {
        this.corpusSize = corpusSize;
    }

    public double getAverageLength() {
        return averageLength;
    }

    public void setAverageLength(double averageLength) {
        this.averageLength = averageLength;
    }

    public List<Map<String, Integer>> getTermFrequency() {
        return termFrequency;
    }

    public void setTermFrequency(List<Map<String, Integer>> termFrequency) {
        this.termFrequency = termFrequency;
    }

    public Map<String, Double> getInverseDocumentFrequency() {
        return inverseDocumentFrequency;
    }

    public void setInverseDocumentFrequency(Map<String, Double> inverseDocumentFrequency) {
        this.inverseDocumentFrequency = inverseDocumentFrequency;
    }

    public List<Integer> getListDocumentLength() {
        return listDocumentLength;
    }

    public void setListDocumentLength(List<Integer> listDocumentLength) {
        this.listDocumentLength = listDocumentLength;
    }

    public BM25(List<List<String>> corpus) {
        this.corpusSize = 0;
        this.averageLength = 0;
        this.termFrequency = new ArrayList<>();
        this.inverseDocumentFrequency = new HashMap<>();
        this.listDocumentLength = new ArrayList<>();

        Map<String, Integer> documentFrequency = this.initialize(corpus);
        this.calcIdf(documentFrequency);
    }

    public Map<String, Integer> initialize(List<List<String>> corpus) {
        Map<String, Integer> documentFrequency = new HashMap<>();
        long lengthOfDocs = 0;

        for (List<String> document : corpus) {
            listDocumentLength.add(document.size());
            lengthOfDocs += document.size();

            Map<String, Integer> frequencies = new HashMap<>();
            for (String word : document) {
                if (!frequencies.containsKey(word)) {
                    frequencies.put(word, 1);
                } else {
                    frequencies.put(word, frequencies.get(word) + 1);
                }
            }

            this.termFrequency.add(frequencies);

            for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
                String word = entry.getKey();
                if (documentFrequency.containsKey(word)) {
                    documentFrequency.put(word, documentFrequency.get(word) + 1);
                } else {
                    documentFrequency.put(word, 1);
                }
            }
            this.corpusSize++;

        }

        this.averageLength = (double) lengthOfDocs / this.getCorpusSize();

        return documentFrequency;
    }

    public void calcIdf(Map<String, Integer> documentFrequency) {
        throw new UnsupportedOperationException("This method is not implemented");
    }

    public List<Double> getScores(List<String> query) {
        throw new UnsupportedOperationException("This method is not implemented");

    }

    public List<String> getTopN(List<String> query, List<String> documents, int n) {
        assert this.corpusSize == documents.size() : "It's never occurr!!";
        List<Double> scores = this.getScores(query);
        Integer[] indices = new Integer[scores.size()];

        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                // TODO Auto-generated method stub
                if (scores.get(o1) - scores.get(o2) == 0) {
                    return 0;
                } else if (scores.get(o1) - scores.get(o2) < 0) {
                    return 1;
                }
                return -1;

            }

        });

        List<String> topN = new ArrayList<>();
        for (int i = 0; i < Math.min(n, indices.length); i++) {
            topN.add(documents.get(indices[i]));
        }

        return topN;
    }

}
