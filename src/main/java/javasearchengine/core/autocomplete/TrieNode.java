package javasearchengine.core.autocomplete;

import java.util.*;

public class TrieNode {
    private Map<Character, TrieNode> children;
    private boolean checkIsWord;

    public TrieNode() {
        children = new HashMap<>();
        checkIsWord = false;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, TrieNode> children) {
        this.children = children;
    }

    public boolean getCheckIsWord() {
        return checkIsWord;
    }

    public void setCheckIsWord(boolean checkIsWord) {
        this.checkIsWord = checkIsWord;
    }

}

