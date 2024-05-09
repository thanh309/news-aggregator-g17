package javasearchengine.core.autocomplete;

import java.util.*;

public class Trie {
    private TrieNode root = new TrieNode();
    protected Map<String, Integer> wordDict;

    public Trie(Map<String, Integer> wordDict) {
        this.wordDict = wordDict;
    }

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }

    public Map<String, Integer> getWordDict() {
        return wordDict;
    }

    public void setWordDict(Map<String, Integer> wordDict) {
        this.wordDict = wordDict;
    }

    public void createTrie() {
        for (Map.Entry<String, Integer> entry : this.wordDict.entrySet()) {
            this.insert(entry.getKey());
        }

    }

    public void insert(String word) {

        if (word == null || word.isEmpty()) {
            return;
        }
        TrieNode node = this.root;
        for (char c : word.toCharArray()) {
            if (!node.getChildren().containsKey(c)) {
                node.getChildren().put(c, new TrieNode());
            }
            node = node.getChildren().get(c);
        }

        node.setCheckIsWord(true);

    }
}
