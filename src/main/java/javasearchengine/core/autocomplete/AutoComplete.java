package javasearchengine.core.autocomplete;

import java.util.*;

public class AutoComplete extends Trie {

    public AutoComplete(Map<String, Integer> wordDict) {
        super(wordDict);

    }

    public List<String> autoComplete(TrieNode node, String word) {
        List<String> results = new ArrayList<>();
        if (node.getCheckIsWord() == true) {
            results.add(word);
        }
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            for (String newWord : this.autoComplete(entry.getValue(), word + entry.getKey())) {

                results.add(newWord);

            }
        }
        return results;
    }

    public int calcScore(String word) {
        return super.getWordDict().get(word);
    }

    public Map<List<String>, Integer> getSuggestion(String word) {

        Map<List<String>, Integer> res = new HashMap<>();

        List<String> ans = new ArrayList<>();

        TrieNode node = super.getRoot();

        for (char c : word.toCharArray()) {

            if (!node.getChildren().containsKey(c)) {
                res.put(ans, 0);
                return res;
            }

            node = node.getChildren().get(c);
        }

        if (node.getChildren() == null) {
            res.put(ans, -1);
            return res;
        }

        List<String> listSuggest = this.autoComplete(node, word);

        // we need at most 10-highest-score suggestions
        Collections.sort(listSuggest, new Comparator<String>() {

            @Override
            public int compare(String word1, String word2) {
                // TODO Auto-generated method stub
                return -calcScore(word1) + calcScore(word2);
            }

        });

        int cnt = 0;
        for (String w : listSuggest) {
            ans.add(w);
            cnt++;
            if (cnt == 10) {
                break;
            }
        }
        res.put(ans, 1);
        return res;

    }

}