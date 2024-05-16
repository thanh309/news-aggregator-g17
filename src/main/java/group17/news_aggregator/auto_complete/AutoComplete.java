package group17.news_aggregator.auto_complete;

import java.util.*;

public class AutoComplete extends Trie {

    public AutoComplete(List<String> words) {
        super(words);

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

    public Map<List<String>, Integer> storeSuggestion(String word) {

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

        int cnt = 0;
        for (String w : listSuggest) {
            ans.add(w);
            cnt++;
            if (cnt == 5) {
                break;
            }
        }
        res.put(ans, 1);
        return res;

    }

    public List<String> getSuggestion(String word) {

        Map<List<String>, Integer> storage = storeSuggestion(word);

        List<String> ans = new ArrayList<>();

        for (Map.Entry<List<String>, Integer> entry : storage.entrySet()) {

            if (entry.getValue() == -1) {

                return ans;

            } else if (entry.getValue() == 0) {

                return ans;

            } else {

                for (String w : entry.getKey()) {

                    ans.add(w);
                }
            }
        }
        return ans;
    }

}