package javasearchengine.tester;

import javasearchengine.core.autocomplete.AutoComplete;

import java.util.*;


public class TestAutoComplete {

    public static Map<String, Integer> createAWordsDict(List<String> wordList) {
        Map<String, Integer> wordDict = new HashMap<>();
        for (String word : wordList) {
            if (!wordDict.containsKey(word)) {
                wordDict.put(word, 1);
            } else {
                wordDict.put(word, wordDict.get(word) + 1);
            }
        }
        return wordDict;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> words = List.of("hello", "dog", "hell", "cat", "a", "hel", "help", "helps", "helping", "heo", "heo");
        Map<String, Integer> wordDict = createAWordsDict(words);

        System.out.println("Enter query here: ");
        String query = sc.nextLine();

        AutoComplete t = new AutoComplete(wordDict);

        t.createTrie();

        Map<List<String>, Integer> comp = t.getSuggestion(query);

        for (Map.Entry<List<String>, Integer> entry : comp.entrySet()) {
            if (entry.getValue() == -1) {
                System.out.println("No other strings found with this prefix");
            } else if (entry.getValue() == 0) {
                System.out.println("No string found with this prefix");
            } else {
                for (String w : entry.getKey()) {
                    System.out.println(w);
                }
            }
        }

//		 System.out.println(t.getRoot().getChildren().get('h').getChildren().get('e').getChildren().get('l').getChildren());
        sc.close();
    }
}

