package group17.news_aggregator.auto_complete;

import java.util.List;

public class Trie {
    public Trie(List<String> words) {
        this.words = words;
    }

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    protected List<String> words;

    private TrieNode root = new TrieNode();

    public void createTrie() {
        for (String w : words) {
            this.insert(w.toLowerCase());
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
