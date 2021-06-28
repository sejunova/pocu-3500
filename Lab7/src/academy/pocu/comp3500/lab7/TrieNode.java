package academy.pocu.comp3500.lab7;

import java.util.HashMap;

public class TrieNode {
    HashMap<Character, TrieNode> children = new HashMap<>();
    boolean isWord = false;

    TrieNode insertChar(char c) {
        children.putIfAbsent(c, new TrieNode());
        return children.get(c);
    }
}
