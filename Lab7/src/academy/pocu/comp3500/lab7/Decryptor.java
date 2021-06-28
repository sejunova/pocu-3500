package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private Trie trie = new Trie();
    private HashMap<Integer, HashMap<Integer, Integer>> maxWordLengthsInfo = new HashMap<>();

    public Decryptor(final String[] codeWords) {
        for (String codeWord : codeWords) {
            trie.insert(codeWord);
            final int wordLength = codeWord.length();
            maxWordLengthsInfo.putIfAbsent(wordLength, new HashMap<>());
            maxWordLengthsInfo.get(wordLength).merge(getHash(codeWord), 1, Integer::sum);
        }
    }

    public String[] findCandidates(final String word) {
        final int hash = getHash(word);
        final int wordLength = word.length();
        if (!maxWordLengthsInfo.containsKey(wordLength) || !(maxWordLengthsInfo.get(wordLength).containsKey(hash))) {
            return new String[0];
        }

        int maxCount = maxWordLengthsInfo.get(wordLength).get(hash);
        ArrayList<String> candidates = trie.search(word, maxCount);
        String[] result = new String[candidates.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = candidates.get(i);
        }
        return result;
    }

    private static int getHash(String string) {
        int ret = 0;
        for (char c : string.toCharArray()) {
            ret += c;
        }
        return ret;
    }
}   
