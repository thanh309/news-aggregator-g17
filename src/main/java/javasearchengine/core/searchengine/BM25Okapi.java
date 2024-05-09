package javasearchengine.core.searchengine;

import java.util.*;

public class BM25Okapi extends BM25 {

    private final double K1 = 1.5;
    private final double B = 0.75;
    private final double EPSILON = 0.25;

    public BM25Okapi(List<List<String>> corpus) {
        super(corpus);
    }

    @Override
    public void calcIdf(Map<String, Integer> documentFrequency) {
        // TODO Auto-generated method stub
        double inverseDocumentFrequencySum = 0.0;
        List<String> negativeInverseDocumentFrequencies = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : documentFrequency.entrySet()) {
            double idf = Math.log(super.getCorpusSize() - entry.getValue() + 0.5) - Math.log(entry.getValue() + 0.5);
            super.getInverseDocumentFrequency().put(entry.getKey(), idf);
            inverseDocumentFrequencySum += idf;
            if (idf < 0) {
                negativeInverseDocumentFrequencies.add(entry.getKey());
            }
        }

        double averageInverseDocumentFrequency = (double) inverseDocumentFrequencySum
                / super.getInverseDocumentFrequency().size();

        double eps = this.EPSILON * averageInverseDocumentFrequency;

        for (String word : negativeInverseDocumentFrequencies) {
            super.getInverseDocumentFrequency().put(word, eps);
        }

    }

    @Override
    public List<Double> getScores(List<String> query) {
        // TODO Auto-generated method stub
        List<Double> scores = new ArrayList<>();
        for (int i = 0; i < super.getCorpusSize(); i++) {
            scores.add(0.0);
        }

        for (String q : query) {
            List<Double> q_freq = new ArrayList<>();
            for (int i = 0; i < super.getTermFrequency().size(); i++) {
                if (super.getTermFrequency().get(i).containsKey(q)) {
                    q_freq.add((double) super.getTermFrequency().get(i).get(q));
                } else {
                    q_freq.add(0.0);
                }
            }
            if (super.getInverseDocumentFrequency().containsKey(q)) {
                for (int i = 0; i < q_freq.size(); i++) {
                    double x = super.getInverseDocumentFrequency().get(q) * q_freq.get(i) * (this.K1 + 1)/(q_freq.get(i) + this.K1*(1 - this.B + this.B * super.getListDocumentLength().get(i)/super.getAverageLength()));
                    scores.set(i, x);
                }
            }

        }
        return scores;
    }

}