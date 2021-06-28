package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Trie {
    TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.insertChar(c);
        }
        node.isWord = true;
    }

    public ArrayList<String> search(String word, int maxCount) {
        ArrayList<String> ret = new ArrayList<>();
        HashMap<Character, Integer> counter = new HashMap<>();
        for (char c : word.toCharArray()) {
            counter.merge(c, 1, Integer::sum);
        }
        searchRecursive(ret, new StringBuilder(word.length()), counter, word.length(), root, maxCount);
        return ret;
    }

    private void searchRecursive(ArrayList<String> ret, StringBuilder chars, HashMap<Character, Integer> counter, int inputLength, TrieNode node, int maxCount) {
        if (chars.length() == inputLength) {
            if (node.isWord) {
                ret.add(chars.toString());
            }
            return;
        }

        if (maxCount == ret.size()) {
            return;
        }

        for (Map.Entry<Character, Integer> counterEntry : counter.entrySet()) {
            char c = counterEntry.getKey();
            int count = counterEntry.getValue();
            if (count > 0 && node.children.containsKey(c)) {
                chars.append(c);
                counter.put(c, count - 1);

                searchRecursive(ret, chars, counter, inputLength, node.children.get(c), maxCount);

                chars.setLength(chars.length() - 1);
                counter.put(c, count);
            }
        }
    }
}
