package javasearchengine.searchengine;

import java.util.*;
import java.io.*;

public class SearchFromFile {

    public static List<String> searchFromFile(String csvFile, String query) throws FileNotFoundException, IOException {
        String[] list = FileHandling.removePuncts(query).split("\\s+");
        List<String> tokenizedQuery = new ArrayList<>();
        for (String x : list) {
            tokenizedQuery.add(x);
        }

        List<String> corpus = new ArrayList<>();
        List<String> columns = new ArrayList<>();

        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        String row;
        boolean check = false;
        while ((row = csvReader.readLine()) != null) {
            if (check == false) {
                for (String x : row.split(",")) {
                    columns.add(x);

                }
                check = true;
            } else {
                row = row.replaceAll(",", " ");
                row = row.replaceAll("\"", "");
                corpus.add(row);
            }
        }
        csvReader.close();

        List<List<String>> tokenizedCorpus = new ArrayList<>();
        for (String doc : corpus) {
            String[] x = FileHandling.removePuncts(doc).split("\\s+");
            List<String> y = new ArrayList<>();
            for (String word : x) {
                y.add(word);
            }
            tokenizedCorpus.add(y);
        }

        BM25Okapi bm25 = new BM25Okapi(tokenizedCorpus);

        List<Double> scores = bm25.getScores(tokenizedQuery);

        int cnt = 0;
        for (double score : scores) {
            if (score != 0.0) {
                cnt++;
            }
        }

        List<String> lines1 = bm25.getTopN(tokenizedQuery, corpus, 10);

        return lines1;

    }

}

