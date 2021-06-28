package academy.pocu.comp3500.lab7;

import java.util.ArrayList;
import java.util.HashMap;

public class Decryptor {
    private Trie trie = new Trie();
    private HashMap<Integer, Integer> maxWordLengthsInfo = new HashMap<>();

    public Decryptor(final String[] codeWords) {
        for (String codeWord : codeWords) {
            trie.insert(codeWord);
            maxWordLengthsInfo.merge(getHash(codeWord), 1, Integer::sum);
        }
    }

    public String[] findCandidates(final String word) {
        int hash = getHash(word);
        if (!maxWordLengthsInfo.containsKey(hash)) {
            return new String[0];
        }

        int maxCount = maxWordLengthsInfo.get(hash);
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
